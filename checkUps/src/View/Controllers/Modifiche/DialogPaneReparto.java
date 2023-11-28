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

public class DialogPaneReparto extends DialogPaneUnita{

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

    public void onSelectedSocieta() {

        modelModifica.onKeyPressedFilter( cercaSocietaR, cercaUnitaLocaleR, listSocieta, listUnitaLocale);
    }

    public void onSelectedUnita() {

        if (cercaUnitaLocaleR.getValue() != null && !cercaUnitaLocaleR.getValue().equals("") ) {
            
            UnitaLocale unita = listUnitaLocale.stream()
                                    .filter(u -> u.getIdSocieta() == modelModifica.getSocietaTmp().getId())
                                    .filter(u -> u.getNome().equals(cercaUnitaLocaleR.getValue()))
                                    .findFirst().get();
                                    
            //System.out.println("Id unita locale: " + id);
            modelModifica.setUnitaLocale(unita);
        }
    }


}
