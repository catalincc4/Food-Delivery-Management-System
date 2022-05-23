package org.example.presentation;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.businessLogic.BaseProduct;
import org.example.businessLogic.DeliveryService;
import org.example.dataAccess.DeliveryServiceDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductEditController implements Initializable {

    @FXML
    private TextField calories;

    @FXML
    private JFXButton createNewProductButton4;

    @FXML
    private JFXButton createNewProductButton41;

    @FXML
    private TextField fat;

    @FXML
    private TextField price;

    @FXML
    private TextField protein;

    @FXML
    private TextField rating;

    @FXML
    private TextField sodium;

    @FXML
    private TextField title;

    private BaseProduct product;
    private BaseProduct product2;
    private Stage stage;
    private DeliveryServiceDAO deliveryServiceDAO;
    private DeliveryService deliveryService;


    @FXML
    void cancelEdit(MouseEvent event) {
              stage.close();
    }

    @FXML
    void saveInfo(MouseEvent event) {
        if(validateData()){
            deliveryService.getProducts().remove(product2);
            deliveryService.getProducts().add(product);
            deliveryServiceDAO.writeObject(deliveryService);
            stage.close();
        }

    }

    public boolean validateData(){
            if(title.getText().equals("")){
                title.setStyle("-fx-border-color: transparent transparent #ec0707 transparent, transparent transparent transparent transparent;");
                return false;
            }else {
                title.setStyle("-fx-border-color: transparent transparent #000000 transparent, transparent transparent transparent transparent;");
                product.setTitle(title.getText());
            }
            try{

            product.setRating(Double.parseDouble(rating.getText()));
                rating.setStyle("-fx-border-color: transparent transparent #000000 transparent, transparent transparent transparent transparent;");
            }catch (ArithmeticException e){
                rating.setStyle("-fx-border-color: transparent transparent #ec0707 transparent, transparent transparent transparent transparent;");
            return false;
            }

            try {
                product.setCalories(Integer.parseInt(calories.getText()));
                calories.setStyle("-fx-border-color: transparent transparent #000000 transparent, transparent transparent transparent transparent;");
            }catch (ArithmeticException e){

                calories.setStyle("-fx-border-color: transparent transparent #ec0707 transparent, transparent transparent transparent transparent;");
                return false;
            }
        try {
            product.setProtein(Integer.parseInt(protein.getText()));
            protein.setStyle("-fx-border-color: transparent transparent #000000 transparent, transparent transparent transparent transparent;");
        }catch (ArithmeticException e){

            protein.setStyle("-fx-border-color: transparent transparent #ec0707 transparent, transparent transparent transparent transparent;");
            return false;
        }

        try {
            product.setFat(Integer.parseInt(fat.getText()));
            fat.setStyle("-fx-border-color: transparent transparent #000000 transparent, transparent transparent transparent transparent;");
        }catch (ArithmeticException e){

            fat.setStyle("-fx-border-color: transparent transparent #ec0707 transparent, transparent transparent transparent transparent;");
            return false;
        }

        try {
            product.setSodium(Integer.parseInt(sodium.getText()));
            sodium.setStyle("-fx-border-color: transparent transparent #000000 transparent, transparent transparent transparent transparent;");
        }catch (ArithmeticException e){

            sodium.setStyle("-fx-border-color: transparent transparent #ec0707 transparent, transparent transparent transparent transparent;");
            return false;
        }

        try {
            product.setPrice(Integer.parseInt(price.getText()));
            price.setStyle("-fx-border-color: transparent transparent #000000 transparent, transparent transparent transparent transparent;");
        }catch (ArithmeticException e){

            price.setStyle("-fx-border-color: transparent transparent #ec0707 transparent, transparent transparent transparent transparent;");
            return false;
        }

        return true;
    }

    public void setProduct(BaseProduct product) {
        this.product = product;
        product2 = product;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void setTextFields(){
        title.setText(product.getTitle());
        rating.setText(String.valueOf(product.getRating()));
        calories.setText(String.valueOf(product.getCalories()));
        price.setText(String.valueOf(product.getPrice()));
        sodium.setText(String.valueOf(product.getSodium()));
        fat.setText(String.valueOf(product.getFat()));
        protein.setText(String.valueOf(product.getProtein()));

    }

    public void setDeliveryServiceDAO(DeliveryServiceDAO deliveryServiceDAO) {
        this.deliveryServiceDAO = deliveryServiceDAO;
    }

    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

}
