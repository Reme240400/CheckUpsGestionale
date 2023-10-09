package View.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Creazione implements Initializable{
    
    @FXML
    private JFXButton btnSocieta;

    @FXML 
    private JFXButton btnUnitaLocali;

    @FXML
    private JFXButton btnReparti;

    @FXML
    private StackPane stackPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnUnitaLocali.setDisable(true);
        btnReparti.setDisable(true);

        try{
            Parent root = FXMLLoader.load(getClass().getResource("/View/fxml/creazione_societa.fxml"));
            stackPane.getChildren().removeAll();
            stackPane.getChildren().setAll(root);

        }catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public void switchToSocieta(javafx.event.ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/View/fxml/creazione_societa.fxml"));
        stackPane.getChildren().removeAll();
        stackPane.getChildren().setAll(root);
    }

    public void switchToUnitaLocali(javafx.event.ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/View/fxml/creazione_unitalocale.fxml"));
        stackPane.getChildren().removeAll();
        stackPane.getChildren().setAll(root);
    }

    public void switchToReparti(javafx.event.ActionEvent event) {
        System.out.println("You clicked me!");
    }

    public void setEnableBtnUnitaLocale() {
        System.out.println("setEnableBtnUnitaLocale");
        btnUnitaLocali.setDisable(false);

    }

    public void setEnableBtnReparti(){
        
        btnReparti.setDisable(false);
    }

}
