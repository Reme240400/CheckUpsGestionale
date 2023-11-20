package View.Controllers.Creazione;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Models.ModelCreazione;
import Models.ModelPaths;
import View.Controllers.ViewController;
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
    private JFXButton btnTitoli;

    @FXML
    private StackPane stackPaneCreazione;

    private ModelCreazione modelCreazione;
    private ViewController viewController;
    private ModelPaths modelPaths;

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

        stackPaneCreazione.getChildren().removeAll();
        Parent root = modelPaths.switchToCreazioneReparti(modelCreazione, viewController);
        stackPaneCreazione.getChildren().setAll(root);
    }

    public void switchToTitoli() throws IOException {

        stackPaneCreazione.getChildren().removeAll();
        Parent root = modelPaths.switchToCreazioneTitolo(modelCreazione, viewController);
        stackPaneCreazione.getChildren().setAll(root);
    }

    // * setta il modello
    public void setModelCreazione(ModelCreazione model, ModelPaths modelPaths, ViewController viewController) {
        this.modelCreazione = model;
        this.modelPaths = modelPaths;
        this.viewController = viewController;

        try {
            switchToSocieta();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
