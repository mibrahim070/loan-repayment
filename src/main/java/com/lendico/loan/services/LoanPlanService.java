package com.lendico.loan.services;

import com.lendico.loan.entities.Payload;
import com.lendico.loan.entities.Repayment;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class LoanPlanService {
    private final static int DAYS_MONTH = 30;
    private final static int MONTHS_YEAR = 12;
    private final static int DAYS_YEAR = 360;

    private double getAnnuityPayment (Payload payload) {
        double monthlyRate = (payload.getNominalRate() / MONTHS_YEAR) / 100;
        double annuityPayment = payload.getLoanAmount() * monthlyRate;
        annuityPayment /= (1 - Math.pow(1 + monthlyRate , -payload.getDuration()));
        return  annuityPayment;
    }

    private String dateFormat(Calendar calendar , int amount) {
        if(amount > 0)
            calendar.add(Calendar.MONTH,1);
        else
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return simpleDateFormat.format(calendar.getTime());
    }

    private double setPrecision(double number) {
        String s = String.format("%.2f", number);
        return Double.parseDouble(s);
    }

    public List<Repayment> generatePlan(Payload payload) throws Exception{
        try{
            List<Repayment> repayments = new ArrayList<>();
            payload.setAnnuity(getAnnuityPayment(payload));

            double outstandingPrincipal = payload.getLoanAmount();

            for(int i = 0 ;i < payload.getDuration() ;i++){
                Repayment repaymentMonth = new Repayment();
                repaymentMonth.setInitialOutstandingPrincipal(outstandingPrincipal);
                repaymentMonth.setInterest(setPrecision(( (payload.getNominalRate() / 100) * DAYS_MONTH *
                        repaymentMonth.getInitialOutstandingPrincipal()) / DAYS_YEAR));
                double valueInterest = repaymentMonth.getInterest();
                if(repaymentMonth.getInterest() > repaymentMonth.getInitialOutstandingPrincipal()) {
                    valueInterest = repaymentMonth.getInitialOutstandingPrincipal();
                }
                repaymentMonth.setPrincipal(setPrecision(payload.getAnnuity() - valueInterest));
                repaymentMonth.setDate(dateFormat(payload.getStartDate(), i));
                repaymentMonth.setBorrowerPaymentAmount(setPrecision(repaymentMonth.getPrincipal() + repaymentMonth.getInterest()));
                double remainingOutstandingPrincipal = setPrecision(repaymentMonth.getInitialOutstandingPrincipal()
                        - repaymentMonth.getPrincipal());
                repaymentMonth.setRemainingOutstandingPrincipal(remainingOutstandingPrincipal < 0 ? 0 : remainingOutstandingPrincipal);
                outstandingPrincipal = setPrecision(repaymentMonth.getRemainingOutstandingPrincipal());
                repayments.add(repaymentMonth);
            }

            return repayments;
        } catch (Exception e) {
            throw new Exception("Error with Payload");
        }
    }
}
