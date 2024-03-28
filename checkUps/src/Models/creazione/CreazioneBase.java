package Models.creazione;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import Controllers.Controller;
import Models.Alerts;
import Models.ModelCreazione;
import Models.ModelPaths;
import Models.TipoCreazionePagina;
import Models.Tables.Oggetto;
import Models.Tables.Provvedimento;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableView;

public class CreazioneBase implements CreazioneInterface {
    protected ModelCreazione modelCreazione;
    protected ModelPaths modelPaths;

    protected Societa localSocieta;
    protected UnitaLocale localUnita;
    protected Reparto localReparto;
    protected Titolo localTitolo;
    protected Oggetto localOggetto;
    protected Provvedimento localProvvedimento;

    protected <T> void showDialog(String relativePath, String title, Consumer<T> prepareDialogPane,
            Consumer<T> updateChanges) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/" + relativePath));
        DialogPane dialogPane = null;

        try {
            dialogPane = loader.load();
            T controller = loader.getController();
            prepareDialogPane.accept(controller);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle(title);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.get() == ButtonType.APPLY) {
                updateChanges.accept(controller);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changePage(TipoCreazionePagina tipo, boolean buttonReset) {
        if (buttonReset) {
            modelCreazione.setCanGoNext(false);
            modelCreazione.setSaved(false);
        }

        Parent root = modelPaths.switchToCreazioneByTipo(tipo, modelCreazione);
        Controller.changePane(modelPaths.getStackPaneCrea(), root);
    }

    @Override
    public void setModel(ModelCreazione modelCreazione, ModelPaths modelPaths) {
        this.modelCreazione = modelCreazione;
        this.modelPaths = modelPaths;

        this.localSocieta = modelCreazione.getSocietaTmp();
        this.localUnita = modelCreazione.getUnitaLocaleTmp();
        this.localReparto = modelCreazione.getRepartoTmp();
        this.localTitolo = modelCreazione.getTitoloTmp();
        this.localOggetto = modelCreazione.getOggettoTmp();
        this.localProvvedimento = modelCreazione.getProvvedimentoTmp();
    }

    // Metodo generico che riempie una tabella con i dati di una lista (usato solo
    // in CreazioneReparto, CreazioneTitolo, CreazioneOggetto,
    // CreazioneProvvedimento)
    public <T, X> void fillTable(TableView<T> table, List<T> list, X local) {
        List<T> specificList = null;
        ObservableList<T> observableList = null;

        if (local != null) {
            if (local instanceof UnitaLocale) {
                UnitaLocale localUnitaLocale = (UnitaLocale) local;

                specificList = list.stream()
                        .filter(rep -> ((Reparto) rep).getIdUnitaLocale() == localUnitaLocale.getId())
                        .toList();

            } else if (local instanceof Reparto) {
                Reparto localReparto = (Reparto) local;

                specificList = list.stream()
                        .filter(titolo -> ((Titolo) titolo).getIdReparto() == localReparto.getId())
                        .toList();
            } else if (local instanceof Titolo) {
                Titolo localTitolo = (Titolo) local;

                specificList = list.stream()
                        .filter(oggetto -> ((Oggetto) oggetto).getIdTitolo() == localTitolo.getId())
                        .toList();
            } else if (local instanceof Oggetto) {
                Oggetto localOggetto = (Oggetto) local;

                specificList = list.stream()
                        .filter(provvedimento -> ((Provvedimento) provvedimento).getIdOggetto() == localOggetto.getId())
                        .toList();
            }

            observableList = FXCollections.observableArrayList(specificList);
            table.setItems(observableList);
        } else
            Alerts.errorAllert("Errore2", "Societa non selezionata",
                    "Impossibile selezionare l'unita locale perchè non è stata selezionata una societa");
    }
}