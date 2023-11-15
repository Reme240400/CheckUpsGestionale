package Controllers;

import java.util.List;

import Models.Model;
import Models.ModelDb;
import Models.ModelListe;
import Models.Tables.TablesId;

public class Controller {

    // Metodo per rimuovere un elemento da DB e da lista
    public static void eliminaRecord(Object obj, int id) {
        ModelListe.rimuoviDaLista(obj, id);
        ModelDb.eliminaRecord(obj, id);
    }

    // Metodo per modificare un campo di un elemento sia da DB che da lista
    public static void modificaCampo(Object obj, String campo, String valore, int id) {
        ModelListe.modificaCampo(obj, campo, valore, id);
        
    }

    // Metodo per inserire un nuovo record sia su DB sia nelle liste
    public void inserisciNuovoRecord(Object obj) {
        ModelListe.inserisciRecordInLista(obj);
        ModelDb.inserisciRecord(obj);
        
    }

    public <T extends TablesId> int getNewId(List<T> list)
    {
        return Model.autoSetId(list);
    }

    //public

}
