package com.wkcto.p2p.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 雅人叔
 * 2020/2/23
 */
@Controller
public class LoanInfoController {

    @RequestMapping(value = "/loan/loan")
    public String loanInfo(Model model){


        return "loan";
    }

}
