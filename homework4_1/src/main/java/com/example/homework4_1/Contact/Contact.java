package com.example.homework4_1.Contact;

public class Contact {

    String name;
    ConnectType connectType;
    String communication;

    public Contact(String name, String communication, ConnectType connectType) {
        this.name = name;
        this.connectType = connectType;
        this.communication = communication;
    }

    public String getName() {
        return name;
    }

    public ConnectType getConnectType() {
        return connectType;
    }

    public String getCommunication() {
        return communication;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setConnectType(ConnectType connectType) {
        this.connectType = connectType;
    }

    public void setCommunication(String communication) {
        this.communication = communication;
    }
}
