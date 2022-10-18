package com.example.petplanet.models;

import java.util.Date;

public class ChatMessage {
    private String senderid;
    private String receiverid;
    private String message;
    private String datetime;
    public Date dateObject;
    public String conversionId,conversionName,conversionImage;
    public ChatMessage() {
    }

    public ChatMessage(String senderid, String receiverid, String message, String datetime) {
        this.senderid = senderid;
        this.receiverid = receiverid;
        this.message = message;
        this.datetime = datetime;
    }




    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid;
    }

    public String getSenderid() {
        return senderid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
