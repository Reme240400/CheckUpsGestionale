package Controller;

import Model.ModelController;

public class PopulateLists {
    
    //CARICAMENTO DATI DA DB A LISTE
    public void popolaListeDaDatabase() {
        ModelController.popolaListaMansioni();
        ModelController.popolaListaTitoli();
        ModelController.popolaListaReparti();
        ModelController.popolaListaRischi();
        ModelController.popolaListaSocieta();
        ModelController.popolaListaOggetti();
        ModelController.popolaListaProvvedimenti();
        ModelController.popolaListaUnitaLocali();
    }
}
