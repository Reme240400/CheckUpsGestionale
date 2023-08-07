package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Controller.ClassHelper;
import Controller.Controller;
import Model.Tabelle.Mansione;
import Model.Tabelle.Oggetto;
import Model.Tabelle.Provvedimento;
import Model.Tabelle.Reparto;
import Model.Tabelle.Rischio;
import Model.Tabelle.Societa;
import Model.Tabelle.Titolo;
import Model.Tabelle.UnitaLocale;
import sql.ControllerSql;

public class ModelController extends ClassHelper{


//CARICAMENTO DATI DA DB A LISTE
 public void popolaListeDaDatabase() {
        popolaListaSocieta();
        popolaListaUnitaLocali();
        popolaListaReparti();
        popolaListaTitoli();
        popolaListaMansioni();
        popolaListaOggetti();
        popolaListaProvvedimenti();
        popolaListaRischi();
    }

    private void popolaListaSocieta() {
        try (Connection connection = ControllerSql.connessioneDb()) {
            if (connection != null) {
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery("SELECT * FROM public.societa")) {

                    while (resultSet.next()) {
                        int idSocieta = resultSet.getInt("id_societa");
                        String indirizzo = resultSet.getString("indirizzo");
                        String localita = resultSet.getString("localita");
                        String provincia = resultSet.getString("provincia");
                        long telefono = resultSet.getLong("telefono");
                        String descrizione = resultSet.getString("descrizione");
                        String ente = resultSet.getString("ente");

                        Societa societa = new Societa(idSocieta, indirizzo, localita, provincia, telefono, descrizione, ente);
                        popolaLista(societa);
                    }
                } catch (SQLException e) {
                    System.out.println("Errore durante la lettura della tabella societa: " + e.getMessage());
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        System.out.println("Errore durante la chiusura della connessione: " + e.getMessage());
                    }
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void popolaListaMansioni() {
        Connection connection = ControllerSql.connessioneDb();
        if (connection != null) {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM public.mansioni")) {

                while (resultSet.next()) {
                    int idMansione = resultSet.getInt("id_mansione");
                    String nome = resultSet.getString("nome");
                    String responsabile = resultSet.getString("responsabile");

                    Mansione mansione = new Mansione(idMansione, nome, responsabile);
                    popolaLista(mansione);
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la lettura della tabella mansioni: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Errore durante la chiusura della connessione: " + e.getMessage());
                }
            }
        }
    }

    private void popolaListaTitoli() {
        Connection connection = ControllerSql.connessioneDb();
        if (connection != null) {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM public.titoli")) {

                while (resultSet.next()) {
                    int idTitolo = resultSet.getInt("id_titolo");
                    String descrizione = resultSet.getString("descrizione");
                    int idReparto = resultSet.getInt("id_reparto");

                    Titolo titolo = new Titolo(idTitolo, descrizione, idReparto);
                    popolaLista(titolo);
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la lettura della tabella titoli: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Errore durante la chiusura della connessione: " + e.getMessage());
                }
            }
        }
    }

