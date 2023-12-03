/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wp2c2project.classes;

/**
 *
 * @author jejer
 */
public class Employee {

    private int empId;
    private String name;
    private String department;
    private int rate;
    private String shiftStart;
    private String shiftEnd;
    private String status;
    private String tin;
    private int philHealth;
    private String sss;
    private int pagibig;
    private String taxStatus;

    // Constructor
    public Employee(int empId, String name, String department, int rate, String shiftStart, String shiftEnd,
            String status, String tin, int philHealth, String sss, int pagibig, String taxStatus) {
        this.empId = empId;
        this.name = name;
        this.department = department;
        this.rate = rate;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.status = status;
        this.tin = tin;
        this.philHealth = philHealth;
        this.sss = sss;
        this.pagibig = pagibig;
        this.taxStatus = taxStatus;
    }

    // Setters
    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setShiftStart(String shiftStart) {
        this.shiftStart = shiftStart;
    }

    public void setShiftEnd(String shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public void setPhilHealth(int philHealth) {
        this.philHealth = philHealth;
    }

    public void setSss(String sss) {
        this.sss = sss;
    }

    public void setPagibig(int pagibig) {
        this.pagibig = pagibig;
    }

    public void setTaxStatus(String taxStatus) {
        this.taxStatus = taxStatus;
    }

    // Getters
    public int getEmpId() {
        return empId;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public int getRate() {
        return rate;
    }

    public String getShiftStart() {
        return shiftStart;
    }

    public String getShiftEnd() {
        return shiftEnd;
    }

    public String getStatus() {
        return status;
    }

    public String getTin() {
        return tin;
    }

    public int getPhilHealth() {
        return philHealth;
    }

    public String getSss() {
        return sss;
    }

    public int getPagibig() {
        return pagibig;
    }

    public String getTaxStatus() {
        return taxStatus;
    }
}
