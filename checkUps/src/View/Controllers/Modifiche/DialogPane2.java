package View.Controllers.Modifiche;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import Controllers.ClassHelper;
import Models.Tables.Societa;
import Models.Tables.UnitaLocale;
import View.Controllers.ViewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;

public class DialogPane2 extends DialogPane1{

    @FXML
    private JFXComboBox<String> cercaSocietaR;

    @FXML
    private JFXComboBox<String> cercaUnitaLocaleR;


    private List<UnitaLocale> listUnitaLocale = ClassHelper.getListUnitaLocale();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // * *************** inizializza i campi *************** //

        ObservableList<String> societies = FXCollections.observableArrayList();

        // * *************** popola i combobox *************** //

        for (Societa societa : listSocieta) {
            cercaSocietaR.getItems().add(societa.getNome());
            societies.add(societa.getNome());
        }
        // * **************************************** //

        // * filtra i Combobox
        FilteredList<String> filteredItems = ViewController.filterComboBoxSocieta(cercaSocietaR, societies);

        cercaSocietaR.setItems(filteredItems);

        // * ************************************************ //
    }

    public void onSelectedSocieta(KeyEvent event) {

        modelModifica.onKeyPressedFilter(event, cercaSocietaR, cercaUnitaLocaleR, listSocieta, listUnitaLocale);
    }

    public void onSelectedUnita(KeyEvent event) {

        int id = listUnitaLocale.stream()
                                .filter(s -> s.getNome().equals(cercaUnitaLocaleR.getValue()))
                                .findFirst().get()
                                .getId();
                                
        System.out.println("Id unita locale: " + id);
        modelModifica.setIdUnitaLocale(id);
    }


}
