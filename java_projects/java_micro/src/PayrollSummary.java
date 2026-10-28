
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class PayrollSummary {

    public void generateSummary(List<Employee> employees,
                                PayrollCalculator calculator,
                                IncomeTaxCalculator taxCalculator) {

        double totalGrossSalary = 0;
        double totalPF = 0;
        double totalProfessionalTax = 0;
        double totalIncomeTax = 0;
        double totalNetSalary = 0;

        for (Employee emp : employees) {

            double gross = calculator.calculateGrossSalary(emp);
            double pf = calculator.calculatePF(emp);

            double professionalTax =
                    calculator.calculateProfessionalTax(emp);

            double annualTax =
                    taxCalculator.calculateIncomeTax(emp);

            double monthlyTDS = annualTax / 12;

            double netSalary =
                    calculator.calculateNetSalary(emp, monthlyTDS);

            totalGrossSalary += gross;
            totalPF += pf;
            totalProfessionalTax += professionalTax;
            totalIncomeTax += monthlyTDS;
            totalNetSalary += netSalary;
        }

        System.out.println("\n======= COMPANY PAYROLL SUMMARY =======");

        System.out.println("Total Employees: " + employees.size());

        System.out.printf("Total Monthly Gross Salary: %.2f%n",
                totalGrossSalary);

        System.out.printf("Total Monthly PF: %.2f%n",
                totalPF);

        System.out.printf("Total Monthly Professional Tax: %.2f%n",
                totalProfessionalTax);

        System.out.printf("Total Monthly TDS: %.2f%n",
                totalIncomeTax);

        System.out.printf("Total Monthly Net Salary: %.2f%n",
                totalNetSalary);

        System.out.println("=======================================");
    }

    public void exportSummary(
            List<Employee> employees,
            PayrollCalculator calculator,
            IncomeTaxCalculator taxCalculator)
            throws IOException {

        double totalGrossSalary = 0;
        double totalPF = 0;
        double totalProfessionalTax = 0;
        double totalTDS = 0;
        double totalNetSalary = 0;

        for (Employee emp : employees) {

            double gross = calculator.calculateGrossSalary(emp);
            double pf = calculator.calculatePF(emp);

            double professionalTax =
                    calculator.calculateProfessionalTax(emp);

            double annualTax =
                    taxCalculator.calculateIncomeTax(emp);

            double monthlyTDS = annualTax / 12;

            double netSalary =
                    calculator.calculateNetSalary(emp, monthlyTDS);

            totalGrossSalary += gross;
            totalPF += pf;
            totalProfessionalTax += professionalTax;
            totalTDS += monthlyTDS;
            totalNetSalary += netSalary;
        }

        try (PrintWriter writer =
                     new PrintWriter(new FileWriter("payroll_summary.txt"))) {

            writer.println("======= COMPANY PAYROLL SUMMARY =======");
            writer.println("Total Employees: " + employees.size());

            writer.printf("Total Monthly Gross Salary: Rs. %.2f%n",
                    totalGrossSalary);
            writer.printf("Total Monthly PF: Rs. %.2f%n", totalPF);
            writer.printf("Total Monthly Professional Tax: Rs. %.2f%n",
                    totalProfessionalTax);
            writer.printf("Total Monthly TDS: Rs. %.2f%n", totalTDS);
            writer.printf("Total Monthly Net Salary: Rs. %.2f%n",
                    totalNetSalary);

            writer.println("=======================================");
        }

        System.out.println("Company summary exported: payroll_summary.txt");
    }


}
