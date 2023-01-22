package com.dgbi.Models;

public class Request {
    String ref;
    String type;
    String requestParams;

    public Request(){}

    public Request(String ref, String type, String request) {
        this.ref = ref;
        this.type = type;
        this.requestParams = request;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String request) {
        this.requestParams = request;
    }
}
