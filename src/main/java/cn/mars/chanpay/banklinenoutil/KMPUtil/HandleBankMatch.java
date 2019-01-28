package cn.mars.chanpay.banklinenoutil.KMPUtil;

import cn.mars.chanpay.banklinenoutil.LogUtil.logUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ClassUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class HandleBankMatch {
    private static Logger logger = logUtil.getLogUtil();
    public static final String bankCodeFile = "T_BANK_LINE_NO.unl";
    public static Map<String, String> bankMap;
    private String matchStr;
    private static String[] resultStr;

    public HandleBankMatch(String matchStr) {
        this.matchStr = matchStr;
    }



    private static long initSourceData() {
        long counts = 0;
        try {
//            InputStream bankCodeInputStream = BankMatch.class.getClassLoader().getResourceAsStream("/YQYL/SpringBoot/"+bankCodeFile);
            InputStream bankCodeInputStream = new FileInputStream("/home/bankSearch/T_BANK_LINE_NO/"+bankCodeFile);
            /*
             * bankCodeInputStream.mark(0); BufferedReader bReader = new BufferedReader(new
             * InputStreamReader(bankCodeInputStream, "GBK")); String lineString; while ((lineString =
             * bReader.readLine()) != null) { System.out.println(lineString); } bankCodeInputStream.reset();
             */
            /*
             * DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); DocumentBuilder
             * documentBuilder = documentBuilderFactory.newDocumentBuilder(); Document bankCodeDoc =
             * documentBuilder.parse(bankCodeInputStream); NodeList nodeList =
             * bankCodeDoc.getElementsByTagName("ibpsName"); bankMap = new HashMap<String, String>(); for (int i = 0;
             * null != nodeList && i < nodeList.getLength(); i++) { Element element = (Element) nodeList.item(i); if
             * (null != element && element.getNodeType() == Node.ELEMENT_NODE) {
             * bankMap.put(element.getAttribute("paySysBankCode"), element.getAttribute("name")); } }
             */

            BufferedReader bReader = new BufferedReader(new InputStreamReader(bankCodeInputStream, "GBK"),20480);
            String lineString;
            bankMap = new HashMap<String, String>();
            String code, name;
            while ((lineString = bReader.readLine()) != null) {
                int firstCommaIndex = lineString.indexOf(",");
                code = lineString.substring(0, firstCommaIndex);
                name = lineString.substring(firstCommaIndex + 1);
               // System.out.println("code=" + code + " and name=" + name+"=========="+counts);
                bankMap.put(code, name);
                counts++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return counts;
    }

    public List<String> handleMatch() {
        List<String> resultList = new ArrayList<String>();
        String code, name;
        String[] nameArray;
        String findResult;
        for (Map.Entry<String, String> entry : bankMap.entrySet()) {
                code = entry.getKey();
            name = entry.getValue();
            nameArray = name.split(",");
            findResult = code + "," + nameArray[0];
            List<String> arrangeList = new ArrayList<String>();
            resultStr = new String[nameArray.length];
            arrageArray(arrangeList, nameArray); // 如果有省份城市,重排其顺序以保证匹配的准确性
            for (String oneArrangeStr : arrangeList) {
                name = oneArrangeStr.replaceAll(",", "");
                // 处理BMP全字匹配的情况
                if ((KMPMatchString.kmpMatch(name, matchStr) || KMPMatchString.kmpMatch(matchStr, name)) && !resultList.contains(findResult)) {
                    resultList.add(findResult);
                }
            }
        }
        // Levenshtein 模糊算法
        if (resultList.size() > 0) {
            // 根据Levenshtein 模糊算法排序
            Collections.sort(resultList, new Comparator<String>() {
                public int compare(String s1, String s2) {
                    return LevenshteinMacthString.levenshteinMacth(s1.split(",")[1], matchStr)
                    - LevenshteinMacthString.levenshteinMacth(s2.split(",")[1], matchStr);
                }
            });
        }
        return resultList;
    }

    private static void arrageArray(List<String> arrangeList, String[] array) {
        int length = array.length;
        if (length == 1) {
            resultStr[resultStr.length - length] = array[0];
            // System.out.println(getStr(resultStr) + "--" + (++num));
            arrangeList.add(getStr(resultStr));
        } else {
            String[] newStrArray = new String[length - 1];
            for (int i = 0; i < length; i++) {
                int m = 0;
                resultStr[resultStr.length - length] = array[i];
                for (int k = 0; k < length - 1; k++) {
                    newStrArray[k] = array[k >= i ? i + (++m) : k];
                }
                // System.out.println("---"+new String(newCharArray));
                arrageArray(arrangeList, newStrArray);
            }
        }
    }

    private static String getStr(String[] resultStr2) {
        String s = "";
        for (String string : resultStr2) {
            s += "," + string;
        }
        return "".equals(s) ? "" : s.substring(1);
    }

    public static long initBankCode() {
        if (null == bankMap) {
            return initSourceData();
        }
        return 0;
    }

}
