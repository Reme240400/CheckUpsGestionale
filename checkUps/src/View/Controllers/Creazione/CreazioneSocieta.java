package View.Controllers.Creazione;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Controllers.Controller;
import Helpers.ClassHelper;
import Models.Alerts;
import Models.ModelCreazione;
import Models.ModelPaths;
import Models.TipoCreazionePagina;
import Models.Tables.Societa;
import Models.creazione.CreazioneBase;
import View.Controllers.ViewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.embed.swt.SWTFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.util.converter.LongStringConverter;

public class CreazioneSocieta extends CreazioneBase implements Initializable {

    @FXML
    private JFXButton btnNext;

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
    private TextArea textAreaDesc;

    @FXML
    private TextField textFieldPartitaIva;

    @FXML
    private TextField textFieldCodiceFiscale;

    @FXML
    private TextField textFieldBancaAppoggio;

    @FXML
    private TextField textFieldCodiceAteco;

    @FXML
    private ImageView logoImageView;

    @FXML
    private JFXComboBox<String> cercaSocieta;

    Optional<Societa> curSocieta = Optional.empty();
    List<Societa> listSocieta = ClassHelper.getListSocieta();

    // ------------------------------------------------------- INITIALIZE
    // -------------------------------------------------------------------- //
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // * controlla se vengono inseriti solo numeri nel campo telefono
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();

            if (text.matches("[0-9]*") && change.getControlNewText().length() <= 15) {
                return change;
            }

