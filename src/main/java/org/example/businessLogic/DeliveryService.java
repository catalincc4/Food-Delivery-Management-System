package org.example.businessLogic;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import org.example.dataAccess.FileWriter;
import org.example.presentation.EmployeeController;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Delivery Service class
 */
public class DeliveryService extends Observer implements IDeliveryServiceProcessing, Serializable {

    /**
     * list of products available for purchase
     */
    private List<BaseProduct> products = new ArrayList<>();
    private HashMap<Order, List<MenuItem>> orderListHashMap = new HashMap<>();
    private List<Order> orderToDo = new ArrayList<>();
    private List<Order> orderDone = new ArrayList<>();
    private Integer reportID = 1;
    private Integer orderID = 1;
    private String userName="";

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<BaseProduct> getProducts() {
        return products;
    }

    public HashMap<Order, List<MenuItem>> getOrderListHashMap() {
        return orderListHashMap;
    }

    public List<Order> getOrderToDo() {
        return orderToDo;
    }

    public List<Order> getOrderDone() {
        return orderDone;
    }


    /**
     * Import products from products.csv file
     */
    @Override
    public void importProducts() {
        try {
            FileReader fileReader = new FileReader("src/main/resources/org/example/products.csv");
            BufferedReader in = new BufferedReader(fileReader);
            in.readLine();
            while (in.ready()) {
                String line = in.readLine();
                String[] strings = line.split(",");
                BaseProduct product = new BaseProduct(strings[0], Double.parseDouble(strings[1]),
                        Integer.parseInt(strings[2]), Integer.parseInt(strings[3]),
                        Integer.parseInt(strings[4]), Integer.parseInt(strings[5]), Integer.parseInt(strings[6]));
                if (!products.contains(product)) {
                    products.add(product);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create report with products order in a time interval
     * @param startHour
     * @param endHour
     */
    @Override
    public void createReportByTimeInterval(Integer startHour, Integer endHour) {
        assert startHour<0;
        assert endHour>23;
        List<Order> orders = orderListHashMap.keySet().stream().filter(e -> e.getOrderDate().getHours() >= startHour && e.getOrderDate().getHours() <= endHour).collect(Collectors.toList());
        String[] text = {"Orders between start hour: " + startHour + " and end hour: " + endHour + "\n"};
        orders.forEach(e -> text[0] = text[0] + e.toString());
        FileWriter fileWriter = new FileWriter("report"+reportID , text[0]);
        reportID++;
    }

    /**
     * create report by products order more than a specified number
     * @param nrOfTimes
     */
    @Override
    public void createReportByProducts(Integer nrOfTimes) {
        assert nrOfTimes>0;
        HashMap<MenuItem, Integer> numberOfTimesForAProduct = new HashMap<>();
        String text = "Product order more than " + nrOfTimes + " times\n";
        List<MenuItem> itemList = new ArrayList<>();
        Set<Order> orders = orderListHashMap.keySet();
        for (Order order : orders) {
            List<MenuItem> items = orderListHashMap.get(order);
            for (MenuItem item : items) {
                if (numberOfTimesForAProduct.containsKey(item)) {
                    numberOfTimesForAProduct.replace(item, numberOfTimesForAProduct.get(item) + 1);
                    if (!itemList.contains(item) && numberOfTimesForAProduct.get(item) >= nrOfTimes) {
                        itemList.add(item);
                        text += item.toString();
                    }
                } else {
                    numberOfTimesForAProduct.put(item, 1);
                }
            }
        }
        FileWriter fileWriter = new FileWriter("report"+reportID , text);
        reportID++;
    }

    /**
     * Create report by clients who order more than a specified number and the order amount is bigger than a specified value
     * @param numberOfTimes
     * @param orderAmount
     */
    @Override
    public void createReportByClientsAndOrderAmount(Integer numberOfTimes, Integer orderAmount) {
        assert numberOfTimes<0;
        assert orderAmount<0;
        String clients = "Clients who order more than " + numberOfTimes + " and order amount more than" + orderAmount + "\n";
        List<String> clientIDs = new ArrayList<>();
        for (Order order : orderListHashMap.keySet()) {
            List<Order> orders = orderListHashMap.keySet().stream().filter(e -> e.getClientID().equals(order.getClientID())).collect(Collectors.toList()).
                    stream().filter(e -> price(orderListHashMap.get(e)) >= orderAmount).collect(Collectors.toList());
            if (orders.size() >= numberOfTimes && !clientIDs.contains(order.getClientID())) {
                clients += order.getClientID() + "\n";
                clientIDs.add(order.getClientID());
            }
        }
        FileWriter fileWriter = new FileWriter("report"+reportID , clients);
        reportID++;

    }

    /**
     * Create a report by products order whiten a day
     * @param date
     */
    @Override
    public void createReportByProductsWithinADay(Date date) {
        assert date==null;
        List<MenuItem> m = new ArrayList<>();
        List<Order> orders = orderListHashMap.keySet().stream().filter(e -> e.getOrderDate().getDay() == date.getDay() &&
                e.getOrderDate().getMonth() == date.getMonth() && e.getOrderDate().getYear() == date.getYear()).collect(Collectors.toList());

        for (Order order : orders) {
            m.addAll(orderListHashMap.get(order));
        }
        System.out.println(orders.size()+" "+m.size());
        String[] text ={"Products order in " + date};
        m.stream().distinct().collect(Collectors.toList()).forEach(e -> text[0] = text[0] + e.toString());
        FileWriter fileWriter = new FileWriter("report"+reportID , text[0]);
        reportID++;
    }

    /**
     * Search products by specified criteria
     * @param startProduct
     * @param endProduct
     * @return
     */
    @Override
    public List<BaseProduct> searchProductsByCriteria(BaseProduct startProduct, BaseProduct endProduct) {
        assert startProduct != null;
        assert endProduct != null;
        List<BaseProduct> prods = products.stream().filter(e -> e.getCalories() >= startProduct.getCalories() && e.getProtein() >= startProduct.getProtein() &&
                e.getFat() >= startProduct.getFat() && e.getSodium() >= startProduct.getSodium() && e.getPrice() >= startProduct.getPrice() &&
                e.getCalories() <= endProduct.getCalories() && e.getProtein() <= endProduct.getProtein() &&
                e.getFat() <= endProduct.getFat() && e.getSodium() <= endProduct.getSodium() && e.getPrice() <= endProduct.getPrice() && e.getTitle().contains(startProduct.getTitle())).collect(Collectors.toList());
        assert prods != null;
        return prods;
    }


    /**
     * Create a new order and also a bill
     * @param menuItems
     */
    @Override
    public void createNewOrder(List<MenuItem> menuItems) {
            assert menuItems!=null;
            if(menuItems.size() > 0){
                Order order = new Order(orderID,userName, new Date());
                orderListHashMap.put(order, menuItems);
                String[] text={"Order number " + orderID + "\n" + "Client :" + userName+"\n"};
                menuItems.forEach(e-> text[0] +=  e.toString());
                text[0] += "Total order: " + price(menuItems);
                FileWriter fileWriter = new FileWriter("bill" + orderID, text[0]);
                orderID++;
            }
    }

    /**
     * compute the total amount of the order
     * @param list
     * @return
     */
    public Integer price(List<MenuItem> list) {
        assert  list==null;
        Integer n = 0;
        assert list != null;
        for (MenuItem item : list) {
            n += item.computePrice();
        }
        assert n < 0;
        return n;
    }

    @Override
    public void update(Order order) {
       orderToDo.add(order);
    }
}
