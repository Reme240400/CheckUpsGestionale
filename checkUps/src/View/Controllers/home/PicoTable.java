package View.Controllers.home;

import java.util.List;
import java.util.function.Consumer;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public abstract class PicoTable<Z> implements Initializable {
    protected ObservableList<Z> originalData = FXCollections.observableArrayList();
    protected Consumer<Z> selectAction;

    public abstract void update(List<Z> updatedValues);

    public abstract void clearTable();

    public void setClickAction(Consumer<Z> selected) {
        this.selectAction = selected;
    }

    protected <X, Y> void makeValueProperty(TableColumn<X, Y> col, String id) {
        col.setCellValueFactory(new PropertyValueFactory<>(id));
    }

    protected void registerSelectAction(TableView<Z> table) {
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                this.selectAction.accept(newValue);
            }
        });
    }

    protected void registerTextFilter(TableView<Z> table, TextField textfield, TableColumn<Z, String> col) {
        textfield.textProperty().addListener(
                (observable, oldValue, newValue) -> {

                    FilteredList<Z> filteredData = new FilteredList<Z>(this.originalData);
                    filteredData.predicateProperty().bind(Bindings.createObjectBinding(() -> {
                        String filterText = textfield.getText().toLowerCase();

                        if (filterText == null || filterText.isEmpty())
                            return null;

                        return o -> {
                            var v = col.getCellObservableValue(o).getValue();
                            return v != null && v.toLowerCase().startsWith(filterText);
                        };
                    }, textfield.textProperty()));

                    SortedList<Z> sortedData = new SortedList<>(filteredData);
                    sortedData.comparatorProperty().bind(table.comparatorProperty());
                    table.setItems(sortedData);
                });
    }
}
