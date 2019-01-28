package cn.mars.chanpay.banklinenoutil.entity;

import java.util.List;

public class ResponseData {
   private String tot_num ;
   private String back_num;
   private List<record> dataList;
   private String err_code;
   private String err_desc;


   public ResponseData(String tot_num, String back_num, List<record> dataList, String err_code, String err_desc) {
      this.tot_num = tot_num;
      this.back_num = back_num;
      this.dataList = dataList;
      this.err_code = err_code;
      this.err_desc = err_desc;
   }

   public void setErr_code(String err_code){
      this.err_code = err_code;
   }

   public void setErr_desc( String err_desc ){
      this.err_desc = err_desc;
   }
   public String getErr_code(){
       return err_code;
   }

   public String getErr_desc(){
       return err_desc;
   }

   public void setSuccess( ){
     setErr_code( "000000" );
     setErr_desc( "查询成功" );
   }

   public void setFail( String err_code, String err_desc ){
      setErr_code( err_code );
      setErr_desc( err_desc );
   }

   public ResponseData() {

   }

   public String getTot_num() {
      return tot_num;
   }

   public void setTot_num(String tot_num) {
      this.tot_num = tot_num;
   }

   public String getBack_num() {
      return back_num;
   }

   public void setBack_num(String back_num) {
      this.back_num = back_num;
   }

   public List<record> getDataList() {
      return dataList;
   }

   public void setDataList(List<record> dataList) {
      this.dataList = dataList;
   }
}
