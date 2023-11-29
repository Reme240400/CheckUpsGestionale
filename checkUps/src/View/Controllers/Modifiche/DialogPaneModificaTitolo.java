package View.Controllers.Modifiche;

import java.net.URL;
import java.util.ResourceBundle;

import Models.ModelModifica;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class DialogPaneModificaTitolo implements Initializable {

    @FXML
    private TextField modificaNomeTitolo;

    private ModelModifica modelModifica;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    public String getDescTitolo() {
        return modificaNomeTitolo.getText();
    }


    public void setModel(ModelModifica modelModifica) {
        this.modelModifica = modelModifica;
        System.out.println(modelModifica.getTitoloTmp().getDescrizione());

        modificaNomeTitolo.setText(modelModifica.getTitoloTmp().getDescrizione());

    }
    
}
