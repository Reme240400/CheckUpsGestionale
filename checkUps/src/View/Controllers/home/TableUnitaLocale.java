package View.Controllers.home;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
        // tableUnitaLocali.setEditable(true);

        this.makeValueProperty(col_idUnitaLocale, "id");
        this.makeValueProperty(col_nomeUnitaLocale, "nome");
        this.makeValueProperty(col_indirizzoUnitaLocale, "indirizzo");
        this.makeValueProperty(col_localitaUnitaLocale, "localita");
        this.makeValueProperty(col_provinciaUnitaLocale, "provincia");
        this.makeValueProperty(col_telefonoUnitaLocale, "telefono");
        // col_telefonoUnitaLocale.setCellFactory(TextFieldTableCell.forTableColumn());

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
