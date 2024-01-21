package View.Controllers.home;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import com.jfoenix.controls.JFXButton;

import Models.Tables.UnitaLocale;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TableUnitaLocale extends PicoTable<UnitaLocale> {
    @FXML
    private TableView<UnitaLocale> tableUnitaLocali;
    @FXML
    private JFXButton btnAddUnitaLocale;
    @FXML
    private JFXButton btnRemoveUnitaLocale;
    @FXML
    private TextField filterUnitaLocale;

    @FXML
    private TableColumn<UnitaLocale, Integer> col_idUnitaLocale;
    @FXML
    private TableColumn<UnitaLocale, String> col_nomeUnitaLocale;
    @FXML
    private TableColumn<UnitaLocale, String> col_indirizzoUnitaLocale;
    @FXML
    private TableColumn<UnitaLocale, String> col_localitaUnitaLocale;
    @FXML
    private TableColumn<UnitaLocale, String> col_provinciaUnitaLocale;
    @FXML
    private TableColumn<UnitaLocale, String> col_telefonoUnitaLocale;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.makeValueProperty(col_idUnitaLocale, "id");
        this.makeValueProperty(col_nomeUnitaLocale, "nome");
        this.makeValueProperty(col_indirizzoUnitaLocale, "indirizzo");
        this.makeValueProperty(col_localitaUnitaLocale, "localita");
        this.makeValueProperty(col_provinciaUnitaLocale, "provincia");
        this.makeValueProperty(col_telefonoUnitaLocale, "telefono");

        this.registerSelectAction(tableUnitaLocali);
        this.registerTextFilter(tableUnitaLocali, filterUnitaLocale, col_nomeUnitaLocale);
    }

    @Override
    public void update(List<UnitaLocale> updatedValues) {
        filterUnitaLocale.setText("");
        this.originalData = FXCollections.observableArrayList(updatedValues);
        tableUnitaLocali.setItems(this.originalData);
    }

    @Override
    public void onRowSelect(Consumer<UnitaLocale> op) {
        this.selectAction = op;
    }

    @Override
    public void clearTable() {
        filterUnitaLocale.setText("");
        tableUnitaLocali.setItems(FXCollections.observableArrayList());
    }

    public void addUnitaLocale() {
        System.out.println("Added");
    }

    public void removeSelectedUnitaLocale() {
        System.out.println("Remove");
    }
}
