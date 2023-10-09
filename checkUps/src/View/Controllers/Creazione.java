package View.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

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
    private JFXButton btnReparti;

    @FXML
    private StackPane stackPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnUnitaLocali.setDisable(true);
        btnReparti.setDisable(true);

        try {

            FXMLLoader mainCreazioneLoader = new FXMLLoader(
                    getClass().getResource("/View/fxml/creazione_societa.fxml"));
            Parent root = mainCreazioneLoader.load();
            CreazioneSocieta creazioneSocieta = mainCreazioneLoader.getController();
            creazioneSocieta.setCreazione(this);
            stackPane.getChildren().removeAll();
            stackPane.getChildren().setAll(root);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void switchToSocieta(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader mainCreazioneLoader = new FXMLLoader(
                getClass().getResource("/View/fxml/creazione_societa.fxml"));

        Parent root = mainCreazioneLoader.load();
        CreazioneSocieta creazioneSocieta = mainCreazioneLoader.getController();
        creazioneSocieta.setCreazione(this);

        stackPane.getChildren().removeAll();
        stackPane.getChildren().setAll(root);
    }

    public void switchToUnitaLocali(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader mainCreazioneLoader = new FXMLLoader(
                getClass().getResource("/View/fxml/creazione_unitalocale.fxml"));

        Parent root = mainCreazioneLoader.load();
        CreazioneUnitaLocale creazioneUnita = mainCreazioneLoader.getController();
        creazioneUnita.setCreazione(this);

        stackPane.getChildren().removeAll();
        stackPane.getChildren().setAll(root);
    }

    public void switchToReparti(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader mainCreazioneLoader = new FXMLLoader(
                getClass().getResource("/View/fxml/creazione_reparti.fxml"));

        Parent root = mainCreazioneLoader.load();
        CreazioneReparti creazioneReparti = mainCreazioneLoader.getController();
        creazioneReparti.setCreazione(this);

        stackPane.getChildren().removeAll();
        stackPane.getChildren().setAll(root);
    }

    public void setEnableBtnUnitaLocale() {

        btnUnitaLocali.setDisable(false);

    }

    public void setEnableBtnReparti() {

        btnReparti.setDisable(false);

    }

    public void finalReport() {

    }

}
