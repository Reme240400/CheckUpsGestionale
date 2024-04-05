package Models;

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
    private final BooleanProperty discard = new SimpleBooleanProperty(true);
    private final BooleanProperty canNext = new SimpleBooleanProperty(false);
    private Societa societaTmp = null;
    private UnitaLocale unitaLocaleTmp = null;
    private Reparto repartoTmp = null;
    private Titolo titoloTmp = null;
    private Oggetto oggettoTmp = null;
    private Provvedimento provvedimentoTmp = null;

    // end initialize variables

    // --------------------- initialize methods --------------------- //

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

    // public void areTextFieldsFilled(boolean comboBoxEmpty, TextField...
    // textField) {
    // boolean areAllEmpty = Stream.of(textField)
    // .allMatch(t -> t.getText() == null || t.getText().isEmpty());

    // boolean isAnyEmpty = Stream.of(textField)
    // .anyMatch(t -> t.getText() == null || t.getText().isEmpty());

    // setSaved(!isAnyEmpty && !comboBoxEmpty);
    // setCanGoNext(!isAnyEmpty);
    // setDiscard(!areAllEmpty);
    // }

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

            createSocietaTmp(
                    ClassHelper.getListSocieta().stream().filter(s -> s.getNome().equals(societa)).findFirst().get());
        }
    }
}
