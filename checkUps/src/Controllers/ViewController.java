package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ViewController implements Initializable{

    @FXML
    private Button btn;

    @FXML
    private VBox vItems;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("fxml/main.fxml"));

        try {
            
        
            Node [] node = new Node[5];
            for(int i = 0; i < node.length; i++){
                node[i] = FXMLLoader.load(getClass().getResource("fxml/main.fxml"));
                final int n = i;
                vItems.getChildren().add(node[i]);

                node[i].setOnMouseEntered(e -> {
                    node[n].setStyle("-fx-background-color : #165DDB");
                });

                node[i].setOnMouseExited(e -> {
                    node[n].setStyle("-fx-background-color : #F0F0F0");
                });

                node[i].setOnMousePressed(e -> {
                    node[n].setStyle("-fx-background-color : #1E1E1E");
                });
            }

        } catch (IOException e) {
                    e.printStackTrace();
                }

        
    }
    
}
