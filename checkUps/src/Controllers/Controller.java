package Controllers;

import Models.ModelListe;

public class Controller {

    // Metodo per rimuovere un elemento da DB e da lista
    public static void eliminaRecord(Object obj, int id) {
        ModelListe.rimuoviDaLista(obj, id);
    }

    // Metodo per modificare un campo di un elemento sia da DB che da lista
    public static void modificaCampo(Object obj, String campo, String valore, int id) {
        ModelListe.modificaCampo(obj, campo, valore, id);
    }

    // Metodo per inserire un nuovo record sia su DB sia nelle liste
    public static void inserisciNuovoRecord(Object obj) {
        ControllerDb.inserisciRecord(obj);
        ModelListe.inserisciRecordInLista(obj);

    }

}
