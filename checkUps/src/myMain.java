
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

public class myMain extends Application{
    private double x = 0;
    private double y = 0;

    @Override
    public void start(javafx.stage.Stage primaryStage) throws Exception {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/home.fxml"));

        Parent root = loader.load();
        primaryStage.setTitle("CheckUp Gestionale");
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNDECORATED);

        

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y  = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
            launch(args);
        
        }

}