            return null;
        };

        TextFormatter<Long> formatter = new TextFormatter<Long>(new LongStringConverter(), null, filter);
        textFieldTel.setTextFormatter(formatter);

        ObservableList<String> societies = FXCollections
                .observableArrayList(listSocieta.stream().map(s -> s.getNome()).toList());

        FilteredList<String> filteredItems = ViewController.filterComboBox(cercaSocieta, societies);
        cercaSocieta.setItems(filteredItems);
    }

    public void selezionaSocieta() {
        // Reset dei bottoni
        modelCreazione.setSaved(false);
        modelCreazione.setCanGoNext(false);

        curSocieta = listSocieta.stream().filter(s -> s.getNome().equals(cercaSocieta.getValue()))
                .findFirst();

        if (curSocieta.isEmpty())
            return;

        Societa soc = curSocieta.get();
        textFieldSocieta.setText(soc.getNome());
        textFieldLocalita.setText(soc.getLocalita());
        textFieldProvincia.setText(soc.getProvincia());
        textFieldTel.setText(soc.getTelefono());
        textFieldIndirizzo.setText(soc.getIndirizzo());
        textAreaDesc.setText(soc.getDescrizione());
        textFieldPartitaIva.setText(soc.getPartitaIva());
        textFieldCodiceFiscale.setText(soc.getCodiceFiscale());
        textFieldBancaAppoggio.setText(soc.getBancaAppoggio());
        textFieldCodiceAteco.setText(soc.getCodiceAteco());
        logoImageView.setImage(soc.getLogoImage());

        modelCreazione.setCanGoNext(true);
        modelCreazione.createSocietaTmp(soc);
    }

    public void setOldTextFields() {
        if (modelCreazione.getSocietaTmp() != null) {
            textFieldSocieta.setText(modelCreazione.getSocietaTmp().getNome());
            textFieldIndirizzo.setText(modelCreazione.getSocietaTmp().getIndirizzo());
            textFieldLocalita.setText(modelCreazione.getSocietaTmp().getLocalita());
            textFieldProvincia.setText(modelCreazione.getSocietaTmp().getProvincia());
            textFieldTel.setText(String.valueOf(modelCreazione.getSocietaTmp().getTelefono()));
            textFieldPartitaIva.setText(modelCreazione.getSocietaTmp().getPartitaIva());
            textFieldCodiceFiscale.setText(modelCreazione.getSocietaTmp().getCodiceFiscale());
            textFieldBancaAppoggio.setText(modelCreazione.getSocietaTmp().getBancaAppoggio());
            textFieldCodiceAteco.setText(modelCreazione.getSocietaTmp().getCodiceAteco());
            textAreaDesc.setText(modelCreazione.getSocietaTmp().getDescrizione());
            logoImageView.setImage(modelCreazione.getSocietaTmp().getLogoImage());
            cercaSocieta.setValue(modelCreazione.getSocietaTmp().getNome());
        }
    }

    @FXML
    public void onImageUpload() {
        FileChooser fChooser = new FileChooser();
        fChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Immagini",
                Arrays.asList("*.jpg", "*.JPG", "*.png", "*.PNG", "*.jpeg", "*.JPEG"));

        fChooser.getExtensionFilters().addAll(extFilter);
        File file = fChooser.showOpenDialog(null);

        if (file == null)
            return;
        else if (!file.exists() || !file.isFile()) {
            Alerts.errorAllert("Errore", "Errore durante la selezione del file: file non valido!", null);
            return;
        }

        try {
            Image img = new Image(file.toURI().toURL().toExternalForm());
            logoImageView.setImage(img);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    // -------------------- salva la societa -------------------- //
    @FXML
    public void onAggiorna() {
        Societa societaTmp = new Societa(modelCreazione.getSocietaTmp().getId(),
                textFieldSocieta.getText(),
                textFieldIndirizzo.getText(),
                textFieldLocalita.getText(),
                textFieldProvincia.getText(),
                textFieldTel.getText(),
                textAreaDesc.getText(),
                textFieldPartitaIva.getText(),
                textFieldCodiceFiscale.getText(),
                textFieldBancaAppoggio.getText(),
                textFieldCodiceAteco.getText(),
                Optional.ofNullable(logoImageView.getImage()));

        Controller.modificaCampo(societaTmp);
        modelCreazione.createSocietaTmp(societaTmp);

        modelCreazione.setSaved(false);
    }

    public void pulisciDati() {
        textFieldIndirizzo.clear();
        textFieldLocalita.clear();
        textFieldProvincia.clear();
        textFieldSocieta.clear();
        textFieldTel.clear();
        textFieldPartitaIva.clear();
        textFieldCodiceFiscale.clear();
        textFieldBancaAppoggio.clear();
        textFieldCodiceAteco.clear();
        textAreaDesc.clear();
        logoImageView.setImage(null);

        cercaSocieta.setValue(null);

        modelCreazione.resetSocietaTmp();
        modelCreazione.setSaved(false);
        modelCreazione.setCanGoNext(false);
    }

    public void keyReleasedProperty() {
        modelCreazione.areTextFieldsFilled(cercaSocieta.getValue() == null, textFieldSocieta, textFieldIndirizzo,
                textFieldLocalita,
                textFieldProvincia, textFieldTel, textFieldCodiceAteco);
    }

    @FXML
    public void onSaveAndGoNext() {
        if (cercaSocieta.getValue() == null) {
            int id = Controller.getNewId(listSocieta);

            Societa societaTmp = new Societa(id,
                    textFieldSocieta.getText(),
                    textFieldIndirizzo.getText(),
                    textFieldLocalita.getText(),
                    textFieldProvincia.getText(),
                    textFieldTel.getText(),
                    textAreaDesc.getText(),
                    textFieldPartitaIva.getText(),
                    textFieldCodiceFiscale.getText(),
                    textFieldBancaAppoggio.getText(),
                    textFieldCodiceAteco.getText(),
                    Optional.ofNullable(logoImageView.getImage()));

            Controller.inserisciNuovoRecord(societaTmp);
            modelCreazione.createSocietaTmp(societaTmp);
        }

        super.changePage(TipoCreazionePagina.UNITA_LOCALE, true);
    }

    @Override
    public void setModel(ModelCreazione modelCreazione, ModelPaths modelPaths) {
        super.setModel(modelCreazione, modelPaths);

        this.btnNext.disableProperty().bind(modelCreazione.canGoNextProperty().not());
        this.btnAggiorna.disableProperty().bind(modelCreazione.savedProperty().not());
    }

}