
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeFileReader {

    public List<Employee> readEmployees(String filePath)
            throws IOException {

        List<Employee> employees = new ArrayList<>();

        try (BufferedReader br =
                     new BufferedReader(new FileReader(filePath))) {

            String line;

            while ((line = br.readLine()) != null) {

                // Skip blank lines
                if (line.isBlank()) {
                    continue;
                }

                String[] data = line.split(",");

                // Skip the CSV header
                if (data[0].trim().equalsIgnoreCase("employeeId")) {
                    continue;
                }

                // Check that the row contains 10 columns
                if (data.length != 10) {
                    System.out.println("Invalid CSV row: " + line);
                    continue;
                }
                try {

                    Employee emp = new Employee(
                            data[0].trim(),
                            data[1].trim(),
                            data[2].trim(),
                            Double.parseDouble(data[3].trim()),
                            Double.parseDouble(data[4].trim()),
                            Double.parseDouble(data[5].trim()),
                            Double.parseDouble(data[6].trim()),
                            Integer.parseInt(data[7].trim()),
                            Integer.parseInt(data[8].trim()),
                            Double.parseDouble(data[9].trim())
                    );

                    employees.add(emp);
                }catch (NumberFormatException e){
                    System.out.println("Invalid numeric data in CSV row: " + line);

                }
            }
        }

        return employees;
    }
}
