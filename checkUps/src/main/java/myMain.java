package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;
import sql.ControllerSql;

public class myMain extends Application{
    public static void main(String[] args) {
        launch(args);
        //visualizzaDatiTabellaSocieta();
    }

    // public static void visualizzaDatiTabellaSocieta() {
    //     new ControllerSql();
    // }

    @Override
    public void start(javafx.stage.Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }


}

