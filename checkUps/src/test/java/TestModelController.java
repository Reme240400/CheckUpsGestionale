package test.java;

import Helpers.ClassHelper;
import Models.Tables.Reparto;

public class TestModelController {
    public static void main(String[] args) {

        // Popolamento della lista Reparto
        // Reparto reparto1 = new Reparto(1, 1, "Reparto 1", "Descrizione Reparto 1",
        // "Revisione rep 1",
        // Optional.of(LocalDate.now()));
        // Reparto reparto2 = new Reparto(2, 1, "Reparto 2", "Descrizione Reparto 2",
        // "Revisione rep 2",
        // Optional.of(LocalDate.now()));
        // Controller.popolaLista(reparto1);
        // Controller.popolaLista(reparto2);

        System.out.println("Lista Reparto:");
        for (Reparto reparto : ClassHelper.getListReparto()) {
            System.out.println(reparto.getId() + " - " + reparto.getNome() + " - " + reparto.getDescrizione());
        }

        // Rimozione di un oggetto dalla lista Reparto

        System.out.println("STAMPA DOPO OPERAZIONI: \n");
        // Visualizzazione della lista Reparto dopo le operazioni
        System.out.println("Lista Reparto:");
        for (Reparto reparto : ClassHelper.getListReparto()) {
            System.out.println(reparto.getId() + " - " + reparto.getNome() + " - " + reparto.getDescrizione());
        }
    }
}
