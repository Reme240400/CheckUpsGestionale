package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import Tables.ElencoRischi;
import Tables.Mansione;
import Tables.Oggetto;
import Tables.Provvedimento;
import Tables.Reparto;
import Tables.Rischio;
import Tables.Societa;
import Tables.Titolo;
import Tables.UnitaLocale;



public class ControllerSql {

    private static final String connectionUrl = "jdbc:postgresql://localhost:5432/checkups_db";
    private static final String username = "postgres";
    private static final String password = "postgres";

    public ControllerSql() { 
        visualizzaTabellaSocieta();
        visualizzaTabellaUnitaLocali();
        visualizzaTabellaReparti();
        visualizzaTabellaTitoli();
        visualizzaTabellaMansioni();
        visualizzaTabellaOggetti();
        visualizzaTabellaProvvedimenti();
        visualizzaTabellaRischi();
        visualizzaTabellaElencoRischi();
    }

    public static Connection connessioneDb() {
        try {
            Connection connection = DriverManager.getConnection(connectionUrl, username, password);
            return connection;
        } catch (SQLException ex) {
            System.out.println("Errore di connessione al database: " + ex.getMessage());
        }
        return null;
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
                    String ente = resultSet.getString("ente");

                    System.out.println("ID: " + idSocieta);
                    System.out.println("Indirizzo: " + indirizzo);
                    System.out.println("Localita: " + localita);
                    System.out.println("Provincia: " + provincia);
                    System.out.println("Telefono: " + telefono);
                    System.out.println("Descrizione: " + descrizione);
                    System.out.println("Ente: " + ente);
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

    public void cancellaDatiTabelle() {
        Connection connection = connessioneDb();
        if (connection != null) {
            try (Statement statement = connection.createStatement()) {
                // Disabilita il controllo delle chiavi esterne temporaneamente
                statement.execute("SET FOREIGN_KEY_CHECKS = 0");

                // Cancella i dati da tutte le tabelle
                statement.executeUpdate("DELETE FROM public.elenco_rischi");
                statement.executeUpdate("DELETE FROM public.provvedimenti");
                statement.executeUpdate("DELETE FROM public.mansioni");
                statement.executeUpdate("DELETE FROM public.oggetti");
                statement.executeUpdate("DELETE FROM public.rischi");
                statement.executeUpdate("DELETE FROM public.titoli");
                statement.executeUpdate("DELETE FROM public.reparti");
                statement.executeUpdate("DELETE FROM public.unita_locali");
                statement.executeUpdate("DELETE FROM public.societa");

                // Riabilita il controllo delle chiavi esterne
                statement.execute("SET FOREIGN_KEY_CHECKS = 1");

                System.out.println("Dati cancellati con successo da tutte le tabelle.");
            } catch (SQLException e) {
                System.out.println("Errore durante la cancellazione dei dati: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Errore durante la chiusura della connessione: " + e.getMessage());
                }
            }
        }
    }

