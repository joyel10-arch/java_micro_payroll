
public class EmployeeValidator {

    public boolean validate(Employee emp) {

        // 1. Validate employee ID
        if (emp.getEmployeeId() == null
                || emp.getEmployeeId().isBlank()) {
            System.out.println("Invalid employee ID.");
            return false;
        }

        // 2. Validate employee name
        if (emp.getName() == null
                || emp.getName().isBlank()) {
            System.out.println(
                    "Invalid employee name: " + emp.getEmployeeId()
            );
            return false;
        }

        // 3. Validate salary components
        if (emp.getBasicPay() < 0
                || emp.getHra() < 0
                || emp.getDa() < 0
                || emp.getConveyance() < 0
                || emp.getInvestment80C() < 0) {

            System.out.println(
                    "Negative salary or investment for: "
                            + emp.getEmployeeId()
            );
            return false;
        }

        // 4. Validate working days
        if (emp.getWorkingDays() < 0
                || emp.getWorkingDays() > 31) {

            System.out.println(
                    "Invalid working days for: "
                            + emp.getEmployeeId()
            );
            return false;
        }

        // 5. Validate overtime hours
        if (emp.getOvertimeHours() < 0) {

            System.out.println(
                    "Invalid overtime hours for: "
                            + emp.getEmployeeId()
            );
            return false;
        }

        return true;
    }
}
