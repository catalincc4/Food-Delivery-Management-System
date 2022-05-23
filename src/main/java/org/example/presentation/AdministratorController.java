
package org.example.presentation;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.example.App;
import org.example.businessLogic.BaseProduct;
import org.example.businessLogic.CompositeProduct;
import org.example.businessLogic.DeliveryService;
import org.example.dataAccess.DeliveryServiceDAO;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AdministratorController implements Initializable {

    @FXML
    private TableColumn<BaseProduct, Integer> calories;

    @FXML
    private FontAwesomeIconView cancelButton;

    @FXML
    private JFXButton createNewProductButton;

    @FXML
    private JFXButton createNewProductButton4;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableColumn<BaseProduct, String> editColumn;

    @FXML
    private JFXComboBox<Integer> endHour;

    @FXML
    private Text endHourText;

    @FXML
    private JFXComboBox<Integer> endMinute;

    @FXML
    private TableColumn<BaseProduct, Integer> fat;

    @FXML
    private ListView<HBox> listProduct;

    @FXML
    private TextField numberOfTimes;

    @FXML
    private TextField orderAmount;

    @FXML
    private TableColumn<BaseProduct, Integer> price;

    @FXML
    private TableView<BaseProduct> productTable;

    @FXML
    private TableColumn<BaseProduct, Integer> protein;

    @FXML
    private TableColumn<BaseProduct, Double> rating;

    @FXML
    private TableColumn<BaseProduct, Integer> sodium;

    @FXML
    private JFXComboBox<Integer> startHour;

    @FXML
    private Text startHourText;

    @FXML
    private JFXComboBox<Integer> startMinute;

    @FXML
    private TableColumn<BaseProduct, String> title;

    @FXML
    private ComboBox<String> createByCombo;

    @FXML
    private TextField nameOfTheProduct;

    @FXML
    private FontAwesomeIconView refreshTableButton;

    DeliveryService deliveryService;
    DeliveryServiceDAO deliveryServiceDAO;
    List<BaseProduct> compositeProducts = new ArrayList<>();
    BaseProduct product;
    ObservableList<BaseProduct> productObservableList;
    ObservableList<HBox> compositeList = FXCollections.observableArrayList();

    @FXML
    void cancelButton(MouseEvent event) throws IOException {
        App.setRoot("log-view");
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

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
                        FontAwesomeIconView plusIcon = new FontAwesomeIconView(FontAwesomeIcon.PLUS);


                        editIcon.setStyleClass("editIcon");
                        deleteIcon.setStyleClass("deleteIcon");
                        plusIcon.setStyleClass("plusIcon");
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            product = productTable.getSelectionModel().getSelectedItem();
                            deliveryService.getProducts().remove(product);
                            deliveryServiceDAO.writeObject(deliveryService);
                            refresh();
                        });

                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            BaseProduct productToEdit = productTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(App.class.getResource("edit-product-info-view.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                            ProductEditController productEditController = loader.getController();
                            productEditController.setProduct(productToEdit);
                            productEditController.setDeliveryService(deliveryService);
                            productEditController.setDeliveryServiceDAO(deliveryServiceDAO);
                            productEditController.setTextFields();
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            productEditController.setStage(stage);
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();
                        });

                        plusIcon.setOnMouseClicked((MouseEvent event) -> {
                            BaseProduct product = productTable.getSelectionModel().getSelectedItem();
                            compositeProducts.add(product);
                            Text text = new Text(product.getTitle());
                            FontAwesomeIconView minusIcon = new FontAwesomeIconView(FontAwesomeIcon.MINUS);
                            minusIcon.setStyleClass("plusIcon");
                            HBox hBox = new HBox(text, minusIcon);
                            HBox.setMargin(text, new Insets(2, 2, 0, 3));
                            HBox.setMargin(minusIcon, new Insets(2, 3, 1, 20));
                            minusIcon.setOnMouseClicked((MouseEvent e) -> {
                                compositeList.remove(hBox);
                                compositeProducts.remove(product);
                            });
                            compositeList.add(hBox);

                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon, plusIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                        HBox.setMargin(plusIcon, new Insets(2, 3, 0, 3));
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
        productObservableList = FXCollections.observableArrayList(deliveryService.getProducts());
        productTable.setItems(productObservableList);
        listProduct.setItems(compositeList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderAmount.setVisible(false);
        datePicker.setVisible(false);
        numberOfTimes.setVisible(false);
        endMinute.setVisible(false);
        endHour.setVisible(false);
        startMinute.setVisible(false);
        startHour.setVisible(false);
        startHourText.setVisible(false);
        endHourText.setVisible(false);
        endHourText.setVisible(false);


        datePicker.setDisable(true);
        numberOfTimes.setDisable(true);
        endMinute.setDisable(true);
        endHour.setDisable(true);
        startMinute.setDisable(true);
        startHour.setDisable(true);
        startHourText.setDisable(true);
        endHourText.setDisable(true);
        endHourText.setDisable(true);
        orderAmount.setDisable(true);

        ObservableList<Integer> minutes = FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
                31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
                41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
                51, 52, 53, 54, 55, 56, 57, 58, 59);
        ObservableList<Integer> hours = FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                21, 22, 23);
        endHour.setItems(hours);
        startHour.setItems(hours);
        startMinute.setItems(minutes);
        endMinute.setItems(minutes);

        ObservableList<String> reportsBy = FXCollections.observableArrayList("time interval", "products ordered more than",
                "clients who ordered > ? and amount of the order > ?", "products ordered within a day");

        createByCombo.setItems(reportsBy);
        deliveryServiceDAO = new DeliveryServiceDAO();
        deliveryService = deliveryServiceDAO.readObject();
        loadData();

    }

    @FXML
    void showInputs(ActionEvent event) {

        if (createByCombo.getTypeSelector().equals("")) {

        } else if (createByCombo.getSelectionModel().getSelectedItem().equals("time interval")) {
            endHourText.setVisible(true);
            endMinute.setVisible(true);
            endHour.setVisible(true);
            startHourText.setVisible(true);
            startMinute.setVisible(true);
            startHour.setVisible(true);
            startHour.setDisable(false);
            startMinute.setDisable(false);
            endHour.setDisable(false);
            endMinute.setDisable(false);

            orderAmount.setVisible(false);
            datePicker.setVisible(false);
            numberOfTimes.setVisible(false);
            datePicker.setDisable(true);
            numberOfTimes.setDisable(true);
            orderAmount.setDisable(true);

        } else if (createByCombo.getSelectionModel().getSelectedItem().equals("products ordered more than")) {
            orderAmount.setVisible(false);
            datePicker.setVisible(false);
            numberOfTimes.setVisible(true);
            endMinute.setVisible(false);
            endHour.setVisible(false);
            startMinute.setVisible(false);
            startHour.setVisible(false);
            startHourText.setVisible(false);
            endHourText.setVisible(false);
            endHourText.setVisible(false);

            datePicker.setDisable(true);
            numberOfTimes.setDisable(false);
            endMinute.setDisable(true);
            endHour.setDisable(true);
            startMinute.setDisable(true);
            startHour.setDisable(true);
            startHourText.setDisable(true);
            endHourText.setDisable(true);
            endHourText.setDisable(true);
            orderAmount.setDisable(true);
        } else if (createByCombo.getSelectionModel().getSelectedItem().equals("clients who ordered > ? and amount of the order > ?")) {
            orderAmount.setVisible(true);
            datePicker.setVisible(false);
            numberOfTimes.setVisible(true);
            endMinute.setVisible(false);
            endHour.setVisible(false);
            startMinute.setVisible(false);
            startHour.setVisible(false);
            startHourText.setVisible(false);
            endHourText.setVisible(false);
            endHourText.setVisible(false);

            datePicker.setDisable(true);
            numberOfTimes.setDisable(false);
            endMinute.setDisable(true);
            endHour.setDisable(true);
            startMinute.setDisable(true);
            startHour.setDisable(true);
            startHourText.setDisable(true);
            endHourText.setDisable(true);
            endHourText.setDisable(true);
            orderAmount.setDisable(false);
        } else if (createByCombo.getSelectionModel().getSelectedItem().equals("products ordered within a day")) {
            orderAmount.setVisible(false);
            datePicker.setVisible(true);
            numberOfTimes.setVisible(false);
            endMinute.setVisible(false);
            endHour.setVisible(false);
            startMinute.setVisible(false);
            startHour.setVisible(false);
            startHourText.setVisible(false);
            endHourText.setVisible(false);
            endHourText.setVisible(false);

            datePicker.setDisable(false);
            numberOfTimes.setDisable(true);
            endMinute.setDisable(true);
            endHour.setDisable(true);
            startMinute.setDisable(true);
            startHour.setDisable(true);
            startHourText.setDisable(true);
            endHourText.setDisable(true);
            endHourText.setDisable(true);
            orderAmount.setDisable(true);

        }

    }

    @FXML
    void createNewProduct(MouseEvent event) {
        if (nameOfTheProduct.getText().equals("")) {
            nameOfTheProduct.setStyle("-fx-border-color: transparent transparent #e30909 transparent, transparent transparent transparent transparent;");
        } else {
            nameOfTheProduct.setStyle("-fx-border-color: transparent transparent #000000 transparent, transparent transparent transparent transparent;");
            if (compositeProducts.size() > 1) {
                CompositeProduct compositeProduct = new CompositeProduct();
                compositeProduct.setProductList(compositeProducts);
                deliveryService.getProducts().add(new BaseProduct(nameOfTheProduct.getText(), compositeProduct.getRating(), compositeProduct.getCalories(),
                        compositeProduct.getProtein(), compositeProduct.getFat(), compositeProduct.getSodium(),
                        compositeProduct.computePrice()));
                deliveryServiceDAO.writeObject(deliveryService);
                compositeProducts.removeAll(compositeProducts);
                compositeList.removeAll(compositeList);
                nameOfTheProduct.setText("");
                refresh();
            }
        }
    }

    @FXML
    void createNewReport(MouseEvent event) {

        if (createByCombo.getTypeSelector().equals("")) {

        } else if (createByCombo.getSelectionModel().getSelectedItem().equals("time interval")) {
            if (validateTimeInterval()) {
                deliveryService.createReportByTimeInterval(startHour.getValue(), endHour.getValue());
                deliveryServiceDAO.writeObject(deliveryService);
            }
        } else if (createByCombo.getSelectionModel().getSelectedItem().equals("products ordered more than")) {
            try {
                Integer n = Integer.parseInt(numberOfTimes.getText());
                deliveryService.createReportByProducts(n);
                deliveryServiceDAO.writeObject(deliveryService);
            } catch (NumberFormatException e) {
            }
        } else if (createByCombo.getSelectionModel().getSelectedItem().equals("clients who ordered > ? and amount of the order > ?")) {
            try {
                Integer n = Integer.parseInt(numberOfTimes.getText());
                Integer m = Integer.parseInt(orderAmount.getText());
                deliveryService.createReportByClientsAndOrderAmount(n, m);
                deliveryServiceDAO.writeObject(deliveryService);
            } catch (NumberFormatException e) {

            }
        } else if (createByCombo.getSelectionModel().getSelectedItem().equals("products ordered within a day")) {
            if (datePicker.getValue() != null) {
                String x = "";
                x = datePicker.getValue().toString();
                String parts[] = x.split("-");
                Date date = new Date(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                deliveryService.createReportByProductsWithinADay(date);
                deliveryServiceDAO.writeObject(deliveryService);
            }

        }

    }

    public boolean validateTimeInterval() {
        if (startHour.getSelectionModel().getSelectedItem() == null) {
            return false;
        } else if (endHour.getSelectionModel().getSelectedItem() == null) {
            return false;
        }
        return true;
    }

    @FXML
    void refreshTable(MouseEvent event) {
        refresh();
    }

}

