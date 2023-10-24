package View.Controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import com.jfoenix.controls.JFXComboBox;

import Controllers.ClassHelper;
import Controllers.ControllerDb;
import Models.ModelModifica;
import Models.Tables.Societa;
import Models.Tables.UnitaLocale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

public class Modifica implements Initializable{

    @FXML
    private JFXComboBox<String> cercaRecordS;

    @FXML
    private JFXComboBox<String> cercaRecordU;

    @FXML
    private TextField textFieldTel;

    private ModelModifica modelModifica ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    // * *************** inizializza i campi *************** //
        ControllerDb.popolaListaSocietaDaDb();
        List<Societa> listSocieta = ClassHelper.getListSocieta();
        List<UnitaLocale> listUnitaLocale = ClassHelper.getListUnitaLocale();
        ObservableList<String> sItems = FXCollections.observableArrayList();
        ObservableList<String> uItems = FXCollections.observableArrayList();

        // * *************** popola il combobox *************** //
        for (Societa societa : listSocieta) {
            cercaRecordS.getItems().add(societa.getNome());
            sItems.add(societa.getNome());
        }

        for (UnitaLocale unitaLocale : listUnitaLocale) {
            cercaRecordU.getItems().add(unitaLocale.getNome());
            uItems.add(unitaLocale.getNome());
        }

        // * filtra il Combobox
        FilteredList<String> filteredItems = ViewController.filterComboBox(cercaRecordS, sItems);

        cercaRecordS.setItems(filteredItems);

        FilteredList<String> filteredItems2 = ViewController.filterComboBox(cercaRecordU, uItems);

        cercaRecordU.setItems(filteredItems2);
        // * ************************************************ //
        
        
        
        // * controlla se vengono inseriti solo numeri
        UnaryOperator<TextFormatter.Change> filter = change -> {

            // remove any non-digit characters from inserted text:
            if (!change.getText().matches("\\d*")) {
                change.setText(change.getText().replaceAll("[^\\d]", ""));
            }

            if (change.getControlNewText().length() > 15) {
                return null;
            }

            return change;
        };
        
        TextFormatter<Integer> formatter = new TextFormatter<Integer>(new IntegerStringConverter(), null, filter);
        textFieldTel.setTextFormatter(formatter);
        // * **************************************** //
    }

    public void setModel(ModelModifica modelModifica) {
        this.modelModifica = modelModifica;
    }

    public void keyReleasedProperty() {


        
    }
    

}
