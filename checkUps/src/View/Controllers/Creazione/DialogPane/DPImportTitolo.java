package View.Controllers.Creazione.dialogPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Helpers.ClassHelper;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;
import Models.imports.ImportTitoloElement;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Pair;

public class DPImportTitolo implements Initializable {
    @FXML
    private TextField textFieldSocieta;

    @FXML
    private TextField textFieldUnitaLocale;

    @FXML
    private TextField textFieldReparto;

    @FXML
    private TextField textFieldFiltra;

    @FXML
    private TableView<ImportTitoloElement> tableImportaTitoli;

    @FXML
    private TableColumn<ImportTitoloElement, String> colReparto;

    @FXML
    private TableColumn<ImportTitoloElement, String> colTitolo;

    private ImportTitoloElement selectedData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        filteredData.predicateProperty().bind(Bindings.createObjectBinding(() -> {
            String text = textFieldFiltra.getText();

            if (text == null || text.isEmpty())
                return null;

            final String filterText = text.toLowerCase();

            return o -> {
                ObservableValue<?> observable = colTitolo.getCellObservableValue(o);
                if (observable != null) {
                    Object value = observable.getValue();
                    if (value != null && value.toString().toLowerCase().trim().startsWith(filterText)) {
                        return true;
                    }
                }
                return false;
            };
        }, textFieldFiltra.textProperty()));

        tableImportaTitoli.setItems(filteredData);
    }

    public ImportTitoloElement getSelectedData() {
        return selectedData;
    }
}
