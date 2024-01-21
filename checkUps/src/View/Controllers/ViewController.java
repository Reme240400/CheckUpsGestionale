package View.Controllers;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Controllers.ControllerDb;
import Helpers.ClassHelper;
import Models.Model;
import Models.Tables.Societa;
import View.Controllers.home.TableOggetto;
import View.Controllers.home.TableProvvedimento;
import View.Controllers.home.TableReparto;
import View.Controllers.home.TableTitolo;
import View.Controllers.home.TableUnitaLocale;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;

public class ViewController implements Initializable {
    @FXML
    private JFXComboBox<String> cercaSocieta;

    @FXML
    private JFXButton addTitoli;

    @FXML
    private JFXButton addUnita1;

    @FXML
    private JFXButton addUnita11;

    @FXML
    private JFXButton addUnita12;

    @FXML
    private JFXButton addUnita121;

    @FXML
    private JFXButton addUnita1211;

    @FXML
    private JFXButton addUnita2;

    @FXML
    private JFXButton removeTitoli;

    @FXML
    private JFXButton removeUnita1;

    @FXML
    private JFXButton removeUnita2;

    private List<Societa> listaSocieta = ClassHelper.getListSocieta();

    @FXML
    private TableUnitaLocale table_unitaLocaliController;
    @FXML
    private TableReparto table_repartiController;
    @FXML
    private TableTitolo table_titoliController;
    @FXML
    private TableOggetto table_oggettiController;
    @FXML
    private TableProvvedimento table_provvedimentiController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ControllerDb.popolaListeDaDatabase();

        ObservableList<String> obsNomiSocieta = FXCollections
                .observableArrayList(listaSocieta.stream().map(soc -> soc.getNome()).toList());
        FilteredList<String> filteredItems = Model.filterComboBox(cercaSocieta, obsNomiSocieta);
        cercaSocieta.setItems(filteredItems);

        table_unitaLocaliController.onRowSelect((uLocale) -> {
            table_titoliController.clearTable();
            table_oggettiController.clearTable();
            table_provvedimentiController.clearTable();

            table_repartiController.update(uLocale.getReparti());
        });

        table_repartiController.onRowSelect((reparto) -> {
            table_oggettiController.clearTable();
            table_provvedimentiController.clearTable();

            table_titoliController.update(reparto.getTitoli());
        });

        table_titoliController.onRowSelect((titolo) -> {
            table_provvedimentiController.clearTable();

            table_oggettiController.update(titolo.getOggetti());
        });

        table_oggettiController.onRowSelect((oggetto) -> {
            table_provvedimentiController.update(oggetto.getProvvedimenti());
        });
    }

    public void selectSocieta() {
        Optional<Societa> soc = listaSocieta.stream().filter(s -> s.getNome().equals(cercaSocieta.getValue()))
                .findFirst();
        if (soc.isEmpty())
            return;

        table_repartiController.clearTable();
        table_titoliController.clearTable();
        table_oggettiController.clearTable();
        table_provvedimentiController.clearTable();

        table_unitaLocaliController.update(ClassHelper.getListUnitaLocale().stream()
                .filter(uLoc -> uLoc.getIdSocieta() == soc.get().getId()).toList());
    }
}
