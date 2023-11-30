

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.IOExceptionList;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Database.FileFormat;
import com.healthmarketscience.jackcess.DatabaseBuilder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


public class myMain extends Application{
    private double x = 0;
    private double y = 0;

    @Override
    public void start(javafx.stage.Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/View/fxml/main.fxml"));
        primaryStage.setTitle("CheckUp Gestionale");

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y  = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
        });

        primaryStage.setScene( new Scene(root));
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            logout(primaryStage);
            }
        );
    }

    public void logout(Stage stage){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        System.out.println("Logout");

        alert.setTitle("Esci");
        alert.setHeaderText("Stai per uscire!");
        alert.setContentText("Vuoi salvare il lavoro prima di uscire?");

        if(alert.showAndWait().get().getText().equals("OK")){
            System.out.println("Salvataggio in corso...");
            stage.close();
        }
    }

    public static void main(String[] args) { 
        
        launch(args);
        
    }

}

