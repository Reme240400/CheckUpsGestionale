package View.Controllers.Creazione.dialogPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
<<<<<<< HEAD
import java.util.function.Predicate;
=======
>>>>>>> 7add8566859de4a89eedc28a70082c60d13213be

import Helpers.ClassHelper;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;
import Models.imports.ImportTitoloElement;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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

        ObjectProperty<Predicate<Societa>> societaFilter = new SimpleObjectProperty<>();
        societaFilter.bind(Bindings.createObjectBinding(
                () -> soc -> soc != null && soc.getNome().toString().toLowerCase().trim()
                        .startsWith(textFieldFiltraSocieta.getText().toLowerCase().trim()),
                textFieldFiltraSocieta.textProperty()));

        ObjectProperty<Predicate<UnitaLocale>> unitaLocaleFilter = new SimpleObjectProperty<>();
        unitaLocaleFilter.bind(Bindings.createObjectBinding(
                () -> uLocale -> uLocale != null && uLocale.getNome().toString().toLowerCase().trim()
                        .startsWith(textFieldFiltraUnitaLocale.getText().toLowerCase().trim()),
                textFieldFiltraUnitaLocale.textProperty()));

        ObjectProperty<Predicate<Reparto>> repartoFilter = new SimpleObjectProperty<>();
        repartoFilter.bind(Bindings.createObjectBinding(
                () -> reparto -> reparto != null && reparto.getNome().toString().toLowerCase().trim()
                        .startsWith(textFieldFiltraReparto.getText().toLowerCase().trim()),
                textFieldFiltraReparto.textProperty()));

        ObjectProperty<Predicate<Reparto>> titoloFilter = new SimpleObjectProperty<>();
        titoloFilter.bind(Bindings.createObjectBinding(
                () -> titolo -> titolo != null && titolo.getNome().toString().toLowerCase().trim()
                        .startsWith(textFieldFiltraTitolo.getText().toLowerCase().trim()),
                textFieldFiltraTitolo.textProperty()));

        // filteredData.predicateProperty().bind(Bindings.createObjectBinding(
        // () -> societaFilter.get().and(unitaLocaleFilter.get()),
        // societaFilter, unitaLocaleFilter));

        // filteredData.predicateProperty().bind(Bindings.createObjectBinding(() -> {
        // String text = textFieldFiltraTitolo.getText();

        // if (text == null || text.isEmpty())
        // return null;

        // final String filterText = text.toLowerCase();

        // return o -> {
        // ObservableValue<?> observable = colTitolo.getCellObservableValue(o);
        // if (observable != null) {
        // Object value = observable.getValue();
        // if (value != null &&
        // value.toString().toLowerCase().trim().startsWith(filterText)) {
        // return true;
        // }
        // }
        // return false;
        // };
        // }, textFieldFiltraTitolo.textProperty()));

        tableImportaTitoli.setItems(filteredData);
    }

    public ImportTitoloElement getSelectedData() {
        return selectedData;
    }
}
