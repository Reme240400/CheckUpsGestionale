package View.Controllers.Creazione;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Controllers.ClassHelper;
import Models.ModelModifica;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.UnitaLocale;
import View.Controllers.ViewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CreazioneReparto extends ViewController {

    @FXML
    private JFXComboBox<String> cercaSocieta;

    @FXML
    private JFXComboBox<String> cercaUnita;

    @FXML
    private TableView<Reparto> tableReparti;

    @FXML
    private TableColumn<Reparto, Integer> idCol;

    @FXML
    private TableColumn<Reparto, String> nomeCol;

    @FXML
    private TableColumn<Reparto, String> descCol;

    // @FXML
    // private TextField textFieldSocieta;

    // @FXML
    // private TextField textFieldUnita;

    @FXML
    private JFXButton btnAnnulla;

    @FXML
    private JFXButton btnSalva;

    private List<Societa> listSocieta;
    private List<UnitaLocale> listUnitaLocale;
    private List<Reparto> listaReparto;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        listSocieta = ClassHelper.getListSocieta();
        listaReparto = ClassHelper.getListReparto();
        listUnitaLocale = ClassHelper.getListUnitaLocale();

        idCol.setCellValueFactory(new PropertyValueFactory<Reparto, Integer>("id"));
        nomeCol.setCellValueFactory(new PropertyValueFactory<Reparto, String>("nome"));
        descCol.setCellValueFactory(new PropertyValueFactory<Reparto, String>("descrizione"));

        ObservableList<String> items = FXCollections.observableArrayList();

        // * *************** popola il combobox *************** //

        for (Societa societa : listSocieta) {
            cercaSocieta.getItems().add(societa.getNome());
            items.add(societa.getNome());
        }

        // --------------- filtra il Combobox --------------- //
        FilteredList<String> filteredItems = ViewController.filterComboBoxSocieta(cercaSocieta, items);

        cercaSocieta.setItems(filteredItems);
    }
    // --------------- triggherato quando si seleziona una societa --------------- //
    public void selectSocieta(){
        List<UnitaLocale> specificList = null;
        modelCreazione.resetUnitaLocaleTmp();
        if (cercaSocieta.getValue() != null && !cercaSocieta.getValue().isEmpty()) {

            modelCreazione.createSocietaTmp(listSocieta.stream().filter(s -> s.getNome().equals(cercaSocieta.getValue())).findFirst().get());
            //textFieldSocieta.setText(modelCreazione.getSocietaTmp().getNome());

            fillTableView();

            specificList = listUnitaLocale.stream()
                                                .filter(u -> u.getIdSocieta() == modelCreazione.getSocietaTmp().getId())
                                                .toList();
        

            ObservableList<String> items = FXCollections.observableArrayList();

            // * *************** popola il combobox *************** //

            for (UnitaLocale unita : specificList) {
                items.add(unita.getNome());
            }

            // --------------- filtra il Combobox --------------- //
            FilteredList<String> filteredItems = ViewController.filterComboBoxSocieta(cercaUnita, items);

            cercaUnita.setItems(filteredItems);
        }
    }

    // --------------- triggherato quando si seleziona un' unita locale --------------- //
    public void selectUnita(){
        if(modelCreazione.getSocietaTmp() != null && cercaUnita.getValue() != null && !cercaUnita.getValue().isEmpty()){
            // ! DA USARE SEMPRE IN QUESTO MODO
            modelCreazione.createUnitaLocaleTmp(listUnitaLocale.stream()
                                                .filter(u -> u.getIdSocieta() == modelCreazione.getSocietaTmp().getId())
                                                .filter(u -> u.getNome().equals(cercaUnita.getValue()))
                                                .findFirst().get());

            //textFieldUnita.setText(modelCreazione.getUnitaLocaleTmp().getNome());

            fillTableView();
        }
    }

    // --------------- popola la tabella dei reparti --------------- //
    private void fillTableView() {
        List<Reparto> specificList = null;
        ObservableList<Reparto> observableList = null;
            
        if(modelCreazione.getSocietaTmp().getId() != -1 && modelCreazione.getUnitaLocaleTmp() == null){
            specificList = modelCreazione.fillAllRepartiTable(listaReparto, listUnitaLocale);

            observableList = FXCollections.observableArrayList(specificList);
            tableReparti.setItems(observableList);
    
        }else{
            specificList = modelCreazione.fillRepartiTable(listaReparto);

            observableList = FXCollections.observableArrayList(specificList);
            tableReparti.setItems(observableList);
        }
    }

    public void salvaReparto(javafx.event.ActionEvent event) {
        // fare la call alla Query

    }

    @FXML
    public void modify() throws IOException{

        Parent root = new Parent(){};
        modelModifica = new ModelModifica();

        root = modelPaths.switchToModificaReparto(modelModifica);

        changePane(root);
    }

    @FXML
    public void delete(){
        if(tableReparti.getSelectionModel().getSelectedItem() != null){
            Reparto reparto = tableReparti.getSelectionModel().getSelectedItem();
            eliminaRecord(reparto, reparto.getId());
            tableReparti.getItems().remove(reparto);
        }
    }

    @FXML
    public void addReparto() throws IOException{

        if (modelCreazione.getSocietaTmp() != null && modelCreazione.getUnitaLocaleTmp() != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/creaReparto_dialogPane.fxml"));
            DialogPane dialogPane = loader.load();

            DialogPaneAddR dialogController = loader.getController();

            dialogController.setModel(modelCreazione);
            dialogController.fillTextBox(modelCreazione.getSocietaTmp().getNome(), modelCreazione.getUnitaLocaleTmp().getNome());
            
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Crea Reparto");
            
            Optional<ButtonType> clickedButton = dialog.showAndWait();

            // ------------------- Se viene premuto il tasto "Applica" ------------------- //

            if(clickedButton.get() == ButtonType.APPLY){
                if( dialogController.getNome() != null){
                    int id = getNewId(listaReparto);
                    Reparto newReparto = new Reparto( id,
                                                        modelCreazione.getUnitaLocaleTmp().getId(), 
                                                        dialogController.getNome(), 
                                                        dialogController.getData());
                    modelCreazione.createRepartoTmp(newReparto);
                    inserisciNuovoRecord(newReparto);

                    tableReparti.refresh();

                }
            }
        }
    }

}
