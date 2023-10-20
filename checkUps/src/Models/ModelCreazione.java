package Models;

import Models.Tables.Societa;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TextField;

public class ModelCreazione {

    private final BooleanProperty societySaved = new SimpleBooleanProperty(false);
    private final BooleanProperty unitaLocaleSaved = new SimpleBooleanProperty(false);
    private final BooleanProperty saved = new SimpleBooleanProperty(false);
    private final BooleanProperty discard = new SimpleBooleanProperty(false);

    private Societa societaTmp = null;

    // initialize methods
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
    // end initialize methods

    // ------------------ GETTER ------------------ //
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

    public void reset() {

        setSocietySaved(false);
        setUnitaSaved(false);
        setSaved(false);
        setDiscard(false);
    }

    public Societa getSocietaTmp() {
        return societaTmp;
    }

    public void setSocietaTmp(Societa societaTmp) {
        this.societaTmp = societaTmp;
        System.out.println("setSocietaTmp: " + societaTmp.getNome());
    }

    public void isTextFilled(TextField textFieldSocieta, TextField textFieldIndirizzo, TextField textFieldLocalita,
            TextField textFieldProvincia, TextField textFieldTel) {

        String txtSocieta = textFieldSocieta.getText();
        String txtIndirizzo = textFieldIndirizzo.getText();
        String txtLocalita = textFieldLocalita.getText();
        String txtProvincia = textFieldProvincia.getText();
        String txtTel = textFieldTel.getText();
        

        boolean areAllDisabled = (txtSocieta.isEmpty() ||
                txtSocieta.trim().isEmpty() ||
                txtIndirizzo.isEmpty() ||
                txtIndirizzo.trim().isEmpty() ||
                txtLocalita.isEmpty() ||
                txtLocalita.trim().isEmpty() ||
                txtProvincia.isEmpty() ||
                txtProvincia.trim().isEmpty() ||
                txtTel.isEmpty() ||
                txtTel.trim().isEmpty());

        boolean isDisabled = (txtSocieta.isEmpty() &&
                txtSocieta.trim().isEmpty() &&
                txtIndirizzo.isEmpty() &&
                txtIndirizzo.trim().isEmpty() &&
                txtLocalita.isEmpty() &&
                txtLocalita.trim().isEmpty() &&
                txtProvincia.isEmpty() &&
                txtProvincia.trim().isEmpty() &&
                txtTel.isEmpty()
                && txtTel.trim().isEmpty());

        setSaved(!areAllDisabled);
        setDiscard(!isDisabled);
        
    }

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
            isTextFilled(textFieldSocieta,
                    textFieldIndirizzo, textFieldLocalita, textFieldProvincia, textFieldTel);

            // model.setSaved(areDisabled[0]);
            //setDiscard(areDisabled[1]);
            // * ************************************************ //
        }
    }

}
