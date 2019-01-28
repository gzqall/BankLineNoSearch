package cn.mars.chanpay.banklinenoutil.entity;

public class record {

    private String bank_no;

    public record(){}
    public record(String bank_no, String bank_name) {
        this.bank_no = bank_no;
        this.bank_name = bank_name;
    }

    private String bank_name;

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }
    public String getBank_no() {
        return bank_no;
    }

    public void setBank_no(String bank_no) {
        this.bank_no = bank_no;
    }


    @Override
    public String toString(){
        return "bank_no:"+bank_no+",bank_name:"+bank_name;
    }

}
