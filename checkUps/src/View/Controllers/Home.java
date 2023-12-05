package View.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Controllers.Controller;
import Helpers.ClassHelper;
import Models.Alerts;
import Models.ModelHome;
import Models.ModelPaths;
import Models.ModelValutaRischi;
import Models.Tables.Societa;
import Models.Tables.UnitaLocale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;


public class Home implements Initializable{

    private List<UnitaLocale> listUnitaLocale = ClassHelper.getListUnitaLocale();
    private List<Societa> listSocieta = ClassHelper.getListSocieta();

    @FXML
    private JFXComboBox<String> cercaSocieta;

    @FXML
    private JFXComboBox<String> cercaUnitaLocale;

    @FXML
    private JFXButton btnValutaRischi;

    private StackPane stackPane;

    private ModelHome modelHome;
    private ModelPaths modelPaths;
    private ModelValutaRischi modelValutaRischi;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // * *************** inizializza i campi *************** //

        ObservableList<String> societies = FXCollections.observableArrayList();

        // * *************** popola i combobox *************** //

        for (Societa societa : listSocieta) {
            cercaSocieta.getItems().add(societa.getNome());
            societies.add(societa.getNome());
        }
        // * **************************************** //

        // * filtra i Combobox
        FilteredList<String> filteredItems = ViewController.filterComboBox(cercaSocieta, societies);

        cercaSocieta.setItems(filteredItems);

        // * ************************************************ //
    }

    public void onSelectedSocieta() {

        modelHome.onKeyPressedFilter(cercaSocieta, cercaUnitaLocale, listSocieta, listUnitaLocale);
    }

    public void goToValutaRischi() {
        if (cercaSocieta.getValue() != null && cercaUnitaLocale.getValue() != null && !cercaSocieta.getValue().isEmpty() && !cercaUnitaLocale.getValue().isEmpty()) {
            try {
                Societa societa = listSocieta.stream()
                                                .filter(s -> s.getNome().equals(cercaSocieta.getValue()))
                                                .findFirst().get();
                    
                UnitaLocale unitaLocale = listUnitaLocale.stream()
                                                            .filter(u -> u.getIdSocieta() == societa.getId())
                                                            .filter(u -> u.getNome().equals(cercaUnitaLocale.getValue()))
                                                            .findFirst().get();

                Parent root = modelPaths.switchToValutaRischi(modelValutaRischi, societa, unitaLocale);

                stackPane = modelPaths.getStackPaneHome();

                if(root != null){
                    Controller.changePane(stackPane,root);
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            } 
        }else {
            Alerts.errorAllert();
        }
        
    }

    public void setModel(ModelHome modelHome, ModelValutaRischi modelValutaRischi, ModelPaths modelPaths){
        this.modelHome = modelHome;
        this.modelPaths = modelPaths;
        this.modelValutaRischi = modelValutaRischi;


    }
}
