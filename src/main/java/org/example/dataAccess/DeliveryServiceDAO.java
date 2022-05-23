package org.example.dataAccess;

import org.example.businessLogic.DeliveryService;

public class DeliveryServiceDAO extends Serializator<DeliveryService>{
    public DeliveryServiceDAO() {
        super("src/main/resources/delivery-service.ser");
    }
}
