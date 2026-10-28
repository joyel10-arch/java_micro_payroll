import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;


public class Main {
        public static void main(String[] ppy) throws Exception {

            EmployeeFileReader reader = new EmployeeFileReader();

            List<Employee> employees;

            try {
                employees = reader.readEmployees(
                        "E:/java_projects/EmployeePayrollSystem/data/employees.csv"
                );
            } catch (java.io.IOException e) {
                System.out.println("Error: Could not read the employee CSV file.");
                System.out.println("Please check the file path and try again.");
                return;
            }

            if (employees.isEmpty()) {
                System.out.println("No valid employee records found in the CSV file.");
                return;
            }

            PayrollCalculator calculator = new PayrollCalculator();
            IncomeTaxCalculator taxCalculator = new IncomeTaxCalculator();
            PayslipGenerator payslip = new PayslipGenerator();
            EmployeeValidator validator = new EmployeeValidator();
            List<Employee> validEmployees = new ArrayList<>();
            Set<String> employeeIds = new HashSet<>();
            for (var emp : employees) {

                if (!validator.validate(emp)) {
                    continue;
                }
                String employeeId = emp.getEmployeeId();

                if (!employeeIds.add(employeeId)) {
                    System.out.println(
                            "Duplicate employee ID skipped: " + employeeId
                    );
                    continue;
                }
                validEmployees.add(emp);

                payslip.generatePayslip(
                        emp,
                        calculator,
                        taxCalculator
                );
                payslip.generatePayslip(
                        emp,
                        calculator,
                        taxCalculator
                );

                payslip.exportPayslip(
                        emp,
                        calculator,
                        taxCalculator
                );

            }
            PayrollSummary summary = new PayrollSummary();

            summary.generateSummary(
                    validEmployees,
                    calculator,
                    taxCalculator
            );
            summary.exportSummary(
                    validEmployees,
                    calculator,
                    taxCalculator
            );

        }
    }
