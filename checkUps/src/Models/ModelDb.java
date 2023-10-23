package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Controllers.ClassHelper;
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

                        Societa societa = new Societa(nome, indirizzo, localita, provincia, telefono, descrizione);
                        societa.setIdSocieta(id_societa);

                        Model.inserisciRecordInLista(societa);
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
                        Model.inserisciRecordInLista(mansione);
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
                        Titolo titolo = new Titolo(idTitolo, descrizione, idReparto);
                        Model.inserisciRecordInLista(titolo);
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
                        Model.inserisciRecordInLista(reparto);
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
                        Model.inserisciRecordInLista(rischio);
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
                        int idOggetto = resultSet.getInt("id_oggetto");
                        String nome = resultSet.getString("nome");
                        int idTitolo = resultSet.getInt("id_titolo");

                        Oggetto oggetto = new Oggetto(idOggetto, nome, idTitolo);
                        Model.inserisciRecordInLista(oggetto);
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
                        int stima = resultSet.getInt("stima");

                        Provvedimento provvedimento = new Provvedimento(idProvvedimento, nome, idMansione, idOggetto,
                                rischio, soggettiEsposti, stima);
                        Model.inserisciRecordInLista(provvedimento);
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
                        Model.inserisciRecordInLista(unitaLocale);
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
    public static void eliminaRecord(String tableName, int recordId) {
        try (Connection connection = connessioneDb()) {
            if (connection != null) {
                try (Statement statement = connection.createStatement()) {
                    String query = "DELETE FROM public." + tableName + " WHERE id_" + tableName + " = ?";
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
            preparedStatement.setInt(1, societaList.get(societaList.size() - 1).getIdSocieta());
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
    // corrispondnome
    public static void inserisciElementoUnitaLocali(Connection connection, List<UnitaLocale> unitaLocaleList)
            throws SQLException {
        String insertQuery = "INSERT INTO public.unita_locali (id_unita_locale, id_societa, nome, indirizzo, localita, provincia) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setInt(1, unitaLocaleList.get(unitaLocaleList.size() - 1).getIdUnitaLocale());
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

            preparedStatement.setInt(1, repartoList.get(repartoList.size() - 1).getIdReparto());
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

            preparedStatement.setInt(1, titoloList.get(titoloList.size() - 1).getIdTitolo());
            preparedStatement.setString(2, titoloList.get(titoloList.size() - 1).getDescrizione());
            preparedStatement.setInt(3, titoloList.get(titoloList.size() - 1).getIdReparto());
            preparedStatement.executeUpdate();

        }
    }

    // Metodo per inserire una riga (l'ultimo elemento della lista) nella tabella
    // corrispondnome
    public static void inserisciElementoMansioni(Connection connection, List<Mansione> mansioneList)
            throws SQLException {
        String insertQuery = "INSERT INTO public.mansioni (id_mansione, nome, responsabile) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setInt(1, mansioneList.get(mansioneList.size() - 1).getIdMansione());
            preparedStatement.setString(2, mansioneList.get(mansioneList.size() - 1).getNome());
            preparedStatement.setString(3, mansioneList.get(mansioneList.size() - 1).getResponsabile());
            preparedStatement.executeUpdate();

        }
    }

    // Metodo per inserire una riga (l'ultimo elemento della lista) nella tabella
    // corrispondnome
    public static void inserisciElementoOggetti(Connection connection, List<Oggetto> oggettoList) throws SQLException {
        String insertQuery = "INSERT INTO public.oggetti (id_oggetto, nome, id_titolo) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setInt(1, oggettoList.get(oggettoList.size() - 1).getIdOggetto());
            preparedStatement.setString(2, oggettoList.get(oggettoList.size() - 1).getNome());
            preparedStatement.setInt(3, oggettoList.get(oggettoList.size() - 1).getIdTitolo());
            preparedStatement.executeUpdate();

        }
    }

    // Metodo per inserire una riga (l'ultimo elemento della lista) nella tabella
    // corrispondnome
    public static void inserisciElementoProvvedimenti(Connection connection, List<Provvedimento> provvedimentoList)
            throws SQLException {
        String insertQuery = "INSERT INTO public.provvedimenti (id_provvedimento, nome, id_mansione, id_oggetto, id_elenco_rischi) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setInt(1, provvedimentoList.get(provvedimentoList.size() - 1).getIdProvvedimento());
            preparedStatement.setString(2, provvedimentoList.get(provvedimentoList.size() - 1).getNome());
            preparedStatement.setInt(3, provvedimentoList.get(provvedimentoList.size() - 1).getIdMansione());
            preparedStatement.setInt(4, provvedimentoList.get(provvedimentoList.size() - 1).getIdOggetto());
            preparedStatement.setString(5, provvedimentoList.get(provvedimentoList.size() - 1).getRischio());
            preparedStatement.setString(5, provvedimentoList.get(provvedimentoList.size() - 1).getSoggettiEsposti());
            preparedStatement.setInt(4, provvedimentoList.get(provvedimentoList.size() - 1).getStima());
            preparedStatement.executeUpdate();

        }
    }

    // Metodo per inserire una riga (l'ultimo elemento della lista) nella tabella
    // corrispondente
    public static void inserisciElementoRischi(Connection connection, List<Rischio> rischioList) throws SQLException {
        String insertQuery = "INSERT INTO public.rischi (id_rischio, nome, P, D, R, id_reparto) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setInt(1, rischioList.get(rischioList.size() - 1).getIdRischio());
            preparedStatement.setString(2, rischioList.get(rischioList.size() - 1).getNome());
            preparedStatement.setInt(3, rischioList.get(rischioList.size() - 1).getP());
            preparedStatement.setInt(4, rischioList.get(rischioList.size() - 1).getD());
            preparedStatement.setInt(5, rischioList.get(rischioList.size() - 1).getR());
            preparedStatement.setInt(6, rischioList.get(rischioList.size() - 1).getIdReparto());
            preparedStatement.executeUpdate();

        }
    }

    public static List<UnitaLocale> filtraUnitaDaSocieta(int idSocieta) throws SQLException {

        String filterQuery = "SELECT * FROM public.unita_locali WHERE id_societa = " + idSocieta + "";
        List<UnitaLocale> unitaLocaleList = new ArrayList<>();

        try (Connection connection = connessioneDb()) {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(filterQuery);

            while (resultSet.next()) {
                int idUnitaLocale = resultSet.getInt("id_unita_locale");
                String nome = resultSet.getString("nome");
                String indirizzo = resultSet.getString("indirizzo");
                String localita = resultSet.getString("localita");
                String provincia = resultSet.getString("provincia");

                UnitaLocale unitaLocale = new UnitaLocale(idUnitaLocale, nome, provincia, indirizzo, localita,
                        idSocieta);
                unitaLocaleList.add(unitaLocale);

            }

            return unitaLocaleList;

        }
    }

}

