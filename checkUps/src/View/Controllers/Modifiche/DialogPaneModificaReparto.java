package View.Controllers.Modifiche;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import Models.ModelCreazione;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class DialogPaneModificaReparto implements Initializable {

    @FXML
    private DatePicker modificaDescReparto;

    @FXML
    private TextField modificaNomeReparto;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    public String getNomeReparto() {
        return modificaNomeReparto.getText();
    }

    public String getDescReparto() {
        return modificaDescReparto.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public void setModel(ModelCreazione model) {
        modificaNomeReparto.setText(model.getRepartoTmp().getNome());

        if (model.getRepartoTmp().getDescrizione().contains("T"))
            modificaDescReparto.setValue(LocalDate.parse(model.getRepartoTmp().getDescrizione(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
        else
            modificaDescReparto.setValue(LocalDate.parse(model.getRepartoTmp().getDescrizione(),
                    DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }

}
