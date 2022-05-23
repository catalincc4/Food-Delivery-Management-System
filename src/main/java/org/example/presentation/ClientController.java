package org.example.presentation;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.example.App;
import org.example.businessLogic.BaseProduct;
import org.example.businessLogic.DeliveryService;
import org.example.businessLogic.MenuItem;
import org.example.dataAccess.DeliveryServiceDAO;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    @FXML
    private TableColumn<BaseProduct, Integer> calories;

    @FXML
    private FontAwesomeIconView cancelButton;

    @FXML
    private JFXButton createNewOrderButton;

    @FXML
    private TableColumn<BaseProduct, String> editColumn;

    @FXML
    private TextField endCalories;

    @FXML
    private TextField endFat;

    @FXML
    private TextField endPrice;

    @FXML
    private TextField endProtein;

    @FXML
    private TextField endSodium;

    @FXML
    private TableColumn<BaseProduct, Integer> fat;

    @FXML
    private ListView<HBox> listProduct;

    @FXML
    private TableColumn<BaseProduct, Integer> price;

    @FXML
    private TableView<BaseProduct> productTable;

    @FXML
    private TableColumn<BaseProduct, Integer> protein;

    @FXML
    private TableColumn<BaseProduct, Double> rating;

    @FXML
    private FontAwesomeIconView searchButton;

    @FXML
    private TableColumn<BaseProduct, Integer> sodium;

    @FXML
    private TextField startCalories;

    @FXML
    private TextField startFat;

    @FXML
    private TextField startPrice;

    @FXML
    private TextField startProtein;

    @FXML
    private TextField startSodium;

    @FXML
    private TableColumn<BaseProduct, String> title;

    @FXML
    private TextField wordKey;

    private List<MenuItem> orderItems;
    private ObservableList<HBox> orderHBoxList;
    private ObservableList<BaseProduct> productObservableList;
    private DeliveryServiceDAO deliveryServiceDAO;
    private DeliveryService deliveryService;
    private BaseProduct productFilterStart;
    private BaseProduct productFilterEnd;
    List<BaseProduct> baseProductList;

    @FXML
    void cancelButton(MouseEvent event) throws IOException {
        App.setRoot("log-view");
    }

    @FXML
    void createNewOrder(MouseEvent event) {
        deliveryService.createNewOrder(orderItems);
        deliveryServiceDAO.writeObject(deliveryService);
        orderItems.removeAll(orderItems);
        orderHBoxList.removeAll(orderHBoxList);
    }

    @FXML
    void search(MouseEvent event) {
        productFilterStart = new BaseProduct("", 0.0, 0, 0, 0, 0, 0);
        productFilterEnd = new BaseProduct("", 0.0, 0, 0, 0, 0, 0);
        productFilterStart.setTitle(wordKey.getText());
        try {
            Integer n = Integer.parseInt(startCalories.getText());
            productFilterStart.setCalories(n);
        } catch (NumberFormatException e) {
            productFilterStart.setCalories(0);
        }
        try {
            Integer n = Integer.parseInt(startProtein.getText());
            productFilterStart.setProtein(n);
        } catch (NumberFormatException e) {
            productFilterStart.setProtein(0);
        }
        try {
            Integer n = Integer.parseInt(startFat.getText());
            productFilterStart.setFat(n);
        } catch (NumberFormatException e) {
            productFilterStart.setFat(0);
        }
        try {
            Integer n = Integer.parseInt(startSodium.getText());
            productFilterStart.setSodium(n);
        } catch (NumberFormatException e) {
            productFilterStart.setSodium(0);
        }
        try {
            Integer n = Integer.parseInt(startPrice.getText());
            productFilterStart.setPrice(n);
        } catch (NumberFormatException e) {
            productFilterStart.setPrice(0);
        }
        try {
            Integer n = Integer.parseInt(endCalories.getText());
            productFilterEnd.setCalories(n);
        } catch (NumberFormatException e) {
            productFilterEnd.setCalories(999999999);
        }


        try {
            Integer n = Integer.parseInt(endProtein.getText());
            productFilterEnd.setProtein(n);
        } catch (NumberFormatException e) {
            productFilterEnd.setProtein(99999999);
        }
        try {
            Integer n = Integer.parseInt(endFat.getText());
            productFilterEnd.setFat(n);
        } catch (NumberFormatException e) {
            productFilterEnd.setFat(9999999);
        }
        try {
            Integer n = Integer.parseInt(endSodium.getText());
            productFilterEnd.setSodium(n);
        } catch (NumberFormatException e) {
            productFilterEnd.setSodium(999999999);
        }
        try {
            Integer n = Integer.parseInt(endPrice.getText());
            productFilterEnd.setPrice(n);
        } catch (NumberFormatException e) {
            productFilterEnd.setPrice(999999999);
        }
        baseProductList = deliveryService.searchProductsByCriteria(productFilterStart, productFilterEnd);
        refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deliveryServiceDAO = new DeliveryServiceDAO();
        deliveryService = deliveryServiceDAO.readObject();
        orderItems = new ArrayList<>();
        orderHBoxList = FXCollections.observableArrayList();
        baseProductList = deliveryService.getProducts();
        loadData();
    }


    public void loadData() {
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        calories.setCellValueFactory(new PropertyValueFactory<>("calories"));
        protein.setCellValueFactory(new PropertyValueFactory<>("protein"));
        fat.setCellValueFactory(new PropertyValueFactory<>("fat"));
        sodium.setCellValueFactory(new PropertyValueFactory<>("sodium"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));


        Callback<TableColumn<BaseProduct, String>, TableCell<BaseProduct, String>> cellFactory = (TableColumn<BaseProduct, String> param) -> {
            // make cell containing buttons
            final TableCell<BaseProduct, String> cell = new TableCell<BaseProduct, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView plusIcon = new FontAwesomeIconView(FontAwesomeIcon.PLUS);
                        plusIcon.setStyleClass("plusIcon");
                        plusIcon.setOnMouseClicked((MouseEvent event) -> {
                            BaseProduct product = productTable.getSelectionModel().getSelectedItem();
                            orderItems.add(product);
                            Text text = new Text(product.getTitle());
                            FontAwesomeIconView minusIcon = new FontAwesomeIconView(FontAwesomeIcon.MINUS);
                            minusIcon.setStyleClass("plusIcon");
                            HBox hBox = new HBox(text, minusIcon);
                            HBox.setMargin(text, new Insets(2, 2, 0, 3));
                            HBox.setMargin(minusIcon, new Insets(2, 3, 1, 20));
                            minusIcon.setOnMouseClicked((MouseEvent e) -> {
                                orderHBoxList.remove(hBox);
                                orderItems.remove(product);
                            });
                            orderHBoxList.add(hBox);

                        });

                        HBox managebtn = new HBox(plusIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(plusIcon, new Insets(2, 2, 0, 3));
                        setGraphic(managebtn);
                        setText(null);

                    }
                }

            };

            return cell;
        };
        editColumn.setCellFactory(cellFactory);
        refresh();
    }

    private void refresh() {
        productObservableList = FXCollections.observableArrayList(baseProductList);
        productTable.setItems(productObservableList);
        listProduct.setItems(orderHBoxList);
    }


}
