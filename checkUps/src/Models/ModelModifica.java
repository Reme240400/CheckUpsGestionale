package Models;

import java.util.List;

import com.jfoenix.controls.JFXComboBox;

import Controllers.ClassHelper;
import Models.Tables.Societa;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TextField;

public class ModelModifica {

    private final BooleanProperty saved = new SimpleBooleanProperty(false);
    private final BooleanProperty isEnable = new SimpleBooleanProperty(false);
    private int idSocieta = -1;

    // ------------------ CONSTRUCTOR ------------------ //
    public BooleanProperty savedProperty() {
        return saved;
    }

    public BooleanProperty isEnableProperty() {
        return isEnable;
    }

    // ------------------ SETTER ------------------ //
    public final void setSaved(boolean saved) {
        savedProperty().set(saved);
    }

    public final void setEnable(boolean isEnable) {
        isEnableProperty().set(isEnable);
    }

    // ------------------ GETTER ------------------ //
    public final boolean isSaved() {
        return savedProperty().get();
    }

    public final boolean isEnable() {
        return isEnableProperty().get();
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

    public void setIdSocieta(JFXComboBox<String> cercaSocieta, List<Societa> lista) {
        
        idSocieta = lista.stream().filter(s -> s.getNome().equals(cercaSocieta.getValue())).findFirst().get().getId();

    }

    public int getIdSocieta() {
        return idSocieta;
    }
}
