package View.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Models.Model;
import Models.ModelCreazione;
import Models.ModelHome;
import Models.ModelModifica;
import View.Controllers.Creazione.Creazione;
import View.Controllers.Modifiche.Modifica;
import javafx.fxml.Initializable;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ViewController implements Initializable{

    @FXML
    private JFXButton btnQuit;

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnModify;

    @FXML
    private JFXButton btnCreate;

    @FXML
    private StackPane stackPane;

    static ModelCreazione modelCreazione = new ModelCreazione();
    static ModelHome modelHome = new ModelHome();
    static ModelModifica modelModifica = new ModelModifica();

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
            switchToHome();

        }catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void switchToHome() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/home.fxml"));
        Parent root = loader.load();
        Home home = loader.getController();

        home.setModel(modelHome);

        stackPane.getChildren().removeAll();
        stackPane.getChildren().setAll(root);

    }

    public void switchToCreazione(ActionEvent event) throws IOException{
        

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/main_creazione.fxml"));

        Parent root = loader.load();
        Creazione creazione = loader.getController();

        creazione.setModelCreazione(modelCreazione);

        stackPane.getChildren().removeAll();
        stackPane.getChildren().setAll(root);
    }

    public void switchToModifica(ActionEvent event) throws IOException{
        

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/modifica.fxml"));

        Parent root = loader.load();
        Modifica modifica = loader.getController();

        modifica.setModel(modelModifica);

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

    public static FilteredList<String> filterComboBoxSocieta(JFXComboBox<String> cercaItem, ObservableList<String> units) {

        return modelCreazione.filterComboBoxSocieta(cercaItem, units);
    }

    public static FilteredList<String> filterComboBoxUnitaLocale(JFXComboBox<String> cercaItem, int id, ObservableList<String> units) {

        return modelCreazione.filterComboBoxById(cercaItem, id, units);
    }

    public static FilteredList<String> filterComboBoxReparti(JFXComboBox<String> cercaItem, int id, ObservableList<String> units) {

        return modelCreazione.filterComboBoxById(cercaItem, id, units);
    }

    
}
