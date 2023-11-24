package Models;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Controllers.ClassHelper;
import Controllers.ControllerDb;
import Models.Tables.Mansione;
import Models.Tables.Oggetto;
import Models.Tables.Provvedimento;
import Models.Tables.Reparto;
import Models.Tables.Rischio;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;

public class ModelListe {

    // Metodo per eliminare un elemento sia da db che nelle liste
    public static void rimuoviDaLista(Object obj, int id) {

        ControllerDb.eliminaRecordDaId(obj.getClass().getSimpleName().toLowerCase(), id);
        switch (obj.getClass().getSimpleName()) {

            case "Mansione":
                ClassHelper.getListMansione().remove((Mansione) obj);
                break;
            case "Titolo":
                ClassHelper.getListTitolo().remove((Titolo) obj);
                break;
            case "Reparto":
                ClassHelper.getListReparto().remove((Reparto) obj);
                break;
            case "Rischio":
                ClassHelper.getListRischio().remove((Rischio) obj);
                break;
            case "Societa":
                ClassHelper.getListSocieta().remove((Societa) obj);
                break;
            case "Oggetto":
                ClassHelper.getListOggetto().remove((Oggetto) obj);
                break;
            case "Provvedimento":
                ClassHelper.getListProvvedimento().remove((Provvedimento) obj);
                break;
            case "UnitaLocale":
                ClassHelper.getListUnitaLocale().remove((UnitaLocale) obj);
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + obj.getClass().getSimpleName());
        }
    }

    // Metodo per modificare il valore di una campo sia nella lista che nel db
    public static void modificaCampo(Object obj/*, String campo, String valore, int id*/) {

        switch (obj.getClass().getSimpleName()) {

            case "Mansione":
                Mansione mansione = ((Mansione) obj);
            
                ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), mansione.getId(), "id_mansione",
                        mansione.getId());

                ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), mansione.getId(), "nome",
                        ((Mansione) obj).getNome());
            
                ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), mansione.getId(), "responsabile",
                        mansione.getResponsabile());
                
                break;

            case "Titolo":
            
                Titolo titolo = ((Titolo) obj);
                    
                ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), titolo.getId(), "id_titolo",
                        titolo.getId());

                ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), titolo.getId(), "descrizione",
                        titolo.getDescrizione());
                
                ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), titolo.getId(), "id_reparto",
                        titolo.getIdReparto());

                break;

            case "Reparto":
                Reparto reparto = ((Reparto) obj);

                ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), reparto.getId(), "id_reparto",
                        reparto.getId());

                ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), reparto.getId(), "nome",
                        reparto.getNome());

                ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), reparto.getId(), "descrizione", 
                        reparto.getDescrizione());

                break;

            case "Rischio":
                
                Rischio rischio = ((Rischio) obj);

                ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), rischio.getId(), "id_rischio",
                        rischio.getId());

                ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), rischio.getId(), "nome",
                        rischio.getNome());

                ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), rischio.getId(), "p",
                        rischio.getP());

                ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), rischio.getId(), "d",
                        rischio.getD());

                ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), rischio.getId(), "r",
                        rischio.getR());

                ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), rischio.getId(), "id_reparto",
                        rischio.getIdReparto());

                break;

            case "Societa":
                Societa societa = ((Societa) obj);

                ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), societa.getId(), "id_societa",
                        societa.getId());

                ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), societa.getId(), "nome",
                        societa.getNome());

                ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), societa.getId(), "indirizzo",   
                        societa.getIndirizzo());

                ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), societa.getId(), "localita",
                        societa.getLocalita());

                ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), societa.getId(), "provincia",
                        societa.getProvincia());

                ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), societa.getId(), "telefono",
                        societa.getTelefono());

                ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), societa.getId(), "descrizione",
                        societa.getDescrizione());

                break;

            case "Oggetto":
                Oggetto oggetto = ((Oggetto) obj);

                ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), oggetto.getId(), "id_oggetto",
                        oggetto.getId());

                ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), oggetto.getId(), "nome",
                        oggetto.getNome());

                ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), oggetto.getId(), "id_titolo",
                        oggetto.getIdTitolo());

                break;

            case "Provvedimento":
                Provvedimento provvedimento = ((Provvedimento) obj);

                ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), provvedimento.getId(), "id_provvedimento",
                        provvedimento.getId());

                ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), provvedimento.getId(), "nome",
                        provvedimento.getNome());

                ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), provvedimento.getId(), "id_mansione",
                        provvedimento.getIdMansione());

                ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), provvedimento.getId(), "id_oggetto",
                        provvedimento.getIdOggetto());

                ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), provvedimento.getId(), "rischio",
                        provvedimento.getRischio());

                ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), provvedimento.getId(), "soggetti_esposti",
                        provvedimento.getSoggettiEsposti());

                ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), provvedimento.getId(), "stima",
                        provvedimento.getStima());

                ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), provvedimento.getId(), "stima_r",
                        provvedimento.getStimaR());

                ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), provvedimento.getId(), "stima_d",
                        provvedimento.getStimaD());

                ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), provvedimento.getId(), "stima_p",
                        provvedimento.getStimaP());

                break;

            case "UnitaLocale":
                UnitaLocale unitaLocale = ((UnitaLocale) obj);

                ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), unitaLocale.getId(), "id_unita_locale",
                        unitaLocale.getId());

                ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), unitaLocale.getId(), "nome",
                        unitaLocale.getNome());

                ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), unitaLocale.getId(), "indirizzo",
                        unitaLocale.getIndirizzo());

                ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), unitaLocale.getId(), "localita",
                        unitaLocale.getLocalita());

                ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), unitaLocale.getId(), "provincia",
                        unitaLocale.getProvincia());

                ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), unitaLocale.getId(), "id_societa",
                        unitaLocale.getIdSocieta());

                break;

            default:
                throw new IllegalArgumentException("Unexpected value: " + obj.getClass().getSimpleName());
        }
    }

    // Metodo per inserire un nuovo elemento nella lista
    public static void inserisciRecordInLista(Object obj) {
        switch (obj.getClass().getSimpleName()) {
            case "Mansione":
                Mansione mansione = new Mansione(((Mansione) obj).getId(),
                        ((Mansione) obj).getNome(),
                        ((Mansione) obj).getResponsabile());

                ClassHelper.getListMansione().add(mansione);
                break;
            case "Titolo":
                Titolo titolo = new Titolo(((Titolo) obj).getId(),
                        ((Titolo) obj).getIdReparto(),
                        ((Titolo) obj).getDescrizione());

                ClassHelper.getListTitolo().add(titolo);
                break;
            case "Reparto":
                Reparto reparto = new Reparto(((Reparto) obj).getId(),
                        ((Reparto) obj).getIdUnitaLocale(),
                        ((Reparto) obj).getNome(),
                        ((Reparto) obj).getDescrizione());

                ClassHelper.getListReparto().add(reparto);
                break;
            case "Rischio":
                Rischio rischio = new Rischio(((Rischio) obj).getId(),
                        ((Rischio) obj).getNome(),
                        ((Rischio) obj).getP(),
                        ((Rischio) obj).getD(),
                        ((Rischio) obj).getR(),
                        ((Rischio) obj).getIdReparto());

                ClassHelper.getListRischio().add(rischio);
                break;
            case "Societa":

                Societa societa = new Societa(
                        ((Societa) obj).getId(),
                        ((Societa) obj).getNome(),
                        ((Societa) obj).getIndirizzo(),
                        ((Societa) obj).getLocalita(),
                        ((Societa) obj).getProvincia(),
                        ((Societa) obj).getTelefono(),
                        ((Societa) obj).getDescrizione());

                ClassHelper.getListSocieta().add(societa);
                break;
            case "Oggetto":
                Oggetto oggetto = new Oggetto(((Oggetto) obj).getId(),
                        ((Oggetto) obj).getNome(),
                        ((Oggetto) obj).getIdTitolo());

                ClassHelper.getListOggetto().add(oggetto);
                break;
            case "Provvedimento":
                Provvedimento provvedimento = new Provvedimento(((Provvedimento) obj).getId(),
                        ((Provvedimento) obj).getNome(),
                        ((Provvedimento) obj).getIdMansione(),
                        ((Provvedimento) obj).getIdOggetto(),
                        ((Provvedimento) obj).getRischio(),
                        ((Provvedimento) obj).getSoggettiEsposti(),
                        ((Provvedimento) obj).getStima(),
                        ((Provvedimento) obj).getStimaR(),
                        ((Provvedimento) obj).getStimaD(),
                        ((Provvedimento) obj).getStimaP());

                ClassHelper.getListProvvedimento().add(provvedimento);
                break;
            case "UnitaLocale":
                UnitaLocale unitaLocale = new UnitaLocale(((UnitaLocale) obj).getId(),
                        ((UnitaLocale) obj).getNome(),
                        ((UnitaLocale) obj).getIndirizzo(),
                        ((UnitaLocale) obj).getLocalita(),
                        ((UnitaLocale) obj).getProvincia(),
                        ((UnitaLocale) obj).getIdSocieta());

                ClassHelper.getListUnitaLocale().add(unitaLocale);
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + obj.getClass().getSimpleName());
        }
    }

    public static List<UnitaLocale> filtraListaUnitaDaSocieta(int idSocieta) {

        return ClassHelper.getListUnitaLocale().stream().filter(ul -> ul.getIdSocieta() == idSocieta)
                .collect(Collectors.toList());

    }

    public static List<Reparto> filtraRepartoDaUnita(int idUnitaLocale) {

        return ClassHelper.getListReparto().stream().filter(ul -> ul.getIdUnitaLocale() == idUnitaLocale)
                .collect(Collectors.toList());

    }

    public static List<Titolo> filtraTitoliDaReparto(int idReparto) {

        return ClassHelper.getListTitolo().stream().filter(ul -> ul.getIdReparto() == idReparto)
                .collect(Collectors.toList());

    }

    public static List<Oggetto> filtraOggettiDaTitolo(int idTitolo) {

        return ClassHelper.getListOggetto().stream().filter(ul -> ul.getIdTitolo() == idTitolo)
                .collect(Collectors.toList());

    }

    public static List<Provvedimento> filtraProvvedimentiDaOggetto(int idOggetto) {

        return ClassHelper.getListProvvedimento().stream().filter(ul -> ul.getIdOggetto() == idOggetto)
                .collect(Collectors.toList());

    }

    // Metodi per backup e ripristino da file
    public static void salvaListeSuFile(String fileName, List<?>... lists) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for (List<?> list : lists) {
                oos.writeObject(list);
            }
            System.out.println("Liste salvate con successo in " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<?> caricaListaDaFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            List<?> loadedList = (List<?>) ois.readObject();
            System.out.println("Lista caricata con successo da " + fileName);
            return loadedList;
        } catch (EOFException e) {
            System.out.println("Fine del file");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<List<?>> caricaListeDaFile(String fileName) {
        List<List<?>> loadedLists = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            while (true) {
                try {
                    List<?> list = (List<?>) ois.readObject();
                    loadedLists.add(list);
                } catch (EOFException e) {
                    break; // Fine del file
                }
            }
            System.out.println("Liste caricate con successo da " + fileName);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return loadedLists;
    }

}
