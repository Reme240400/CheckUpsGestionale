package View.Controllers.Creazione;

import java.util.List;

import Models.Alerts;
import Models.Tables.Oggetto;
import Models.Tables.Provvedimento;
import Models.Tables.Reparto;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public abstract class CreazioneBaseTable extends CreazioneBase {
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
