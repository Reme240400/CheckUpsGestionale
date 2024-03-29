package Models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import Helpers.ClassHelper;
import Models.Tables.Mansione;
import Models.Tables.Oggetto;
import Models.Tables.Provvedimento;
import Models.Tables.Reparto;
//import Models.Tables.Rischio;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;

public class ModelDb {

    private static final String placeholderLogo = "https://via.placeholder.com/150";
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
                        String localita = resultSet.getString("localita");
                        String provincia = resultSet.getString("provincia");
                        String telefono = resultSet.getString("telefono");
                        String descrizione = resultSet.getString("descrizione");
                        String indirizzo = resultSet.getString("indirizzo");
                        String partitaIva = resultSet.getString("partita_iva");
                        String codiceFiscale = resultSet.getString("codice_fiscale");
                        String bancaAppoggio = resultSet.getString("banca_appoggio");
                        String codiceAteco = resultSet.getString("codice_ateco");
                        String logoUrl = resultSet.getString("logo");

                        Societa societa = new Societa(id_societa, nome, indirizzo, localita, provincia, telefono,
                                descrizione, partitaIva, codiceFiscale, bancaAppoggio, codiceAteco,
                                logoUrl == null ? placeholderLogo : logoUrl);

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
                        String revisione = resultSet.getString("revisione");

                        Optional<LocalDate> data = Optional.empty();
                        Date data_db = resultSet.getDate("data");
                        if (data_db != null)
                            data = Optional.of(data_db.toLocalDate());

                        Reparto reparto = new Reparto(idReparto, idUnitaLocale, nome, descrizione, revisione, data);
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
                        int idOggetto = resultSet.getInt("id_oggetto");
                        String rischio = resultSet.getString("rischio");
                        String soggettiEsposti = resultSet.getString("soggetti_esposti");
                        int stima_d = resultSet.getInt("stima_d");
                        int stima_p = resultSet.getInt("stima_p");

                        Optional<LocalDate> data_inizio = Optional.empty();
                        Date data_inizio_db = resultSet.getDate("data_inizio");
                        if (data_inizio_db != null)
                            data_inizio = Optional.of(data_inizio_db.toLocalDate());

                        Optional<LocalDate> data_scadenza = Optional.empty();
                        Date data_scadenza_db = resultSet.getDate("data_scadenza");
                        if (data_scadenza_db != null)
                            data_scadenza = Optional.of(data_scadenza_db.toLocalDate());

