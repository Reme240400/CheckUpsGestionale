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
    private Spinner<Integer> stimaP;

    @FXML
    private Spinner<Integer> stimaD;

    @FXML
    private TextField stimaR;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stimaD.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        stimaP.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));

        stimaD.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                stimaR.setText(String.valueOf(stimaD.getValue() * stimaP.getValue()));
            } catch (NullPointerException e) {
            }
        });

        stimaP.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                stimaR.setText(String.valueOf(stimaD.getValue() * stimaP.getValue()));
            } catch (NullPointerException e) {
            }
        });
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
        return stimaD.getValue();
    }

    public int getStimaP() {
        return stimaP.getValue();
    }

    public int getStimaR() {
        try {
            return Integer.parseInt(stimaR.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public LocalDate getModificaData() {
        return modificaData.getValue();
    }

    public void setModel(ModelCreazione model) {
        modificaNome.setText(model.getProvvedimentoTmp().getNome());
        modificaRischio.setText(model.getProvvedimentoTmp().getRischio());
        modificaSoggettiEsposti.setText(model.getProvvedimentoTmp().getSoggettiEsposti());
        stimaD.getValueFactory().setValue(model.getProvvedimentoTmp().getStimaD());
        stimaP.getValueFactory().setValue(model.getProvvedimentoTmp().getStimaP());
        stimaR.setText(
                String.valueOf(model.getProvvedimentoTmp().getStimaD() * model.getProvvedimentoTmp().getStimaP()));
    }

}
