package com.lakeqiu.base.juc.utils.charpter8;

/**
 * @author lakeqiu
 */
public class JavaBean {


    /**
     * name : 企业名称
     * code : 统一社会信用代码
     * registrationDay : 注册日期
     * character : 企业类型
     * legalRepresentative : 法人代表
     * capital : 注册资金
     * businessScope : 经营范围
     * province : 所在省份
     * city : 地区
     * address : 注册地址
     */

    private String name;
    private String code;
    private String registrationDay;
    private String character;
    private String legalRepresentative;
    private String capital;
    private String businessScope;
    private String province;
    private String city;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRegistrationDay() {
        return registrationDay;
    }

    public void setRegistrationDay(String registrationDay) {
        this.registrationDay = registrationDay;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
