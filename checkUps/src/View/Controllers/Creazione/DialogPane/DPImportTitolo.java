package View.Controllers.Creazione.dialogPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Helpers.ClassHelper;
import Models.Model;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;
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

public class DPImportTitolo implements Initializable {
    @FXML
    private TextField textFieldSocieta;

    @FXML
    private TextField textFieldUnitaLocale;

    @FXML
    private TextField textFieldReparto;

    @FXML
    private TextField textFieldFiltraSocieta;

    @FXML
    private TextField textFieldFiltraUnitaLocale;

    @FXML
    private TextField textFieldFiltraReparto;

    @FXML
    private TextField textFieldFiltraTitolo;

    @FXML
    private ImageView clearTitolo;

    @FXML
    private ImageView clearReparto;

    @FXML
    private ImageView clearUnitaLocale;

    @FXML
    private ImageView clearSocieta;

    @FXML
    private TableView<ImportTitoloElement> tableImportaTitoli;

    @FXML
    private TableColumn<ImportTitoloElement, String> colSocieta;

    @FXML
    private TableColumn<ImportTitoloElement, String> colUnitaLocale;

    @FXML
    private TableColumn<ImportTitoloElement, String> colReparto;

    @FXML
    private TableColumn<ImportTitoloElement, String> colTitolo;

    private ImportTitoloElement selectedData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colSocieta.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSocieta().getNome()));
        colUnitaLocale
                .setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUnitaLocale().getNome()));
        colReparto.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getReparto().getNome()));
        colTitolo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitolo().getDescrizione()));

        tableImportaTitoli.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selected) -> {
            if (selected != null) {
                this.selectedData = selected;
            }
        });

        clearTitolo.onMouseClickedProperty().set(e -> textFieldFiltraTitolo.clear());
        clearReparto.onMouseClickedProperty().set(e -> textFieldFiltraReparto.clear());
        clearUnitaLocale.onMouseClickedProperty().set(e -> textFieldFiltraUnitaLocale.clear());
        clearSocieta.onMouseClickedProperty().set(e -> textFieldFiltraSocieta.clear());

        // btnImport.disableProperty().bind(tableImportaTitoli.getSelectionModel().selectedItemProperty().isNull());
    }

    public void fillInfo(String nomeSocieta, String nomeUnita, String nomeReparto) {
        textFieldSocieta.setText(nomeSocieta);
        textFieldUnitaLocale.setText(nomeUnita);
        textFieldReparto.setText(nomeReparto);
    }

    public void populateTable(int idCurrentReparto) {
        var listaTitoli = ClassHelper.getListTitolo();
        var listaReparti = ClassHelper.getListReparto();
        var listaUnitaLocali = ClassHelper.getListUnitaLocale();
        var listaSocieta = ClassHelper.getListSocieta();

        List<ImportTitoloElement> list = new ArrayList<>();
        for (Titolo titolo : listaTitoli) {
            if (titolo.getIdReparto() == idCurrentReparto)
                continue;

            Reparto reparto = listaReparti.stream().filter(r -> r.getId() == titolo.getIdReparto()).findFirst().get();
            UnitaLocale unitaLocale = listaUnitaLocali.stream().filter(u -> u.getId() == reparto.getIdUnitaLocale())
                    .findFirst().get();
            Societa societa = listaSocieta.stream().filter(s -> s.getId() == unitaLocale.getIdSocieta()).findFirst()
                    .get();

            list.add(new ImportTitoloElement(titolo, reparto, unitaLocale, societa));
        }

        FilteredList<ImportTitoloElement> filteredData = new FilteredList<>(FXCollections.observableArrayList(list));

        var societaFilter = Model.genericTableViewFilter((ImportTitoloElement e) -> e.getSocieta().getNome(),
                textFieldFiltraSocieta);
        var unitaLocaleFilter = Model.genericTableViewFilter((ImportTitoloElement e) -> e.getUnitaLocale().getNome(),
                textFieldFiltraUnitaLocale);
        var repartoFilter = Model.genericTableViewFilter((ImportTitoloElement e) -> e.getReparto().getNome(),
                textFieldFiltraReparto);
        var titoloFilter = Model.genericTableViewFilter((ImportTitoloElement e) -> e.getTitolo().getDescrizione(),
                textFieldFiltraTitolo);

        filteredData.predicateProperty().bind(Bindings.createObjectBinding(
                () -> societaFilter.get().and(unitaLocaleFilter.get()).and(repartoFilter.get()).and(titoloFilter.get()),
                societaFilter, unitaLocaleFilter, repartoFilter, titoloFilter));

        tableImportaTitoli.setItems(filteredData);
    }

    public ImportTitoloElement getSelectedData() {
        return selectedData;
    }
}
