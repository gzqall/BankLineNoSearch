package cn.mars.chanpay.banklinenoutil.service.impl;

import cn.mars.chanpay.banklinenoutil.KMPUtil.BankMatch;
import cn.mars.chanpay.banklinenoutil.LogUtil.logUtil;
import cn.mars.chanpay.banklinenoutil.entity.ResponseData;
import cn.mars.chanpay.banklinenoutil.entity.record;
import cn.mars.chanpay.banklinenoutil.service.SearchService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    private static Logger logger = logUtil.getLogUtil();
    @Override
    public String searchBankNo(String keywords) {
        ResponseData rspd = new ResponseData();
        int tot_num = 0;
        int back_num = 0;
        if( keywords == null && keywords.length() <= 0 ) {
            rspd.setFail( "dataerr", "请上送搜索关键字" );
            rspd.setTot_num( "0");
            rspd.setBack_num( "0");
            return JSON.toJSONString( rspd );
        }

        List<String> result_list = BankMatch.getBankMatchRes( keywords );

        back_num = result_list.size();
        tot_num = result_list.size();
        if( back_num == 0 || tot_num == 0 ) {
            rspd.setFail( "ntofnd", "未找到符合条件记录" );
            rspd.setTot_num( "0");
            rspd.setBack_num( "0");
            return JSON.toJSONString( rspd );
        }
        rspd.setBack_num( String.valueOf( back_num ));
        rspd.setTot_num( String.valueOf( tot_num ));
        List<record> record_list = new ArrayList<record>();

        for( String tmp_str : result_list ) {
            record rr = new record();
            logger.info( "tmp_str-----------"+tmp_str );
            String[] tmp_array = tmp_str.split( "," );
            rr.setBank_name( tmp_array[1]);
            rr.setBank_no( tmp_array[0]);
            logger.info(  rr.toString() );
            record_list.add( rr );
        }

        rspd.setDataList( record_list );
        rspd.setSuccess();

        String respJson =  JSON.toJSONString( rspd );
        logger.debug( respJson );
        return respJson;
    }
}
