package Models.others;

import java.util.List;
import java.util.stream.Stream;

import Controllers.Controller;
import Interfaces.CreazioneInterface;
import Models.ModelCreazione;
import Models.ModelPaths;
import Models.Tables.Oggetto;
import Models.Tables.Provvedimento;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CreazioneBase implements CreazioneInterface {
    protected ModelCreazione modelCreazione;
    protected ModelPaths modelPaths;

    protected Societa localSocieta;
    protected UnitaLocale localUnita;
    protected Reparto localReparto;
    protected Titolo localTitolo;
    protected Oggetto localOggetto;
    protected Provvedimento localProvvedimento;

    @Override
    public boolean checkNeededTextFields(TextField... fields) {
        boolean noFieldsEmpty = !Stream.of(fields).anyMatch(t -> t.getText() == null || t.getText().isEmpty());
        return noFieldsEmpty;
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

    protected boolean isSingleFieldChanged(String text, TextInputControl field) {
        return !text.equals(field.getText());
    }

    protected boolean isImageChanged(Image oldImage, ImageView newImageView) {
        Image newImage = newImageView.getImage();

        if (oldImage == null && newImage != null)
            return true;
        if (oldImage != null && newImage == null)
            return true;

        if (oldImage.getWidth() != newImage.getWidth())
            return true;
        if (oldImage.getHeight() != newImage.getHeight())
            return true;

        for (int x = 0; x < oldImage.getWidth(); x++) {
            for (int y = 0; y < oldImage.getHeight(); y++) {
                int firstArgb = oldImage.getPixelReader().getArgb(x, y);
                int secondArgb = newImage.getPixelReader().getArgb(x, y);

                if (firstArgb != secondArgb)
                    return true;
            }
        }

        return false;
    }

    // Metodo generico che riempie una tabella con i dati di una lista (usato solo
    // in CreazioneReparto, CreazioneTitolo, CreazioneOggetto,
    // CreazioneProvvedimento)
    public <T, X> void fillTable(TableView<T> table, List<T> list, X local) {
        List<T> specificList = null;
        ObservableList<T> observableList = null;

        if (local == null) {
            Alerts.errorAlert("Errore", "Impossibile riempire la tabella", null);
            return;
        }

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

    }
}