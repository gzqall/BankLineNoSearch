package cn.mars.chanpay.banklinenoutil.KMPUtil;

import cn.mars.chanpay.banklinenoutil.LogUtil.logUtil;
import cn.mars.chanpay.banklinenoutil.entity.record;
import org.slf4j.Logger;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class BankMatch {
    private static Logger logger = logUtil.getLogUtil();
    Thread matchThread;
    ExecutorService checkExe;

    static{
        initData();//
    }
    public BankMatch() {

        // 数据加载完成后,启动查找守护线程
        checkExe = Executors.newSingleThreadExecutor();
    }
    
    // 初始化联行号数据
	public static  void initData() {
        logger.info("等待银行列表数据初始化完成....");
        long beforeLoadTime = System.nanoTime();
        long counts = HandleBankMatch.initBankCode();
        logger.info("加载完成," + "共加载" + counts + "条数据,耗时:" + new DecimalFormat("#.###").format((System.nanoTime() - beforeLoadTime) / Math.pow(10, 9))
                + "秒");
	}
	public static List<String> getBankMatchRes(String searchStr){
		long startTime = System.nanoTime();

		List<String> resultList = new HandleBankMatch( searchStr ).handleMatch();
		if (resultList.size() > 0) {
//			System.out.println("到符合条件的记录"+JsonUtil.toJSONString(resultList));
		}else{
			logger.error("未找到符合条件的记录");
		}
		logger.info("共用时:" + new DecimalFormat("#.###").format((System.nanoTime() - startTime) / Math.pow(10, 9)) + "秒");
		return resultList;
	}


}
