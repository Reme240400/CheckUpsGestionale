package Controllers;

import java.util.List;

import Models.Model;
import Models.ModelDb;
import Models.ModelListe;
import Models.Tables.TablesId;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class Controller {

    // Metodo per rimuovere un elemento da DB e da lista
    public static void eliminaRecord(Object obj, int id) {
        ModelListe.rimuoviDaLista(obj, id);
        ModelDb.eliminaRecord(obj, id);
    }

    // Metodo per modificare un campo di un elemento sia da DB che da lista
    public static void modificaCampo(Object obj/*, String campo, String valore, int id*/) {
        ModelListe.modificaCampoInLista(obj/*, campo, valore, id*/);
        ModelDb.modificaCampo(obj/*, campo, valore, id*/);
    }

    // Metodo per inserire un nuovo record sia su DB sia nelle liste
    public static void inserisciNuovoRecord(Object obj) {
        ModelListe.inserisciRecordInLista(obj);
        ModelDb.inserisciRecord(obj);
        
    }

    public static <T extends TablesId> int getNewId(List<T> list)
    {
        return Model.autoSetId(list);
    }

    public static void changePane(StackPane stackPane, Parent root){
        stackPane.getChildren().removeAll();
        stackPane.getChildren().setAll(root);
    }

}
