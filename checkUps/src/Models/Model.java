package Models;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import com.jfoenix.controls.JFXComboBox;

import Models.Tables.TablesId;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TextField;

public class Model {

    /**
     ** Serve a creare un nuovo id senza conflitti
     * 
     * @param path: file da controllare
     */
    public static <T extends TablesId> int autoSetId(List<T> list) {

        int maxId = 0;

        for (T t : list) {
            if (t.getId() > maxId)
                maxId = t.getId();
        }

        for (int i = 1; i <= maxId; i++) {
            boolean idExists = false;
            for (T t : list) {
                if (t.getId() == i) {
                    idExists = true;
                    break;
                }
            }

            if (!idExists) {
                return i;
            }
        }
        return maxId + 1;

    }

    public static FilteredList<String> filterComboBox(JFXComboBox<String> cercaItem, ObservableList<String> units) {

        FilteredList<String> filteredList = new FilteredList<String>(units, p -> true);

        // Add a listener to the textProperty of the combobox editor. The
        // listener will simply filter the list every time the input is changed
        // as long as the user hasn't selected an item in the list.
        cercaItem.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            final TextField editor = cercaItem.getEditor();
            final String selected = cercaItem.getSelectionModel().getSelectedItem();

            Platform.runLater(() -> {
                // If the no item in the list is selected or the selected item
                // isn't equal to the current input, we refilter the list.
                if (selected == null || !selected.equals(editor.getText())) {
                    filteredList.setPredicate(item -> {
                        // We return true for any items that starts with the
                        // same letters as the input. We use toUpperCase to
                        // avoid case sensitivity.
                        if (item.toUpperCase().startsWith(newValue.toUpperCase())) {
                            return true;
                        } else {
                            return false;
                        }
                    });
                }
            });
        });

        return filteredList;

    }

    public static FilteredList<String> filterComboBoxById(JFXComboBox<String> cercaItem, int id,
            ObservableList<String> units) {

        FilteredList<String> filteredList = new FilteredList<String>(units, p -> true);

        // Add a listener to the textProperty of the combobox editor. The
        // listener will simply filter the list every time the input is changed
        // as long as the user hasn't selected an item in the list.
        cercaItem.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            final TextField editor = cercaItem.getEditor();
            final String selected = cercaItem.getSelectionModel().getSelectedItem();

            Platform.runLater(() -> {
                // If the no item in the list is selected or the selected item
                // isn't equal to the current input, we refilter the list.
                if (selected == null || !selected.equals(editor.getText())) {
                    filteredList.setPredicate(item -> {
                        // We return true for any items that starts with the
                        // same letters as the input. We use toUpperCase to
                        // avoid case sensitivity.
                        if (item.toUpperCase().startsWith(newValue.toUpperCase())
                                && item.contains(String.valueOf(id))) {
                            return true;
                        } else {
                            return false;
                        }
                    });
                }
            });
        });

        return filteredList;

    }

    public static <T> ObjectProperty<Predicate<T>> genericTableViewFilter(Function<T, String> fieldFun,
            TextField filterField) {
        var property = new SimpleObjectProperty<Predicate<T>>();
        property.bind(Bindings.createObjectBinding(
                () -> elem -> elem != null
                        && fieldFun.apply(elem).toLowerCase().trim()
                                .startsWith(filterField.getText().toLowerCase().trim()),
                filterField.textProperty()));
        return property;
    }
}
