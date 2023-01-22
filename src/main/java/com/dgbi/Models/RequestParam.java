package com.dgbi.Models;

public class RequestParam {
    String param_name;
    String type;
    boolean isMandatory;

    String param_type;

    public RequestParam(){}

    public RequestParam(String ref, String type, boolean isMandatory, String param_type) {
        this.param_name = ref;
        this.type = type;
        this.isMandatory = isMandatory;
        this.param_type = param_type;
    }

    public String getParam_name() {
        return param_name;
    }

    public void setParam_name(String param_name) {
        this.param_name = param_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    public String getParam_type() {
        return param_type;
    }

    public void setParam_type(String param_type) {
        this.param_type = param_type;
    }
}
