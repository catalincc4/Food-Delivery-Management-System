package org.example.dataAccess;

import org.example.businessLogic.Order;

import java.util.List;

public class OrderDAO extends Serializator<List<Order>> {

    public OrderDAO() {
        super("order");
    }
}
