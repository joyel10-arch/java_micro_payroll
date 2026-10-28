import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
public class PayslipGenerator {

    public void generatePayslip(
            Employee emp,
            PayrollCalculator calculator,
            IncomeTaxCalculator taxCalculator) {

        double gross = calculator.calculateGrossSalary(emp);
        double pf = calculator.calculatePF(emp);

        double professionalTax =
                calculator.calculateProfessionalTax(emp);

        double salaryAfterDeductions =
                calculator.SalaryAfterDeductions(emp);

        double annualIncome =
                taxCalculator.calculateAnnualIncome(emp);

        double taxableIncome =
                taxCalculator.calculateTaxableIncome(emp);

        double annualIncomeTax =
                taxCalculator.calculateIncomeTax(emp);

        double monthlyTDS = annualIncomeTax / 12;

        double netSalary =
                calculator.calculateNetSalary(emp, monthlyTDS);




        System.out.println("\n========================================");
        System.out.println("             EMPLOYEE PAYSLIP");
        System.out.println("========================================");

        System.out.println("Employee ID   : " + emp.getEmployeeId());
        System.out.println("Name          : " + emp.getName());
        System.out.println("Designation   : " + emp.getDesignation());

        System.out.println("----------------------------------------");
        System.out.printf("Basic Pay     : %.2f%n", emp.getBasicPay());
        System.out.printf("HRA           : %.2f%n", emp.getHra());
        System.out.printf("DA            : %.2f%n", emp.getDa());
        System.out.printf("Conveyance    : %.2f%n", emp.getConveyance());
        System.out.printf("Gross Salary  : %.2f%n", gross);

        System.out.println("----------------------------------------");
        System.out.printf("PF Deduction  : %.2f%n", pf);
        System.out.printf("Professional Tax: %.2f%n", professionalTax);
        System.out.printf("Before TDS    : %.2f%n", salaryAfterDeductions);

        System.out.println("\n--- ANNUAL TAX DETAILS ---");
        System.out.printf("Annual Income : %.2f%n", annualIncome);
        System.out.printf("Taxable Income: %.2f%n", taxableIncome);
        System.out.printf("Annual Tax    : %.2f%n", annualIncomeTax);
        System.out.printf("Monthly TDS   : %.2f%n", monthlyTDS);

        System.out.println("----------------------------------------");
        System.out.printf("TAKE-HOME PAY : %.2f%n", netSalary);
        System.out.println("========================================");

    }

    public void exportPayslip(Employee emp,
                              PayrollCalculator calculator,
                              IncomeTaxCalculator taxCalculator)
            throws IOException {

        double gross = calculator.calculateGrossSalary(emp);
        double pf = calculator.calculatePF(emp);
        double professionalTax =
                calculator.calculateProfessionalTax(emp);

        double annualIncome =
                taxCalculator.calculateAnnualIncome(emp);
        double taxableIncome =
                taxCalculator.calculateTaxableIncome(emp);
        double annualTax =
                taxCalculator.calculateIncomeTax(emp);

        double monthlyTDS = annualTax / 12;
        double netSalary =
                calculator.calculateNetSalary(emp, monthlyTDS);

        String fileName = "payslip_" + emp.getEmployeeId() + ".txt";

        try (PrintWriter writer =
                     new PrintWriter(new FileWriter(fileName))) {

            writer.println("========================================");
            writer.println("             EMPLOYEE PAYSLIP");
            writer.println("========================================");

            writer.println("Employee ID : " + emp.getEmployeeId());
            writer.println("Name        : " + emp.getName());
            writer.println("Designation : " + emp.getDesignation());

            writer.println("----------------------------------------");
            writer.printf("Basic Pay   : Rs. %.2f%n", emp.getBasicPay());
            writer.printf("HRA         : Rs. %.2f%n", emp.getHra());
            writer.printf("DA          : Rs. %.2f%n", emp.getDa());
            writer.printf("Conveyance  : Rs. %.2f%n", emp.getConveyance());
            writer.printf("Gross Salary: Rs. %.2f%n", gross);

            writer.println("----------------------------------------");
            writer.printf("PF          : Rs. %.2f%n", pf);
            writer.printf("Professional Tax: Rs. %.2f%n",
                    professionalTax);
            writer.printf("Monthly TDS : Rs. %.2f%n", monthlyTDS);

            writer.println("----------------------------------------");
            writer.printf("Annual Income : Rs. %.2f%n", annualIncome);
            writer.printf("Taxable Income: Rs. %.2f%n", taxableIncome);
            writer.printf("Annual Tax    : Rs. %.2f%n", annualTax);

            writer.println("----------------------------------------");
            writer.printf("TAKE-HOME PAY: Rs. %.2f%n", netSalary);
            writer.println("========================================");
        }

        System.out.println("Payslip exported: " + fileName);
    }

}
