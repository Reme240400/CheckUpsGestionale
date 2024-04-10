package Models.others;

import java.util.Optional;
import java.util.function.Consumer;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

public class Dialogs {
    public static <T> void showDialogWithResponse(String relativePath, String title, Consumer<T> prepareDialogPane,
            Consumer<T> updateChanges) {
        FXMLLoader loader = new FXMLLoader(Dialogs.class.getResource("/View/fxml/" + relativePath));
        DialogPane dialogPane = null;

        try {
            dialogPane = loader.load();
            T controller = loader.getController();
            prepareDialogPane.accept(controller);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle(title);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.get() == ButtonType.APPLY) {
                updateChanges.accept(controller);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> void showDialog(String relativePath, String title, Consumer<T> prepareDialogPane) {
        FXMLLoader loader = new FXMLLoader(Dialogs.class.getResource("/View/fxml/" + relativePath));
        DialogPane dialogPane = null;

        try {
            dialogPane = loader.load();
            T controller = loader.getController();
            prepareDialogPane.accept(controller);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle(title);

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
