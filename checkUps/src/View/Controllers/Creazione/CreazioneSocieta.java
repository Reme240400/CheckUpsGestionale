package View.Controllers.Creazione;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import com.jfoenix.controls.JFXButton;

import Controllers.Controller;
import Helpers.ClassHelper;
import Models.ModelCreazione;
import Models.ModelModifica;
import Models.ModelPaths;
import Models.Tables.Societa;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

public class CreazioneSocieta implements Initializable {

    @FXML
    private JFXButton btnAnnulla;

    @FXML
    private JFXButton btnSalva;

    @FXML
    private JFXButton btnSalvaAggiungi;

    @FXML
    private TextField textFieldSocieta;

    @FXML
    private TextField textFieldIndirizzo;

    @FXML
    private TextField textFieldLocalita;

    @FXML
    private TextField textFieldProvincia;

    @FXML
    private TextField textFieldTel;

    // @FXML
    // private TextField textFieldDesc;
 
    private ModelCreazione modelCreazione;
    private ModelPaths modelPaths;
    private ModelModifica modelModifica;

    Societa societaTmp = null;
    List<Societa> listSocieta = ClassHelper.getListSocieta();
    // private String txtDesc;

    // ------------------------------------------------------- INITIALIZE -------------------------------------------------------------------- //
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // * controlla se vengono inseriti solo numeri
        UnaryOperator<TextFormatter.Change> filter = change -> {

            // remove any non-digit characters from inserted text:
            if (!change.getText().matches("\\d*")) {
                change.setText(change.getText().replaceAll("[^\\d]", ""));
            }

            if (change.getControlNewText().length() > 15) {
                return null;
            }

            return change;
        };

        TextFormatter<Integer> formatter = new TextFormatter<Integer>(new IntegerStringConverter(), null, filter);
        textFieldTel.setTextFormatter(formatter);

        // * **************************************** //

    }
    // ------------------------------------------------------- END INITIALIZE -------------------------------------------------------------------- //

    // -------------------- salva la societa -------------------- //
    public void save_addSocieta() {

        int id = Controller.getNewId(listSocieta);

        Societa societaTmp = new Societa(id, 
                                        textFieldSocieta.getText(), 
                                        textFieldIndirizzo.getText(), 
                                        textFieldLocalita.getText(), 
                                        textFieldProvincia.getText(), 
                                        textFieldTel.getText(), 
                                        "");

        modelCreazione.createSocietaTmp(societaTmp);

        Controller.inserisciNuovoRecord(societaTmp);

        modelCreazione.setSaved(false);

        try {
            Parent root = modelPaths.switchToCreazioneUnitaLocale(modelCreazione);

            Controller.changePane(modelPaths.getStackPaneCrea(), root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void salvaSocieta() {

        int id = Controller.getNewId(listSocieta);

        Societa societaTmp = new Societa(id, 
                                        textFieldSocieta.getText(), 
                                        textFieldIndirizzo.getText(), 
                                        textFieldLocalita.getText(), 
                                        textFieldProvincia.getText(), 
                                        textFieldTel.getText(), 
                                        "");

        Controller.inserisciNuovoRecord(societaTmp);

        eliminaSocieta();

    }

    // * ----------------------------- elimina la societa ----------------------------- //
    public void eliminaSocieta() {

        // textFieldDesc.clear();
        textFieldIndirizzo.clear();
        textFieldLocalita.clear();
        textFieldProvincia.clear();
        textFieldSocieta.clear();
        textFieldTel.clear();

        modelCreazione.setEnable(true);
        modelCreazione.resetSocietaTmp();
        modelCreazione.setSaved(false);
        modelCreazione.setDiscard(false);
    }
    // * ------------------------------------------------------------------------------- //


    // * ---------------------- controlla se i campi sono vuoti ---------------------- //
    public void keyReleasedProperty() {

        modelCreazione.isTextFilled(textFieldSocieta, textFieldIndirizzo, textFieldLocalita,
                textFieldProvincia, textFieldTel);

    }
    // * ------------------------------------------------------------------------------- //

    // * ************ setta il modelCreazionelo ************ //
    public void setTextFields() {

        this.btnSalva.disableProperty().bind(modelCreazione.savedProperty().not());
        this.btnSalvaAggiungi.disableProperty().bind(modelCreazione.savedProperty().not());
        this.btnAnnulla.disableProperty().bind(modelCreazione.discardProperty().not());
        
        this.textFieldIndirizzo.editableProperty().bind(modelCreazione.isEnableProperty());
        this.textFieldLocalita.editableProperty().bind(modelCreazione.isEnableProperty());
        this.textFieldProvincia.editableProperty().bind(modelCreazione.isEnableProperty());
        this.textFieldSocieta.editableProperty().bind(modelCreazione.isEnableProperty());
        this.textFieldTel.editableProperty().bind(modelCreazione.isEnableProperty());

        // * ************ setta i campi come sono stati salvati ************ //
        modelCreazione.setOldTextFields(textFieldSocieta, textFieldIndirizzo, textFieldLocalita, textFieldProvincia,
            textFieldTel);
        // * ************************************************ //
    }

    public void setModel(ModelCreazione modelCreazione, ModelPaths modelPaths) {

        this.modelCreazione = modelCreazione;
        this.modelPaths = modelPaths;
    
    }

}
