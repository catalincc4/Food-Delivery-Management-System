package org.example.presentation;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.example.App;
import org.example.businessLogic.DeliveryService;
import org.example.businessLogic.Order;
import org.example.dataAccess.DeliveryServiceDAO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeController implements  Initializable {

    @FXML
    private FontAwesomeIconView cancelButton;

    @FXML
    private ListView<HBox> listProductDone;

    @FXML
    private ListView<HBox> listProductToDo;

    DeliveryService deliveryService;
    DeliveryServiceDAO deliveryServiceDAO;
    ObservableList<HBox> orderDone;
    ObservableList<HBox> orderToDo;

    @FXML
    void cancelButton(MouseEvent event) throws IOException {
        App.setRoot("log-view");
    }



    void populateListToDo(){
        for (Order order: deliveryService.getOrderToDo()) {
            orderToDo.add(createHBoxToDoOrder(order));
        }
    }
    HBox createHBoxToDoOrder(Order order){
        Text text = new Text(order.getOrderID() + " " + order.getClientID());
        FontAwesomeIconView minusIcon = new FontAwesomeIconView(FontAwesomeIcon.MINUS);
        minusIcon.setStyleClass("plusIcon");
        HBox hBox = new HBox(text, minusIcon);
        HBox.setMargin(text, new Insets(2, 2, 0, 3));
        HBox.setMargin(minusIcon, new Insets(2, 3, 1, 20));
        minusIcon.setOnMouseClicked((MouseEvent e) -> {
            orderToDo.remove(hBox);
            deliveryService.getOrderDone().add(order);
            deliveryService.getOrderToDo().remove(order);
            orderDone.removeAll(orderDone);
            populateListDone();
            deliveryServiceDAO.writeObject(deliveryService);
            refresh();
        });
        return hBox;
    }

   void populateListDone(){
       for (Order order: deliveryService.getOrderDone()) {
           orderDone.add(createHBoxDoneOrder(order));
       }

   }
    HBox createHBoxDoneOrder(Order order){
        Text text = new Text(order.getOrderID() + " " + order.getClientID());
        HBox hBox = new HBox(text);
        HBox.setMargin(text, new Insets(2, 2, 0, 3));
        return hBox;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      loadData();
    }
    public void loadData(){
        orderDone = FXCollections.observableArrayList();
        orderToDo = FXCollections.observableArrayList();
        deliveryServiceDAO = new DeliveryServiceDAO();
        deliveryService = deliveryServiceDAO.readObject();
        populateListDone();
        populateListToDo();
        refresh();
    }
   void  refresh(){
        listProductDone.setItems(orderDone);
        listProductToDo.setItems(orderToDo);
    }
}
