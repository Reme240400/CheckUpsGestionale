package View.Controllers.Creazione;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import com.jfoenix.controls.JFXButton;

import Controllers.ClassHelper;
import Controllers.Controller;
import Models.ModelCreazione;
import Models.ModelPaths;
import Models.Tables.Societa;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

public class CreazioneSocieta extends Controller implements Initializable{

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

    private String txtSocieta;
    private String txtIndirizzo;
    private String txtLocalita;
    private String txtTel;
    private String txtProvincia;

    Societa societaTmp = null;
    List<Societa> listSocieta = ClassHelper.getListSocieta();
    Controller controller = new Controller();
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

        int id = controller.getNewId(listSocieta);

        Societa societaTmp = new Societa(id, txtSocieta, txtIndirizzo, txtLocalita, txtProvincia, txtTel, "");

        modelCreazione.createSocietaTmp(societaTmp);

        controller.inserisciNuovoRecord(societaTmp);

        modelCreazione.setSaved(false);

        try {
            modelPaths.switchToCreazioneUnitaLocale(modelCreazione);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void salvaSocieta() {

        int id = controller.getNewId(listSocieta);

        Societa societaTmp = new Societa(id, txtSocieta, txtIndirizzo, txtLocalita, txtProvincia, txtTel, "");

        controller.inserisciNuovoRecord(societaTmp);

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
        this.txtSocieta = textFieldSocieta.getText();
        this.txtIndirizzo = textFieldIndirizzo.getText();
        this.txtLocalita = textFieldLocalita.getText();
        this.txtProvincia = textFieldProvincia.getText();
        this.txtTel = textFieldTel.getText();

        modelCreazione.isTextFilled(textFieldSocieta, textFieldIndirizzo, textFieldLocalita,
                textFieldProvincia, textFieldTel);

    }
    // * ------------------------------------------------------------------------------- //

    // * ************ setta il modelCreazionelo ************ //
    public void setModel(ModelCreazione modelCreazione, ModelPaths modelPaths) {
        this.modelCreazione = modelCreazione;
        this.modelPaths = modelPaths;

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

}
