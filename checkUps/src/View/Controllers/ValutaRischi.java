package View.Controllers;

import java.util.List;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Helpers.ClassHelper;
import Helpers.PdfHelpers.pdfGenerator;
import Models.Model;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.UnitaLocale;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ValutaRischi implements Initializable {

    @FXML
    private TableColumn<Reparto, String> descCol;
    @FXML
    private TableColumn<Reparto, String> nameCol;

    @FXML
    private TableView<Reparto> tableView;

    @FXML
    private TextField nomeSocieta;

    @FXML
    private TextField nomeUnitaLocale;

    @FXML
    private TextField filterTextField;

    @FXML
    private JFXButton btnSelect;

    private List<Reparto> reparti = ClassHelper.getListReparto();

    private UnitaLocale unitaLocale;
    private Societa societa;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameCol.setCellValueFactory(new PropertyValueFactory<Reparto, String>("nome"));
        descCol.setCellValueFactory(new PropertyValueFactory<Reparto, String>("descrizione"));

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // tableView.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
        // Node node = evt.getPickResult().getIntersectedNode();

        // // Vai su dalla destinazione dell'evento fino a trovare una riga o
        // // diventa chiaro che il nodo di destinazione non era una riga.
        // while (node != null && node != tableView && !(node instanceof TableRow)) {
        // node = node.getParent();
        // }

        // // Se fa parte di una riga o della riga stessa,
        // // gestisci l'evento invece di usare la gestione standard
        // if (node instanceof TableRow) {
        // // Impedisci ulteriori gestioni
        // evt.consume();

        // TableRow<Reparto> row = (TableRow<Reparto>) node;
        // TableView<Reparto> tv = row.getTableView();

        // // Focalizza la TableView
        // tv.requestFocus();

        // if (!row.isEmpty()) {
        // // Gestisci la selezione per i nodi non vuoti
        // int index = row.getIndex();
        // if (row.isSelected()) {
        // tv.getSelectionModel().clearSelection(index);
        // } else {
        // tv.getSelectionModel().select(index);
        // }
        // }
        // }
        // });

    }

    public void fillTable() {
        List<Reparto> specificList = reparti.stream()
                .filter(reparto -> reparto.getIdUnitaLocale() == unitaLocale.getId()).toList();
        FilteredList<Reparto> filteredData = new FilteredList<>(FXCollections.observableArrayList(specificList));

        var filter = Model.genericTableViewFilter((Reparto r) -> r.getNome(), filterTextField);
        filteredData.predicateProperty().bind(Bindings.createObjectBinding(() -> filter.get(), filter));
        tableView.setItems(filteredData);

    }

    public void setSection(UnitaLocale unitaLocale, Societa societa) {
        this.societa = societa;
        this.unitaLocale = unitaLocale;

        nomeSocieta.setText(societa.getNome());
        nomeUnitaLocale.setText(unitaLocale.getNome());

        fillTable();
    }

    public void createPDF() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            List<Reparto> reparti = tableView.getSelectionModel().getSelectedItems();
            pdfGenerator.stampaValutazioneRischi(societa, unitaLocale, reparti, "prova.pdf");
        }

    }

}
