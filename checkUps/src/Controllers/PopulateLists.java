package Controllers;

public class PopulateLists {
    
    //CARICAMENTO DATI DA DB A LISTE
    public void popolaListeDaDatabase() {
        DbController.popolaListaMansioni();
        DbController.popolaListaTitoli();
        DbController.popolaListaReparti();
        DbController.popolaListaRischi();
        //DbController.popolaListaSocieta();
        DbController.popolaListaOggetti();
        DbController.popolaListaProvvedimenti();
        DbController.popolaListaUnitaLocali();
    }
}
