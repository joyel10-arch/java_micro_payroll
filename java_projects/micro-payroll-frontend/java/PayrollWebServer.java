import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/** Same-origin UI adapter. Employee storage stays in the browser. */
public class PayrollWebServer {
    public static void main(String[] args) throws Exception {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;
        Path root = Paths.get("dist").toAbsolutePath().normalize();
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", port), 0);
        server.createContext("/api/health", ex -> respond(ex, 200, "application/json", "{\"engine\":\"java\"}"));
        server.createContext("/api/calculate", ex -> {
            if (!ex.getRequestMethod().equals("POST")) { respond(ex,405,"application/json","{\"error\":\"Use POST\"}"); return; }
            try {
                byte[] bytes = ex.getRequestBody().readNBytes(16385);
                if (bytes.length > 16384) throw new IllegalArgumentException();
                Map<String,String> form = new HashMap<>();
                for (String entry : new String(bytes, StandardCharsets.UTF_8).split("&")) {
                    String[] pair = entry.split("=", 2);
                    if (pair.length == 2) form.put(URLDecoder.decode(pair[0], StandardCharsets.UTF_8), URLDecoder.decode(pair[1], StandardCharsets.UTF_8));
                }
                for (String key : List.of("employeeId","name","designation")) if (form.getOrDefault(key, "").isBlank()) throw new IllegalArgumentException();
                Employee emp = new Employee(form.get("employeeId"), form.get("name"), form.get("designation"), number(form,"basicPay"),number(form,"hra"),number(form,"da"),number(form,"conveyance"),Integer.parseInt(form.get("workingDays")),Integer.parseInt(form.get("overtimeHours")),number(form,"investment80C"));
                if (emp.getWorkingDays()<0 || emp.getWorkingDays()>31 || emp.getOvertimeHours()<0) throw new IllegalArgumentException();
                PayrollCalculator calc = new PayrollCalculator();
                IncomeTaxCalculator tax = new IncomeTaxCalculator();
                double annualTax = tax.calculateIncomeTax(emp), tds = annualTax / 12;
                String json = String.format(Locale.ROOT, "{\"gross\":%.10f,\"pf\":%.10f,\"professionalTax\":%.10f,\"annualIncome\":%.10f,\"taxableIncome\":%.10f,\"annualTax\":%.10f,\"monthlyTDS\":%.10f,\"net\":%.10f}", calc.calculateGrossSalary(emp),calc.calculatePF(emp),calc.calculateProfessionalTax(emp),tax.calculateAnnualIncome(emp),tax.calculateTaxableIncome(emp),annualTax,tds,calc.calculateNetSalary(emp,tds));
                respond(ex,200,"application/json",json);
            } catch (IllegalArgumentException | NullPointerException error) { respond(ex,400,"application/json","{\"error\":\"Invalid employee data\"}"); }
        });
        server.createContext("/", ex -> {
            if (!ex.getRequestMethod().equals("GET") && !ex.getRequestMethod().equals("HEAD")) { respond(ex,405,"text/plain","Use GET"); return; }
            String request = ex.getRequestURI().getPath();
            Path file = root.resolve(request.equals("/") ? "index.html" : request.substring(1)).normalize();
            if (!file.startsWith(root) || !Files.isRegularFile(file)) { respond(ex,404,"text/plain","Not found"); return; }
            String name=file.toString();
            String mime=name.endsWith(".html")?"text/html; charset=utf-8":name.endsWith(".css")?"text/css; charset=utf-8":name.endsWith(".js")?"text/javascript; charset=utf-8":"application/octet-stream";
            byte[] body=Files.readAllBytes(file);
            ex.getResponseHeaders().set("Content-Type",mime);
            ex.getResponseHeaders().set("X-Content-Type-Options","nosniff");
            if (ex.getRequestMethod().equals("HEAD")) { ex.sendResponseHeaders(200,-1); } else { ex.sendResponseHeaders(200,body.length);ex.getResponseBody().write(body); }ex.close();
        });
        server.start();
        System.out.println("Micro Payroll running at http://localhost:" + port);
    }
    static double number(Map<String,String> values,String key) { double value=Double.parseDouble(values.get(key));if(!Double.isFinite(value)||value<0||value>1e12)throw new IllegalArgumentException();return value; }
    static void respond(HttpExchange ex,int code,String type,String body) throws java.io.IOException { byte[] bytes=body.getBytes(StandardCharsets.UTF_8);ex.getResponseHeaders().set("Content-Type",type+"; charset=utf-8");ex.getResponseHeaders().set("X-Content-Type-Options","nosniff");ex.sendResponseHeaders(code,bytes.length);ex.getResponseBody().write(bytes);ex.close(); }
}
