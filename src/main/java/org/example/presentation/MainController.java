package org.example.presentation;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.example.App;
import org.example.businessLogic.DeliveryService;
import org.example.businessLogic.MenuItem;
import org.example.businessLogic.Order;
import org.example.businessLogic.User;
import org.example.dataAccess.DeliveryServiceDAO;
import org.example.dataAccess.UserDAO;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private JFXButton connectButton;

    @FXML
    private Text errorText;

    @FXML
    private FontAwesomeIconView hidePassword;

    @FXML
    private PasswordField password;

    @FXML
    private JFXButton registryButton;

    @FXML
    private FontAwesomeIconView showPassword;

    @FXML
    private TextField userName;

    @FXML
    private TextField showPasswordField;

    private List<User> users;
    private UserDAO userDAO;
    private DeliveryService deliveryService;
    private DeliveryServiceDAO deliveryServiceDAO;

    @FXML
    void connectButton(MouseEvent event) throws IOException {
        errorText.setText("");
        if(setError()){
            String type = userDAO.getUserType(users, userName.getText());
            type.toLowerCase();
            deliveryService.setUserName(userName.getText());
            deliveryServiceDAO.writeObject(deliveryService);
           App.setRoot(type + "-view");
        }
    }

    @FXML
    void hidePassword(MouseEvent event) {
        showPassword.setDisable(false);
        hidePassword.setDisable(true);
        hidePassword.setVisible(true);
        showPassword.setVisible(true);

        password.setDisable(false);
        password.setVisible(true);
        showPasswordField.setDisable(true);
        showPasswordField.setVisible(false);
        password.setText(showPasswordField.getText());
    }

    @FXML
    void registryButton(MouseEvent event) throws IOException {
        errorText.setText("");
        if(setError2()){
            String pass;
            if(password.isDisable()) {
                pass = showPassword.getText();
            }else{
                pass = password.getText();
            }
            User user = new User(userName.getText(), pass, "CLIENT");
            users.add(user);
            userDAO.writeObject(users);
            deliveryService.setUserName(user.getUserName());
            deliveryServiceDAO.writeObject(deliveryService);
            App.setRoot("client-view");
        }
    }

    @FXML
    void showPassword(MouseEvent event) {
        showPassword.setDisable(true);
        hidePassword.setDisable(false);
        showPassword.setVisible(false);
        hidePassword.setVisible(true);

        password.setDisable(true);
        password.setVisible(false);
        showPasswordField.setDisable(false);
        showPasswordField.setVisible(true);
        showPasswordField.setText(password.getText());


    }

    private boolean setError() {
        if (userName.getText().equals("") || !userDAO.exists(users, userName.getText())) {
            errorText.setText("Username invalid");
            return false;
        } else {
            if (password.isDisable()) {
                if (showPasswordField.getText().equals("") || !userDAO.isPasswordCorrect(users, userName.getText(), showPasswordField.getText())) {
                    errorText.setText("Wrong password");
                    return false;
                }
            } else {
                if (password.getText().equals("") || !userDAO.isPasswordCorrect(users, userName.getText(), password.getText()))
                {
                    errorText.setText("Wrong password");
                    return false;
                }
            }
        }
        return true;
    }
    private boolean setError2() {
        if (userName.getText().equals("") || userDAO.exists(users, userName.getText())) {
            errorText.setText("Username invalid");
            return false;
        } else {
            if (password.isDisable()) {
                if (showPasswordField.getText().equals("")) {
                    errorText.setText("Wrong password");
                    return false;
                }
            } else {
                if (password.getText().equals(""))
                {
                    errorText.setText("Wrong password");
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deliveryServiceDAO = new DeliveryServiceDAO();
        deliveryService = deliveryServiceDAO.readObject();
        showPasswordField.setVisible(false);
        showPasswordField.setDisable(true);
        userDAO = new UserDAO();
        users = userDAO.readObject();
    }
}
