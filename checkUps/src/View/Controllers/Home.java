package View.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Controllers.ClassHelper;
import Models.Alerts;
import Models.ModelHome;
import Models.Tables.Societa;
import Models.Tables.UnitaLocale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;


public class Home extends ViewController {

    private List<UnitaLocale> listUnitaLocale = ClassHelper.getListUnitaLocale();
    private List<Societa> listSocieta = ClassHelper.getListSocieta();
    private ModelHome model;
    private ViewController controller;

    @FXML
    private JFXComboBox<String> cercaSocieta;

    @FXML
    private JFXComboBox<String> cercaUnitaLocale;

    @FXML
    private JFXButton btnValutaRischi;

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
        FilteredList<String> filteredItems = ViewController.filterComboBoxSocieta(cercaSocieta, societies);

        cercaSocieta.setItems(filteredItems);

        // * ************************************************ //
    }

    public void onSelectedSocieta() {

        model.onKeyPressedFilter(cercaSocieta, cercaUnitaLocale, listSocieta, listUnitaLocale);
    }

    public void goToValutaRischi() {
        if (cercaSocieta.getValue() != null && cercaUnitaLocale.getValue() != null) {
            try {
                Societa societa = listSocieta.stream()
                                                .filter(s -> s.getNome().equals(cercaSocieta.getValue()))
                                                .findFirst().get();
                    
                UnitaLocale unitaLocale = listUnitaLocale.stream()
                                                            .filter(u -> u.getNome().equals(cercaUnitaLocale.getValue()))
                                                            .findFirst().get();

                controller.switchToValutaRischi(societa, unitaLocale);
            } catch (IOException e) {
                e.printStackTrace();
            } 
        }else {
            Alerts.errorAllert();
        }
        
    }

    public void setModel(ModelHome model) {
        this.model = model;
    }

    public void setController(ViewController controller) {
        this.controller = controller;
    }

}
