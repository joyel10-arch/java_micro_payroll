public class IncomeTaxCalculator {
    //ANNUAL GROSS INCOME
    public double calculateAnnualIncome(Employee emp) {

        double monthlyGross =
                emp.getBasicPay()
                        + emp.getHra()
                        + emp.getDa()
                        + emp.getConveyance();

        return monthlyGross * 12;
    }
    public double calculateTaxableIncome(Employee emp) {

        double annualIncome = calculateAnnualIncome(emp);

        double investment = emp.getInvestment80C();

        double deduction = Math.min(investment, 150000);

        return Math.max(0, annualIncome - deduction);
    }

    // Calculate illustrative income tax
    public double calculateIncomeTax(Employee emp) {

        double taxableIncome = calculateTaxableIncome(emp);
        double tax = 0;

        if (taxableIncome <= 300000) {
            tax = 0;
        } else if (taxableIncome <= 600000) {
            tax = (taxableIncome - 300000) * 0.05;
        } else if (taxableIncome <= 900000) {
            tax = 15000 + (taxableIncome - 600000) * 0.10;
        } else {
            tax = 45000 + (taxableIncome - 900000) * 0.15;
        }

        return tax;
    }
}
