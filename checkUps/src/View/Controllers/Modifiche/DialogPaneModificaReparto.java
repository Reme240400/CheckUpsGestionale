package View.Controllers.Modifiche;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import Models.ModelCreazione;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DialogPaneModificaReparto implements Initializable {

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

    public String getDataReparto() {
        return modificaDataReparto.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public String getRevisioneReparto() {
        return modificaRevisioneReparto.getText();
    }

    public String getDescReparto() {
        return modificaDescReparto.getText();
    }

    public void setModel(ModelCreazione model) {
        modificaNomeReparto.setText(model.getRepartoTmp().getNome());

        if (String.valueOf(model.getRepartoTmp().getData()).contains("T"))
            modificaDataReparto.setValue(LocalDate.parse(model.getRepartoTmp().getDescrizione(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
        else
            modificaDataReparto.setValue(LocalDate.parse(model.getRepartoTmp().getDescrizione(),
                    DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }

}
