package View.Controllers.Creazione.dialogPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Interfaces.DialogInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DialogPaneAddP implements DialogInterface, Initializable {
    @FXML
    private TextField creaNome;

    @FXML
    private TextArea creaRischio;

    @FXML
    private TextField creaSoggettiEsposti;

    @FXML
    private DatePicker creaDataFine;

    @FXML
    private Spinner<Integer> stimaD;

    @FXML
    private Spinner<Integer> stimaP;

    @FXML
    private TextField stimaR;

    @FXML
    private TextField textFieldOggetto;

    @FXML
    private TextField textFieldReparto;

    @FXML
    private TextField textFieldSocieta;

    @FXML
    private TextField textFieldTitolo;

    @FXML
    private TextField textFieldUnitaLocale;

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
        return creaNome.getText();
    }

    public String getRischio() {
        return creaRischio.getText();
    }

    public String getSoggettiEsposti() {
        return creaSoggettiEsposti.getText();
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

    public void fillTextBox(String nomeS, String nomeU, String nomeR, String nomeT, String nomeO) {
        textFieldSocieta.setText(nomeS);
        textFieldUnitaLocale.setText(nomeU);
        textFieldReparto.setText(nomeR);
        textFieldTitolo.setText(nomeT);
        textFieldOggetto.setText(nomeO);
    }

    public LocalDate getDataFine() {
        return this.creaDataFine.getValue();
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

        if (this.getDataFine() == null || this.getDataFine().isBefore(LocalDate.now())) {
            return new FieldsCheckResponse("La data di fine deve essere valida e nel futuro");
        }

        if (this.getStimaR() == -1) {
            return new FieldsCheckResponse("Stime non valide");
        }

        return new FieldsCheckResponse();
    }
}
