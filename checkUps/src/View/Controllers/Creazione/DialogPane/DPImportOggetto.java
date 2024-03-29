package View.Controllers.Creazione.dialogPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Helpers.ClassHelper;
import Models.Model;
import Models.Tables.Oggetto;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;
import Models.imports.ImportOggettoElement;
import Models.imports.ImportTitoloElement;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

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
    private ImageView clearO;

    @FXML
    private ImageView clearT;

    @FXML
    private ImageView clearR;

    @FXML
    private ImageView clearU;

    @FXML
    private ImageView clearS;

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

        clearO.onMouseClickedProperty().set(e -> textFieldFiltraO.clear());
        clearT.onMouseClickedProperty().set(e -> textFieldFiltraT.clear());
        clearR.onMouseClickedProperty().set(e -> textFieldFiltraR.clear());
        clearU.onMouseClickedProperty().set(e -> textFieldFiltraU.clear());
        clearS.onMouseClickedProperty().set(e -> textFieldFiltraS.clear());

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

        var societaFilter = Model.genericTableViewFilter((ImportOggettoElement e) -> e.getSocieta().getNome(),
                textFieldFiltraS);
        var unitaLocaleFilter = Model.genericTableViewFilter((ImportOggettoElement e) -> e.getUnitaLocale().getNome(),
                textFieldFiltraU);
        var repartoFilter = Model.genericTableViewFilter((ImportOggettoElement e) -> e.getReparto().getNome(),
                textFieldFiltraR);
        var titoloFilter = Model.genericTableViewFilter((ImportOggettoElement e) -> e.getTitolo().getDescrizione(),
                textFieldFiltraT);
        var oggettoFilter = Model.genericTableViewFilter((ImportOggettoElement e) -> e.getOggetto().getNome(),
                textFieldFiltraO);

        filteredData.predicateProperty().bind(Bindings.createObjectBinding(
                () -> societaFilter.get().and(unitaLocaleFilter.get()).and(repartoFilter.get()).and(titoloFilter.get())
                        .and(oggettoFilter.get()),
                societaFilter, unitaLocaleFilter, repartoFilter, titoloFilter, oggettoFilter));

        tableImportaOggetti.setItems(filteredData);
    }

    public ImportOggettoElement getSelectedData() {
        return selectedData;
    }
}
