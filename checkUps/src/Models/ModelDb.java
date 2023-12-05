package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import Helpers.ClassHelper;
import Models.Tables.Mansione;
import Models.Tables.Oggetto;
import Models.Tables.Provvedimento;
import Models.Tables.Reparto;
import Models.Tables.Rischio;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;

public class ModelDb {

    private static final String connectionUrl = "jdbc:postgresql://localhost:5432/checkups_db";
    private static final String username = "postgres";
    private static final String password = "postgres";

    public static Connection connessioneDb() {
        try {
            Connection connection = DriverManager.getConnection(connectionUrl, username, password);
            return connection;
        } catch (SQLException ex) {
            System.out.println("Errore di connessione al database: " + ex.getMessage());
        }
        return null;
    }

    // Prendo i dati dal db e li metto in una lista
    public static void popolaListaSocieta() {
        try (Connection connection = connessioneDb()) {
            if (connection != null) {
                ClassHelper.svuotaListaSocieta();
                try (Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM public.societa")) {
                    while (resultSet.next()) {
                        int id_societa = resultSet.getInt("id_societa");
                        String nome = resultSet.getString("nome");
                        String indirizzo = resultSet.getString("indirizzo");
                        String localita = resultSet.getString("localita");
                        String provincia = resultSet.getString("provincia");
                        String telefono = resultSet.getString("telefono");
                        String descrizione = resultSet.getString("descrizione");

                        Societa societa = new Societa(id_societa, nome, indirizzo, localita, provincia, telefono,
                                descrizione);

                        ModelListe.inserisciRecordInLista(societa);
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

    public static void popolaListaMansioni() {
        try (Connection connection = connessioneDb()) {
            if (connection != null) {
                ClassHelper.svuotaListaMansioni();
                try (Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM public.mansioni")) {
                    while (resultSet.next()) {
                        int idMansione = resultSet.getInt("id_mansione");
                        String nome = resultSet.getString("nome");
                        String responsabile = resultSet.getString("responsabile");

                        Mansione mansione = new Mansione(idMansione, nome, responsabile);
                        ModelListe.inserisciRecordInLista(mansione);
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
        } catch (SQLException e) {
            System.out.println(e);// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void popolaListaTitoli() {
        try (Connection connection = connessioneDb()) {
            if (connection != null) {
                ClassHelper.svuotaListaTitoli();
                try (Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM public.titoli")) {

                    while (resultSet.next()) {
                        int idTitolo = resultSet.getInt("id_titolo");
                        int idReparto = resultSet.getInt("id_reparto");
                        String descrizione = resultSet.getString("descrizione");
                        Titolo titolo = new Titolo(idTitolo, idReparto, descrizione);
                        ModelListe.inserisciRecordInLista(titolo);
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
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void popolaListaReparti() {
        try (Connection connection = connessioneDb()) {
            if (connection != null) {
                ClassHelper.svuotaListaReparti();
                try (Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM public.reparti")) {

                    while (resultSet.next()) {
                        int idReparto = resultSet.getInt("id_reparto");
                        int idUnitaLocale = resultSet.getInt("id_unita_locale");
                        String nome = resultSet.getString("nome");
                        String descrizione = resultSet.getString("descrizione");

                        Reparto reparto = new Reparto(idReparto, idUnitaLocale, nome, descrizione);
                        ModelListe.inserisciRecordInLista(reparto);
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
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void popolaListaRischi() {
        try (Connection connection = connessioneDb()) {
            if (connection != null) {
                ClassHelper.svuotaListaRischi();
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
                        ModelListe.inserisciRecordInLista(rischio);
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
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void popolaListaOggetti() {
        try (Connection connection = connessioneDb()) {
            if (connection != null) {
                ClassHelper.svuotaListaOggetti();
                try (Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM public.oggetti")) {

                    while (resultSet.next()) {
                        int idOggetto = resultSet.getInt("id");
                        String nome = resultSet.getString("nome");
                        int idTitolo = resultSet.getInt("id_titolo");

                        Oggetto oggetto = new Oggetto(idOggetto, nome, idTitolo);
                        ModelListe.inserisciRecordInLista(oggetto);
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
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void popolaListaProvvedimenti() {
        try (Connection connection = connessioneDb()) {
            if (connection != null) {
                ClassHelper.svuotaListaProvvedimenti();
                try (Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM public.provvedimenti")) {

                    while (resultSet.next()) {
                        int idProvvedimento = resultSet.getInt("id_provvedimento");
                        String nome = resultSet.getString("nome");
                        int idMansione = resultSet.getInt("id_mansione");
                        int idOggetto = resultSet.getInt("id_oggetto");
                        String rischio = resultSet.getString("rischio");
                        String soggettiEsposti = resultSet.getString("soggetti_esposti");
                        int stima_r = resultSet.getInt("stima_r");
                        int stima_d = resultSet.getInt("stima_d");
                        int stima_p = resultSet.getInt("stima_p");

                        Provvedimento provvedimento = new Provvedimento(idProvvedimento, nome, idMansione, idOggetto,
                                rischio, soggettiEsposti, stima_r, stima_d, stima_p);
                        ModelListe.inserisciRecordInLista(provvedimento);
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
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void popolaListaUnitaLocali() {
        try (Connection connection = connessioneDb()) {
            if (connection != null) {
                ClassHelper.svuotaListaUnitaLocali();
                try (Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM public.unita_locali")) {

                    while (resultSet.next()) {
                        int idUnitaLocale = resultSet.getInt("id_unita_locale");
                        int idSocieta = resultSet.getInt("id_societa");
                        String nome = resultSet.getString("nome");
                        String indirizzo = resultSet.getString("indirizzo");
                        String localita = resultSet.getString("localita");
                        String provincia = resultSet.getString("provincia");

                        UnitaLocale unitaLocale = new UnitaLocale(idUnitaLocale, nome, provincia, indirizzo, localita,
                                idSocieta);

                        ModelListe.inserisciRecordInLista(unitaLocale);
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
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // Metodo generico per l'eliminazione di un record da qualsiasi tabella
    public static void eliminaRecord(Object obj, int recordId) {
        String objName = obj.getClass().getSimpleName();
        String tableName = objName;

        switch (objName) {
            case "Titolo":
                tableName = "titoli";
                break;

            case "Reparto":
                tableName = "reparti";
                break;

            case "Oggetto":
                tableName = "oggetti";
                break;

            default:
                break;
        }

        try (Connection connection = connessioneDb()) {
            if (connection != null) {
                try (Statement statement = connection.createStatement()) {
                    String query = "DELETE FROM public." + tableName + " WHERE id_" + objName + " = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, recordId);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("Errore durante l'eliminazione del record dalla tabella " + tableName + ": "
                            + e.getMessage());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metodo generico per la modifica di un campo in qualsiasi tabella
    public static void modificaCampoStringa(String tableName, int recordId, String campo, String nuovoValore) {
        try (Connection connection = connessioneDb()) {
            if (connection != null) {
                try (Statement statement = connection.createStatement()) {
                    String query = "UPDATE public." + tableName + " SET " + campo + " = ? WHERE id_" + tableName
                            + " = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, nuovoValore);
                    preparedStatement.setInt(2, recordId);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(
                            "Errore durante la modifica del campo nella tabella " + tableName + ": " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metodo generico per la modifica di un campo in qualsiasi tabella
    public static void modificaCampoIntero(String tableName, int recordId, String campo, int nuovoValore) {
        try (Connection connection = connessioneDb()) {
            if (connection != null) {
                try (Statement statement = connection.createStatement()) {
                    String query = "UPDATE public." + tableName + " SET " + campo + " = ? WHERE id_" + tableName
                            + " = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, nuovoValore);
                    preparedStatement.setInt(2, recordId);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(
                            "Errore durante la modifica del campo nella tabella " + tableName + ": " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metodo generico per l'inserimento di un record in una tabella
    public static void inserisciRecord(Object obj) {
        try (Connection connection = connessioneDb()) {
            if (connection != null) {
                try (Statement statement = connection.createStatement()) {

                    switch (obj.getClass().getSimpleName()) {
                        case "Mansione":
                            inserisciElementoMansioni(connection, ClassHelper.getListMansione());
                            break;
                        case "Titolo":
                            inserisciElementoTitoli(connection, ClassHelper.getListTitolo());
                            break;
                        case "Reparto":
                            inserisciElementoReparti(connection, ClassHelper.getListReparto());
                            break;
                        case "Rischio":
                            inserisciElementoRischi(connection, ClassHelper.getListRischio());
                            break;
                        case "Societa":
                            inserisciElementoSocieta(connection, ClassHelper.getListSocieta());
                            break;
                        case "Oggetto":
                            inserisciElementoOggetti(connection, ClassHelper.getListOggetto());
                            break;
                        case "Provvedimento":
                            inserisciElementoProvvedimenti(connection, ClassHelper.getListProvvedimento());
                            break;
                        case "UnitaLocale":
                            inserisciElementoUnitaLocali(connection, ClassHelper.getListUnitaLocale());
                            break;
                        default:
                            throw new IllegalArgumentException("Unexpected value: " + obj.getClass().getSimpleName());
                    }

                } catch (SQLException e) {
                    System.out.println("Errore durante l'inserimento di un nuovo record nel DB:  " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void modificaCampo(Object obj) {
        switch (obj.getClass().getSimpleName()) {

            case "Mansione":
                Mansione mansione = ((Mansione) obj);

                modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(),
                        mansione.getId(),
                        "id_mansione",
                        mansione.getId());

                modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(),
                        mansione.getId(),
                        "nome",
                        ((Mansione) obj).getNome());

                modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(),
                        mansione.getId(),
                        "responsabile",
                        mansione.getResponsabile());

                break;

            case "Titolo":

                Titolo titolo = ((Titolo) obj);

                modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(),
                        titolo.getId(),
                        "id_titolo",
                        titolo.getId());

                modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(),
                        titolo.getId(),
                        "descrizione",
                        titolo.getDescrizione());

                modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(),
                        titolo.getId(),
                        "id_reparto",
                        titolo.getIdReparto());

                break;

            case "Reparto":
                Reparto reparto = ((Reparto) obj);

                modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(),
                        reparto.getId(),
                        "id_reparto",
                        reparto.getId());

                modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(),
                        reparto.getId(), "nome",
                        reparto.getNome());

                modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(),
                        reparto.getId(),
                        "descrizione",
                        reparto.getDescrizione());

                break;

            case "Rischio":

                Rischio rischio = ((Rischio) obj);

                modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(),
                        rischio.getId(),
                        "id_rischio",
                        rischio.getId());

                modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(),
                        rischio.getId(), "nome",
                        rischio.getNome());

                modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(),
                        rischio.getId(), "p",
                        rischio.getP());

                modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(),
                        rischio.getId(), "d",
                        rischio.getD());

                modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(),
                        rischio.getId(), "r",
                        rischio.getR());

                modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(),
                        rischio.getId(),
                        "id_reparto",
                        rischio.getIdReparto());

                break;

            case "Societa":
                Societa societa = ((Societa) obj);

                modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(),
                        societa.getId(),
                        "id_societa",
                        societa.getId());

                modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(),
                        societa.getId(), "nome",
                        societa.getNome());

                modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(),
                        societa.getId(),
                        "indirizzo",
                        societa.getIndirizzo());

                modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(),
                        societa.getId(),
                        "localita",
                        societa.getLocalita());

                modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(),
                        societa.getId(),
                        "provincia",
                        societa.getProvincia());

                modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(),
                        societa.getId(),
                        "telefono",
                        societa.getTelefono());

                modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(),
                        societa.getId(),
                        "descrizione",
                        societa.getDescrizione());

                break;

            case "Oggetto":
                Oggetto oggetto = ((Oggetto) obj);

                modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(),
                        oggetto.getId(),
                        "id_oggetto",
                        oggetto.getId());

                modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(),
                        oggetto.getId(), "nome",
                        oggetto.getNome());

                modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(),
                        oggetto.getId(),
                        "id_titolo",
                        oggetto.getIdTitolo());

                break;

            case "Provvedimento":
                Provvedimento provvedimento = ((Provvedimento) obj);

                modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(),
                        provvedimento.getId(),
                        "id_provvedimento",
                        provvedimento.getId());

                modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(),
                        provvedimento.getId(),
                        "nome",
                        provvedimento.getNome());

                modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(),
                        provvedimento.getId(),
                        "id_mansione",
                        provvedimento.getIdMansione());

                modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(),
                        provvedimento.getId(),
                        "id_oggetto",
                        provvedimento.getIdOggetto());

                modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(),
                        provvedimento.getId(),
                        "rischio",
                        provvedimento.getRischio());

                modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(),
                        provvedimento.getId(),
                        "soggetti_esposti",
                        provvedimento.getSoggettiEsposti());

                modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(),
                        provvedimento.getId(),
                        "stima_r",
                        provvedimento.getStimaR());

                modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(),
                        provvedimento.getId(),
                        "stima_d",
                        provvedimento.getStimaD());

                modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(),
                        provvedimento.getId(),
                        "stima_p",
                        provvedimento.getStimaP());

                break;

            case "UnitaLocale":
                UnitaLocale unitaLocale = ((UnitaLocale) obj);

                modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(),
                        unitaLocale.getId(),
                        "id_unita_locale",
                        unitaLocale.getId());

                modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(),
                        unitaLocale.getId(),
                        "nome",
                        unitaLocale.getNome());

                modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(),
                        unitaLocale.getId(),
                        "indirizzo",
                        unitaLocale.getIndirizzo());

                modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(),
                        unitaLocale.getId(),
                        "localita",
                        unitaLocale.getLocalita());

                modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(),
                        unitaLocale.getId(),
                        "provincia",
                        unitaLocale.getProvincia());

                modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(),
                        unitaLocale.getId(),
                        "id_societa",
                        unitaLocale.getIdSocieta());

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

    public void visualizzaTabellaSocieta() {
        Connection connection = connessioneDb();
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
                    String nome = resultSet.getString("nome");

                    System.out.println("ID: " + idSocieta);
                    System.out.println("Indirizzo: " + indirizzo);
                    System.out.println("Localita: " + localita);
                    System.out.println("Provincia: " + provincia);
                    System.out.println("Telefono: " + telefono);
                    System.out.println("Descrizione: " + descrizione);
                    System.out.println("Nome: " + nome);
                    System.out.println();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la lettura della tabella societa: " + e.getMessage());
            } finally {
                try {
                    connection.close(); // Chiude la connessione in modo controllato
                } catch (SQLException e) {
                    System.out.println("Errore durante la chiusura della connessione: " + e.getMessage());
                }
            }
        }
    }

    public void visualizzaTabellaUnitaLocali() {
        Connection connection = connessioneDb();
        if (connection != null) {
            try (Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM public.unita_locali")) {

                while (resultSet.next()) {
                    int idUnitaLocale = resultSet.getInt("id_unita_locale");
                    int idSocieta = resultSet.getInt("id_societa");
                    String nome = resultSet.getString("nome");
                    String indirizzo = resultSet.getString("indirizzo");
                    String localita = resultSet.getString("localita");
                    String provincia = resultSet.getString("provincia");

                    System.out.println("Tabella Unita Locali - ID: " + idUnitaLocale);
                    System.out.println("ID Societa: " + idSocieta);
                    System.out.println("Nome: " + nome);
                    System.out.println("Indirizzo: " + indirizzo);
                    System.out.println("Localita: " + localita);
                    System.out.println("Provincia: " + provincia);

                    System.out.println();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la lettura della tabella unita_locali: " + e.getMessage());
            } finally {
                try {
                    connection.close(); // Chiude la connessione in modo controllato
                } catch (SQLException e) {
                    System.out.println("Errore durante la chiusura della connessione: " + e.getMessage());
                }
            }
        }
    }

    public void visualizzaTabellaReparti() {
        Connection connection = connessioneDb();
        if (connection != null) {
            try (Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM public.reparti")) {

                while (resultSet.next()) {
                    int idReparto = resultSet.getInt("id_reparto");
                    int idUnitaLocale = resultSet.getInt("id_unita_locale");
                    String nome = resultSet.getString("nome");
                    String descrizione = resultSet.getString("descrizione");

                    System.out.println("ID Reparto: " + idReparto);
                    System.out.println("ID Unita Locale: " + idUnitaLocale);
                    System.out.println("Nome: " + nome);
                    System.out.println("Descrizione: " + descrizione);
                    System.out.println();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la lettura della tabella reparti: " + e.getMessage());
            } finally {
                try {
                    connection.close(); // Chiude la connessione in modo controllato
                } catch (SQLException e) {
                    System.out.println("Errore durante la chiusura della connessione: " + e.getMessage());
                }
            }
        }
    }

    public void visualizzaTabellaTitoli() {
        Connection connection = connessioneDb();
        if (connection != null) {
            try (Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM public.titoli")) {

                while (resultSet.next()) {
                    int idTitolo = resultSet.getInt("id_titolo");
                    String descrizione = resultSet.getString("descrizione");
                    int idReparto = resultSet.getInt("id_reparto");

                    System.out.println("ID Titolo: " + idTitolo);
                    System.out.println("Descrizione: " + descrizione);
                    System.out.println("ID Reparto: " + idReparto);
                    System.out.println();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la lettura della tabella titoli: " + e.getMessage());
            } finally {
                try {
                    connection.close(); // Chiude la connessione in modo controllato
                } catch (SQLException e) {
                    System.out.println("Errore durante la chiusura della connessione: " + e.getMessage());
                }
            }
        }
    }

    public void visualizzaTabellaRischi() {
        Connection connection = connessioneDb();
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

                    System.out.println("ID Rischio: " + idRischio);
                    System.out.println("Nome: " + nome);
                    System.out.println("P: " + p);
                    System.out.println("D: " + d);
                    System.out.println("R: " + r);
                    System.out.println("ID Reparto: " + idReparto);
                    System.out.println();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la lettura della tabella rischi: " + e.getMessage());
            } finally {
                try {
                    connection.close(); // Chiude la connessione in modo controllato
                } catch (SQLException e) {
                    System.out.println("Errore durante la chiusura della connessione: " + e.getMessage());
                }
            }
        }
    }

    public void visualizzaTabellaOggetti() {
        Connection connection = connessioneDb();
        if (connection != null) {
            try (Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM public.oggetti")) {

                while (resultSet.next()) {
                    int idOggetto = resultSet.getInt("id_oggetto");
                    String nome = resultSet.getString("nome");
                    int idTitolo = resultSet.getInt("id_titolo");

                    System.out.println("ID Oggetto: " + idOggetto);
                    System.out.println("Nome: " + nome);
                    System.out.println("ID Titolo: " + idTitolo);
                    System.out.println();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la lettura della tabella oggetti: " + e.getMessage());
            } finally {
                try {
                    connection.close(); // Chiude la connessione in modo controllato
                } catch (SQLException e) {
                    System.out.println("Errore durante la chiusura della connessione: " + e.getMessage());
                }
            }
        }
    }

    public void visualizzaTabellaMansioni() {
        Connection connection = connessioneDb();
        if (connection != null) {
            try (Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM public.mansioni")) {

                while (resultSet.next()) {
                    int idMansione = resultSet.getInt("id_mansione");
                    String nome = resultSet.getString("nome");
                    String responsabile = resultSet.getString("responsabile");

                    System.out.println("ID Mansione: " + idMansione);
                    System.out.println("Nome: " + nome);
                    System.out.println("Responsabile: " + responsabile);
                    System.out.println();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la lettura della tabella mansioni: " + e.getMessage());
            } finally {
                try {
                    connection.close(); // Chiude la connessione in modo controllato
                } catch (SQLException e) {
                    System.out.println("Errore durante la chiusura della connessione: " + e.getMessage());
                }
            }
        }
    }

    public void visualizzaTabellaProvvedimenti() {
        Connection connection = connessioneDb();
        if (connection != null) {
            try (Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM public.provvedimenti")) {

                while (resultSet.next()) {
                    int idProvvedimento = resultSet.getInt("id_provvedimento");
                    String nome = resultSet.getString("nome");
                    int idMansione = resultSet.getInt("id_mansione");
                    int idOggetto = resultSet.getInt("id_oggetto");
                    int idElencoRischi = resultSet.getInt("id_elenco_rischi");

                    System.out.println("ID Provvedimento: " + idProvvedimento);
                    System.out.println("Nome: " + nome);
                    System.out.println("ID Mansione: " + idMansione);
                    System.out.println("ID Oggetto: " + idOggetto);
                    System.out.println("ID Elenco Rischi: " + idElencoRischi);
                    System.out.println();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la lettura della tabella provvedimenti: " + e.getMessage());
            } finally {
                try {
                    connection.close(); // Chiude la connessione in modo controllato
                } catch (SQLException e) {
                    System.out.println("Errore durante la chiusura della connessione: " + e.getMessage());
                }
            }
        }
    }

    public void visualizzaTabellaElencoRischi() {
        Connection connection = connessioneDb();
        if (connection != null) {
            try (Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM public.elenco_rischi")) {

                while (resultSet.next()) {
                    int idProvvedimento = resultSet.getInt("id_provvedimento");
                    int idRischio = resultSet.getInt("id_rischio");

                    System.out.println("ID Provvedimento: " + idProvvedimento);
                    System.out.println("ID Rischio: " + idRischio);
                    System.out.println();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la lettura della tabella elenco_rischi: " + e.getMessage());
            } finally {
                try {
                    connection.close(); // Chiude la connessione in modo controllato
                } catch (SQLException e) {
                    System.out.println("Errore durante la chiusura della connessione: " + e.getMessage());
                }
            }
        }
    }

    // Metodo per inserire una riga (l'ultimo elemento della lista) nella tabella
    // corrispondnome
    public static void inserisciElementoSocieta(Connection connection, List<Societa> societaList) throws SQLException {
        String insertQuery = "INSERT INTO public.societa (id_societa, indirizzo, localita, provincia, telefono, descrizione, nome) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, societaList.get(societaList.size() - 1).getId());
            preparedStatement.setString(2, societaList.get(societaList.size() - 1).getIndirizzo());
            preparedStatement.setString(3, societaList.get(societaList.size() - 1).getLocalita());
            preparedStatement.setString(4, societaList.get(societaList.size() - 1).getProvincia());
            preparedStatement.setString(5, societaList.get(societaList.size() - 1).getTelefono());
            preparedStatement.setString(6, societaList.get(societaList.size() - 1).getDescrizione());
            preparedStatement.setString(7, societaList.get(societaList.size() - 1).getNome());
            preparedStatement.executeUpdate();

        }
    }

    // Metodo per inserire una riga (l'ultimo elemento della lista) nella tabella
    // corrisponde nome
    public static void inserisciElementoUnitaLocali(Connection connection, List<UnitaLocale> unitaLocaleList)
            throws SQLException {
        String insertQuery = "INSERT INTO public.unita_locali (id_unita_locale, id_societa, nome, indirizzo, localita, provincia) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            // System.out.println(unitaLocaleList.get(unitaLocaleList.size() - 1).getId());
            preparedStatement.setInt(1, unitaLocaleList.get(unitaLocaleList.size() - 1).getId());
            preparedStatement.setInt(2, unitaLocaleList.get(unitaLocaleList.size() - 1).getIdSocieta());
            preparedStatement.setString(3, unitaLocaleList.get(unitaLocaleList.size() - 1).getNome());
            preparedStatement.setString(4, unitaLocaleList.get(unitaLocaleList.size() - 1).getIndirizzo());
            preparedStatement.setString(5, unitaLocaleList.get(unitaLocaleList.size() - 1).getLocalita());
            preparedStatement.setString(6, unitaLocaleList.get(unitaLocaleList.size() - 1).getProvincia());
            preparedStatement.executeUpdate();

        }
    }

    // Metodo per inserire una riga (l'ultimo elemento della lista) nella tabella
    // corrispondnome
    public static void inserisciElementoReparti(Connection connection, List<Reparto> repartoList) throws SQLException {
        String insertQuery = "INSERT INTO public.reparti (id_reparto, id_unita_locale, nome, descrizione) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setInt(1, repartoList.get(repartoList.size() - 1).getId());
            preparedStatement.setInt(2, repartoList.get(repartoList.size() - 1).getIdUnitaLocale());
            preparedStatement.setString(3, repartoList.get(repartoList.size() - 1).getNome());
            preparedStatement.setString(4, repartoList.get(repartoList.size() - 1).getDescrizione());
            preparedStatement.executeUpdate();

        }
    }

    // Metodo per inserire una riga (l'ultimo elemento della lista) nella tabella
    // corrispondnome
    public static void inserisciElementoTitoli(Connection connection, List<Titolo> titoloList) throws SQLException {
        String insertQuery = "INSERT INTO public.titoli (id_titolo, descrizione, id_reparto) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setInt(1, titoloList.get(titoloList.size() - 1).getId());
            preparedStatement.setString(2, titoloList.get(titoloList.size() - 1).getDescrizione());
            preparedStatement.setInt(3, titoloList.get(titoloList.size() - 1).getIdReparto());
            preparedStatement.executeUpdate();

        }
    }

    // Metodo per inserire una riga (l'ultimo elemento della lista) nella tabella
    // corrispondente
    public static void inserisciElementoMansioni(Connection connection, List<Mansione> mansioneList)
            throws SQLException {
        String insertQuery = "INSERT INTO public.mansioni (id_mansione, nome, responsabile) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setInt(1, mansioneList.get(mansioneList.size() - 1).getId());
            preparedStatement.setString(2, mansioneList.get(mansioneList.size() - 1).getNome());
            preparedStatement.setString(3, mansioneList.get(mansioneList.size() - 1).getResponsabile());
            preparedStatement.executeUpdate();

        }
    }

    // Metodo per inserire una riga (l'ultimo elemento della lista) nella tabella
    // corrispondnome
    public static void inserisciElementoOggetti(Connection connection, List<Oggetto> oggettoList) throws SQLException {
        String insertQuery = "INSERT INTO public.oggetti (id, nome, id_titolo) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setInt(1, oggettoList.get(oggettoList.size() - 1).getId());
            preparedStatement.setString(2, oggettoList.get(oggettoList.size() - 1).getNome());
            preparedStatement.setInt(3, oggettoList.get(oggettoList.size() - 1).getIdTitolo());
            preparedStatement.executeUpdate();

        }
    }

    // Metodo per inserire una riga (l'ultimo elemento della lista) nella tabella
    // corrispondnome
    public static void inserisciElementoProvvedimenti(Connection connection, List<Provvedimento> provvedimentoList)
            throws SQLException {
        String insertQuery = "INSERT INTO public.provvedimenti (id_provvedimento, id_oggetto, rischio, nome, soggetti_esposti, stima_r, stima_d, stima_p) VALUES (?, ?, ?, ?, ?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setInt(1, provvedimentoList.get(provvedimentoList.size() - 1).getId());
            preparedStatement.setInt(2, provvedimentoList.get(provvedimentoList.size() - 1).getIdOggetto());
            preparedStatement.setString(3, provvedimentoList.get(provvedimentoList.size() - 1).getRischio());
            preparedStatement.setString(4, provvedimentoList.get(provvedimentoList.size() - 1).getNome());
            preparedStatement.setString(5, provvedimentoList.get(provvedimentoList.size() - 1).getSoggettiEsposti());
            preparedStatement.setInt(6, provvedimentoList.get(provvedimentoList.size() - 1).getStimaR());
            preparedStatement.setInt(7, provvedimentoList.get(provvedimentoList.size() - 1).getStimaD());
            preparedStatement.setInt(8, provvedimentoList.get(provvedimentoList.size() - 1).getStimaP());
            preparedStatement.executeUpdate();

        }
    }

    // Metodo per inserire una riga (l'ultimo elemento della lista) nella tabella
    // corrispondente
    public static void inserisciElementoRischi(Connection connection, List<Rischio> rischioList) throws SQLException {
        String insertQuery = "INSERT INTO public.rischi (id_rischio, nome, P, D, R, id_reparto) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setInt(1, rischioList.get(rischioList.size() - 1).getId());
            preparedStatement.setString(2, rischioList.get(rischioList.size() - 1).getNome());
            preparedStatement.setInt(3, rischioList.get(rischioList.size() - 1).getP());
            preparedStatement.setInt(4, rischioList.get(rischioList.size() - 1).getD());
            preparedStatement.setInt(5, rischioList.get(rischioList.size() - 1).getR());
            preparedStatement.setInt(6, rischioList.get(rischioList.size() - 1).getIdReparto());
            preparedStatement.executeUpdate();

        }
    }

}