    private void popolaListaReparti() {
        Connection connection = ControllerSql.connessioneDb();
        if (connection != null) {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM public.reparti")) {

                while (resultSet.next()) {
                    int idReparto = resultSet.getInt("id_reparto");
                    int idUnitaLocale = resultSet.getInt("id_unita_locale");
                    String nome = resultSet.getString("nome");
                    String descrizione = resultSet.getString("descrizione");

                    Reparto reparto = new Reparto(idReparto, idUnitaLocale, nome, descrizione);
                    popolaLista(reparto);
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la lettura della tabella reparti: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Errore durante la chiusura della connessione: " + e.getMessage());
                }
            }
        }
    }

    private void popolaListaRischi() {
        Connection connection = ControllerSql.connessioneDb();
        if (connection != null) {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM public.rischi")) {

                while (resultSet.next()) {
                    int idRischio = resultSet.getInt("id_rischio");
                    String nome = resultSet.getString("nome");
                    int p = resultSet.getInt("P");
                    int d = resultSet.getInt("D");
                    int r = resultSet.getInt("R");
                    int idReparto = resultSet.getInt("id_reparto");

                    Rischio rischio = new Rischio(idRischio, nome, p, d, r, idReparto);
                    popolaLista(rischio);
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la lettura della tabella rischi: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Errore durante la chiusura della connessione: " + e.getMessage());
                }
            }
        }
    }

    private void popolaListaOggetti() {
        Connection connection = ControllerSql.connessioneDb();
        if (connection != null) {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM public.oggetti")) {

                while (resultSet.next()) {
                    int idOggetto = resultSet.getInt("id_oggetto");
                    String nome = resultSet.getString("nome");
                    int idTitolo = resultSet.getInt("id_titolo");

                    Oggetto oggetto = new Oggetto(idOggetto, nome, idTitolo);
                    popolaLista(oggetto);
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la lettura della tabella oggetti: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Errore durante la chiusura della connessione: " + e.getMessage());
                }
            }
        }
    }

    private void popolaListaProvvedimenti() {
        Connection connection = ControllerSql.connessioneDb();
        if (connection != null) {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM public.provvedimenti")) {

                while (resultSet.next()) {
                    int idProvvedimento = resultSet.getInt("id_provvedimento");
                    String nome = resultSet.getString("tipo");
                    int idMansione = resultSet.getInt("id_mansione");
                    int idOggetto = resultSet.getInt("id_oggetto");
                    int idElencoRischi = resultSet.getInt("id_elenco_rischi");

                    Provvedimento provvedimento = new Provvedimento(idProvvedimento, nome, idMansione, idOggetto, idElencoRischi);
                    popolaLista(provvedimento);
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la lettura della tabella provvedimenti: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Errore durante la chiusura della connessione: " + e.getMessage());
                }
            }
        }
    }

    private void popolaListaUnitaLocali() {
        Connection connection = ControllerSql.connessioneDb();
        if (connection != null) {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM public.unita_locale")) {

                while (resultSet.next()) {
                    int idUnitaLocale = resultSet.getInt("id_unita_locale");
                    int idSocieta = resultSet.getInt("id_societa");
                    String nome = resultSet.getString("nome");
                    String indirizzo = resultSet.getString("indirizzo");
                    String localita = resultSet.getString("localita");
                    String provincia = resultSet.getString("provincia");
                    

                    UnitaLocale unitaLocale = new UnitaLocale(idUnitaLocale, idSocieta ,nome, indirizzo, localita, provincia);
                    popolaLista(unitaLocale);
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la lettura della tabella unita_locale: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Errore durante la chiusura della connessione: " + e.getMessage());
                }
            }
        }
    }








