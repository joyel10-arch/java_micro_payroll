public class PayrollCalculator {
    public double calculateGrossSalary(Employee emp){
        return emp.getBasicPay()
                + emp.getHra()
                + emp.getDa()
                + emp.getConveyance();

    }
    public double calculatePF(Employee emp){
        return emp.getBasicPay() * 0.12;

    }
    public double calculateProfessionalTax(Employee emp){
        return 200.0;
    }
    public double SalaryAfterDeductions(Employee emp){
        double grossSalary = calculateGrossSalary(emp);
        double pf = calculatePF(emp);
        double professionalTax = calculateProfessionalTax(emp);

        double salaryAfterDeductions = grossSalary - pf - professionalTax;

        return salaryAfterDeductions;
    }
    public double calculateNetSalary(Employee emp,double monthlyTDS){
        double grossSalary = calculateGrossSalary(emp);
        double pf = calculatePF(emp);
        double professionalTax = calculateProfessionalTax(emp);

        return grossSalary - pf - professionalTax - monthlyTDS;

    }

}
