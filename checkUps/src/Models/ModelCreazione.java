package Models;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jfoenix.controls.JFXComboBox;

import Helpers.ClassHelper;
import Models.Tables.Oggetto;
import Models.Tables.Provvedimento;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TextField;

public class ModelCreazione extends ModelListe {

    // initialize variables

    private final BooleanProperty saved = new SimpleBooleanProperty(false);
    private final BooleanProperty discard = new SimpleBooleanProperty(false);
    private final BooleanProperty isEnable = new SimpleBooleanProperty(true);
    private final BooleanProperty canNext = new SimpleBooleanProperty(false);
    private Societa societaTmp = null;
    private UnitaLocale unitaLocaleTmp = null;
    private Reparto repartoTmp = null;
    private Titolo titoloTmp = null;
    private Oggetto oggettoTmp = null;
    private Provvedimento provvedimentoTmp = null;

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

    public BooleanProperty canGoNextProperty() {
        return canNext;
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

    public Titolo getTitoloTmp() {
        return titoloTmp;
    }

    public Oggetto getOggettoTmp() {
        return oggettoTmp;
    }

    public Provvedimento getProvvedimentoTmp() {
        return provvedimentoTmp;
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

    public final void setCanGoNext(boolean canNext) {
        canGoNextProperty().set(canNext);
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

    public void createTitoloTmp(Titolo newTitolo) {
        this.titoloTmp = newTitolo;
    }

    public void createOggettoTmp(Oggetto oggetto) {
        this.oggettoTmp = oggetto;
    }

    public void createProvvedimentoTmp(Provvedimento provvedimento) {
        this.provvedimentoTmp = provvedimento;
    }

    // ------------------ END SETTER ------------------ //

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

    public void resetProvvedimentoTmp() {
        this.provvedimentoTmp = null;
    }

    public void resetAllTmp() {
        resetSocietaTmp();
        resetUnitaLocaleTmp();
        resetRepartoTmp();
        resetTitoloTmp();
        resetOggettoTmp();
        resetProvvedimentoTmp();
    }

    // ------------------ Controllo se i campi sono stati inseriti
    // ------------------ //
    public void areTextFieldsFilled(TextField... textField) {
        boolean areAllEmpty = Stream.of(textField).allMatch(t -> t.getText().isEmpty());
        boolean isAnyEmpty = Stream.of(textField).anyMatch(t -> t.getText().isEmpty());

        setSaved(!isAnyEmpty);
        setDiscard(!areAllEmpty);
    }
    // ------------------ THE END ------------------ //

    // ------------------ Setta i campi come sono stati salvati ------------------
    // //
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
            areTextFieldsFilled(textFieldSocieta, textFieldIndirizzo, textFieldLocalita, textFieldProvincia,
                    textFieldTel);
        }
    }
    // ------------------ END ------------------ //

    // ------------------ Riempie i campi con le informazioni prese dalle liste
    // ------------------ //
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

            createSocietaTmp(
                    ClassHelper.getListSocieta().stream().filter(s -> s.getNome().equals(societa)).findFirst().get());
        } else {
            setDiscard(false);
        }
    }

    public List<Reparto> fillRepartiTable(List<Reparto> listaReparti, UnitaLocale unitaLocale) {
        List<Reparto> specificList = listaReparti.stream()
                .filter(reparto -> reparto.getIdUnitaLocale() == unitaLocale.getId())
                .toList();

        return specificList;
    }

    public List<Reparto> fillRepartiTable(List<Reparto> listaReparti) {
        List<Reparto> specificList = listaReparti.stream()
                .filter(reparto -> reparto.getIdUnitaLocale() == getUnitaLocaleTmp().getId())
                .toList();

        return specificList;
    }

    public List<Reparto> fillAllRepartiTable(List<Reparto> listaReparto, List<UnitaLocale> listUnitaLocale,
            Societa societa) {
        List<UnitaLocale> allUnitaLocali = listUnitaLocale.stream()
                .filter(unita -> unita.getIdSocieta() == societa.getId())
                .toList();

        List<Reparto> allReparti = allUnitaLocali.stream()
                .flatMap(unita -> filtraRepartoDaUnita(unita.getId()).stream())
                .collect(Collectors.toList());

        return allReparti;
    }

    public List<Reparto> fillAllRepartiTable(List<Reparto> listaReparto, List<UnitaLocale> listUnitaLocale) {
        List<UnitaLocale> allUnitaLocali = listUnitaLocale.stream()
                .filter(unita -> unita.getIdSocieta() == getSocietaTmp().getId())
                .toList();

        List<Reparto> allReparti = allUnitaLocali.stream()
                .flatMap(unita -> filtraRepartoDaUnita(unita.getId()).stream())
                .collect(Collectors.toList());

        return allReparti;
    }

    public List<Titolo> fillTitoliTable(List<Titolo> listaTitolo, List<Reparto> listaReparto, Reparto reparto) {
        List<Titolo> specificList = listaTitolo.stream()
                .filter(titolo -> titolo.getIdReparto() == reparto.getId())
                .toList();

        return specificList;
    }

    public List<Titolo> fillTitoliTable(List<Titolo> listaTitolo, List<Reparto> listaReparto) {
        List<Titolo> specificList = listaTitolo.stream()
                .filter(titolo -> titolo.getIdReparto() == getRepartoTmp().getId())
                .toList();

        return specificList;
    }

}
