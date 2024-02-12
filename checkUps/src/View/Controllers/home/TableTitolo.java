package View.Controllers.home;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Models.Tables.Titolo;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TableTitolo extends PicoTable<Titolo> {
    @FXML
    private TableColumn<Titolo, Integer> col_idTitolo;
    @FXML
    private TableColumn<Titolo, String> col_descriptionTitolo;
    @FXML
    private TextField filterTitoli;

    @FXML
    private TableView<Titolo> tableTitoli;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.makeValueProperty(col_idTitolo, "id");
        this.makeValueProperty(col_descriptionTitolo, "descrizione");

        this.registerSelectAction(tableTitoli);
        this.registerTextFilter(tableTitoli, filterTitoli, col_descriptionTitolo);
    }

    @Override
    public void update(List<Titolo> updatedValues) {
        filterTitoli.setText("");
        this.originalData = FXCollections.observableArrayList(updatedValues);
        tableTitoli.setItems(this.originalData);
    }

    @Override
    public void clearTable() {
        filterTitoli.setText("");
        tableTitoli.setItems(FXCollections.observableArrayList());
    }
}