                        Provvedimento provvedimento = new Provvedimento(idProvvedimento, idOggetto, rischio, nome,
                                soggettiEsposti, stima_d, stima_p, data_inizio, data_scadenza);
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
                        String telefono = resultSet.getString("telefono");
                        UnitaLocale unitaLocale = new UnitaLocale(idUnitaLocale, nome, provincia, indirizzo, localita,
                                telefono, idSocieta);

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
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void modificaCampoStringa(String tableName, String pkName, int recordId, String campo,
            String nuovoValore) {
        try (Connection connection = connessioneDb()) {
            if (connection != null) {
                try (Statement statement = connection.createStatement()) {
                    String query = "UPDATE public." + tableName + " SET " + campo + " = ? WHERE " + pkName
                            + " = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, nuovoValore);
                    preparedStatement.setInt(2, recordId);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(
                            "Errore durante la modifica del campo nella tabella " + tableName + ": " + e.getMessage());
                    e.printStackTrace();
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
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void modificaCampoIntero(String tableName, String pkName, int recordId, String campo,
            int nuovoValore) {
        try (Connection connection = connessioneDb()) {
            if (connection != null) {
                try (Statement statement = connection.createStatement()) {
                    String query = "UPDATE public." + tableName + " SET " + campo + " = ? WHERE " + pkName
                            + " = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, nuovoValore);
                    preparedStatement.setInt(2, recordId);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(
                            "Errore durante la modifica del campo nella tabella " + tableName + ": " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void modificaCampoData(String tableName, String pkName, int recordId, String campo,
            LocalDate nuovoValore) {
        try (Connection connection = connessioneDb()) {
            if (connection != null) {
                try (Statement statement = connection.createStatement()) {
                    String query = "UPDATE public." + tableName + " SET " + campo + " = ? WHERE " + pkName
                            + " = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setDate(1, java.sql.Date.valueOf(nuovoValore));
                    preparedStatement.setInt(2, recordId);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(
                            "Errore durante la modifica del campo nella tabella " + tableName + ": " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void modificaCampoData(String tableName, int recordId, String campo,
            LocalDate nuovoValore) {
        try (Connection connection = connessioneDb()) {
            if (connection != null) {
                try (Statement statement = connection.createStatement()) {
                    String query = "UPDATE public." + tableName + " SET " + campo + " = ? WHERE id_" + tableName
                            + " = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setDate(1, java.sql.Date.valueOf(nuovoValore));
                    preparedStatement.setInt(2, recordId);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(
                            "Errore durante la modifica del campo nella tabella " + tableName + ": " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metodo generico per l'inserimento di un record in una tabella
    public static void inserisciRecord(Object obj) {

        switch (obj.getClass().getSimpleName()) {
            case "Mansione":
                inserisciElementoMansioni(ClassHelper.getListMansione());
                break;
            case "Titolo":
                inserisciElementoTitoli(ClassHelper.getListTitolo());
                break;
            case "Reparto":
                inserisciElementoReparti(ClassHelper.getListReparto());
                break;
            /*
             * case "Rischio":
             * inserisciElementoRischi(ClassHelper.getListRischio());
             * break;
             */
            case "Societa":
                inserisciElementoSocieta(ClassHelper.getListSocieta());
                break;
            case "Oggetto":
                inserisciElementoOggetti(ClassHelper.getListOggetto());
                break;
            case "Provvedimento":
                inserisciElementoProvvedimenti(ClassHelper.getListProvvedimento());
                break;
            case "UnitaLocale":
                inserisciElementoUnitaLocali(ClassHelper.getListUnitaLocale());
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + obj.getClass().getSimpleName());
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

                modificaCampoStringa("titoli",
                        "id_titolo",
                        titolo.getId(),
                        "descrizione",
                        titolo.getDescrizione());

                break;

            case "Reparto":
                Reparto reparto = ((Reparto) obj);

                modificaCampoStringa("reparti",
                        "id_reparto",
                        reparto.getId(),
                        "descrizione",
                        reparto.getDescrizione());

                modificaCampoStringa("reparti",
                        "id_reparto",
                        reparto.getId(),
                        "nome",
                        reparto.getDescrizione());

                modificaCampoStringa("reparti",
                        "id_reparto",
                        reparto.getId(),
                        "revisione",
                        reparto.getDescrizione());

                modificaCampoData("reparti",
                        "id_reparto",
                        reparto.getId(),
                        "data",
                        reparto.getData().get());

                break;

            case "Societa":
                Societa societa = ((Societa) obj);

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

                modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(),
                        societa.getId(),
                        "logo",
                        societa.getLogoUrl());

                modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(),
                        societa.getId(),
                        "codice_ateco",
                        societa.getCodiceAteco());

                modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(),
                        societa.getId(),
                        "banca_appoggio",
                        societa.getBancaAppoggio());

                modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(),
                        societa.getId(),
                        "codice_fiscale",
                        societa.getCodiceFiscale());

                modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(),
                        societa.getId(),
                        "partita_iva",
                        societa.getPartitaIva());

                break;

            case "Oggetto":
                Oggetto oggetto = ((Oggetto) obj);

                modificaCampoStringa("oggetti",
                        "id",
                        oggetto.getId(), "nome",
                        oggetto.getNome());

                modificaCampoIntero("oggetti",
                        "id",
                        oggetto.getId(),
                        "id_titolo",
                        oggetto.getIdTitolo());

                break;

            case "Provvedimento":
                Provvedimento provvedimento = ((Provvedimento) obj);

                modificaCampoStringa("provvedimenti",
                        "id_provvedimento",
                        provvedimento.getId(),
                        "nome",
                        provvedimento.getNome());

                modificaCampoIntero("provvedimenti",
                        "id_provvedimento",
                        provvedimento.getId(),
                        "id_oggetto",
                        provvedimento.getIdOggetto());

                modificaCampoStringa("provvedimenti",
                        "id_provvedimento",
                        provvedimento.getId(),
                        "rischio",
                        provvedimento.getRischio());

                modificaCampoStringa("provvedimenti",
                        "id_provvedimento",
                        provvedimento.getId(),
                        "soggetti_esposti",
                        provvedimento.getSoggettiEsposti());

                modificaCampoIntero("provvedimenti",
                        "id_provvedimento",
                        provvedimento.getId(),
                        "stima_r",
                        provvedimento.getStimaR());

                modificaCampoIntero("provvedimenti",
                        "id_provvedimento",
                        provvedimento.getId(),
                        "stima_d",
                        provvedimento.getStimaD());

                modificaCampoIntero("provvedimenti",
                        "id_provvedimento",
                        provvedimento.getId(),
                        "stima_p",
                        provvedimento.getStimaP());

                modificaCampoData("provvedimenti",
                        "id_provvedimento",
                        provvedimento.getId(),
                        "data_inizio",
                        provvedimento.getDataInizio().get());

                modificaCampoData("provvedimenti",
                        "id_provvedimento",
                        provvedimento.getId(),
                        "data_scadenza",
                        provvedimento.getDataScadenza().get());

                break;

            case "UnitaLocale":
                UnitaLocale unitaLocale = ((UnitaLocale) obj);

                modificaCampoStringa("unita_locali",
                        "id_unita_locale",
                        unitaLocale.getId(),
                        "nome",
                        unitaLocale.getNome());

                modificaCampoStringa("unita_locali",
                        "id_unita_locale",
                        unitaLocale.getId(),
                        "indirizzo",
                        unitaLocale.getIndirizzo());

                modificaCampoStringa("unita_locali",
                        "id_unita_locale",
                        unitaLocale.getId(),
                        "localita",
                        unitaLocale.getLocalita());

                modificaCampoStringa("unita_locali",
                        "id_unita_locale",
                        unitaLocale.getId(),
                        "provincia",
                        unitaLocale.getProvincia());

                modificaCampoStringa("unita_locali",
                        "id_unita_locale",
                        unitaLocale.getId(),
                        "telefono",
                        unitaLocale.getTelefono());

                modificaCampoIntero("unita_locali",
                        "id_unita_locale",
                        unitaLocale.getId(),
                        "id_societa",
                        unitaLocale.getIdSocieta());

                break;

            default:
                throw new IllegalArgumentException(
                        "Unexpected value: " + obj.getClass().getSimpleName());
        }
    }

    public static void existField() {

    }

    private static void doUpdateQuery(String query, Consumer<PreparedStatement> action) {
        try (Connection connection = connessioneDb()) {
            if (connection != null) {
                try (PreparedStatement pStatement = connection.prepareStatement(query)) {
                    action.accept(pStatement);
                    pStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
                    // int idMansione = resultSet.getInt("id_mansione");
                    int idOggetto = resultSet.getInt("id_oggetto");
                    int idElencoRischi = resultSet.getInt("id_elenco_rischi");

                    System.out.println("ID Provvedimento: " + idProvvedimento);
                    System.out.println("Nome: " + nome);
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

    // Metodo per inserire una riga (l'ultimo elemento della lista) nella tabella
    // corrispondnome
    public static void inserisciElementoSocieta(List<Societa> societaList) {
        doUpdateQuery(
                "INSERT INTO public.societa (id_societa, nome, localita, provincia, telefono, descrizione, indirizzo, partita_iva, codice_fiscale, banca_appoggio, codice_ateco, logo) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)",
                (ps) -> {
                    try {
                        Societa soc = societaList.get(societaList.size() - 1);
                        ps.setInt(1, soc.getId());
                        ps.setString(2, soc.getNome());
                        ps.setString(3, soc.getLocalita());
                        ps.setString(4, soc.getProvincia());
                        ps.setString(5, soc.getTelefono());
                        ps.setString(6, soc.getDescrizione());
                        ps.setString(7, soc.getIndirizzo());
                        ps.setString(8, soc.getPartitaIva());
                        ps.setString(9, soc.getCodiceFiscale());
                        ps.setString(10, soc.getBancaAppoggio());
                        ps.setString(11, soc.getCodiceAteco());
                        ps.setString(12, soc.getLogoUrl().equals(placeholderLogo) ? null : soc.getLogoUrl());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
    }

    // Metodo per inserire una riga (l'ultimo elemento della lista) nella tabella
    // corrisponde nome
    public static void inserisciElementoUnitaLocali(List<UnitaLocale> unitaLocaleList) {
        doUpdateQuery(
                "INSERT INTO public.unita_locali (id_unita_locale, id_societa, nome, indirizzo, localita, provincia, telefono) VALUES (?, ?, ?, ?, ?, ?, ?)",
                (ps) -> {
                    try {
                        UnitaLocale uLocale = unitaLocaleList.get(unitaLocaleList.size() - 1);
                        ps.setInt(1, uLocale.getId());
                        ps.setInt(2, uLocale.getIdSocieta());
                        ps.setString(3, uLocale.getNome());
                        ps.setString(4, uLocale.getIndirizzo());
                        ps.setString(5, uLocale.getLocalita());
                        ps.setString(6, uLocale.getProvincia());
                        ps.setString(7, uLocale.getTelefono());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
    }

    // Metodo per inserire una riga (l'ultimo elemento della lista) nella tabella
    // corrispondnome
    public static void inserisciElementoReparti(List<Reparto> repartoList) {
        doUpdateQuery(
                "INSERT INTO public.reparti (id_reparto, id_unita_locale, nome, descrizione, revisione, data) VALUES (?, ?, ?, ?, ?, ?)",
                (ps) -> {
                    try {
                        Reparto reparto = repartoList.get(repartoList.size() - 1);
                        ps.setInt(1, reparto.getId());
                        ps.setInt(2, reparto.getIdUnitaLocale());
                        ps.setString(3, reparto.getNome());
                        ps.setString(4, reparto.getDescrizione());
                        ps.setString(5, reparto.getRevisione());
                        ps.setDate(6,
                                reparto.getData().orElse(null) != null ? java.sql.Date.valueOf(reparto.getData().get())
                                        : null);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

    }

    // Metodo per inserire una riga (l'ultimo elemento della lista) nella tabella
    // corrispondnome
    public static void inserisciElementoTitoli(List<Titolo> titoloList) {
        doUpdateQuery("INSERT INTO public.titoli (id_titolo, descrizione, id_reparto) VALUES (?, ?, ?)", (ps) -> {
            try {
                ps.setInt(1, titoloList.get(titoloList.size() - 1).getId());
                ps.setString(2, titoloList.get(titoloList.size() - 1).getDescrizione());
                ps.setInt(3, titoloList.get(titoloList.size() - 1).getIdReparto());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    // Metodo per inserire una riga (l'ultimo elemento della lista) nella tabella
    // corrispondente
    public static void inserisciElementoMansioni(List<Mansione> mansioneList) {
        doUpdateQuery("INSERT INTO public.mansioni (id_mansione, nome, responsabile) VALUES (?, ?, ?)", (statement) -> {
            try {
                statement.setInt(1, mansioneList.get(mansioneList.size() - 1).getId());
                statement.setString(2, mansioneList.get(mansioneList.size() - 1).getNome());
                statement.setString(3, mansioneList.get(mansioneList.size() - 1).getResponsabile());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    // Metodo per inserire una riga (l'ultimo elemento della lista) nella tabella
    // corrispondnome
    public static void inserisciElementoOggetti(List<Oggetto> oggettoList) {
        doUpdateQuery("INSERT INTO public.oggetti (id, nome, id_titolo) VALUES (?, ?, ?)", (ps) -> {
            try {
                ps.setInt(1, oggettoList.get(oggettoList.size() - 1).getId());
                ps.setString(2, oggettoList.get(oggettoList.size() - 1).getNome());
                ps.setInt(3, oggettoList.get(oggettoList.size() - 1).getIdTitolo());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    // Metodo per inserire una riga (l'ultimo elemento della lista) nella tabella
    // corrispondnome
    public static void inserisciElementoProvvedimenti(List<Provvedimento> provvedimentoList) {
        doUpdateQuery(
                "INSERT INTO public.provvedimenti (id_provvedimento, id_oggetto, rischio, nome, soggetti_esposti, stima_r, stima_d, stima_p, data_inizio, data_scadenza) VALUES (?, ?, ?, ?, ?,?,?,?,?,?)",
                (ps) -> {
                    try {
                        Provvedimento prov = provvedimentoList.get(provvedimentoList.size() - 1);
                        ps.setInt(1, prov.getId());
                        ps.setInt(2, prov.getIdOggetto());
                        ps.setString(3, prov.getRischio());
                        ps.setString(4, prov.getNome());
                        ps.setString(5, prov.getSoggettiEsposti());
                        ps.setInt(6, prov.getStimaR());
                        ps.setInt(7, prov.getStimaD());
                        ps.setInt(8, prov.getStimaP());
                        ps.setDate(9,
                                prov.getDataInizio().isPresent() ? java.sql.Date.valueOf(prov.getDataInizio().get())
                                        : null);
                        ps.setDate(10,
                                prov.getDataScadenza().isPresent() ? java.sql.Date.valueOf(prov.getDataScadenza().get())
                                        : null);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
    }

    public static void bulkInsertTitolo(int nuovoIdTitolo, List<Oggetto> oggetti, List<Provvedimento> provvedimenti) {
        List<Provvedimento> newProvs = new ArrayList<>();
        try (Connection connection = connessioneDb()) {
            if (connection != null) {
                PreparedStatement ps = connection
                        .prepareStatement("INSERT INTO public.oggetti (id, nome, id_titolo) VALUES (?, ?, ?)");
                connection.setAutoCommit(false);

                for (Oggetto oggetto : oggetti) {
                    Oggetto nuovoOggetto = new Oggetto(Model.autoSetId(ClassHelper.getListOggetto()), oggetto.getNome(),
                            nuovoIdTitolo);

                    ps.setInt(1, nuovoOggetto.getId());
                    ps.setString(2, nuovoOggetto.getNome());
                    ps.setInt(3, nuovoOggetto.getIdTitolo());
                    ps.addBatch();

                    List<Provvedimento> relatedProv = provvedimenti.stream()
                            .filter(p -> p.getIdOggetto() == oggetto.getId()).toList();
                    for (Provvedimento prov : relatedProv) {
                        Provvedimento tmpProv = new Provvedimento(Model.autoSetId(ClassHelper.getListProvvedimento()),
                                nuovoOggetto.getId(), prov.getRischio(), prov.getNome(), prov.getSoggettiEsposti(),
                                prov.getStimaD(), prov.getStimaP(), prov.getDataInizio(),
                                prov.getDataScadenza());
                        newProvs.add(tmpProv);
                        ClassHelper.getListProvvedimento().add(tmpProv);
                    }

                    ClassHelper.getListOggetto().add(nuovoOggetto);
                }

                int[] updated = ps.executeBatch();
                System.out.println("Importati " + updated.length + " oggetti");
                connection.commit();
                connection.setAutoCommit(true);

                ps = connection
                        .prepareStatement(
                                "INSERT INTO public.provvedimenti (id_provvedimento, id_oggetto, rischio, nome, soggetti_esposti, stima_r, stima_d, stima_p, data_inizio, data_scadenza) VALUES (?, ?, ?, ?, ?,?,?,?,?,?)");

                connection.setAutoCommit(false);

                for (Provvedimento prov : newProvs) {

                    ps.setInt(1, prov.getId());
                    ps.setInt(2, prov.getIdOggetto());
                    ps.setString(3, prov.getRischio());
                    ps.setString(4, prov.getNome());
                    ps.setString(5, prov.getSoggettiEsposti());
                    ps.setInt(6, prov.getStimaR());
                    ps.setInt(7, prov.getStimaD());
                    ps.setInt(8, prov.getStimaP());
                    ps.setDate(9,
                            prov.getDataInizio().isPresent() ? java.sql.Date.valueOf(prov.getDataInizio().get())
                                    : null);
                    ps.setDate(10,
                            prov.getDataScadenza().isPresent() ? java.sql.Date.valueOf(prov.getDataScadenza().get())
                                    : null);

                    ps.addBatch();
                }

                updated = ps.executeBatch();
                System.out.println("Importati " + updated.length + " provvedimenti");
                connection.commit();
                connection.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void bulkInsertOggetto(int nuovoIdOggetto, List<Provvedimento> provvedimenti) {
        try (Connection connection = connessioneDb()) {
            if (connection != null) {
                PreparedStatement ps = connection
                        .prepareStatement(
                                "INSERT INTO public.provvedimenti (id_provvedimento, id_oggetto, rischio, nome, soggetti_esposti, stima_r, stima_d, stima_p, data_inizio, data_scadenza) VALUES (?, ?, ?, ?, ?,?,?,?,?,?)");
                connection.setAutoCommit(false);

                for (Provvedimento prov : provvedimenti) {
                    Provvedimento tmpProv = new Provvedimento(Model.autoSetId(ClassHelper.getListProvvedimento()),
                            nuovoIdOggetto, prov.getRischio(), prov.getNome(), prov.getSoggettiEsposti(),
                            prov.getStimaD(), prov.getStimaP(), prov.getDataInizio(),
                            prov.getDataScadenza());

                    ps.setInt(1, tmpProv.getId());
                    ps.setInt(2, tmpProv.getIdOggetto());
                    ps.setString(3, tmpProv.getRischio());
                    ps.setString(4, tmpProv.getNome());
                    ps.setString(5, tmpProv.getSoggettiEsposti());
                    ps.setInt(6, tmpProv.getStimaR());
                    ps.setInt(7, tmpProv.getStimaD());
                    ps.setInt(8, tmpProv.getStimaP());
                    ps.setDate(9,
                            tmpProv.getDataInizio().isPresent() ? java.sql.Date.valueOf(tmpProv.getDataInizio().get())
                                    : null);
                    ps.setDate(10,
                            tmpProv.getDataScadenza().isPresent()
                                    ? java.sql.Date.valueOf(tmpProv.getDataScadenza().get())
                                    : null);
                    ps.addBatch();

                    ClassHelper.getListProvvedimento().add(tmpProv);
                }

                int[] updated = ps.executeBatch();
                System.out.println("Importati " + updated.length + " provvedimenti");
                connection.commit();
                connection.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}