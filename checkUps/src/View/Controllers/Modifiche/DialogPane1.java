package View.Controllers.Modifiche;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import Controllers.ClassHelper;
import Models.ModelModifica;
import Models.Tables.Societa;
import View.Controllers.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class DialogPane1 implements Initializable {

    private ModelModifica modelModifica;

    @FXML
    private JFXComboBox<String> cercaSocieta;

    @FXML
    private JFXComboBox<String> cercaUnitaLocale;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        List<Societa> listSocieta = ClassHelper.getListSocieta();

        ObservableList<String> sItems = FXCollections.observableArrayList();

        for (Societa societa : listSocieta) {
            cercaSocieta.getItems().add(societa.getNome());
            sItems.add(societa.getNome());
        }

        FilteredList<String> filteredItems = ViewController.filterComboBoxSocieta(cercaSocieta, sItems);

        

        cercaSocieta.setItems(filteredItems);
    }

    public void setModel(ModelModifica modelModifica) {
        this.modelModifica = modelModifica;
    }

}
