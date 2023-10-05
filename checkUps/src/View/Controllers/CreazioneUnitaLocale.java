package View.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;


public class CreazioneUnitaLocale implements Initializable{
    
    @FXML
    JFXButton btnSalva;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public void salvaSocieta(javafx.event.ActionEvent event) throws IOException{
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/fxml/main_creazione.fxml"));
        System.out.println(loader.getController().toString());
        Creazione controller = loader.getController();
        controller.setEnableBtnReparti();    
    }
}
