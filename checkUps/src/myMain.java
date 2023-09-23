
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;
import sql.ControllerSql;

public class myMain extends Application{
    public static void main(String[] args) {
        launch(args);
        visualizzaDatiTabellaSocieta();
    }

    public static void visualizzaDatiTabellaSocieta() {
        new ControllerSql();
    }

    @Override
    public void start(javafx.stage.Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/main.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("CheckUp Gestionale");
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNDECORATED);

        final double[] x = {0};
        final double[] y = {0};

        root.setOnMousePressed(event -> {
            x[0] = event.getSceneX();
            y[0] = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x[0]);
            primaryStage.setY(event.getScreenY() - y[0]);
        });

        primaryStage.show();
    }



}

