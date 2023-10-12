package Models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ModelCreazione {

    private final BooleanProperty societySaved = new SimpleBooleanProperty(false);
    private final BooleanProperty unitaLocaleSaved = new SimpleBooleanProperty(false);
    private final BooleanProperty repartoSaved = new SimpleBooleanProperty(false);

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

    public BooleanProperty repartoSavedProperty() {
        return repartoSaved ;
    }

    public final boolean isRepartoSaved() {
        return repartoSavedProperty().get();
    }

    public final void setRepartoSaved(boolean repartoSaved) {
        repartoSavedProperty().set(repartoSaved);
    }

    
}
