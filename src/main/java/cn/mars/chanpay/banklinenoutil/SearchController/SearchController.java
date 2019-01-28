package cn.mars.chanpay.banklinenoutil.SearchController;


import cn.mars.chanpay.banklinenoutil.LogUtil.logUtil;
import cn.mars.chanpay.banklinenoutil.service.SearchService;
import cn.mars.chanpay.banklinenoutil.service.impl.SearchServiceImpl;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class SearchController {
    private static Logger logger = logUtil.getLogUtil();
    @RequestMapping( value="/BankLineSearch", method=RequestMethod.GET )
    public String doGet(@RequestParam("keywords") String keywords) {
        logger.info( "the parameter ----------"+keywords );
        return getBankInfo( keywords );
    }
    @RequestMapping( value="/BankLineSearch", method=RequestMethod.POST )
    public String doPost(@RequestParam("keywords") String keywords) {
        logger.info( "the parameter ----------"+keywords );
        return getBankInfo( keywords );
    }

    @Autowired
    private SearchService searchService;

    public String getBankInfo( String keywords ) {
        String cmc_res=searchService.searchBankNo( keywords );
        logger.info("cmc_返回信息---"+cmc_res);
        return cmc_res;
    }
}
