package org.example.businessLogic;

import java.util.Date;
import java.util.List;

public interface IDeliveryServiceProcessing {
    void importProducts();
    void createReportByTimeInterval(Integer startHour, Integer endHour);
    void createReportByProducts(Integer nrOfTimes);
    void createReportByClientsAndOrderAmount(Integer numberOfTimes, Integer orderAmount);
    void createReportByProductsWithinADay(Date date);
    List<BaseProduct> searchProductsByCriteria(BaseProduct startProduct, BaseProduct endProduct);
    void createNewOrder(List<MenuItem> menuItems);
}
