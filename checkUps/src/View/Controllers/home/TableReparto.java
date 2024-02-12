package View.Controllers.home;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Models.Tables.Reparto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TableReparto extends PicoTable<Reparto> {
    @FXML
    private JFXButton btnAddReparto;
    @FXML
    private JFXButton btnRemoveReparto;
    @FXML
    private TextField filterReparto;

    @FXML
    private TableColumn<Reparto, Integer> col_idReparto;
    @FXML
    private TableColumn<Reparto, String> col_nomeReparto;
    @FXML
    private TableColumn<Reparto, String> col_dataReparto;

    @FXML
    private TableView<Reparto> tableReparti;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.makeValueProperty(col_idReparto, "id");
        this.makeValueProperty(col_nomeReparto, "nome");
        this.makeValueProperty(col_dataReparto, "data");

        this.registerSelectAction(tableReparti);
        this.registerTextFilter(tableReparti, filterReparto, col_nomeReparto);
    }

    @Override
    public void update(List<Reparto> updatedValues) {
        filterReparto.setText("");
        this.originalData = FXCollections.observableArrayList(updatedValues);
        tableReparti.setItems(this.originalData);
    }

    @Override
    public void clearTable() {
        filterReparto.setText("");
        tableReparti.setItems(FXCollections.observableArrayList());
    }
}
