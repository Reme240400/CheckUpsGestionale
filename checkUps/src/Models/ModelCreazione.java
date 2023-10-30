package Models;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Models.Tables.Societa;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TextField;

public class ModelCreazione {

    // initialize variables 

    private final BooleanProperty societySaved = new SimpleBooleanProperty(false);
    private final BooleanProperty unitaLocaleSaved = new SimpleBooleanProperty(false);
    private final BooleanProperty saved = new SimpleBooleanProperty(false);
    private final BooleanProperty discard = new SimpleBooleanProperty(false);
    private Societa societaTmp = null;

    // end initialize variables


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

    // ------------------ Controllo se i campi sono stati inseriti ------------------ //
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
    // ------------------ END ------------------ //

    // ------------------ Setta i campi come sono stati salvati ------------------ //
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
    // ------------------ END ------------------ //


    public FilteredList<String> filterComboBoxSocieta(JFXComboBox<String> cercaItem, ObservableList<String> units ) {
        
        FilteredList<String> filteredList = new FilteredList<String>(units, p -> true);

            // Add a listener to the textProperty of the combobox editor. The
            // listener will simply filter the list every time the input is changed
            // as long as the user hasn't selected an item in the list.
            cercaItem.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
                final TextField editor = cercaItem.getEditor();
                final String selected = cercaItem.getSelectionModel().getSelectedItem();

                Platform.runLater(() -> {
                    // If the no item in the list is selected or the selected item
                    // isn't equal to the current input, we refilter the list.
                    if (selected == null || !selected.equals(editor.getText())) {
                        filteredList.setPredicate(item -> {
                            // We return true for any items that starts with the
                            // same letters as the input. We use toUpperCase to
                            // avoid case sensitivity.
                            if (item.toUpperCase().startsWith(newValue.toUpperCase())) {
                                return true;
                            } else {
                                return false;
                            }
                        });
                    }
                });
            });

        return filteredList;
        
    }

    public FilteredList<String> filterComboBoxById(JFXComboBox<String> cercaItem, int id, ObservableList<String> units ) {
        
        FilteredList<String> filteredUnita = new FilteredList<String>(units, p -> true);

            // Add a listener to the textProperty of the combobox editor. The
            // listener will simply filter the list every time the input is changed
            // as long as the user hasn't selected an item in the list.
            cercaItem.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
                final TextField editor = cercaItem.getEditor();
                final String selected = cercaItem.getSelectionModel().getSelectedItem();

                // This needs run on the GUI thread to avoid the error described
                // here: https://bugs.openjdk.java.net/browse/JDK-8081700.
                Platform.runLater(() -> {
                    // If the no item in the list is selected or the selected item
                    // isn't equal to the current input, we refilter the list.
                    if (selected == null || !selected.equals(editor.getText())) {
                        filteredUnita.setPredicate(item -> {
                            // We return true for any items that starts with the
                            // same letters as the input. We use toUpperCase to
                            // avoid case sensitivity.
                            if (item.toUpperCase().startsWith(newValue.toUpperCase())) {
                                return true;
                            } else {
                                return false;
                            }
                        });
                    }
                });
            });

        return filteredUnita;
        
    }

}
