package View.Controllers.Creazione;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Helpers.ClassHelper;
import Models.Model;
import Models.ModelCreazione;
import Models.ModelModifica;
import Models.ModelPaths;
import Models.Tables.Oggetto;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;

public class CreazioneProvvedimento implements Initializable{

    private ModelCreazione modelCreazione;
    private ModelPaths modelPaths;
    private ModelModifica modelModifica;

    private List<Societa> societaList = ClassHelper.getListSocieta();
    private List<UnitaLocale> unitaLocaleList = ClassHelper.getListUnitaLocale();
    private List<Oggetto> oggettoList = ClassHelper.getListOggetto();
    private List<Reparto> repartoList = ClassHelper.getListReparto();
    private List<Titolo> titoloList = ClassHelper.getListTitolo();

    private Societa localSocieta;
    private UnitaLocale localUnita;
    private Reparto localReparto;
    private Titolo localTitolo;
    private Oggetto localOggetto;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        societaList = ClassHelper.getListSocieta();
        unitaLocaleList = ClassHelper.getListUnitaLocale();
        oggettoList = ClassHelper.getListOggetto();
        repartoList = ClassHelper.getListReparto();
        titoloList = ClassHelper.getListTitolo();

        idCol.setCellValueFactory(new PropertyValueFactory<Oggetto, Integer>("id"));
        nomeColT.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(titoloList.stream()
                                                        .filter(reparto -> reparto.getId() == cellData.getValue().getIdTitolo())
                                                        .findFirst().get()
                                                        .getDescrizione());
        });

        nomeCol.setCellValueFactory(new PropertyValueFactory<Oggetto, String>("nome"));


        ObservableList<String> items = FXCollections.observableArrayList();

        // * *************** popola il combobox *************** //

        for (Societa societa : societaList) {
            cercaSocieta.getItems().add(societa.getNome());
            items.add(societa.getNome());
        }

        // --------------- filtra il Combobox --------------- //
        FilteredList<String> filteredItems = Model.filterComboBox(cercaSocieta, items);

        cercaSocieta.setItems(filteredItems);

    }

    

}
