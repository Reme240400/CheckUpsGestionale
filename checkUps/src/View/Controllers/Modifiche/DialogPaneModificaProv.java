package View.Controllers.Modifiche;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Models.ModelCreazione;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DialogPaneModificaProv implements Initializable {

    @FXML
    private TextField modificaNome;

    @FXML
    private TextArea modificaRischio;

    @FXML
    private TextField modificaSoggettiEsposti;

    @FXML
    private DatePicker modificaData;

    @FXML
    private Spinner<Integer> valStimaP;

    @FXML
    private Spinner<Integer> valStimaD;

    @FXML
    private Spinner<Integer> valStimaR;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        valStimaD.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        valStimaP.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        valStimaR.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
    }

    public String getNome() {
        return modificaNome.getText();
    }

    public String getRischio() {
        return modificaRischio.getText();
    }

    public String getSoggettiEsposti() {
        return modificaSoggettiEsposti.getText();
    }

    public int getStimaD() {
        return valStimaD.getValue();
    }

    public int getStimaP() {
        return valStimaP.getValue();
    }

    public int getStimaR() {
        return valStimaR.getValue();
    }

    public LocalDate getModificaData() {
        return modificaData.getValue();
    }

    public void setModel(ModelCreazione model) {
        modificaNome.setText(model.getProvvedimentoTmp().getNome());
        modificaRischio.setText(model.getProvvedimentoTmp().getRischio());
        modificaSoggettiEsposti.setText(model.getProvvedimentoTmp().getSoggettiEsposti());
        valStimaD.getValueFactory().setValue(model.getProvvedimentoTmp().getStimaD());
        valStimaP.getValueFactory().setValue(model.getProvvedimentoTmp().getStimaP());
        valStimaR.getValueFactory().setValue(model.getProvvedimentoTmp().getStimaR());
    }

}
