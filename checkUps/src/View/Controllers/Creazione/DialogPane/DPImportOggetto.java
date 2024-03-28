package View.Controllers.Creazione.dialogPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Helpers.ClassHelper;
import Models.Tables.Oggetto;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;
import Models.imports.ImportOggettoElement;
import Models.imports.ImportTitoloElement;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class DPImportOggetto implements Initializable {

    @FXML
    private TextField textFieldSocieta;

    @FXML
    private TextField textFieldUnitaLocale;

    @FXML
    private TextField textFieldReparto;

    @FXML
    private TextField textFieldTitolo;

    @FXML
    private TextField textFieldFiltraS;

    @FXML
    private TextField textFieldFiltraU;

    @FXML
    private TextField textFieldFiltraR;

    @FXML
    private TextField textFieldFiltraT;

    @FXML
    private TextField textFieldFiltraO;

    @FXML
    private TableView<ImportOggettoElement> tableImportaOggetti;

    @FXML
    private TableColumn<ImportOggettoElement, String> colOggetto;

    @FXML
    private TableColumn<ImportOggettoElement, String> colReparto;

    @FXML
    private TableColumn<ImportOggettoElement, String> colTitolo;

    @FXML
    private TableColumn<ImportOggettoElement, String> colUnitaLocale;

    @FXML
    private TableColumn<ImportOggettoElement, String> colSocieta;

    private ImportOggettoElement selectedData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colOggetto.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOggetto().getNome()));
        colReparto.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getReparto().getNome()));
        colTitolo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitolo().getDescrizione()));
        colUnitaLocale
                .setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUnitaLocale().getNome()));
        colSocieta.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSocieta().getNome()));

        tableImportaOggetti.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selected) -> {
            if (selected != null) {
                this.selectedData = selected;
            }
        });

        // btnImport.disableProperty().bind(tableImportaTitoli.getSelectionModel().selectedItemProperty().isNull());
    }

    public void fillInfo(String nomeSocieta, String nomeUnita, String nomeReparto, String nomeTitolo) {
        textFieldSocieta.setText(nomeSocieta);
        textFieldUnitaLocale.setText(nomeUnita);
        textFieldReparto.setText(nomeReparto);
        textFieldTitolo.setText(nomeTitolo);
    }

    public void populateTable(int idCurrentTitolo) {
        var listaOggetti = ClassHelper.getListOggetto();
        var listaTitoli = ClassHelper.getListTitolo();
        var listaReparti = ClassHelper.getListReparto();
        var listaUnitaLocali = ClassHelper.getListUnitaLocale();
        var listaSocieta = ClassHelper.getListSocieta();

        List<ImportOggettoElement> list = new ArrayList<>();
        for (Oggetto oggetto : listaOggetti) {
            if (oggetto.getIdTitolo() == idCurrentTitolo)
                continue;

            Titolo titolo = listaTitoli.stream().filter(t -> t.getId() == oggetto.getIdTitolo()).findFirst().get();
            Reparto reparto = listaReparti.stream().filter(r -> r.getId() == titolo.getIdReparto()).findFirst().get();
            UnitaLocale unitaLocale = listaUnitaLocali.stream().filter(u -> u.getId() == reparto.getIdUnitaLocale())
                    .findFirst().get();
            Societa societa = listaSocieta.stream().filter(s -> s.getId() == unitaLocale.getIdSocieta()).findFirst()
                    .get();

            list.add(new ImportOggettoElement(oggetto, titolo, reparto, unitaLocale, societa));
        }

        FilteredList<ImportOggettoElement> filteredData = new FilteredList<>(FXCollections.observableArrayList(list));
        tableImportaOggetti.setItems(filteredData);
    }

    public ImportOggettoElement getSelectedData() {
        return selectedData;
    }
}
