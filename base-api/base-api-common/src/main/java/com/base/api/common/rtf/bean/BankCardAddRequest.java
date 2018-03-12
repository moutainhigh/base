package com.base.api.common.rtf.bean;

/**
 * 银行卡绑定 请求
 *
 * @author zhj
 * @date 2017/12/12
 */
public class BankCardAddRequest extends RtfBaseRequest {

    /**
     * 充值订单号
     */
    private String orderNo;
    /**
     * 用户协议号
     */
    private String contracts;
    /**
     * 银行代码
     */
    private String bankCode;
    /**
     * 银行卡信息
     */
    private String bankAccountNo;
    /**
     * 银行所在省
     */
    private String accountProvince;
    /**
     * 银行所在市
     */
    private String accountCity;
    /**
     * 银行分行
     */
    private String branch;
    /**
     * 银行支行
     */
    private String subbranch;
    /**
     * 银行预留手机
     */
    private String mobilePhone;
    /**
     * 处理时间
     */
    private String applyTime;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getContracts() {
        return contracts;
    }

    public void setContracts(String contracts) {
        this.contracts = contracts;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getAccountProvince() {
        return accountProvince;
    }

    public void setAccountProvince(String accountProvince) {
        this.accountProvince = accountProvince;
    }

    public String getAccountCity() {
        return accountCity;
    }

    public void setAccountCity(String accountCity) {
        this.accountCity = accountCity;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSubbranch() {
        return subbranch;
    }

    public void setSubbranch(String subbranch) {
        this.subbranch = subbranch;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }
}