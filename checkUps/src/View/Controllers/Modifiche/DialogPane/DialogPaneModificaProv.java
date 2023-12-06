package View.Controllers.Modifiche.DialogPane;

import java.net.URL;
import java.util.ResourceBundle;

import Models.ModelCreazione;
import Models.ModelModifica;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DialogPaneModificaProv implements Initializable{

    @FXML
    private TextField modificaNome;

    @FXML
    private TextArea modificaRischio;

    @FXML
    private TextField modificaSoggettiEsposti;

    @FXML
    private Spinner<Integer> valStimaP;

    @FXML
    private Spinner<Integer> valStimaD;

    @FXML
    private Spinner<Integer> valStimaR;
    
    private ModelModifica modelModifica;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        valStimaD.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        valStimaP.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        valStimaR.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));

    }

    public String getNome(){
        return modificaNome.getText();
    }

    public String getRischio(){
        return modificaRischio.getText();
    }

    public String getSoggettiEsposti(){
        return modificaSoggettiEsposti.getText();
    }

    public int getStimaD(){
        return valStimaD.getValue();
    }

    public int getStimaP(){
        return valStimaP.getValue();
    }

    public int getStimaR(){
        return valStimaR.getValue();
    }

    public void setModel(ModelModifica modelModifica) {
        this.modelModifica = modelModifica;

        modificaNome.setText(modelModifica.getProvTmp().getNome());
        modificaRischio.setText(modelModifica.getProvTmp().getRischio());
        modificaSoggettiEsposti.setText(modelModifica.getProvTmp().getSoggettiEsposti());
        valStimaD.getValueFactory().setValue(modelModifica.getProvTmp().getStimaD());
        valStimaP.getValueFactory().setValue(modelModifica.getProvTmp().getStimaP());
        valStimaR.getValueFactory().setValue(modelModifica.getProvTmp().getStimaR());
    }    
}
