package View.Controllers.Creazione.dialogPane;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Helpers.ClassHelper;
import Models.Tables.Oggetto;
import Models.Tables.Provvedimento;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;
import Models.others.ScadenzaElement;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
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

        @FXML
        private DialogPane dialogPane;

        @Override
        public void initialize(URL location, ResourceBundle resources) {
                societaCol.setCellValueFactory(
                                data -> new SimpleStringProperty(data.getValue().getSocieta().getNome()));
                provCol.setCellValueFactory(
                                data -> new SimpleStringProperty(data.getValue().getProvvedimento().getNome()));
                emailCol.setCellValueFactory(data -> new SimpleStringProperty(""));
                scadenzaCol.setCellValueFactory(data -> new SimpleStringProperty(
                                data.getValue().getProvvedimento().getDataScadenza().get().toString()));

                addCustomButtons(dialogPane);
        }

        public void populate(List<Provvedimento> expired) {
                List<ScadenzaElement> tableElements = new ArrayList<>();

                for (Provvedimento prov : expired) {
                        Oggetto oggetto = ClassHelper.getListOggetto().stream()
                                        .filter(o -> o.getId() == prov.getIdOggetto())
                                        .findFirst().get();
                        Titolo titolo = ClassHelper.getListTitolo().stream()
                                        .filter(t -> t.getId() == oggetto.getIdTitolo())
                                        .findFirst().get();
                        Reparto reparto = ClassHelper.getListReparto().stream()
                                        .filter(r -> r.getId() == titolo.getIdReparto())
                                        .findFirst().get();
                        UnitaLocale uLocale = ClassHelper.getListUnitaLocale().stream()
                                        .filter(u -> u.getId() == reparto.getIdUnitaLocale()).findFirst().get();
                        Societa soc = ClassHelper.getListSocieta().stream()
                                        .filter(s -> s.getId() == uLocale.getIdSocieta())
                                        .findFirst().get();

                        tableElements.add(new ScadenzaElement(soc, prov));
                }

                tableScadenze.setItems(FXCollections.observableArrayList(tableElements));
        }

        protected void addCustomButtons(DialogPane dialogPane) {

                final ButtonType bTypeCalculate = new ButtonType("Salva", ButtonData.OK_DONE);
                dialogPane.getButtonTypes().add(bTypeCalculate);

                final Button buttonCalculate = (Button) dialogPane.lookupButton(bTypeCalculate);

                buttonCalculate.addEventFilter(ActionEvent.ACTION, (event) -> {
                        event.consume();
                        createFile();
                        dialogPane.getScene().getWindow().hide();
                });
        }

        private void createFile() {
                File file = new File(System.getProperty("user.home") + "/Desktop/scadenze.txt");

                try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

                        for (ScadenzaElement element : tableScadenze.getItems()) {
                                writer.write(element.getSocieta().getNome() + " | "
                                                + element.getProvvedimento().getNome() + " | "
                                                + element.getProvvedimento().getDataScadenza().get().toString() + "\n");
                                writer.newLine();
                        }

                        writer.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
}
