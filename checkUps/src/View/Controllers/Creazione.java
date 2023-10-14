package View.Controllers;

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
    private JFXButton btnUnitaLocali;

    @FXML
    private StackPane stackPane;

    private ModelCreazione modelCreazione;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    public void switchToSocieta() throws IOException {
        FXMLLoader mainCreazioneLoader = new FXMLLoader(
                getClass().getResource("/View/fxml/creazione_societa.fxml"));

        Parent root = mainCreazioneLoader.load();
        CreazioneSocieta creazioneSocieta = mainCreazioneLoader.getController();

        creazioneSocieta.setModel(modelCreazione);

        stackPane.getChildren().removeAll();
        stackPane.getChildren().setAll(root);
    }

    public void switchToUnitaLocali(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader mainCreazioneLoader = new FXMLLoader(
                getClass().getResource("/View/fxml/creazione_unitalocale.fxml"));

        Parent root = mainCreazioneLoader.load();
        CreazioneUnitaLocale creazioneUnita = mainCreazioneLoader.getController();

        creazioneUnita.setModel(modelCreazione);

        stackPane.getChildren().removeAll();
        stackPane.getChildren().setAll(root);
    }

    public void setModelCreazione(ModelCreazione modelCreazione) {
        this.modelCreazione = modelCreazione;

        try {
            switchToSocieta();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.btnUnitaLocali.disableProperty().bind(modelCreazione.societaSavedProperty().not());
        
    }

}
