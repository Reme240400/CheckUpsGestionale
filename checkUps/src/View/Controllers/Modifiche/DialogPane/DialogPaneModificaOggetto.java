package View.Controllers.Modifiche.DialogPane;

import java.net.URL;
import java.util.ResourceBundle;

import Models.ModelModifica;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class DialogPaneModificaOggetto implements Initializable{

    @FXML
    private TextField modificaNomeOggetto;

    private ModelModifica modelModifica;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public String getNome() {
        return modificaNomeOggetto.getText();
    }


    public void setModel(ModelModifica modelModifica) {
        this.modelModifica = modelModifica;

        modificaNomeOggetto.setText(modelModifica.getOggettoTmp().getNome());

    }

}
