package View.Controllers;

import java.util.List;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;

import Helpers.ClassHelper;
import Helpers.PdfHelpers.pdfGenerator;
import Models.ModelValutaRischi;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.UnitaLocale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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

    private ObservableList<Reparto> observableList;
    private ModelValutaRischi modelValutaRischi;

    private UnitaLocale unitaLocale;
    private Societa societa;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        nameCol.setCellValueFactory(new PropertyValueFactory<Reparto, String>("nome"));
        descCol.setCellValueFactory(new PropertyValueFactory<Reparto, String>("descrizione"));

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tableView.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
            Node node = evt.getPickResult().getIntersectedNode();

            // Vai su dalla destinazione dell'evento fino a trovare una riga o
            // diventa chiaro che il nodo di destinazione non era una riga.
            while (node != null && node != tableView && !(node instanceof TableRow)) {
                node = node.getParent();
            }

            // Se fa parte di una riga o della riga stessa,
            // gestisci l'evento invece di usare la gestione standard
            if (node instanceof TableRow<?>) {
                // Impedisci ulteriori gestioni
                evt.consume();

                TableRow<?> row = (TableRow<?>) node;
                TableView<?> tv = row.getTableView();

                // Focalizza la TableView
                tv.requestFocus();

                if (!row.isEmpty()) {
                    // Gestisci la selezione per i nodi non vuoti
                    int index = row.getIndex();
                    if (row.isSelected()) {
                        tv.getSelectionModel().clearSelection(index);
                    } else {
                        tv.getSelectionModel().select(index);
                    }
                }
            }
        });

    }

    public void fillTable() {

        List<Reparto> specificList = reparti.stream()
                .filter(reparto -> reparto.getIdUnitaLocale() == unitaLocale.getId()).toList();

        observableList = FXCollections.observableArrayList(specificList);
        tableView.setItems(observableList);

    }

    public void filterTable() {
        modelValutaRischi.filterTable(filterTextField, tableView, observableList);
    }

    public void setSection(UnitaLocale unitaLocale, Societa societa) {
        this.societa = societa;
        this.unitaLocale = unitaLocale;

        nomeSocieta.setText(societa.getNome());
        nomeUnitaLocale.setText(unitaLocale.getNome());

        fillTable();
    }

    public void setModel(ModelValutaRischi modelValutaRischi) {
        this.modelValutaRischi = modelValutaRischi;
    }

    public void createPDF() throws IOException {
        String nomeFile = null;
        // chiedi con un pop up che chiede il nome del file
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            JFrame frame = new JFrame("Inserisci il nome del file");
            
            // Visualizzazione del pop-up per l'inserimento del nome del file
            nomeFile = JOptionPane.showInputDialog(frame, "Inserisci il nome del file:");
            
            // Visualizzazione del nome del file inserito
            if (nomeFile != null && !nomeFile.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Hai inserito il nome del file: " + nomeFile);
            } else {
                JOptionPane.showMessageDialog(frame, "Nessun nome del file inserito.");
                nomeFile = "valutazione_rischi";
            }
            
            // Chiusura del frame
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            List<Reparto> reparti = tableView.getSelectionModel().getSelectedItems();
            pdfGenerator.stampaValutazioneRischi(societa, unitaLocale, reparti, nomeFile + ".pdf");
        }

    }

}
