/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wp2c2project.classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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

    public void calcAttendance() throws ParseException {
        //check if there is timeIn before doing attendance calculation
        if (!"".equals(timeIn) && !"".equals(timeOut)) {
            //Time in & time out
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            dateFormat.setTimeZone(TimeZone.getTimeZone("PT"));

            Date timeInDate = dateFormat.parse(timeIn);
            Date timeOutDate = dateFormat.parse(timeOut);
            Date shiftStartThreshold = dateFormat.parse(shiftStart);
            Date shiftEndThreshold = dateFormat.parse(shiftEnd);

            Date startOverlap;
            Date endOverlap;

            //convert to hours and minutes
            long timeInMill = timeInDate.getTime();
            long timeOutMill = timeOutDate.getTime();

            //calculate day (needs proper rounding off logic)
            switch (dateType) {
                case 0 -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(shiftStartThreshold);
                    calendar.add(Calendar.MINUTE, 30);
                    Date shiftStartLateThreshold = calendar.getTime();
                    startOverlap = timeInDate.after(shiftStartLateThreshold) ? timeInDate : shiftStartThreshold;
                    endOverlap = timeOutDate.before(shiftEndThreshold) ? timeOutDate : shiftEndThreshold;
                    long durationMillis = endOverlap.getTime() - startOverlap.getTime();
                    float durationHours = (float) durationMillis / (60 * 60 * 1000);
                    durationHours = durationHours < 0 ? +24 : durationHours;
                    day = durationHours / 9;

                    //calculate overtime (✅ working omg)
                    ot = 0;
                    if (timeOutDate.after(shiftEndThreshold)) {
                        ot = roundToNearestHalf((float) (timeOutMill - shiftEndThreshold.getTime()) / (60 * 60 * 1000));
                    }

                    //calculate late
                    late = 0;
                    calendar.setTime(shiftStartThreshold);
                    calendar.add(Calendar.MINUTE, 5);
                    Date thresholdTimeLate = calendar.getTime();
                    if (timeInDate.after(thresholdTimeLate)) {
                        late = roundToNearestHalf((float) (timeInMill - shiftStartThreshold.getTime()) / (60 * 1000)) * 2;
                    }
                    spc = 0;
                    spcOt = 0;
                    leg = 0;
                }
                case 1 -> {
                    long durationMillis = timeOutDate.getTime() - timeInDate.getTime();
                    float durationHours = (float) durationMillis / (60 * 60 * 1000);
                    durationHours = durationHours < 0 ? +24 : durationHours;
                    if (durationHours < 8) {
                        spc = durationHours;
                        spcOt = 0;
                    } else {
                        spc = 8;
                        float spcOtDuration = durationHours - 8;
                        spcOt = spcOtDuration;
                    }
                    late = 0;
                    ot = 0;
                    day = 0;
                    leg = 0;
                }
                case 2 -> {
                    long durationMillis = timeOutDate.getTime() - timeInDate.getTime();
                    float durationHours = (float) durationMillis / (60 * 60 * 1000);
                    durationHours = durationHours < 0 ? +24 : durationHours;
                    leg = durationHours;
                    late = 0;
                    ot = 0;
                    day = 0;
                    spc = 0;
                    spcOt = 0;
                }
                case 3 -> {
                    day = 1;
                    late = 0;
                    ot = 0;
                    spc = 0;
                    spcOt = 0;
                    leg = 0;
                }
            }

            //calculate nd
            Date ndStartTreshold = dateFormat.parse("22:00");
            Date ndEndTreshold = dateFormat.parse("06:00");
            if (timeInDate.after(timeOutDate)) { //time crossing midnight
                startOverlap = timeInDate.after(ndStartTreshold) ? timeInDate : ndStartTreshold;
                endOverlap = timeOutDate.before(ndEndTreshold) ? timeOutDate : ndEndTreshold;
                nd = setDurationHours(startOverlap, endOverlap);
            } else { //time not crossing midnight
                if (timeOutDate.after(ndStartTreshold)) {
                    startOverlap = timeInDate.after(ndStartTreshold) ? timeInDate : ndStartTreshold;
                    nd = setDurationHours(startOverlap, timeOutDate);
                }
                if (timeInDate.before(ndEndTreshold)) {
                    endOverlap = timeOutDate.before(ndEndTreshold) ? timeOutDate : ndEndTreshold;
                    nd = setDurationHours(timeInDate, endOverlap);
                }
            }
        } else if (dateType != 0) {
            day = 1;
            resetTime();
        } else {
            day = 0;
            resetTime();
        }

    }

    private static float roundToNearestHalf(float number) {
        return (float) (Math.floor(number * 2) / 2.0);
    }

    private static float setDurationHours(Date startOverlap, Date endOverlap) {
        long durationMillis = endOverlap.getTime() - startOverlap.getTime();
        float durationHours = (float) durationMillis / (60 * 60 * 1000);
        durationHours = durationHours < 0 ? +24 : durationHours;
        return durationHours;
    }

    private void resetTime() {
        late = 0;
        ot = 0;
        nd = 0;
        spc = 0;
        spcOt = 0;
        leg = 0;
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
