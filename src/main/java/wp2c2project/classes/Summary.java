/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wp2c2project.classes;

/**
 *
 * @author jejer
 */
public class Summary {

    private int sumId;
    private int empId;
    private String period;
    private float rph;
    private float dayTot;
    private float lateTot;
    private float otTot;
    private float ndTod;
    private float spcTot;
    private float spcOtTot;
    private float legTot;
    private float lateAmt;
    private float otAmt;
    private float ndAmt;
    private float spcAmt;
    private float spcOtAmt;
    private float legAmt;
    private float regWage;
    private float gross;
    private float sssReg;
    private float sssMpf;
    private float phealth;
    private float wtax;
    private float sssLoanS;
    private float sssLoanC;
    private float pagibigCont;
    private float efund;
    private float pagibigLoanS;
    private float pagibigLoanC;
    private float otherDedt;
    private float dedtTot;
    private float allowance;
    private float netPay;

    public Summary(int sumId, int empId, String period, float rph, float dayTot, float lateTot, float otTot, float ndTod, float spcTot, float spcOtTot, float legTot, float lateAmt, float otAmt, float ndAmt, float spcAmt, float spcOtAmt, float legAmt, float regWage, float gross, float sssReg, float sssMpf, float phealth, float wtax, float sssLoanS, float sssLoanC, float pagibigCont, float efund, float pagibigLoanS, float pagibigLoanC, float otherDedt, float dedtTot, float allowance, float netPay) {
        this.sumId = sumId;
        this.empId = empId;
        this.period = period;
        this.rph = rph;
        this.dayTot = dayTot;
        this.lateTot = lateTot;
        this.otTot = otTot;
        this.ndTod = ndTod;
        this.spcTot = spcTot;
        this.spcOtTot = spcOtTot;
        this.legTot = legTot;
        this.lateAmt = lateAmt;
        this.otAmt = otAmt;
        this.ndAmt = ndAmt;
        this.spcAmt = spcAmt;
        this.spcOtAmt = spcOtAmt;
        this.legAmt = legAmt;
        this.regWage = regWage;
        this.gross = gross;
        this.sssReg = sssReg;
        this.sssMpf = sssMpf;
        this.phealth = phealth;
        this.wtax = wtax;
        this.sssLoanS = sssLoanS;
        this.sssLoanC = sssLoanC;
        this.pagibigCont = pagibigCont;
        this.efund = efund;
        this.pagibigLoanS = pagibigLoanS;
        this.pagibigLoanC = pagibigLoanC;
        this.otherDedt = otherDedt;
        this.dedtTot = dedtTot;
        this.allowance = allowance;
        this.netPay = netPay;
    }

    public int getSumId() {
        return sumId;
    }

    public void setSumId(int sumId) {
        this.sumId = sumId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public float getRph() {
        return rph;
    }

    public void setRph(float rph) {
        this.rph = rph;
    }

    public float getDayTot() {
        return dayTot;
    }

    public void setDayTot(float dayTot) {
        this.dayTot = dayTot;
    }

    public float getLateTot() {
        return lateTot;
    }

    public void setLateTot(float lateTot) {
        this.lateTot = lateTot;
    }

    public float getOtTot() {
        return otTot;
    }

    public void setOtTot(float otTot) {
        this.otTot = otTot;
    }

    public float getNdTod() {
        return ndTod;
    }

    public void setNdTod(float ndTod) {
        this.ndTod = ndTod;
    }

    public float getSpcTot() {
        return spcTot;
    }

    public void setSpcTot(float spcTot) {
        this.spcTot = spcTot;
    }

    public float getSpcOtTot() {
        return spcOtTot;
    }

    public void setSpcOtTot(float spcOtTot) {
        this.spcOtTot = spcOtTot;
    }

    public float getLegTot() {
        return legTot;
    }

    public void setLegTot(float legTot) {
        this.legTot = legTot;
    }

    public float getLateAmt() {
        return lateAmt;
    }

    public void setLateAmt(float lateAmt) {
        this.lateAmt = lateAmt;
    }

    public float getOtAmt() {
        return otAmt;
    }

    public void setOtAmt(float otAmt) {
        this.otAmt = otAmt;
    }

    public float getNdAmt() {
        return ndAmt;
    }

    public void setNdAmt(float ndAmt) {
        this.ndAmt = ndAmt;
    }

    public float getSpcAmt() {
        return spcAmt;
    }

    public void setSpcAmt(float spcAmt) {
        this.spcAmt = spcAmt;
    }

    public float getSpcOtAmt() {
        return spcOtAmt;
    }

    public void setSpcOtAmt(float spcOtAmt) {
        this.spcOtAmt = spcOtAmt;
    }

    public float getLegAmt() {
        return legAmt;
    }

    public void setLegAmt(float legAmt) {
        this.legAmt = legAmt;
    }

    public float getRegWage() {
        return regWage;
    }

    public void setRegWage(float regWage) {
        this.regWage = regWage;
    }

    public float getGross() {
        return gross;
    }

    public void setGross(float gross) {
        this.gross = gross;
    }

    public float getSssReg() {
        return sssReg;
    }

    public void setSssReg(float sssReg) {
        this.sssReg = sssReg;
    }

    public float getSssMpf() {
        return sssMpf;
    }

    public void setSssMpf(float sssMpf) {
        this.sssMpf = sssMpf;
    }

    public float getPhealth() {
        return phealth;
    }

    public void setPhealth(float phealth) {
        this.phealth = phealth;
    }

    public float getWtax() {
        return wtax;
    }

    public void setWtax(float wtax) {
        this.wtax = wtax;
    }

    public float getSssLoanS() {
        return sssLoanS;
    }

    public void setSssLoanS(float sssLoanS) {
        this.sssLoanS = sssLoanS;
    }

    public float getSssLoanC() {
        return sssLoanC;
    }

    public void setSssLoanC(float sssLoanC) {
        this.sssLoanC = sssLoanC;
    }

    public float getPagibigCont() {
        return pagibigCont;
    }

    public void setPagibigCont(float pagibigCont) {
        this.pagibigCont = pagibigCont;
    }

    public float getEfund() {
        return efund;
    }

    public void setEfund(float efund) {
        this.efund = efund;
    }

    public float getPagibigLoanS() {
        return pagibigLoanS;
    }

    public void setPagibigLoanS(float pagibigLoanS) {
        this.pagibigLoanS = pagibigLoanS;
    }

    public float getPagibigLoanC() {
        return pagibigLoanC;
    }

    public void setPagibigLoanC(float pagibigLoanC) {
        this.pagibigLoanC = pagibigLoanC;
    }

    public float getOtherDedt() {
        return otherDedt;
    }

    public void setOtherDedt(float otherDedt) {
        this.otherDedt = otherDedt;
    }

    public float getDedtTot() {
        return dedtTot;
    }

    public void setDedtTot(float dedtTot) {
        this.dedtTot = dedtTot;
    }

    public float getAllowance() {
        return allowance;
    }

    public void setAllowance(float allowance) {
        this.allowance = allowance;
    }

    public float getNetPay() {
        return netPay;
    }

    public void setNetPay(float netPay) {
        this.netPay = netPay;
    }
}
