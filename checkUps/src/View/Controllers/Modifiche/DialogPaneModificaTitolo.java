package View.Controllers.Modifiche;

import java.net.URL;
import java.util.ResourceBundle;

import Models.ModelCreazione;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class DialogPaneModificaTitolo implements Initializable {

    @FXML
    private TextField modificaNomeTitolo;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    public String getDescTitolo() {
        return modificaNomeTitolo.getText();
    }

    public void setModel(ModelCreazione model) {
        modificaNomeTitolo.setText(model.getTitoloTmp().getDescrizione());
    }

}
