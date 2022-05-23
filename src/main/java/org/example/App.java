package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.businessLogic.User;
import org.example.dataAccess.UserDAO;

import java.io.IOException;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("log-view"));
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        if(fxml.equals("ADMINISTRATOR-view") || fxml.equals("CLIENT-view") || fxml.equals("EMPLOYEE-view") ){
            scene.getWindow().setHeight(840);
            scene.getWindow().setWidth(1215);
        }
        if(fxml.equals("log-view")){
            scene.getWindow().setHeight(615);
            scene.getWindow().setWidth(840);
        }

        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}