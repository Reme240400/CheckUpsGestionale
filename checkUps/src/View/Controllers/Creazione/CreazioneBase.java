package View.Controllers.Creazione;

import Controllers.Controller;
import Models.ModelCreazione;
import Models.ModelPaths;
import Models.TipoCreazionePagina;
import Models.Tables.Oggetto;
import Models.Tables.Provvedimento;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;
import javafx.scene.Parent;

public abstract class CreazioneBase {
    protected ModelCreazione modelCreazione;
    protected ModelPaths modelPaths;

    protected Societa localSocieta;
    protected UnitaLocale localUnita;
    protected Reparto localReparto;
    protected Titolo localTitolo;
    protected Oggetto localOggetto;
    protected Provvedimento localProvvedimento;

    public void changePage(TipoCreazionePagina tipo, boolean buttonReset) {
        if (buttonReset) {
            modelCreazione.setCanGoNext(false);
            modelCreazione.setSaved(false);
        }

        Parent root = modelPaths.switchToCreazioneByTipo(tipo, modelCreazione);
        Controller.changePane(modelPaths.getStackPaneCrea(), root);
    }

    public void setModel(ModelCreazione modelCreazione, ModelPaths modelPaths) {
        this.modelCreazione = modelCreazione;
        this.modelPaths = modelPaths;

        this.localSocieta = modelCreazione.getSocietaTmp();
        this.localUnita = modelCreazione.getUnitaLocaleTmp();
        this.localReparto = modelCreazione.getRepartoTmp();
        this.localTitolo = modelCreazione.getTitoloTmp();
        this.localOggetto = modelCreazione.getOggettoTmp();
        this.localProvvedimento = modelCreazione.getProvvedimentoTmp();
    }
}