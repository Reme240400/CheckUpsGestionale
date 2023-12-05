package Models;

import com.jfoenix.controls.JFXComboBox;

import Models.Tables.Societa;
import Models.Tables.UnitaLocale;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ModelHome {
    private int id = -1;    

    public void onKeyPressedFilter(JFXComboBox<String> cercaSocieta, JFXComboBox<String> cercaUnitaLocale,
            List<Societa> listSocieta, List<UnitaLocale> listUnitaLocale) {

        if (cercaSocieta.getValue() != null) {
            // Remove the previous listener, if any
            String selectedSocieta = cercaSocieta.getValue();

            for (Societa societa : listSocieta) {
                if (societa.getNome().equals(selectedSocieta)) {
                    this.id = societa.getId();
                }
            }

            // Retrieve the list of UnitaLocale based on the selected Societa
            List<String> filtroUnitaLocali = new ArrayList<>();

            // Filter UnitaLocale based on the selected Societa
            for (UnitaLocale unitaLocale : listUnitaLocale) {
                if (unitaLocale.getIdSocieta() == this.id) {
                    filtroUnitaLocali.add(unitaLocale.getNome());
                }
            }

            ObservableList<String> units = FXCollections.observableArrayList(filtroUnitaLocali);
            cercaUnitaLocale.setItems(units);
        }

    }

}
