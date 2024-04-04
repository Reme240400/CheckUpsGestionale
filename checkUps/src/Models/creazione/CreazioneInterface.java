package Models.creazione;

import Models.ModelCreazione;
import Models.ModelPaths;
import Models.TipoCreazionePagina;
import javafx.scene.control.TextField;

public interface CreazioneInterface {
    public void changePage(TipoCreazionePagina tipo, boolean buttonReset);

    public void setModel(ModelCreazione modelCreazione, ModelPaths modelPaths);

    public boolean checkNeededTextFields(TextField... fields);
}
