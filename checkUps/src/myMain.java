
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


public class myMain extends Application{
    private double x = 0;
    private double y = 0;

    @Override
    public void start(javafx.stage.Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/View/fxml/main.fxml"));
        primaryStage.setTitle("CheckUp Gestionale");

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y  = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
        });

        primaryStage.setScene( new Scene(root));
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            logout(primaryStage);
            }
        );
    }

    public void logout(Stage stage){
        Alert alert = new Alert(AlertType.CONFIRMATION);

        alert.setTitle("Esci");
        alert.setHeaderText("Stai per uscire!");
        alert.setContentText("Vuoi salvare il lavoro prima di uscire?");

        if(alert.showAndWait().get().getText().equals("OK")){
            stage.close();
        }
    }

    public static void verificaScadenze(){
    
    }

    public static void main(String[] args) { 
        verificaScadenze();
        launch(args);
        
    }

}

