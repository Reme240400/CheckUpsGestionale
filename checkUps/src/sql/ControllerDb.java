package sql;
import Models.ModelDb;





public class ControllerDb {

    public static void popolaListeDaDatabase(){
        ModelDb.popolaListaMansioni();
        ModelDb.popolaListaOggetti();
        ModelDb.popolaListaProvvedimenti();
        ModelDb.popolaListaReparti();
        ModelDb.popolaListaRischi();
        ModelDb.popolaListaSocieta();
        ModelDb.popolaListaTitoli();
        ModelDb.popolaListaUnitaLocali();
    }
    

    

}