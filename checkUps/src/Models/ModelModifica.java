package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXComboBox;

import Controllers.ClassHelper;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.UnitaLocale;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ModelModifica {

    private final BooleanProperty saved = new SimpleBooleanProperty(false);
    private final BooleanProperty isEnable = new SimpleBooleanProperty(false);
    private final IntegerProperty idSocieta = new SimpleIntegerProperty(-1);
    private final IntegerProperty idUnitaLocale = new SimpleIntegerProperty(-1);

    // ------------------ CONSTRUCTOR ------------------ //
    public BooleanProperty savedProperty() {
        return saved;
    }

    public BooleanProperty isEnableProperty() {
        return isEnable;
    }

    public IntegerProperty idSocietaProperty() {
        return idSocieta;
    }

    public IntegerProperty idUnitaLocaleProperty() {
        return idUnitaLocale;
    }

    // ------------------ SETTER ------------------ //
    public final void setSaved(boolean saved) {
        savedProperty().set(saved);
    }

    public final void setEnable(boolean isEnable) {
        isEnableProperty().set(isEnable);
    }

    public final void setIdSocieta(int idSocieta) {
        idSocietaProperty().set(idSocieta);
    }

    public void setIdUnitaLocale(int idUnita) {
        idUnitaLocaleProperty().set(idUnita);
    }

    // ------------------ GETTER ------------------ //
    public final boolean isSaved() {
        return savedProperty().get();
    }

    public final boolean isEnable() {
        return isEnableProperty().get();
    }

    public final int getIdSocietaTmp() {
        return idSocietaProperty().get();
    }

    public final int getIdUnitaLocaleTmp() {
        return idUnitaLocaleProperty().get();
    }

    // ------------------ Riempie i campi con le informazioni prese dalle liste ------------------ //
    public void fillTextField(JFXComboBox<String> cercaRecord, TextField textFieldSocieta,
            TextField textFieldIndirizzo, TextField textFieldLocalita, TextField textFieldProvincia,
            TextField textFieldTel) {
    
        String societa = cercaRecord.getValue();

        if (societa != null) {
            ClassHelper.getListSocieta().stream().filter(s -> s.getNome().equals(societa)).forEach(s -> {
                textFieldSocieta.setText(s.getNome());
                textFieldIndirizzo.setText(s.getIndirizzo());
                textFieldLocalita.setText(s.getLocalita());
                textFieldProvincia.setText(s.getProvincia());
                textFieldTel.setText(String.valueOf(s.getTelefono()));
            });
            setSaved(true);
            setEnable(true);
        }
    }

    public void fillTextField(JFXComboBox<String> cercaRecord, int idS, TextField textFieldUnita,
            TextField textFieldIndirizzo, TextField textFieldLocalita, TextField textFieldProvincia) {
    
        String societa = cercaRecord.getValue();

        if (societa != null) {
            ClassHelper.getListUnitaLocale().stream().filter(u -> u.getIdSocieta() == idS).forEach(u -> {
                textFieldUnita.setText(u.getNome());
                textFieldIndirizzo.setText(u.getIndirizzo());
                textFieldLocalita.setText(u.getLocalita());
                textFieldProvincia.setText(u.getProvincia());
            });

            setSaved(true);
            setEnable(true);
        }
    }
    
    public List<Reparto> fillRepartiTable(List<Reparto> listaReparti) {
        List<Reparto> specificList = listaReparti.stream()
                                            .filter(reparto -> reparto.getIdUnitaLocale() == getIdUnitaLocaleTmp())
                                            .toList();

        return specificList;
    }

    public List<Reparto> fillAllRepartiTable(List<Reparto> listaReparti, List<UnitaLocale> listaUnita) {

        List<UnitaLocale> allUnitaLocali = listaUnita.stream().filter( unita -> unita.getIdSocieta() == getIdSocietaTmp()).toList();

        List<Reparto> allReparti = allUnitaLocali.stream()
            .flatMap(unita -> unita.getReparti().stream())
            .collect(Collectors.toList());

        return allReparti;
 
    }

    public void filterTable( TextField filterTextField, TableView<Reparto> tableView, ObservableList<Reparto> observableList) {

        String filterText = filterTextField.getText().toLowerCase().trim();
    
        // Create a filtered list based on the original observableList
        FilteredList<Reparto> filteredData = new FilteredList<>(observableList, reparto -> {
            if (filterText.isEmpty()) {
                return true; // Show all items when no filter is applied
            }
            // Check if the name contains the filter text (case-insensitive)
            return reparto.getNome().toLowerCase().contains(filterText);
        });

        // Bind the filtered data to the TableView
        tableView.setItems(filteredData);
    }

    public void onKeyPressedFilter(KeyEvent event, JFXComboBox<String> cercaSocieta, JFXComboBox<String> cercaUnitaLocale,
            List<Societa> listSocieta, List<UnitaLocale> listUnitaLocale) {

        if (event.getCode().equals(KeyCode.ENTER)) {
            // Remove the previous listener, if any
            String selectedSocieta = cercaSocieta.getValue();

            for (Societa societa : listSocieta) {
                if (societa.getNome().equals(selectedSocieta)) {
                    setIdSocieta(societa.getId());
                    System.out.println("Societa selezionata: " + idSocieta);
                }
            }

            // Retrieve the list of UnitaLocale based on the selected Societa
            List<String> filtroUnitaLocali = new ArrayList<>();

            // Filter UnitaLocale based on the selected Societa
            for (UnitaLocale unitaLocale : listUnitaLocale) {
                if (unitaLocale.getIdSocieta() == getIdSocietaTmp()) {
                    System.out.println("aggiunte unita locali" + unitaLocale.getNome());
                    filtroUnitaLocali.add(unitaLocale.getNome());
                }
            }

            ObservableList<String> units = FXCollections.observableArrayList(filtroUnitaLocali);
            cercaUnitaLocale.setItems(units);
        }

    }

}
