package Controllers;

import java.util.List;

import Models.Model;
import Models.ModelDb;
import Models.ModelListe;
import Models.Tables.TableData;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class Controller {

    // Metodo per rimuovere un elemento da DB e da lista
    public static void eliminaRecord(TableData tData) {
        tData.selfRemoveFromList();
        ModelDb.eliminaRecord(tData);
    }

    // Metodo per modificare un campo di un elemento sia da DB che da lista
    public static void modificaCampo(TableData obj/* , String campo, String valore, int id */) {
        ModelListe.modificaCampoInLista(obj);
        ModelDb.modificaCampo(obj);
    }

    // Metodo per inserire un nuovo record sia su DB sia nelle liste
    public static void inserisciNuovoRecord(Object obj) {
        ModelListe.inserisciRecordInLista(obj); // Locale
        ModelDb.inserisciRecord(obj); // DB
    }

    public static void addOrUpdateCampo(Object obj) {
        // if (ModelDb.existField(obj)) {
        // ModelDb.modificaCampo(obj);
        // } else {
        ModelDb.inserisciRecord(obj);
        // }
    }

    public static <T extends TableData> int getNewId(List<T> list) {
        return Model.autoSetId(list);
    }

    public static void changePane(StackPane stackPane, Parent root) {
        stackPane.getChildren().removeAll();
        stackPane.getChildren().setAll(root);
    }

}
