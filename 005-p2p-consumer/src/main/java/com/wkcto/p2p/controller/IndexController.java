package com.wkcto.p2p.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wkcto.p2p.constant.Constants;
import com.wkcto.p2p.model.loan.LoanInfo;
import com.wkcto.p2p.service.BidInfoService;
import com.wkcto.p2p.service.LoanInfoService;
import com.wkcto.p2p.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 雅人叔
 * 2020/2/20
 */
@Controller
public class IndexController {

    @Reference(interfaceClass = LoanInfoService.class,version = "1.0.0",check = false)
    private LoanInfoService loanInfoService;

    @Reference(interfaceClass = UserService.class,version = "1.0.0",check = false)
    private UserService userService;

    @Reference(interfaceClass = BidInfoService.class,version = "1.0.0",check = false)
    private BidInfoService bidInfoService;

    @RequestMapping("/index")
    public String index(Model model){

        //获取平台总利润

        Double historyAverageRate = loanInfoService.queryHistoryAverageRate();
        model.addAttribute("historyAverageRate", historyAverageRate);

        //获取平台总人数
        Integer allUserCount = userService.queryAllUserCount();

        model.addAttribute(Constants.ALLUSERCOUNT, allUserCount);


        //获取平台总投资
        Long allBidMoney = bidInfoService.queryAllBidMoney();
        model.addAttribute(Constants.ALLBIDMONEY, allBidMoney);



        //获取新手宝

        Map<String,Object> map = new HashMap<>();
        map.put("productType", Constants.PRODUCT_TYPE_X);
        map.put("currentPage", 0);
        map.put("pageSize", 1);

        List<LoanInfo> xLoanInfoList = loanInfoService.queryLoanInfoListByProductType(map);
        model.addAttribute("xLoanInfoList", xLoanInfoList);

        //获取满月宝

        map.put("productType", Constants.PRODUCT_TYPE_U);
        map.put("currentPage", 0);
        map.put("pageSize", 4);

        List<LoanInfo> uLoanInfoList = loanInfoService.queryLoanInfoListByProductType(map);
        model.addAttribute("uLoanInfoList", uLoanInfoList);

        //获取季度宝

        map.put("productType", Constants.PRODUCT_TYPE_S);
        map.put("currentPage", 0);
        map.put("pageSize", 8);

        List<LoanInfo> sLoanInfoList = loanInfoService.queryLoanInfoListByProductType(map);
        model.addAttribute("sLoanInfoList", sLoanInfoList);



        return "index";
    }
}
