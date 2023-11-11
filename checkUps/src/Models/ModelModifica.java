package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXComboBox;

import Controllers.ClassHelper;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
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

public class ModelModifica extends ModelListe{

    private boolean check = false;

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
            .flatMap(unita -> filtraRepartoDaUnita(unita.getId()).stream())
            .collect(Collectors.toList());

        return allReparti;

    }

    public <T> void  filterTable( TextField filterTextField, TableView<T> tableView, ObservableList<T> observableList) {

        String filterText = filterTextField.getText().toLowerCase().trim();
        
        // Create a filtered list based on the original observableList
        FilteredList<T> filteredData = new FilteredList<>(observableList, classe -> {
            if (filterText.isEmpty()) {
                return true; // Show all items when no filter is applied
            }
            // Check if the name contains the filter text (case-insensitive)
            switch (classe.getClass().getName()) {
                case "Reparto":
                    Reparto reparto = (Reparto) classe;
                    check = reparto.getNome().toLowerCase().contains(filterText);
                    break;

                case "Societa":
                    Societa societa = (Societa) classe;
                    check = societa.getNome().toLowerCase().contains(filterText);
                    break;

                case "UnitaLocale":
                    UnitaLocale unitaLocale = (UnitaLocale) classe;
                    check = unitaLocale.getNome().toLowerCase().contains(filterText);
                    break;
                
                case "Titolo":
                    Titolo titolo = (Titolo) classe;
                    check = (String.valueOf(titolo.getId())).contains(filterText);
                
                default:
                    return check;
            }
            return check;

        });

        // Bind the filtered data to the TableView
        tableView.setItems(filteredData);
    }

    public void onKeyPressedFilter(JFXComboBox<String> cercaSocieta, JFXComboBox<String> cercaUnitaLocale,
            List<Societa> listSocieta, List<UnitaLocale> listUnitaLocale) {

        if (cercaSocieta.getValue() != null) {
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
