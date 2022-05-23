package org.example.businessLogic;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private Integer orderID;
    private String clientID;
    private Date orderDate;

    public Order(Integer orderID, String clientID, Date orderDate) {
        this.orderID = orderID;
        this.clientID = clientID;
        this.orderDate = orderDate;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public Integer getOrderID() {
        return orderID;
    }

    public String getClientID() {
        return clientID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    @Override
    public String toString() {
        return
                "orderID=" + orderID +
                ", clientID='" + clientID + '\'' +
                ", orderDate=" + orderDate + "\n" ;
    }
}
