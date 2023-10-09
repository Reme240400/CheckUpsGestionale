package View.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ViewController implements Initializable{

    @FXML
    private JFXButton btnQuit;

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnOrders;

    @FXML
    private StackPane stackPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnQuit.setOnAction(event -> {
            try {
                logout(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        try{
            Parent root = FXMLLoader.load(getClass().getResource("/View/fxml/home.fxml"));
            stackPane.getChildren().removeAll();
            stackPane.getChildren().setAll(root);

        }catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void switchToHome(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/View/fxml/home.fxml"));
        stackPane.getChildren().removeAll();
        stackPane.getChildren().setAll(root);

    }

    public void switchToCreazione(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/View/fxml/main_creazione.fxml"));
        stackPane.getChildren().removeAll();
        stackPane.getChildren().setAll(root);
    }

    public void logout(ActionEvent event) throws IOException{
        System.out.println("Logout");
        Alert alert = new Alert(AlertType.CONFIRMATION);

        alert.setTitle("Quit");
        alert.setHeaderText("Stai per uscire!");
        alert.setContentText("Vuoi salvare il lavoro prima di uscire?");

        if(alert.showAndWait().get().getText().equals("OK")){
            Stage stage = (Stage) btnQuit.getScene().getWindow();
            System.out.println("Salvataggio in corso...");
            stage.close();
        }
    }

    
}
