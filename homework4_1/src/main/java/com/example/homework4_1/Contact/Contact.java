package com.example.homework4_1.Contact;

import java.io.Serializable;
import java.util.Objects;

public class Contact implements Serializable {

    private String name;
    private ConnectType connectType;
    private String communication;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(name, contact.name) &&
                connectType == contact.connectType &&
                Objects.equals(communication, contact.communication);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, connectType, communication);
    }
}
