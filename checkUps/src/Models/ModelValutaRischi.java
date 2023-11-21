package Models;

import Models.Tables.Reparto;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ModelValutaRischi {

    public void filterTable( TextField filterTextField, TableView<Reparto> tableView, ObservableList<Reparto> observableList) {

        String filterText = filterTextField.getText().toLowerCase().trim();
    
        // Create a filtered list based on the original observableList
        FilteredList<Reparto> filteredData = new FilteredList<>(observableList, reparto -> {
            if (filterText.isEmpty()) {
                return true; // Show all items when no filter is applied
            }
            // Check if the name contains the filter text (case-insensitive)
            return reparto.getNome().toLowerCase().contains(filterText);
        });

        // Bind the filtered data to the TableView
        tableView.setItems(filteredData);
    }
}
