public class Employee {
    private String employeeId;
    private String name;
    private String designation;
    private double basicPay;
    private double hra;
    private double da;
    private double conveyance;

    private int workingDays;
    private int overtimeHours;
    private double investment80C;

    public Employee(String employeeId,String name,String designation,double basicPay,double hra,double da,double conveyance,
                    int workingDays,int overtimeHours,double investment80C){
        this.employeeId = employeeId;
        this.name = name;
        this.designation = designation;
        this.basicPay = basicPay;
        this.hra = hra;
        this.da = da;
        this.conveyance = conveyance;
        this.workingDays = workingDays;
        this.overtimeHours = overtimeHours;
        this.investment80C = investment80C;

    }
    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getDesignation() {
        return designation;
    }
    public double getBasicPay() {
        return basicPay;
    }

    public double getHra() {
        return hra;
    }

    public double getDa() {
        return da;
    }
    public double getConveyance() {
        return conveyance;
    }

    public int getWorkingDays() {
        return workingDays;
    }

    public int getOvertimeHours() {
        return overtimeHours;
    }
    public double getInvestment80C() {
        return investment80C;
    }
}