/*
 * public void cancellaDatiTabelle() {
 * Connection connection = connessioneDb();
 * if (connection != null) {
 * try (Statement statement = connection.createStatement()) {
 * // Disabilita il controllo delle chiavi esterne temporaneamnome
 * statement.execute("SET FOREIGN_KEY_CHECKS = 0");
 * 
 * // Cancella i dati da tutte le tabelle
 * statement.executeUpdate("DELETE FROM public.elenco_rischi");
 * statement.executeUpdate("DELETE FROM public.provvedimenti");
 * statement.executeUpdate("DELETE FROM public.mansioni");
 * statement.executeUpdate("DELETE FROM public.oggetti");
 * statement.executeUpdate("DELETE FROM public.rischi");
 * statement.executeUpdate("DELETE FROM public.titoli");
 * statement.executeUpdate("DELETE FROM public.reparti");
 * statement.executeUpdate("DELETE FROM public.unita_locali");
 * statement.executeUpdate("DELETE FROM public.societa");
 * 
 * // Riabilita il controllo delle chiavi esterne
 * statement.execute("SET FOREIGN_KEY_CHECKS = 1");
 * 
 * System.out.println("Dati cancellati con successo da tutte le tabelle.");
 * } catch (SQLException e) {
 * System.out.println("Errore durante la cancellazione dei dati: " +
 * e.getMessage());
 * } finally {
 * try {
 * connection.close();
 * } catch (SQLException e) {
 * System.out.println("Errore durante la chiusura della connessione: " +
 * e.getMessage());
 * }
 * }
 * }
 * }
 * 
 * public void popolaDatabaseDaListe(List<Societa> societaList,
 * List<UnitaLocale> unitaLocaleList, List<Reparto> repartoList,
 * List<Titolo> titoloList, List<Mansione> mansioneList, List<Oggetto>
 * oggettoList,
 * List<Provvedimento> provvedimentoList, List<Rischio> rischioList,
 * List<ElencoRischi> elencorischiList) {
 * Connection connection = connessioneDb();
 * 
 * try {
 * if (connection != null) {
 * // Popola la tabella "societa"
 * inserisciElementoSocieta(connection, societaList);
 * 
 * // Popola la tabella "unita_locali"
 * inserisciElementoUnitaLocali(connection, unitaLocaleList);
 * 
 * // Popola la tabella "reparti"
 * inserisciElementoReparti(connection, repartoList);
 * 
 * // Popola la tabella "titoli"
 * inserisciElementoTitoli(connection, titoloList);
 * 
 * // Popola la tabella "mansioni"
 * inserisciElementoMansioni(connection, mansioneList);
 * 
 * // Popola la tabella "oggetti"
 * inserisciElementoOggetti(connection, oggettoList);
 * 
 * // Popola la tabella "provvedimenti"
 * inserisciElementoProvvedimenti(connection, provvedimentoList);
 * 
 * // Popola la tabella "rischi"
 * inserisciElementoRischi(connection, rischioList);
 * 
 * // Popola la tabella "elenco_rischi"
 * inserisciElementoElencoRischi(connection, elencorischiList);
 * 
 * System.out.println("Popolamento del database completato con successo.");
 * }
 * } catch (SQLException e) {
 * System.out.println("Errore durante il popolamento del database: " +
 * e.getMessage());
 * } finally {
 * try {
 * if (connection != null) {
 * connection.close();
 * }
 * } catch (SQLException e) {
 * System.out.println("Errore durante la chiusura della connessione: " +
 * e.getMessage());
 * }
 * }
 * }
 */
