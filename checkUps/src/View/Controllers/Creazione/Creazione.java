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
    private StackPane stackPaneCreazione;

    private StackPane modificaStackPane;

    private ModelCreazione modelCreazione;
    static ModelPaths modelPaths = new ModelPaths();

    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    // * cambia scena in UnitaLocale
    public void switchToSocieta() throws IOException {

        Parent root = modelPaths.switchToCreazioneSocieta(modelCreazione);

        stackPaneCreazione.getChildren().removeAll();
        stackPaneCreazione.getChildren().setAll(root);
    }

    // * cambia scena in UnitaLocale
    public void switchToUnitaLocali() throws IOException {
        
        stackPaneCreazione.getChildren().removeAll();
        Parent root = modelPaths.switchToCreazioneUnitaLocale(modelCreazione);
        stackPaneCreazione.getChildren().setAll(root);
    }

    // * cambia scena in Reparti
    public void switchToReparto() throws IOException {
        System.out.println("StackPane 4: " + modificaStackPane.getChildren());

        stackPaneCreazione.getChildren().removeAll();
        Parent root = modelPaths.switchToCreazioneReparti(modelCreazione, modificaStackPane, stackPaneCreazione);
        stackPaneCreazione.getChildren().setAll(root);
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

    public void giveStackPane(StackPane stackPaneHome) {
        this.modificaStackPane = stackPaneHome;
        System.out.println("StackPane 3: " + modificaStackPane.getChildren());
    }

}
