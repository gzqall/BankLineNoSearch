package cn.mars.chanpay.banklinenoutil.servlet;

import cn.mars.chanpay.banklinenoutil.LogUtil.logUtil;
import cn.mars.chanpay.banklinenoutil.service.SearchService;
import org.slf4j.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(urlPatterns = "/BankLineSearch.jdo",description = "联行号检索")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger= logUtil.getLogUtil();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        logger.info("=========get请求========");
        doPost(req,resp);
    }
    @Resource
    private SearchService searchService;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        logger.info("========post请求====callback===");
        //1.获取对应编码
        String charset=req.getCharacterEncoding();
        resp.setContentType("text/html;charset="+charset);

        resp.setCharacterEncoding(charset);

        //2.
        String keywords=req.getParameter("keywords");

        //2.
        OutputStream out=null;
        logger.info("发送过来的keywords--->"+keywords);
        //3.
        try {
            //向cmc发送https请求
            String cmc_res=searchService.searchBankNo( keywords );

            logger.info("cmc_返回信息---"+cmc_res);
            out=resp.getOutputStream();
            logger.info("charset--->"+charset);
            out.write(cmc_res.getBytes(charset));
            out.flush();
        }catch (Exception e){
            logger.info("通讯异常",e);
        }finally {
            if(null!=out){
                out.close();
            }
        }
    }
}