    public void popolaDatabaseDaListe(List<Societa> societaList, List<UnitaLocale> unitaLocaleList, List<Reparto> repartoList,
                                      List<Titolo> titoloList, List<Mansione> mansioneList, List<Oggetto> oggettoList,
                                      List<Provvedimento> provvedimentoList, List<Rischio> rischioList, List<ElencoRischi> elencorischiList) {
        Connection connection = connessioneDb();

        try {
            if (connection != null) {
                // Popola la tabella "societa"
                popolaTabellaSocieta(connection, societaList);

                // Popola la tabella "unita_locali"
                popolaTabellaUnitaLocali(connection, unitaLocaleList);

                // Popola la tabella "reparti"
                popolaTabellaReparti(connection, repartoList);

                // Popola la tabella "titoli"
                popolaTabellaTitoli(connection, titoloList);

                // Popola la tabella "mansioni"
                popolaTabellaMansioni(connection, mansioneList);

                // Popola la tabella "oggetti"
                popolaTabellaOggetti(connection, oggettoList);

                // Popola la tabella "provvedimenti"
                popolaTabellaProvvedimenti(connection, provvedimentoList);

                // Popola la tabella "rischi"
                popolaTabellaRischi(connection, rischioList);

                // Popola la tabella "elenco_rischi"
                popolaTabellaElencoRischi(connection, elencorischiList);

                System.out.println("Popolamento del database completato con successo.");
            }
        } catch (SQLException e) {
            System.out.println("Errore durante il popolamento del database: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la chiusura della connessione: " + e.getMessage());
            }
        }
    }

    // Metodi per popolare le tabelle individuali
    private void popolaTabellaSocieta(Connection connection, List<Societa> societaList) throws SQLException {
        String insertQuery = "INSERT INTO public.societa (id_societa, indirizzo, localita, provincia, telefono, descrizione, ente) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            for (Societa societa : societaList) {
                preparedStatement.setInt(1, societa.getIdSocieta());
                preparedStatement.setString(2, societa.getIndirizzo());
                preparedStatement.setString(3, societa.getLocalita());
                preparedStatement.setString(4, societa.getProvincia());
                preparedStatement.setLong(5, societa.getTelefono());
                preparedStatement.setString(6, societa.getDescrizione());
                preparedStatement.setString(7, societa.getEnte());
                preparedStatement.executeUpdate();
            }
        }
    }

    private void popolaTabellaUnitaLocali(Connection connection, List<UnitaLocale> unitaLocaleList) throws SQLException {
        String insertQuery = "INSERT INTO public.unita_locali (id_unita_locale, id_societa, nome, indirizzo, localita, provincia) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            for (UnitaLocale unitaLocale : unitaLocaleList) {
                preparedStatement.setInt(1, unitaLocale.getIdUnitaLocale());
                preparedStatement.setInt(2, unitaLocale.getIdSocieta());
                preparedStatement.setString(3, unitaLocale.getNome());
                preparedStatement.setString(4, unitaLocale.getIndirizzo());
                preparedStatement.setString(5, unitaLocale.getLocalita());
                preparedStatement.setString(6, unitaLocale.getProvincia());
                preparedStatement.executeUpdate();
            }
        }
    }
    
    private void popolaTabellaReparti(Connection connection, List<Reparto> repartoList) throws SQLException {
        String insertQuery = "INSERT INTO public.reparti (id_reparto, id_unita_locale, nome, descrizione) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            for (Reparto reparto : repartoList) {
                preparedStatement.setInt(1, reparto.getIdReparto());
                preparedStatement.setInt(2, reparto.getIdUnitaLocale());
                preparedStatement.setString(3, reparto.getNome());
                preparedStatement.setString(4, reparto.getDescrizione());
                preparedStatement.executeUpdate();
            }
        }
    }
    
    private void popolaTabellaTitoli(Connection connection, List<Titolo> titoloList) throws SQLException {
        String insertQuery = "INSERT INTO public.titoli (id_titolo, descrizione, id_reparto) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            for (Titolo titolo : titoloList) {
                preparedStatement.setInt(1, titolo.getIdTitolo());
                preparedStatement.setString(2, titolo.getDescrizione());
                preparedStatement.setInt(3, titolo.getIdReparto());
                preparedStatement.executeUpdate();
            }
        }
    }
    
    private void popolaTabellaMansioni(Connection connection, List<Mansione> mansioneList) throws SQLException {
        String insertQuery = "INSERT INTO public.mansioni (id_mansione, nome, responsabile) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            for (Mansione mansione : mansioneList) {
                preparedStatement.setInt(1, mansione.getIdMansione());
                preparedStatement.setString(2, mansione.getNome());
                preparedStatement.setString(3, mansione.getResponsabile());
                preparedStatement.executeUpdate();
            }
        }
    }
    
    private void popolaTabellaOggetti(Connection connection, List<Oggetto> oggettoList) throws SQLException {
        String insertQuery = "INSERT INTO public.oggetti (id_oggetto, nome, id_titolo) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            for (Oggetto oggetto : oggettoList) {
                preparedStatement.setInt(1, oggetto.getIdOggetto());
                preparedStatement.setString(2, oggetto.getNome());
                preparedStatement.setInt(3, oggetto.getIdTitolo());
                preparedStatement.executeUpdate();
            }
        }
    }
    
    private void popolaTabellaProvvedimenti(Connection connection, List<Provvedimento> provvedimentoList) throws SQLException {
        String insertQuery = "INSERT INTO public.provvedimenti (id_provvedimento, nome, id_mansione, id_oggetto, id_elenco_rischi) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            for (Provvedimento provvedimento : provvedimentoList) {
                preparedStatement.setInt(1, provvedimento.getIdProvvedimento());
                preparedStatement.setString(2, provvedimento.getNome());
                preparedStatement.setInt(3, provvedimento.getIdMansione());
                preparedStatement.setInt(4, provvedimento.getIdOggetto());
                preparedStatement.setInt(5, provvedimento.getIdElencoRischi());
                preparedStatement.executeUpdate();
            }
        }
    }
    
    private void popolaTabellaRischi(Connection connection, List<Rischio> rischioList) throws SQLException {
        String insertQuery = "INSERT INTO public.rischi (id_rischio, nome, P, D, R, id_reparto) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            for (Rischio rischio : rischioList) {
                preparedStatement.setInt(1, rischio.getIdRischio());
                preparedStatement.setString(2, rischio.getNome());
                preparedStatement.setInt(3, rischio.getP());
                preparedStatement.setInt(4, rischio.getD());
                preparedStatement.setInt(5, rischio.getR());
                preparedStatement.setInt(6, rischio.getIdReparto());
                preparedStatement.executeUpdate();
            }
        }
    }
    private void popolaTabellaElencoRischi(Connection connection, List<ElencoRischi> elencoRischiList) throws SQLException {
    String insertQuery = "INSERT INTO public.elenco_rischi (id_provvedimento, id_rischio) VALUES (?, ?)";
    try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
        for (ElencoRischi elencoRischi : elencoRischiList) {
            preparedStatement.setInt(1, elencoRischi.getIdProvvedimento());
            preparedStatement.setInt(2, elencoRischi.getIdRischio());
            preparedStatement.executeUpdate();
        }
    }
}

}
