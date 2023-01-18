package com.dgbi.BL;

public class RequestParam {
    String param_name;
    String type;
    boolean isMandatory;

    public RequestParam(){}

    public RequestParam(String ref, String type, boolean isMandatory) {
        this.param_name = ref;
        this.type = type;
        this.isMandatory = isMandatory;
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
}
