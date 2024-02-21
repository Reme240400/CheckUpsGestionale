package View.Controllers.Creazione;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Controllers.Controller;
import Helpers.ClassHelper;
import Interfaces.CreazioneInterface;
import Models.ModelCreazione;
import Models.ModelPaths;
import Models.Tables.Societa;
import View.Controllers.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

public class CreazioneSocieta implements Initializable, CreazioneInterface {

    @FXML
    private JFXButton btnSalva;

    @FXML
    private JFXButton btnAggiorna;

    @FXML
    private TextField textFieldSocieta;

    @FXML
    private TextField textFieldLocalita;

    @FXML
    private TextField textFieldIndirizzo;

    @FXML
    private TextField textFieldProvincia;

    @FXML
    private TextField textFieldTel;

    @FXML
    private TextField textFieldDescrizione;

    @FXML
    private TextField textFieldPartitaIva;

    @FXML
    private TextField textFieldCodiceFiscale;

    @FXML
    private TextField textFieldBancaAppoggio;

    @FXML
    private TextField textFieldCodiceAteco;

    @FXML
    private JFXButton buttonLogoUrl;

    @FXML
    private JFXComboBox<String> cercaSocieta;

    private ModelCreazione modelCreazione;
    private ModelPaths modelPaths;

    Societa societaTmp = null;
    List<Societa> listSocieta = ClassHelper.getListSocieta();

    // ------------------------------------------------------- INITIALIZE
    // -------------------------------------------------------------------- //
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

        ObservableList<String> societies = FXCollections
                .observableArrayList(listSocieta.stream().map(s -> s.getNome()).toList());
        FilteredList<String> filteredItems = ViewController.filterComboBox(cercaSocieta, societies);
        cercaSocieta.setItems(filteredItems);

        cercaSocieta.onKeyReleasedProperty().addListener((obs, oValue, nValue) -> {
            filtraSocieta();
        });
    }
    // ------------------------------------------------------- END INITIALIZE
    // -------------------------------------------------------------------- //

    public void filtraSocieta() {
        Optional<Societa> curSocieta = listSocieta.stream().filter(s -> s.getNome().equals(cercaSocieta.getValue()))
                .findFirst();
        if (curSocieta.isEmpty())
            return;

        Societa soc = curSocieta.get();
        textFieldSocieta.setText(soc.getNome());
        textFieldLocalita.setText(soc.getLocalita());
        textFieldProvincia.setText(soc.getProvincia());
        textFieldTel.setText(soc.getTelefono());
        textFieldIndirizzo.setText(soc.getIndirizzo());
        textFieldDescrizione.setText(soc.getDescrizione());
        textFieldPartitaIva.setText(soc.getPartitaIva());
        textFieldCodiceFiscale.setText(soc.getCodiceFiscale());
        textFieldBancaAppoggio.setText(soc.getBancaAppoggio());
        textFieldCodiceAteco.setText(soc.getCodiceAteco());

        // buttonLogoUrl.; // soc.getLogoUrl()
    }

    // -------------------- salva la societa -------------------- //
    public void aggiorna() {

        int id = Controller.getNewId(listSocieta);

        Societa societaTmp = new Societa(id,
                textFieldSocieta.getText(),
                textFieldIndirizzo.getText(),
                textFieldLocalita.getText(),
                textFieldProvincia.getText(),
                textFieldTel.getText(),
                textFieldDescrizione.getText(),
                textFieldPartitaIva.getText(),
                textFieldCodiceFiscale.getText(),
                textFieldBancaAppoggio.getText(),
                textFieldCodiceAteco.getText(),
                buttonLogoUrl.getText());

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

    public void salva() {

        int id = Controller.getNewId(listSocieta);

        Societa societaTmp = new Societa(id,
                textFieldSocieta.getText(),
                textFieldIndirizzo.getText(),
                textFieldLocalita.getText(),
                textFieldProvincia.getText(),
                textFieldTel.getText(),
                textFieldDescrizione.getText(),
                textFieldPartitaIva.getText(),
                textFieldCodiceFiscale.getText(),
                textFieldBancaAppoggio.getText(),
                textFieldCodiceAteco.getText(),
                buttonLogoUrl.getText());

        Controller.inserisciNuovoRecord(societaTmp);

        eliminaSocieta();

        modelCreazione.setSaved(false);

    }

    // * ----------------------------- elimina la societa
    // ----------------------------- //
     public void eliminaSocieta() {

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
    // *
    // ------------------------------------------------------------------------------- //

    // * ---------------------- controlla se i campi sono vuoti
    // ---------------------- //
    public void keyReleasedProperty() {
        modelCreazione.isTextFilled(textFieldSocieta, textFieldIndirizzo, textFieldLocalita,
                textFieldProvincia, textFieldTel);
    }
    // *
    // ------------------------------------------------------------------------------- //

    // * ************ setta il modelCreazionelo ************ //
    public void setTextFields() {

        this.btnSalva.disableProperty().bind(modelCreazione.savedProperty().not());
        this.btnAggiorna.disableProperty().bind(modelCreazione.savedProperty().not());

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
