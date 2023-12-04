package View.Controllers.Modifiche.DialogPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import Helpers.ClassHelper;
import Models.Model;
import Models.ModelModifica;
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

    private Societa localSocieta;

    private ModelModifica modelModifica;

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
        FilteredList<String> filteredItems = Model.filterComboBox(cercaSocietaR, societies);

        cercaSocietaR.setItems(filteredItems);

        // * ************************************************ //
    }

    public void onSelectedSocieta() {

        if(cercaSocietaR.getSelectionModel().getSelectedItem() != null){
            
            localSocieta = listSocieta.stream()
                                        .filter(s -> s.getNome().equals(cercaSocietaR.getSelectionModel().getSelectedItem()))
                                        .findFirst().get();

            ObservableList<String> unitaLocali = FXCollections.observableArrayList();

            for (UnitaLocale unitaLocale : listUnitaLocale) {
                if (unitaLocale.getIdSocieta() == localSocieta.getId()) {
                    unitaLocali.add(unitaLocale.getNome());
                }
            }

            // * filtra i Combobox
            FilteredList<String> filteredItems = Model.filterComboBox(cercaUnitaLocaleR, unitaLocali);

            cercaUnitaLocaleR.setItems(filteredItems);
        }
    }

    public void onSelectedUnita() {

        if (cercaUnitaLocaleR.getValue() != null && !cercaUnitaLocaleR.getValue().equals("") ) {
            
            UnitaLocale unita = listUnitaLocale.stream()
                                    .filter(u -> u.getIdSocieta() == localSocieta.getId())
                                    .filter(u -> u.getNome().equals(cercaUnitaLocaleR.getValue()))
                                    .findFirst().get();
                                    
            modelModifica.setSocieta(localSocieta);
            modelModifica.setUnitaLocale(unita);
        }
    }

    public void setModel(ModelModifica modelModifica){
        this.modelModifica = modelModifica;
    }


}
