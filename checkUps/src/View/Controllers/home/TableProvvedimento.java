package View.Controllers.home;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Models.Tables.Provvedimento;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TableProvvedimento extends PicoTable<Provvedimento> {
    @FXML
    private TableColumn<Provvedimento, Integer> col_idProvvedimento;
    @FXML
    private TableColumn<Provvedimento, String> col_rischioProvvedimento;
    @FXML
    private TableColumn<Provvedimento, String> col_nomeProvvedimento;
    @FXML
    private TableColumn<Provvedimento, String> col_soggettiEspostiProvvedimento;
    @FXML
    private TableColumn<Provvedimento, Integer> col_stimarProvvedimento;
    @FXML
    private TableColumn<Provvedimento, Integer> col_stimadProvvedimento;
    @FXML
    private TableColumn<Provvedimento, Integer> col_stimapProvvedimento;

    @FXML
    private TableView<Provvedimento> tableProvvedimenti;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.makeValueProperty(col_idProvvedimento, "id");
        this.makeValueProperty(col_rischioProvvedimento, "rischio");
        this.makeValueProperty(col_nomeProvvedimento, "nome");
        this.makeValueProperty(col_soggettiEspostiProvvedimento, "soggettiEsposti");
        this.makeValueProperty(col_stimarProvvedimento, "stimaR");
        this.makeValueProperty(col_stimadProvvedimento, "stimaD");
        this.makeValueProperty(col_stimapProvvedimento, "stimaP");
    }

    @Override
    public void update(List<Provvedimento> updatedValues) {
        tableProvvedimenti.setItems(FXCollections.observableArrayList(updatedValues));
    }

    @Override
    public void clearTable() {
        // TODO: Resettare filtro
        tableProvvedimenti.setItems(FXCollections.observableArrayList());
    }
}
