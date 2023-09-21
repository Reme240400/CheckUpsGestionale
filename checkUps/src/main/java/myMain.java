package main.java;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sql.ControllerSql;

public class myMain extends Application{
    public static void main(String[] args) throws Exception {
        launch(args);
        visualizzaDatiTabellaSocieta();
    }

    public static void visualizzaDatiTabellaSocieta() {
        new ControllerSql();
    }

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("Hello World!");
            }  

        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);

        /*
        Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        Scene scene = new Scene(root);
        */
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
                primaryStage.setScene(scene);
                primaryStage.show();
    }

}

