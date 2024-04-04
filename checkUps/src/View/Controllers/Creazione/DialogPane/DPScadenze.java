package View.Controllers.Creazione.dialogPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Helpers.ClassHelper;
import Models.ScadenzaElement;
import Models.Tables.Oggetto;
import Models.Tables.Provvedimento;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DPScadenze implements Initializable {
    @FXML
    private TableView<ScadenzaElement> tableScadenze;

    @FXML
    private TableColumn<ScadenzaElement, String> societaCol;

    @FXML
    private TableColumn<ScadenzaElement, String> provCol;

    @FXML
    private TableColumn<ScadenzaElement, String> emailCol;

    @FXML
    private TableColumn<ScadenzaElement, String> scadenzaCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        societaCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSocieta().getNome()));
        provCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProvvedimento().getNome()));
        emailCol.setCellValueFactory(data -> new SimpleStringProperty(""));
        scadenzaCol.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getProvvedimento().getDataScadenza().get().toString()));
    }

    public void populate(List<Provvedimento> expired) {
        List<ScadenzaElement> tableElements = new ArrayList<>();

        for (Provvedimento prov : expired) {
            Oggetto oggetto = ClassHelper.getListOggetto().stream().filter(o -> o.getId() == prov.getIdOggetto())
                    .findFirst().get();
            Titolo titolo = ClassHelper.getListTitolo().stream().filter(t -> t.getId() == oggetto.getIdTitolo())
                    .findFirst().get();
            Reparto reparto = ClassHelper.getListReparto().stream().filter(r -> r.getId() == titolo.getIdReparto())
                    .findFirst().get();
            UnitaLocale uLocale = ClassHelper.getListUnitaLocale().stream()
                    .filter(u -> u.getId() == reparto.getIdUnitaLocale()).findFirst().get();
            Societa soc = ClassHelper.getListSocieta().stream().filter(s -> s.getId() == uLocale.getIdSocieta())
                    .findFirst().get();

            tableElements.add(new ScadenzaElement(soc, prov));
        }

        tableScadenze.setItems(FXCollections.observableArrayList(tableElements));
    }
}
