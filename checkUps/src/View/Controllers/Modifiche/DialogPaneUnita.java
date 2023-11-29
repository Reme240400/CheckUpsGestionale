package View.Controllers.Modifiche;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Controllers.ClassHelper;
import Models.ModelModifica;
import Models.Tables.Societa;
import View.Controllers.ViewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import com.jfoenix.controls.JFXComboBox;

public class DialogPaneUnita implements Initializable {

    protected ModelModifica modelModifica;
    protected List<Societa> listSocieta = ClassHelper.getListSocieta();

    @FXML 
    JFXComboBox<String> cercaSocieta;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<String> sItems = FXCollections.observableArrayList();

        for (Societa societa : listSocieta) {
            cercaSocieta.getItems().add(societa.getNome());
            sItems.add(societa.getNome());
        }

        FilteredList<String> filteredItems = ViewController.filterComboBoxSocieta(cercaSocieta, sItems);

        cercaSocieta.setItems(filteredItems);

    }

    public void searchIdSocieta() {
        
        if(cercaSocieta.getValue() != null){
            Societa societa = listSocieta.stream().filter(s -> s.getNome().equals(cercaSocieta.getValue())).findFirst().get();
    
            modelModifica.setSocieta(societa);
        }

    }

    public void setModel(ModelModifica modelModifica) {
        this.modelModifica = modelModifica;
        
    }

}
