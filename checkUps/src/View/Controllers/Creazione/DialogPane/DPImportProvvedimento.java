package View.Controllers.Creazione.dialogPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Helpers.ClassHelper;
import Models.Model;
import Models.Tables.Oggetto;
import Models.Tables.Provvedimento;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;
import Models.imports.ImportProvvedimentoElement;
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

public class DPImportProvvedimento implements Initializable {

    @FXML
    private TextField textFieldSocieta;

    @FXML
    private TextField textFieldUnitaLocale;

    @FXML
    private TextField textFieldReparto;

    @FXML
    private TextField textFieldTitolo;

    @FXML
    private TextField textFieldOggetto;

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
    private ImageView clearP;

    @FXML
    private TextField textFieldFiltraO;

    @FXML
    private TextField textFieldFiltraT;

    @FXML
    private TextField textFieldFiltraR;

    @FXML
    private TextField textFieldFiltraU;

    @FXML
    private TextField textFieldFiltraS;

    @FXML
    private TextField textFieldFiltraP;

    @FXML
    private TableColumn<ImportProvvedimentoElement, String> colProv;

    @FXML
    private TableColumn<ImportProvvedimentoElement, String> colOggetto;

    @FXML
    private TableColumn<ImportProvvedimentoElement, String> colTitolo;

    @FXML
    private TableColumn<ImportProvvedimentoElement, String> colReparto;

    @FXML
    private TableColumn<ImportProvvedimentoElement, String> colUnitaLocale;

    @FXML
    private TableColumn<ImportProvvedimentoElement, String> colSocieta;

    @FXML
    private TableView<ImportProvvedimentoElement> tableImportaProvvedimenti;

    private ImportProvvedimentoElement selectedData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colProv.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProvvedimento().getNome()));
        colOggetto.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOggetto().getNome()));
        colReparto.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getReparto().getNome()));
        colTitolo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitolo().getDescrizione()));
        colUnitaLocale
                .setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUnitaLocale().getNome()));
        colSocieta.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSocieta().getNome()));

        tableImportaProvvedimenti.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, selected) -> {
                    if (selected != null) {
                        this.selectedData = selected;
                    }
                });

        clearO.onMouseClickedProperty().set(e -> textFieldFiltraO.clear());
        clearT.onMouseClickedProperty().set(e -> textFieldFiltraT.clear());
        clearR.onMouseClickedProperty().set(e -> textFieldFiltraR.clear());
        clearU.onMouseClickedProperty().set(e -> textFieldFiltraU.clear());
        clearS.onMouseClickedProperty().set(e -> textFieldFiltraS.clear());
        clearP.onMouseClickedProperty().set(e -> textFieldFiltraP.clear());
    }

    public void fillInfo(String nomeSocieta, String nomeUnita, String nomeReparto, String nomeTitolo,
            String nomeOggetto) {
        textFieldSocieta.setText(nomeSocieta);
        textFieldUnitaLocale.setText(nomeUnita);
        textFieldReparto.setText(nomeReparto);
        textFieldTitolo.setText(nomeTitolo);
        textFieldOggetto.setText(nomeOggetto);
    }

    public void populateTable(int idCurrentOggetto) {
        var listaProvvedimenti = ClassHelper.getListProvvedimento();
        var listaOggetti = ClassHelper.getListOggetto();
        var listaTitoli = ClassHelper.getListTitolo();
        var listaReparti = ClassHelper.getListReparto();
        var listaUnitaLocali = ClassHelper.getListUnitaLocale();
        var listaSocieta = ClassHelper.getListSocieta();

        List<ImportProvvedimentoElement> list = new ArrayList<>();
        for (Provvedimento prov : listaProvvedimenti) {
            if (prov.getIdOggetto() == idCurrentOggetto)
                continue;

            Oggetto oggetto = listaOggetti.stream().filter(o -> o.getId() == prov.getIdOggetto()).findFirst().get();
            Titolo titolo = listaTitoli.stream().filter(t -> t.getId() == oggetto.getIdTitolo()).findFirst().get();
            Reparto reparto = listaReparti.stream().filter(r -> r.getId() == titolo.getIdReparto()).findFirst().get();
            UnitaLocale unitaLocale = listaUnitaLocali.stream().filter(u -> u.getId() == reparto.getIdUnitaLocale())
                    .findFirst().get();
            Societa societa = listaSocieta.stream().filter(s -> s.getId() == unitaLocale.getIdSocieta()).findFirst()
                    .get();

            list.add(new ImportProvvedimentoElement(prov, oggetto, titolo, reparto, unitaLocale, societa));
        }

        FilteredList<ImportProvvedimentoElement> filteredData = new FilteredList<>(
                FXCollections.observableArrayList(list));

        var societaFilter = Model.genericTableViewFilter((ImportProvvedimentoElement e) -> e.getSocieta().getNome(),
                textFieldFiltraS);
        var unitaLocaleFilter = Model.genericTableViewFilter(
                (ImportProvvedimentoElement e) -> e.getUnitaLocale().getNome(),
                textFieldFiltraU);
        var repartoFilter = Model.genericTableViewFilter((ImportProvvedimentoElement e) -> e.getReparto().getNome(),
                textFieldFiltraR);
        var titoloFilter = Model.genericTableViewFilter(
                (ImportProvvedimentoElement e) -> e.getTitolo().getDescrizione(),
                textFieldFiltraT);
        var oggettoFilter = Model.genericTableViewFilter((ImportProvvedimentoElement e) -> e.getOggetto().getNome(),
                textFieldFiltraO);
        var provvedimentoFilter = Model.genericTableViewFilter(
                (ImportProvvedimentoElement e) -> e.getProvvedimento().getNome(), textFieldFiltraP);

        filteredData.predicateProperty().bind(Bindings.createObjectBinding(
                () -> societaFilter.get().and(unitaLocaleFilter.get()).and(repartoFilter.get()).and(titoloFilter.get())
                        .and(oggettoFilter.get()).and(provvedimentoFilter.get()),
                societaFilter, unitaLocaleFilter, repartoFilter, titoloFilter, oggettoFilter, provvedimentoFilter));

        tableImportaProvvedimenti.setItems(filteredData);
    }

    public ImportProvvedimentoElement getSelectedData() {
        return selectedData;
    }
}
