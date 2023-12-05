package Models;

import java.util.List;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXComboBox;

import Helpers.ClassHelper;
import Models.Tables.Oggetto;
import Models.Tables.Provvedimento;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class ModelModifica extends ModelListe{

    private boolean check = false;

    private final BooleanProperty saved = new SimpleBooleanProperty(false);
    private final BooleanProperty isEnable = new SimpleBooleanProperty(false);
    private final BooleanProperty selectedReparto = new SimpleBooleanProperty(false);
    private final BooleanProperty selectedOggetto = new SimpleBooleanProperty(false);
    private Societa societaTmp = null;
    private UnitaLocale unitaLocaleTmp = null;
    private Reparto repartoTmp = null;
    private Titolo titoloTmp = null;
    private Oggetto oggettoTmp = null;
    private Provvedimento provvedimentoTmp = null;

    // ------------------ CONSTRUCTOR ------------------ //
    public BooleanProperty savedProperty() {
        return saved;
    }

    public BooleanProperty isEnableProperty() {
        return isEnable;
    }

    public BooleanProperty selectedRepartoProperty() {
        return selectedReparto;
    }

    public BooleanProperty selectedOggettoProperty() {
        return selectedOggetto;
    }
    // ------------------ SETTER ------------------ //
    public final void setSaved(boolean saved) {
        savedProperty().set(saved);
    }

    public final void setEnable(boolean isEnable) {
        isEnableProperty().set(isEnable);
    }

    public final void setSelectedReparto(boolean selectedReparto) {
        selectedRepartoProperty().set(selectedReparto);
    }
 
    public final void setSelectedOggetto(boolean selectedOggetto){
        selectedOggettoProperty().set(selectedOggetto);
    }


    public void setSocieta(Societa societa) {
        this.societaTmp = societa;
    }

    public final void setUnitaLocale(UnitaLocale unitaLocale) {
        this.unitaLocaleTmp = unitaLocale;
        
    }

    public final void setReparto(Reparto reparto) {
        this.repartoTmp = reparto;
    }

    public final void setTitolo(Titolo titolo) {
        this.titoloTmp = titolo;
    }

    public void setOggetto(Oggetto oggetto) {
        this.oggettoTmp = oggetto;
    }

    public void setProvvedimento(Provvedimento prov) {
        this.provvedimentoTmp = prov;
    }


    // ------------------ GETTER ------------------ //
    public final boolean isSaved() {
        return savedProperty().get();
    }

    public final boolean isEnable() {
        return isEnableProperty().get();
    }

    public final boolean isSelectedReparto() {
        return selectedRepartoProperty().get();
    }

    public final Societa getSocietaTmp() {
        
        return societaTmp;
    }

    public final UnitaLocale getUnitaLocaleTmp() {
        return unitaLocaleTmp;
    }

    public final Reparto getRepartoTmp() {
        return repartoTmp;
    }

    public final Titolo getTitoloTmp() {
        return titoloTmp;
    }

    public final Oggetto getOggettoTmp() {
        return oggettoTmp;
    }
    
    public final Provvedimento getProvTmp(){
        return provvedimentoTmp;
    }

    // ------------------ RESETTER ------------------ //
    public void resetSocietaTmp() {
        this.societaTmp = null;
    }

    public void resetUnitaLocaleTmp() {
        this.unitaLocaleTmp = null;
    }

    public void resetRepartoTmp() {
        this.repartoTmp = null;
    }

    public void resetTitoloTmp() {
        this.titoloTmp = null;
    }

    public void resetOggettoTmp() {
        this.oggettoTmp = null;
    }

    public void resetProvTmp(){
        this.provvedimentoTmp = null;
    }

    public void resetAllTmp() {
        resetSocietaTmp();
        resetUnitaLocaleTmp();
        resetRepartoTmp();
        resetTitoloTmp();
        resetOggettoTmp();
        resetProvTmp();
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
                                            .filter(reparto -> reparto.getIdUnitaLocale() == getUnitaLocaleTmp().getId())
                                            .toList();

        return specificList;
    }

    public List<Reparto> fillAllRepartiTable(List<Reparto> listaReparti, List<UnitaLocale> listaUnita) {

        List<UnitaLocale> allUnitaLocali = listaUnita.stream().filter( unita -> unita.getIdSocieta() == getSocietaTmp().getId()).toList();

        List<Reparto> allReparti = allUnitaLocali.stream()
            .flatMap(unita -> filtraRepartoDaUnita(unita.getId()).stream())
            .collect(Collectors.toList());

        return allReparti;

    }

    public List<Titolo> fillTitoliTable(List<Titolo> listaTitoli) {
        List<Titolo> specificList = listaTitoli.stream()
                                            .filter(titolo -> titolo.getIdReparto() == getRepartoTmp().getId())
                                            .toList();
        
        return specificList;  
    }

    public List<Oggetto> fillOggettiTable(List<Oggetto> listaOggetti) {
        List<Oggetto> specificList = listaOggetti.stream()
                                            .filter(o -> o.getIdTitolo() == getTitoloTmp().getId())
                                            .toList();
        
        return specificList;  
    }
    
    public List<Provvedimento> fillProvvedimentiTable(List<Provvedimento> listaProvvedimenti) {
        List<Provvedimento> specificList = listaProvvedimenti.stream()
                                            .filter(p -> p.getIdOggetto() == getOggettoTmp().getId())
                                            .toList();
        
        return specificList;  
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
                    check = titolo.getDescrizione().contains(filterText);
                
                default:
                    return check;
            }
            return check;

        });

        // Bind the filtered data to the TableView
        tableView.setItems(filteredData);
    }
    
}