//OPERAZIONI SULLE LISTE
    
    public void popolaLista(Object obj) {
        
        switch (obj.getClass().getSimpleName()) {

        case "Mansione":   
            ClassHelper.getListMansione().add((Mansione) obj);
            break;
        case "Titolo":
            ClassHelper.getListTitolo().add((Titolo) obj);
            break;
        case "Reparto":
            ClassHelper.getListReparto().add((Reparto) obj);
            break;
        case "Rischio":
            ClassHelper.getListRischio().add((Rischio) obj);
            break;
        case "Societa":
            ClassHelper.getListSocieta().add((Societa) obj);
            break;
        case "Oggetto":
            ClassHelper.getListOggetto().add((Oggetto) obj);
            break;
        case "Provvedimento":
            ClassHelper.getListProvvedimento().add((Provvedimento) obj);
            break;
        case "UnitaLocale":
            ClassHelper.getListUnitaLocale().add((UnitaLocale) obj);
            break;
        default:
            throw new IllegalArgumentException("Unexpected value: " + obj.getClass().getSimpleName());
        }
    }

    public void rimuoviDaLista(Object obj) {
        
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

    public void modificaCampo(Object obj, String campo, String valore) {
        
        switch (obj.getClass().getSimpleName()) {

        case "Mansione":
            switch (campo) {
                case "idMansione":
                    ((Mansione) obj).setIdMansione(Integer.parseInt(valore));
                    break;
                case "nome":
                    ((Mansione) obj).setNome(valore);
                    break;
                case "responsabile":
                    ((Mansione) obj).setResponsabile(valore);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected value: " + campo);
            }
        break;

        case "Titolo":
            switch (campo) {
                case "idTitolo":
                    ((Titolo) obj).setIdTitolo(Integer.parseInt(valore));
                    break;
                case "idReparto":
                    ((Titolo) obj).setIdReparto(Integer.parseInt(valore));
                    break;
                case "descrizione":
                    ((Titolo) obj).setDescrizione(valore);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected value: " + campo);
            }
        break;

        case "Reparto":
            switch (campo) {
                case "idReparto":
                    ((Reparto) obj).setIdReparto(Integer.parseInt(valore));
                    break;
                case "nome":
                    ((Reparto) obj).setNome(valore);
                    break;
                case "descrizione":
                    ((Reparto) obj).setDescrizione(valore);
                    break;
                case "idUnitaLocale":
                    ((Reparto) obj).setIdUnitaLocale(Integer.parseInt(valore));
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected value: " + campo);
            }
        break;

        case "Rischio":
            switch (campo) {
                case "idRischio":
                    ((Rischio) obj).setIdRischio(Integer.parseInt(valore));
                    break;
                case "nome":
                    ((Rischio) obj).setNome(valore);
                    break;
                case "p":
                    ((Rischio) obj).setP(Integer.parseInt(valore));
                    break;
                case "d":
                    ((Rischio) obj).setD(Integer.parseInt(valore));
                    break;
                case "idReparto":
                    ((Rischio) obj).setIdReparto(Integer.parseInt(valore));
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected value: " + campo);
            }
        break;

        case "Societa":
            switch (campo) {
                case "idSocieta":
                    ((Societa) obj).setIdSocieta(Integer.parseInt(valore));
                    break;
                case "indirizzo":
                    ((Societa) obj).setIndirizzo(valore);
                    break;
                case "localita":
                    ((Societa) obj).setLocalita(valore);
                    break;
                case "provincia":
                    ((Societa) obj).setProvincia(valore);
                    break;
                case "telefono":
                    ((Societa) obj).setTelefono(valore);
                    break;
                case "descrizione":
                    ((Societa) obj).setDescrizione(valore);
                    break;
                case "ente":
                    ((Societa) obj).setEnte(valore);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected value: " + campo);
            }
        break;

        case "Oggetto":
            switch (campo) {
                case "idOggetto":
                    ((Oggetto) obj).setIdOggetto(Integer.parseInt(valore));
                    break;
                case "nome":
                    ((Oggetto) obj).setNome(valore);
                    break;
                case "idTitolo":
                    ((Oggetto) obj).setIdTitolo(Integer.parseInt(valore));
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected value: " + campo);
            }
        break;

        case "Provvedimento":
            switch (campo) {
                case "idProvvedimento":
                    ((Provvedimento) obj).setIdProvvedimento(Integer.parseInt(valore));
                    break;
                case "nome":
                    ((Provvedimento) obj).setNome(valore);
                    break;
                case "idMansione":
                    ((Provvedimento) obj).setIdMansione(Integer.parseInt(valore));
                    break;
                case "idOggetto":
                    ((Provvedimento) obj).setIdOggetto(Integer.parseInt(valore));
                    break;
                case "idElencoRischi":
                    ((Provvedimento) obj).setIdElencoRischi(Integer.parseInt(valore));
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected value: " + campo);
            }
        break;

        case "UnitaLocale":
            switch (campo) {
                case "idUnitaLocale":
                    ((UnitaLocale) obj).setIdUnitaLocale(Integer.parseInt(valore));
                    break;
                case "nome":
                    ((UnitaLocale) obj).setNome(valore);
                    break;
                case "idSocieta":
                    ((UnitaLocale) obj).setIdSocieta(Integer.parseInt(valore));
                    break;
                case "indirizzo":
                    ((UnitaLocale) obj).setIndirizzo(valore);
                    break;
                case "localita":
                    ((UnitaLocale) obj).setLocalita(valore);
                    break;
                case "provincia":
                    ((UnitaLocale) obj).setProvincia(valore);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected value: " + campo);
            }
        break;

        default:
            throw new IllegalArgumentException("Unexpected value: " + obj.getClass().getSimpleName());
        }
    }
        
}
