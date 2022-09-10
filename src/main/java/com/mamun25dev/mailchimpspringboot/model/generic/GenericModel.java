package com.mamun25dev.mailchimpspringboot.model.generic;

public class GenericModel {

    private String key;
    private String value;
    private String key2;
    private String value2;
    private String key3;
    private String value3;

    private String param;
    private String paramValue;
    private String param2;
    private String paramValue2;

    private String attribute;
    private String attributeValue;
    private String attribute2;
    private String attributeValue2;

    public GenericModel(){

    }

    public GenericModel(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public GenericModel(String key, String value, String key2, String value2, String key3, String value3) {
        this.key = key;
        this.value = value;
        this.key2 = key2;
        this.value2 = value2;
        this.key3 = key3;
        this.value3 = value3;
    }


    public GenericModel(String key, String value, String key2, String value2, String key3, String value3, String param, String paramValue, String param2, String paramValue2, String attribute, String attributeValue, String attribute2, String attributeValue2) {
        this.key = key;
        this.value = value;
        this.key2 = key2;
        this.value2 = value2;
        this.key3 = key3;
        this.value3 = value3;
        this.param = param;
        this.paramValue = paramValue;
        this.param2 = param2;
        this.paramValue2 = paramValue2;
        this.attribute = attribute;
        this.attributeValue = attributeValue;
        this.attribute2 = attribute2;
        this.attributeValue2 = attributeValue2;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getKey3() {
        return key3;
    }

    public void setKey3(String key3) {
        this.key3 = key3;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParamValue2() {
        return paramValue2;
    }

    public void setParamValue2(String paramValue2) {
        this.paramValue2 = paramValue2;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    public String getAttributeValue2() {
        return attributeValue2;
    }

    public void setAttributeValue2(String attributeValue2) {
        this.attributeValue2 = attributeValue2;
    }


}
