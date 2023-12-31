package Controllers;

import Models.Model;
import sql.ControllerDb;

public class Controller {

    // Metodo per rimuovere un elemento da DB e da lista
    public static void eliminaRecord(Object obj, int id) {
        Model.rimuoviDaLista(obj, id);
    }

    // Metodo per modificare un campo di un elemento sia da DB che da lista
    public static void modificaCampo(Object obj, String campo, String valore, int id) {
        Model.modificaCampo(obj, campo, valore, id);
    }

    // Metodo per inserire un nuovo record sia su DB sia nelle liste
    public static void inserisciNuovoRecord(Object obj) {
        ControllerDb.inserisciRecord(obj);
        Model.inserisciRecordInLista(obj);

    }

}
