package Models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ModelSave {

    private final BooleanProperty saved = new SimpleBooleanProperty(false);

    public BooleanProperty savedProperty() {
        return saved;
    }

    public final void setSaved(boolean saved) {
        savedProperty().set(saved);
    }
    
    public final boolean isSaved() {
        return savedProperty().get();
    }
    
}
