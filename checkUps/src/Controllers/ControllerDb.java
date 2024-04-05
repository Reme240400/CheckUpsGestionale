package Controllers;

import Models.ModelDb;

public class ControllerDb {

    // Metodo per popolare tutte le liste dal DB
    public static void popolaListeDaDatabase() {
        ModelDb.popolaListaMansioni();
        ModelDb.popolaListaOggetti();
        ModelDb.popolaListaProvvedimenti();
        ModelDb.popolaListaReparti();
        ModelDb.popolaListaSocieta();
        ModelDb.popolaListaTitoli();
        ModelDb.popolaListaUnitaLocali();
    }

    public static void popolaListaSocietaDaDb() {
        ModelDb.popolaListaSocieta();
    }

    public static void popolaListaUnitaLocaleDaDb() {
        ModelDb.popolaListaUnitaLocali();
    }

    public static void popolaListaProvvedimentiDaDb() {
        ModelDb.popolaListaProvvedimenti();
    }

    // Metodo per eliminare un qualsiasi record passandogli il nome della tabella e
    // l'id del record da eliminare
    public static void eliminaRecordDaId(String tableName, int recordId) {
        ModelDb.eliminaRecord(tableName, recordId);
    }

    // Metodo per modificare un qualsiasi campo di tipo stringa di un record
    // passandogli nome della tabella, id del record da modificare, nome del campo
    // da modificare ed il nuovo valore da inserire
    public static void modificaCampoStringa(String tableName, int recordId, String campo, String nuovoValore) {
        ModelDb.modificaCampoStringa(tableName, recordId, campo, nuovoValore);
    }

    // Metodo per modificare un qualsiasi campo di tipo stringa di un record
    // passandogli nome della tabella, id del record da modificare, nome del campo
    // da modificare ed il nuovo valore da inserire
    public static void modificaCampoIntero(String tableName, int recordId, String campo, int nuovoValore) {
        ModelDb.modificaCampoIntero(tableName, recordId, campo, nuovoValore);
    }

    // Metodo per inserire una nuova riga in una qualsiasi tabella
    public void inserisciRecord(Object obj) {
        ModelDb.inserisciRecord(obj);
    }

}