package com.itschool.course;

import java.util.HashMap;
import java.util.Map;

public class Course {

    private Integer r030;
    private String txt;
    private Double rate;
    private String cc;
    private String exchangedate;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Course(Integer r030, String txt, Double rate, String cc, String exchangedate) {
        this.r030 = r030;
        this.txt = txt;
        this.rate = rate;
        this.cc = cc;
        this.exchangedate = exchangedate;
    }

    public Integer getR030() {
        return r030;
    }

    public void setR030(Integer r030) {
        this.r030 = r030;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getExchangedate() {
        return exchangedate;
    }

    public void setExchangedate(String exchangedate) {
        this.exchangedate = exchangedate;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append("r030: " + r030).append("\ntxt"
                + txt).append("\nrate" +  rate).append("\ncc" + cc).append("\nexchangedate"
                + exchangedate).toString();
    }

}