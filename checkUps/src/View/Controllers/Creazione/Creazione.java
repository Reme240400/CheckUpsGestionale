package View.Controllers.Creazione;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Models.ModelCreazione;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class Creazione implements Initializable {

    @FXML
    private JFXButton btnSocieta;

    @FXML
    protected JFXButton btnUnitaLocali;

    @FXML
    private StackPane stackPane;

    private ModelCreazione modelCreazione;
    private FXMLLoader loaderSocieta;
    private FXMLLoader loaderUnitaLocale;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    // * cambia scena in UnitaLocale
    public void switchToSocieta() throws IOException {

        loaderSocieta = new FXMLLoader(
                getClass().getResource("/View/fxml/creazione_societa.fxml"));
        Parent root = loaderSocieta.load();
        CreazioneSocieta creazioneSocieta = loaderSocieta.getController();

        creazioneSocieta.setModel(modelCreazione);

        stackPane.getChildren().removeAll();
        stackPane.getChildren().setAll(root);
    }

    // * cambia scena in UnitaLocale
    public void switchToUnitaLocali(javafx.event.ActionEvent event) throws IOException {
        
        loaderUnitaLocale = new FXMLLoader(
                getClass().getResource("/View/fxml/creazione_unitalocale.fxml"));
        Parent root = loaderUnitaLocale.load();
        CreazioneUnitaLocale creazioneUnita = loaderUnitaLocale.getController();

        creazioneUnita.setModel(modelCreazione);

        stackPane.getChildren().removeAll();
        stackPane.getChildren().setAll(root);
    }

    // * setta il modello
    public void setModelCreazione(ModelCreazione model) {
        this.modelCreazione = model;

        try {
            switchToSocieta();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.btnUnitaLocali.disableProperty().bind(modelCreazione.societaSavedProperty().not());

    }

}
