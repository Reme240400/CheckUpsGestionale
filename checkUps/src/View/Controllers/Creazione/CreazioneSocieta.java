package View.Controllers.Creazione;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Controllers.ClassHelper;
import Controllers.Controller;
import Models.ModelCreazione;
import Models.Tables.Societa;
import View.Controllers.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.IntegerStringConverter;

public class CreazioneSocieta extends Creazione {

    @FXML
    private JFXButton btnAnnulla;

    @FXML
    private JFXButton btnSalva;

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

    @FXML
    private JFXComboBox<String> cercaRecord;

    // private Creazione creazione;
    private ModelCreazione modelCreazione;

    private String txtSocieta;
    private String txtIndirizzo;
    private String txtLocalita;
    private String txtTel;
    private String txtProvincia;

    Societa societaTmp = null;
    List<Societa> listSocieta;
    Controller controller = new Controller();
    // private String txtDesc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // * *************** inizializza i campi *************** //

        listSocieta = ClassHelper.getListSocieta();
        ObservableList<String> items = FXCollections.observableArrayList();

        // * *************** popola il combobox *************** //

        for (Societa societa : listSocieta) {
            cercaRecord.getItems().add(societa.getNome());
            items.add(societa.getNome());
        }

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

        // * filtra il Combobox
        FilteredList<String> filteredItems = ViewController.filterComboBoxSocieta(cercaRecord, items);

        cercaRecord.setItems(filteredItems);
        // * ************************************************ //

    }

    // ------------------------------------------------------- END INITIALIZE -------------------------------------------------------------------- //

    public void fillTextField(KeyEvent event ){
        if (event.getCode().toString().equals("ENTER")){
            modelCreazione.setEnable(false);
            modelCreazione.fillTextField( cercaRecord, textFieldSocieta, textFieldIndirizzo, textFieldLocalita, textFieldProvincia, textFieldTel);
        }
    }

    // * salva la societa
    public void salvaSocieta(javafx.event.ActionEvent event) {

        int id = controller.getNewId(listSocieta);

        Societa societaTmp = new Societa(id, txtSocieta, txtIndirizzo, txtLocalita, txtProvincia, txtTel,
                "");

        modelCreazione.createSocietaTmp(societaTmp);

        modelCreazione.setSocietySaved(true);
        modelCreazione.setSaved(false);

    }

    // * ----------------------------- elimina la societa ----------------------------- //
    public void eliminaSocieta() {

        // textFieldDesc.clear();
        textFieldIndirizzo.clear();
        textFieldLocalita.clear();
        textFieldProvincia.clear();
        textFieldSocieta.clear();
        textFieldTel.clear();
        
        cercaRecord.getSelectionModel().clearSelection();

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
    public void setModel(ModelCreazione modelCreazione) {
        this.modelCreazione = modelCreazione;

        this.btnSalva.disableProperty().bind(modelCreazione.savedProperty().not());
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
