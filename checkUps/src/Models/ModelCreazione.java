package Models;

import java.util.List;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXComboBox;

import Controllers.ClassHelper;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TextField;

public class ModelCreazione extends ModelListe{

    // initialize variables

    private final BooleanProperty saved = new SimpleBooleanProperty(false);
    private final BooleanProperty discard = new SimpleBooleanProperty(false);
    private final BooleanProperty isEnable = new SimpleBooleanProperty(true);
    private Societa societaTmp = null;
    private UnitaLocale unitaLocaleTmp = null;
    private Reparto repartoTmp = null;

    // end initialize variables

    // --------------------- initialize methods --------------------- //

    public BooleanProperty isEnableProperty() {
        return isEnable;
    }

    public BooleanProperty discardProperty() {
        return discard;
    }

    public BooleanProperty savedProperty() {
        return saved;
    }

    // -------------------- end initialize methods -------------------- //

    // ------------------ GETTER ------------------ //

    public final boolean isEnable() {
        return isEnableProperty().get();
    }

    public final boolean isSaved() {
        return savedProperty().get();
    }

    public final boolean isDiscard() {
        return discardProperty().get();
    }
    
    public Societa getSocietaTmp() {
        return societaTmp;
    } 
    
    public UnitaLocale getUnitaLocaleTmp() {
        
        return unitaLocaleTmp;
    }

    public Reparto getRepartoTmp() {
        return repartoTmp;
    }
    // ------------------ END GETTER ------------------ //

    // ------------------ SETTER ------------------ //

    public final void setEnable(boolean isEnable) {
        isEnableProperty().set(isEnable);
    }

    public final void setSaved(boolean saved) {
        savedProperty().set(saved);
    }

    public final void setDiscard(boolean discard) {
        discardProperty().set(discard);
    } 
    
    public void createSocietaTmp(Societa societaTmp) {
        this.societaTmp = societaTmp;
    }
    
    public void createUnitaLocaleTmp(UnitaLocale unitaLocale) {
        this.unitaLocaleTmp = unitaLocale;
    }

    public void createRepartoTmp(Reparto reparto) {
        this.repartoTmp = reparto;
    }
    // ------------------ END SETTER ------------------ //

    public void resetSocietaTmp() {
        this.societaTmp = null;
    }

    public void resetUnitaLocaleTmp() {
        this.unitaLocaleTmp = null;
    }

    // ------------------ Controllo se i campi sono stati inseriti ------------------ //
    public void isTextFilled(TextField textFieldSocieta, TextField textFieldIndirizzo, TextField textFieldLocalita,
            TextField textFieldProvincia, TextField textFieldTel) {

        String txtSocieta = textFieldSocieta.getText();
        String txtIndirizzo = textFieldIndirizzo.getText();
        String txtLocalita = textFieldLocalita.getText();
        String txtProvincia = textFieldProvincia.getText();
        String txtTel = textFieldTel.getText();

        boolean areAllEnable = (txtSocieta.isEmpty() ||
                txtSocieta.trim().isEmpty() ||
                txtIndirizzo.isEmpty() ||
                txtIndirizzo.trim().isEmpty() ||
                txtLocalita.isEmpty() ||
                txtLocalita.trim().isEmpty() ||
                txtProvincia.isEmpty() ||
                txtProvincia.trim().isEmpty() ||
                txtTel.isEmpty() ||
                txtTel.trim().isEmpty());

        boolean isEnable = (txtSocieta.isEmpty() &&
                txtSocieta.trim().isEmpty() &&
                txtIndirizzo.isEmpty() &&
                txtIndirizzo.trim().isEmpty() &&
                txtLocalita.isEmpty() &&
                txtLocalita.trim().isEmpty() &&
                txtProvincia.isEmpty() &&
                txtProvincia.trim().isEmpty() &&
                txtTel.isEmpty()
                && txtTel.trim().isEmpty());

        setSaved(!areAllEnable);
        setDiscard(!isEnable);

    }
    // ------------------ END ------------------ //

    // ------------------ Setta i campi come sono stati salvati ------------------ //
    // //
    public void setOldTextFields(TextField textFieldSocieta, TextField textFieldIndirizzo, TextField textFieldLocalita,
            TextField textFieldProvincia, TextField textFieldTel) {
        if (getSocietaTmp() != null) {
            textFieldSocieta.setText(getSocietaTmp().getNome());
            textFieldIndirizzo.setText(getSocietaTmp().getIndirizzo());
            textFieldLocalita.setText(getSocietaTmp().getLocalita());
            textFieldProvincia.setText(getSocietaTmp().getProvincia());
            textFieldTel.setText(String.valueOf(getSocietaTmp().getTelefono()));
            // textFieldDesc.setText(societaTmp.getDescrizione());

            // * ************ controlla se i campi sono vuoti ************ //
            isTextFilled(textFieldSocieta, textFieldIndirizzo, textFieldLocalita, textFieldProvincia, textFieldTel);

        }
    }
    // ------------------ END ------------------ //

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

            setDiscard(true);

            createSocietaTmp(ClassHelper.getListSocieta().stream().filter(s -> s.getNome().equals(societa)).findFirst().get());
        } else {
            setDiscard(false);
        }
    }

    public List<Reparto> fillRepartiTable(List<Reparto> listaReparti) {
        List<Reparto> specificList = listaReparti.stream()
            .filter(reparto -> reparto.getIdUnitaLocale() == getUnitaLocaleTmp().getId())
            .toList();

            System.out.println("SpecificList: " + specificList.size());
        return specificList;
    }

    public List<Reparto> fillAllRepartiTable(List<Reparto> listaReparto, List<UnitaLocale> listUnitaLocale) {
        List<UnitaLocale> allUnitaLocali = listUnitaLocale.stream()
            .filter( unita -> unita.getIdSocieta() == getSocietaTmp().getId())
            .toList();

        List<Reparto> allReparti = allUnitaLocali.stream()
            .flatMap(unita -> filtraRepartoDaUnita(unita.getId()).stream())
            .collect(Collectors.toList());

            System.out.println("AllReparti: " + allReparti.size());

        return allReparti;
    }

    public List<Titolo> fillTitoliTable(List<Titolo> listaTitolo, List<Reparto> listaReparto) {
        List<Titolo> specificList = listaTitolo.stream()
            .filter(titolo -> titolo.getIdReparto() == getRepartoTmp().getId())
            .toList();

            System.out.println("SpecificList: " + specificList.size());
        return specificList;
    }

    


}
