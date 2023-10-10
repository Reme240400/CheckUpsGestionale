package Models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class CreazioneModel {

    private final BooleanProperty societySaved = new SimpleBooleanProperty(false);
    private final BooleanProperty unitaLocaleSaved = new SimpleBooleanProperty(false);

    public BooleanProperty societaSavedProperty() {
        return societySaved ;
    }

    public final boolean isSocietySaved() {
        return societaSavedProperty().get();
    }

    public final void setSocietySaved(boolean societySaved) {
        societaSavedProperty().set(societySaved);
    }

    public BooleanProperty unitaLocaleSavedProperty() {
        return unitaLocaleSaved ;
    }

    public final boolean isUnitaLocaleSaved() {
        return unitaLocaleSavedProperty().get();
    }

    public final void setUnitaSaved(boolean unitaLocaleSaved) {
        unitaLocaleSavedProperty().set(unitaLocaleSaved);
    }
    
}
