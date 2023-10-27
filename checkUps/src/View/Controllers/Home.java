package View.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Controllers.ClassHelper;
import Controllers.ControllerDb;
import Models.ModelHome;
import Models.ModelPaths;
import Models.Tables.Societa;
import Models.Tables.UnitaLocale;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;

import javafx.scene.input.KeyEvent;

public class Home extends ViewController {

    private List<UnitaLocale> listUnitaLocale = ClassHelper.getListUnitaLocale();
    private List<Societa> listSocieta = ClassHelper.getListSocieta();
    private ModelHome model;

    @FXML
    private JFXComboBox<String> cercaSocieta;

    @FXML
    private JFXComboBox<String> cercaUnitaLocale;

    @FXML
    private JFXButton btnValutaRischi;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // * *************** inizializza i campi *************** //
        ControllerDb.popolaListaSocietaDaDb();
        ControllerDb.popolaListaUnitaLocaleDaDb();

        List<Societa> listSocieta = ClassHelper.getListSocieta();

        ObservableList<String> societies = FXCollections.observableArrayList();

        // * *************** popola i combobox *************** //
        //cercaSocieta.getItems().add("Nuovo");
        //societies.add("Seleziona...");

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

    public void onSelectedSocieta(KeyEvent event) {

        model.onKeyPressedFilter(event, cercaSocieta, cercaUnitaLocale, listSocieta, listUnitaLocale);
    }

    public void goToValutaRischi() {
        try {
            switchToValutaRischi();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    public void setModel(ModelHome model) {
        this.model = model;
    }

}
