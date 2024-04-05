package View.Controllers.Modifiche;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Interfaces.DialogInterface;
import Models.ModelCreazione;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DialogPaneModificaProv implements DialogInterface, Initializable {

    @FXML
    private TextField modificaNome;

    @FXML
    private TextArea modificaRischio;

    @FXML
    private TextField modificaSoggettiEsposti;

    @FXML
    private DatePicker modificaDataFine;

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

        modificaDataFine.disableProperty().set(true);
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

    public LocalDate getDataFine() {
        return modificaDataFine.getValue();
    }

    public void setModel(ModelCreazione model) {
        modificaNome.setText(model.getProvvedimentoTmp().getNome());
        modificaRischio.setText(model.getProvvedimentoTmp().getRischio());
        modificaSoggettiEsposti.setText(model.getProvvedimentoTmp().getSoggettiEsposti());
        modificaDataFine.setValue(model.getProvvedimentoTmp().getDataScadenza().orElse(null));
        stimaD.getValueFactory().setValue(model.getProvvedimentoTmp().getStimaD());
        stimaP.getValueFactory().setValue(model.getProvvedimentoTmp().getStimaP());
        stimaR.setText(
                String.valueOf(model.getProvvedimentoTmp().getStimaD() * model.getProvvedimentoTmp().getStimaP()));
    }

    @Override
    public FieldsCheckResponse areFieldsValid() {
        if (this.getNome() == null || this.getNome().equals("")) {
            return new FieldsCheckResponse("Nome non valido");
        }

        if (this.getRischio() == null || this.getRischio().equals("")) {
            return new FieldsCheckResponse("Rischio non valido");
        }

        if (this.getSoggettiEsposti() == null || this.getSoggettiEsposti().equals("")) {
            return new FieldsCheckResponse("Soggetti Esposti non validi");
        }

        if (this.getStimaR() == -1) {
            return new FieldsCheckResponse("Stime non valide");
        }

        return new FieldsCheckResponse();
    }
}
