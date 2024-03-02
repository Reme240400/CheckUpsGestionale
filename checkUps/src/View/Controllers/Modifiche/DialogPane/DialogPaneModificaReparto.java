package View.Controllers.Modifiche.DialogPane;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import Models.ModelModifica;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class DialogPaneModificaReparto implements Initializable {

    @FXML
    private DatePicker modificaDescReparto;

    @FXML
    private TextField modificaNomeReparto;

    private ModelModifica modelModifica;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    public String getNomeReparto() {
        return modificaNomeReparto.getText();
    }

    public String getDescReparto() {
        return modificaDescReparto.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public void setModel(ModelModifica modelModifica) {
        this.modelModifica = modelModifica;

        modificaNomeReparto.setText(modelModifica.getRepartoTmp().getNome());
        if (modelModifica.getRepartoTmp().getDescrizione().contains("T"))
            modificaDescReparto.setValue(LocalDate.parse(modelModifica.getRepartoTmp().getDescrizione(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
        else
            modificaDescReparto.setValue(LocalDate.parse(modelModifica.getRepartoTmp().getDescrizione(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

}
