package com.mamun25dev.mailchimpspringboot.model.generic;

public class ResponseErrorModel {

    private boolean status;
    private Integer statusCode;
    private String message;
    private String messageDetails;
    private Object data;

    public ResponseErrorModel(){
    }

    public ResponseErrorModel(boolean status, Integer statusCode, String message, String messageDetails, Object data) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.messageDetails = messageDetails;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageDetails() {
        return messageDetails;
    }

    public void setMessageDetails(String messageDetails) {
        this.messageDetails = messageDetails;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
