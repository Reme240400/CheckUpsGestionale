package Models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ModelCreazione {

    private final BooleanProperty societySaved = new SimpleBooleanProperty(false);
    private final BooleanProperty unitaLocaleSaved = new SimpleBooleanProperty(false);
    private final BooleanProperty saved = new SimpleBooleanProperty(false);
    private final BooleanProperty discard = new SimpleBooleanProperty(false);

// initialize methods
    public BooleanProperty societaSavedProperty() {
        return societySaved;
    }

    public BooleanProperty discardProperty() {
        return discard;
    }

    public BooleanProperty savedProperty() {
        return saved;
    }

    public BooleanProperty unitaLocaleSavedProperty() {
        return unitaLocaleSaved;
    }
// end initialize methods

// ------------------ GETTER ------------------ //
    public final boolean isSocietySaved() {
        return societaSavedProperty().get();
    }

    public final boolean isUnitaLocaleSaved() {
        return unitaLocaleSavedProperty().get();
    }

    public final boolean isSaved() {
        return savedProperty().get();
    }

    public final boolean isDiscard() {
        return discardProperty().get();
    }
// ------------------ END GETTER ------------------ //

// ------------------ SETTER ------------------ //
    public final void setSocietySaved(boolean societySaved) {
        societaSavedProperty().set(societySaved);
    }

    public final void setUnitaSaved(boolean unitaLocaleSaved) {
        unitaLocaleSavedProperty().set(unitaLocaleSaved);
    }

    public final void setSaved(boolean saved) {
        savedProperty().set(saved);
    }

    public final void setDiscard(boolean discard) {
        discardProperty().set(discard);
    }
// ------------------ END SETTER ------------------ //

    public void reset() {

        // textFieldIndirizzo.clear();
        // textFieldLocalita.clear();
        // textFieldProvincia.clear();
        // textFieldSocieta.clear();
        // textFieldTel.clear();

        setSocietySaved(false);
        setUnitaSaved(false);
        setSaved(false);
        setDiscard(false);
    }
}
