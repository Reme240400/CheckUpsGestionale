package View.Controllers.home;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import Models.Tables.Oggetto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TableOggetto extends PicoTable<Oggetto> {
    @FXML
    private TableColumn<Oggetto, Integer> col_idOggetto;
    @FXML
    private TableColumn<Oggetto, String> col_nomeOggetto;

    @FXML
    private TableView<Oggetto> tableOggetti;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.makeValueProperty(col_idOggetto, "id");
        this.makeValueProperty(col_nomeOggetto, "nome");

        tableOggetti.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectAction.accept(newValue);
            }
        });
    }

    @Override
    public void update(List<Oggetto> updatedValues) {
        tableOggetti.setItems(FXCollections.observableArrayList(updatedValues));
    }

    @Override
    public void onRowSelect(Consumer<Oggetto> selected) {
        this.selectAction = selected;
    }

    @Override
    public void clearTable() {
        // TODO: Resettare filtro
        tableOggetti.setItems(FXCollections.observableArrayList());
    }
}
