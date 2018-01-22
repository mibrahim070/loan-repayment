package com.lendico.loan.entities;
import java.util.Calendar;

public class Payload {
    private double loanAmount;
    private double nominalRate;
    private int duration;
    private Calendar startDate;
    private double Annuity;

    public Payload(){

    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getNominalRate() {
        return nominalRate;
    }

    public void setNominalRate(double nominalRate) {
        this.nominalRate = nominalRate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public double getAnnuity() {
        return Annuity;
    }

    public void setAnnuity(double annuity) {
        Annuity = annuity;
    }
}
