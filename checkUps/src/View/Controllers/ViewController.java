package View.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Controllers.ControllerDb;
import Models.Model;
import Models.ModelCreazione;
import Models.ModelHome;
import Models.ModelModifica;
import Models.ModelPaths;
import Models.ModelValutaRischi;
import Models.Tables.Societa;
import Models.Tables.UnitaLocale;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private JFXButton btnModify;

    @FXML
    private JFXButton btnCreate;

    @FXML
    private StackPane stackPane;

    static Model model = new Model();
    static ModelPaths modelPaths = new ModelPaths();
    static ModelCreazione modelCreazione = new ModelCreazione();
    static ModelHome modelHome = new ModelHome();
    static ModelModifica modelModifica = new ModelModifica();
    static ModelValutaRischi modelValutaRischi = new ModelValutaRischi();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ControllerDb.popolaListeDaDatabase();

        btnQuit.setOnAction(event -> {
            try {
                logout(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        try{
            switchToHome();
            modelPaths.setStackPaneHome(stackPane);
        }catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void switchToHome() throws IOException{
        
        Parent root = modelPaths.switchToHome(modelHome, this);
        if(root != null){
            stackPane.getChildren().removeAll();
            stackPane.getChildren().setAll(root);
        }

    }

    public void switchToCreazione() throws IOException{
        
        Parent root = modelPaths.switchToCreazione(modelCreazione); 

        System.out.println("StackPane 1: " + stackPane.getChildren());
        if(root != null){
            stackPane.getChildren().removeAll();
            stackPane.getChildren().setAll(root);
        }
    }

    public void switchToModifica() throws IOException{
        
        Parent root = modelPaths.switchToModifica(modelModifica);

        if(root != null){
            stackPane.getChildren().removeAll();
            stackPane.getChildren().setAll(root);
        }
        
    }

    public void switchToValutaRischi(Societa societa, UnitaLocale unitaLocale) throws IOException{

        Parent root = modelPaths.switchToValutaRischi(modelValutaRischi, societa, unitaLocale);

        if(root != null){
            stackPane.getChildren().removeAll();
            stackPane.getChildren().setAll(root);
        }
    }

    public void logout(ActionEvent event) throws IOException{
        System.out.println("Logout");
        Alert alert = new Alert(AlertType.CONFIRMATION);

        alert.setTitle("Esci");
        alert.setHeaderText("Stai per uscire!");
        alert.setContentText("Vuoi salvare il lavoro prima di uscire?");


        if(alert.showAndWait().get().getText().equals("OK")){
            Stage stage = (Stage) btnQuit.getScene().getWindow();
            System.out.println("Salvataggio in corso...");
            stage.close();
        }
    }

    public static FilteredList<String> filterComboBoxSocieta(JFXComboBox<String> cercaItem, ObservableList<String> units) {

        return model.filterComboBoxSocieta(cercaItem, units);
    }

    public static FilteredList<String> filterComboBoxUnitaLocale(JFXComboBox<String> cercaItem, int id, ObservableList<String> units) {

        return model.filterComboBoxById(cercaItem, id, units);
    }

    public static FilteredList<String> filterComboBoxReparti(JFXComboBox<String> cercaItem, int id, ObservableList<String> units) {

        return model.filterComboBoxById(cercaItem, id, units);
    }

    
}
