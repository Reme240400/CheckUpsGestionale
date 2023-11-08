package Models;

import com.jfoenix.controls.JFXComboBox;

import Controllers.ClassHelper;
import Models.Tables.Societa;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TextField;

public class ModelCreazione {

    // initialize variables

    private final BooleanProperty societySaved = new SimpleBooleanProperty(false);
    private final BooleanProperty unitaLocaleSaved = new SimpleBooleanProperty(false);
    private final BooleanProperty saved = new SimpleBooleanProperty(false);
    private final BooleanProperty discard = new SimpleBooleanProperty(false);
    private final BooleanProperty isEnable = new SimpleBooleanProperty(true);
    private Societa societaTmp = null;

    // end initialize variables

    // --------------------- initialize methods --------------------- //

    public BooleanProperty isEnableProperty() {
        return isEnable;
    }

    public BooleanProperty societaSavedProperty() {
        return societySaved;
    }

    public BooleanProperty discardProperty() {
        return discard;
    }

    public BooleanProperty savedProperty() {
        return saved;
    }

    public BooleanProperty unitaLocaleSavedProperty() {
        return unitaLocaleSaved;
    }
    // -------------------- end initialize methods -------------------- //

    // ------------------ GETTER ------------------ //

    public final boolean isEnable() {
        return isEnableProperty().get();
    }

    public final boolean isSocietySaved() {
        return societaSavedProperty().get();
    }

    public final boolean isUnitaLocaleSaved() {
        return unitaLocaleSavedProperty().get();
    }

    public final boolean isSaved() {
        return savedProperty().get();
    }

    public final boolean isDiscard() {
        return discardProperty().get();
    }
    // ------------------ END GETTER ------------------ //

    // ------------------ SETTER ------------------ //

    public final void setEnable(boolean isEnable) {
        isEnableProperty().set(isEnable);
    }

    // * modifica anche lo stato del bottone unitaLocale
    public final void setSocietySaved(boolean societySaved) {
        societaSavedProperty().set(societySaved);
    }

    public final void setUnitaSaved(boolean unitaLocaleSaved) {
        unitaLocaleSavedProperty().set(unitaLocaleSaved);
    }

    public final void setSaved(boolean saved) {
        savedProperty().set(saved);
    }

    public final void setDiscard(boolean discard) {
        discardProperty().set(discard);
    }
    // ------------------ END SETTER ------------------ //

    // ------------------ SocietaTmp ------------------ //
    public Societa getSocietaTmp() {
        return societaTmp;
    }

    public void createSocietaTmp(Societa societaTmp) {
        this.societaTmp = societaTmp;
        System.out.println("setSocietaTmp: " + societaTmp.getNome());
    }

    public void resetSocietaTmp() {
        this.societaTmp = null;
        setSocietySaved(false);
    }
    // ------------------ END SocietaTmp ------------------ //

    // ------------------ Controllo se i campi sono stati inseriti
    // ------------------ //
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

    // ------------------ Setta i campi come sono stati salvati ------------------
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
            setSocietySaved(true);

            createSocietaTmp(ClassHelper.getListSocieta().stream().filter(s -> s.getNome().equals(societa)).findFirst().get());
        } else {
            setDiscard(false);
            setSocietySaved(false);
        }
    }

}
