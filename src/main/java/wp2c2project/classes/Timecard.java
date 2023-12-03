/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wp2c2project.classes;

/**
 *
 * @author jejer
 */
public class Timecard {

    private int dateId;
    private int empId;
    private String date;
    private int dateType;
    private String shiftStart;
    private String shiftEnd;
    private String timeIn;
    private String timeOut;
    private float day;
    private float late;
    private float ot;
    private float nd;
    private float spc;
    private float spcOt;
    private float leg;

    // Constructor
    public Timecard(int dateId, int empId, String date, int dateType, String shiftStart, String shiftEnd,
            String timeIn, String timeOut, float day, float late, float ot, float nd, float spc,
            float spcOt, float leg) {
        this.dateId = dateId;
        this.empId = empId;
        this.date = date;
        this.dateType = dateType;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.day = day;
        this.late = late;
        this.ot = ot;
        this.nd = nd;
        this.spc = spc;
        this.spcOt = spcOt;
        this.leg = leg;
    }

    // Setters
    public void setDateId(int dateId) {
        this.dateId = dateId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDateType(int dateType) {
        this.dateType = dateType;
    }

    public void setShiftStart(String shiftStart) {
        this.shiftStart = shiftStart;
    }

    public void setShiftEnd(String shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public void setDay(float day) {
        this.day = day;
    }

    public void setLate(float late) {
        this.late = late;
    }

    public void setOt(float ot) {
        this.ot = ot;
    }

    public void setNd(float nd) {
        this.nd = nd;
    }

    public void setSpc(float spc) {
        this.spc = spc;
    }

    public void setSpcOt(float spcOt) {
        this.spcOt = spcOt;
    }

    public void setLeg(float leg) {
        this.leg = leg;
    }

    // Getters
    public int getDateId() {
        return dateId;
    }

    public int getEmpId() {
        return empId;
    }

    public String getDate() {
        return date;
    }

    public int getDateType() {
        return dateType;
    }

    public String getShiftStart() {
        return shiftStart;
    }

    public String getShiftEnd() {
        return shiftEnd;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public float getDay() {
        return day;
    }

    public float getLate() {
        return late;
    }

    public float getOt() {
        return ot;
    }

    public float getNd() {
        return nd;
    }

    public float getSpc() {
        return spc;
    }

    public float getSpcOt() {
        return spcOt;
    }

    public float getLeg() {
        return leg;
    }
}
