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
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;

public class DialogPane1 implements Initializable {

    private ModelModifica modelModifica;

    @FXML 
    JFXComboBox<String> cercaSocieta;

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

    @FXML
    private void selectSocieta(){

       
    }

    public void searchIdSocieta() {
        modelModifica.setIdSocieta(cercaSocieta, ClassHelper.getListSocieta());
    }

    public void setModel(ModelModifica modelModifica) {
        this.modelModifica = modelModifica;
    }

}
