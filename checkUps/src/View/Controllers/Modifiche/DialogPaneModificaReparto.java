package View.Controllers.Modifiche;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import Interfaces.DialogInterface;
import Models.ModelCreazione;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DialogPaneModificaReparto implements DialogInterface, Initializable {

    @FXML
    private DatePicker modificaDataReparto;

    @FXML
    private TextField modificaNomeReparto;

    @FXML
    private TextArea modificaDescReparto;

    @FXML
    private TextField modificaRevisioneReparto;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }

    public String getNomeReparto() {
        return modificaNomeReparto.getText();
    }

    public LocalDate getDataReparto() {
        return modificaDataReparto.getValue(); // .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public String getRevisioneReparto() {
        return modificaRevisioneReparto.getText();
    }

    public String getDescReparto() {
        return modificaDescReparto.getText();
    }

    public void setModel(ModelCreazione model) {
        modificaNomeReparto.setText(model.getRepartoTmp().getNome());
        modificaDescReparto.setText(model.getRepartoTmp().getDescrizione());
        modificaRevisioneReparto.setText(model.getRepartoTmp().getRevisione());

        Optional<LocalDate> date = model.getRepartoTmp().getData();
        if (date.isEmpty())
            return;

        modificaDataReparto.setValue(date.get());
        // if (String.valueOf(model.getRepartoTmp().getData()).contains("T"))
        // modificaDataReparto.setValue(LocalDate.parse(date,
        // DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
        // else
        // modificaDataReparto.setValue(LocalDate.parse(model.getRepartoTmp().getDescrizione(),
        // DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }

    @Override
    public FieldsCheckResponse areFieldsValid() {
        if (this.getNomeReparto() == null || this.getNomeReparto().isBlank()) {
            return new FieldsCheckResponse("Nome Reparto non valido");
        }

        if (this.getDataReparto() == null) {
            return new FieldsCheckResponse("Data Reparto non valida");
        }

        if (this.getRevisioneReparto() == null || this.getRevisioneReparto().isBlank()) {
            return new FieldsCheckResponse("Revisione Reparto non valida");
        }

        return new FieldsCheckResponse();
    }
}
