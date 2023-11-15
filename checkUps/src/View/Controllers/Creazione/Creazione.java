package View.Controllers.Creazione;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Models.ModelCreazione;
import Models.ModelPaths;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class Creazione implements Initializable {

    @FXML
    private JFXButton btnSocieta;

    @FXML
    protected JFXButton btnUnitaLocali;

    @FXML
    private JFXButton btnReparti;

    @FXML
    private StackPane stackPane;

    @FXML
    private StackPane modificaStackPane;

    private ModelCreazione modelCreazione;
    static ModelPaths modelPaths = new ModelPaths();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    // * cambia scena in UnitaLocale
    public void switchToSocieta() throws IOException {

        Parent root = modelPaths.switchToCreazioneSocieta(modelCreazione);

        stackPane.getChildren().removeAll();
        stackPane.getChildren().setAll(root);
    }

    // * cambia scena in UnitaLocale
    public void switchToUnitaLocali(javafx.event.ActionEvent event) throws IOException {
        
        Parent root = modelPaths.switchToCreazioneUnitaLocale(modelCreazione);

        stackPane.getChildren().removeAll();
        stackPane.getChildren().setAll(root);
    }

    // * cambia scena in Reparti
    public void switchToReparto(javafx.event.ActionEvent event) throws IOException {

        Parent root = modelPaths.switchToCreazioneReparti(modelCreazione, modificaStackPane, stackPane);

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

    }

    public void giveStackPane(StackPane stackPane2) {
        modificaStackPane = stackPane2;
    }

}
