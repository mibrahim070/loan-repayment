package com.lendico.loan.controllers;

import com.lendico.loan.entities.Payload;
import com.lendico.loan.entities.Repayment;
import com.lendico.loan.services.LoanPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoanPlanController {
    @Autowired
    private LoanPlanService loanPlanService;

    @RequestMapping(value = "/generate-plan",method = RequestMethod.POST)
    public List<Repayment> getLoanRepayment(@RequestBody Payload payload) throws Exception{
        try {
            if(payload == null) {
                throw new NullPointerException("Payload is empty");
            }
            return loanPlanService.generatePlan(payload);
        }catch (Exception e ) {
            throw new Exception(e.getMessage());
        }


    }
}
