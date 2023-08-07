package test;

import Controller.ClassHelper;
import Model.ModelController;
import Model.Tabelle.Mansione;
import Model.Tabelle.Reparto;

public class TestModelController {
    public static void main(String[] args) {
        ModelController modelController = new ModelController();

        // Popolamento della lista Reparto
        Reparto reparto1 = new Reparto(1, 1, "Reparto 1", "Descrizione Reparto 1");
        Reparto reparto2 = new Reparto(2, 1, "Reparto 2", "Descrizione Reparto 2");
        modelController.popolaLista(reparto1);
        modelController.popolaLista(reparto2);

        System.out.println("Lista Reparto:");
        for (Reparto reparto : ClassHelper.listReparto) {
            System.out.println(reparto.getIdReparto() + " - " + reparto.getNome() + " - " + reparto.getDescrizione());
        }

        Mansione mansione1 = new Mansione(1, "Mansione 1", "Responsabile 1");
        Mansione mansione2 = new Mansione(2, "Mansione 2", "Responsabile 2");
        modelController.popolaLista(mansione1);
        modelController.popolaLista(mansione2);

        System.out.println("\nLista Mansione:");
        for (Mansione mansione : modelController.listMansione) {
            System.out.println(mansione.getIdMansione() + " - " + mansione.getNome() + " - " + mansione.getResponsabile());
        }
        
        // Modifica di un campo nella lista Reparto
        Reparto repartoModificato = ClassHelper.listReparto.get(0); // Prendi il primo elemento della lista
        modelController.modificaCampo(repartoModificato, "nome", "Nuovo nome del Reparto 1");

        // Modifica di un campo in un'altra lista
        Mansione mansioneModificata = ClassHelper.listMansione.get(0); // Prendi il primo elemento della lista Mansione
        modelController.modificaCampo(mansioneModificata, "nome", "Nuovo nome della Mansione 1");

        // Rimozione di un oggetto dalla lista Reparto
        modelController.rimuoviDaLista(reparto2);

        System.out.println("STAMPA DOPO OPERAZIONI: \n");
        // Visualizzazione della lista Reparto dopo le operazioni
        System.out.println("Lista Reparto:");
        for (Reparto reparto : ClassHelper.listReparto) {
            System.out.println(reparto.getIdReparto() + " - " + reparto.getNome() + " - " + reparto.getDescrizione());
        }

        System.out.println("\nLista Mansione:");
        for (Mansione mansione : ClassHelper.listMansione) {
            System.out.println(mansione.getIdMansione() + " - " + mansione.getNome() + " - " + mansione.getResponsabile());
        }
    }
}
