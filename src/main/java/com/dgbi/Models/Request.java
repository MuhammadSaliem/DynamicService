package com.dgbi.Models;

import java.io.Serializable;

public class Request implements Serializable {
    String reference;
    String type;
    String requestParams;

    public Request(){}

    public Request(String ref, String type, String request) {
        this.reference = ref;
        this.type = type;
        this.requestParams = request;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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

    @Override
    public String toString() {
        return "Request{" +
                "ref='" + reference + '\'' +
                ", type='" + type + '\'' +
                ", requestParams='" + requestParams + '\'' +
                '}';
    }
}
