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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;

import com.jfoenix.controls.JFXComboBox;

public class DialogPane1 implements Initializable {

    private ModelModifica modelModifica;
    private List<Societa> listSocieta = ClassHelper.getListSocieta();

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

    public void searchIdSocieta(KeyEvent event) {
        
        if(event.getCode().toString().equals("ENTER")){
            int id = listSocieta.stream().filter(s -> s.getNome().equals(cercaSocieta.getValue())).findFirst().get().getId();
            System.out.println(id);
            modelModifica.setIdSocieta(id);
        }

    }

    public void setModel(ModelModifica modelModifica) {
        this.modelModifica = modelModifica;

        
    }

}
