package View.Controllers.Creazione;

import java.net.URL;
import java.util.ResourceBundle;

import Models.ModelCreazione;
import Models.ModelPaths;
import Models.others.TipoCreazionePagina;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class Creazione implements Initializable {
    @FXML
    private StackPane stackPaneCreazione;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    // * setta il modello
    public void setModelCreazione(ModelCreazione modelCreazione, ModelPaths modelPaths) {
        modelPaths.setStackPaneCrea(stackPaneCreazione);
        Parent root = modelPaths.switchToCreazioneByTipo(TipoCreazionePagina.SOCIETA, modelCreazione);

        stackPaneCreazione.getChildren().removeAll();
        stackPaneCreazione.getChildren().setAll(root);
    }

}
