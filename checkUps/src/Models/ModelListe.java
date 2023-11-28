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
                                throw new IllegalArgumentException(
                                                "Unexpected value: " + obj.getClass().getSimpleName());
                }
        }

        // Metodo per modificare il valore di una campo sia nella lista che nel db
        public static void modificaCampoInLista(Object obj/* , String campo, String valore, int id */) {

                switch (obj.getClass().getSimpleName()) {

                        case "Mansione":
                                Mansione mansione = ((Mansione) obj);

                                ClassHelper.getListMansione().stream().filter(m -> m.getId() == mansione.getId())
                                                .forEach(m -> {
                                                        m.setNome(mansione.getNome());
                                                        m.setResponsabile(mansione.getResponsabile());
                                                });

                                break;

                        case "Titolo":

                                Titolo titolo = ((Titolo) obj);

                                ClassHelper.getListTitolo().stream().filter(t -> t.getId() == titolo.getId())
                                                .forEach(t -> {
                                                        t.setDescrizione(titolo.getDescrizione());
                                                        t.setIdReparto(titolo.getIdReparto());
                                                });

                                break;

                        case "Reparto":
                                Reparto reparto = ((Reparto) obj);

                                ClassHelper.getListReparto().stream().filter(r -> r.getId() == reparto.getId())
                                                .forEach(r -> {
                                                        r.setNome(reparto.getNome());
                                                        r.setDescrizione(reparto.getDescrizione());
                                                        r.setIdUnitaLocale(reparto.getIdUnitaLocale());
                                                });

                                break;

                        case "Rischio":

                                Rischio rischio = ((Rischio) obj);

                                ClassHelper.getListRischio().stream().filter(r -> r.getId() == rischio.getId())
                                                .forEach(r -> {
                                                        r.setNome(rischio.getNome());
                                                        r.setP(rischio.getP());
                                                        r.setD(rischio.getD());
                                                        r.setR(rischio.getR());
                                                        r.setIdReparto(rischio.getIdReparto());
                                                });

                                break;

                        case "Societa":
                                Societa societa = ((Societa) obj);

                                ClassHelper.getListSocieta().stream().filter(s -> s.getId() == societa.getId())
                                                .forEach(s -> {
                                                        s.setNome(societa.getNome());
                                                        s.setIndirizzo(societa.getIndirizzo());
                                                        s.setLocalita(societa.getLocalita());
                                                        s.setProvincia(societa.getProvincia());
                                                        s.setTelefono(societa.getTelefono());
                                                        s.setDescrizione(societa.getDescrizione());
                                                });

                                break;

                        case "Oggetto":
                                Oggetto oggetto = ((Oggetto) obj);

                                ClassHelper.getListOggetto().stream().filter(o -> o.getId() == oggetto.getId())
                                                .forEach(o -> {
                                                        o.setNome(oggetto.getNome());
                                                        o.setIdTitolo(oggetto.getIdTitolo());
                                                });

                                break;

                        case "Provvedimento":
                                Provvedimento provvedimento = ((Provvedimento) obj);

                                ClassHelper.getListProvvedimento().stream()
                                                .filter(p -> p.getId() == provvedimento.getId()).forEach(p -> {
                                                        p.setNome(provvedimento.getNome());
                                                        p.setIdMansione(provvedimento.getIdMansione());
                                                        p.setIdOggetto(provvedimento.getIdOggetto());
                                                        p.setRischio(provvedimento.getRischio());
                                                        p.setSoggettiEsposti(provvedimento.getSoggettiEsposti());
                                                        p.setStima(provvedimento.getStima());
                                                        p.setStimaR(provvedimento.getStimaR());
                                                        p.setStimaD(provvedimento.getStimaD());
                                                        p.setStimaP(provvedimento.getStimaP());
                                                });

                                break;

                        case "UnitaLocale":
                                UnitaLocale unitaLocale = ((UnitaLocale) obj);

                                ClassHelper.getListUnitaLocale().stream()
                                                .filter(u -> u.getId() == unitaLocale.getId()).forEach(u -> {
                                                        u.setNome(unitaLocale.getNome());
                                                        u.setIndirizzo(unitaLocale.getIndirizzo());
                                                        u.setLocalita(unitaLocale.getLocalita());
                                                        u.setProvincia(unitaLocale.getProvincia());
                                                        u.setIdSocieta(unitaLocale.getIdSocieta());
                                                });

                                break;

                        default:
                                throw new IllegalArgumentException(
                                                "Unexpected value: " + obj.getClass().getSimpleName());
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
                                throw new IllegalArgumentException(
                                                "Unexpected value: " + obj.getClass().getSimpleName());
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

        public static List<Titolo> filtraTitoliDaReparto(List<Reparto> reparti) {

                return ClassHelper.getListTitolo().stream().filter(t -> reparti.stream()
                                .anyMatch(r -> r.getId() == t.getIdReparto())).collect(Collectors.toList());

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
