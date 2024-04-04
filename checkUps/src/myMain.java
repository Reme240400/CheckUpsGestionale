import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class myMain extends Application {
    @Override
    public void start(javafx.stage.Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/View/fxml/main.fxml"));
        primaryStage.setTitle("CheckUp Gestionale");

        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            logout(primaryStage);
        });
    }

    public void logout(Stage stage) {
        // Alert alert = new Alert(AlertType.CONFIRMATION);

        // alert.setTitle("Esci");
        // alert.setHeaderText("Stai per uscire!");
        // alert.setContentText("Vuoi salvare il lavoro prima di uscire?");

        // if (alert.showAndWait().get().getText().equals("OK")) {
        stage.close();
        // }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
