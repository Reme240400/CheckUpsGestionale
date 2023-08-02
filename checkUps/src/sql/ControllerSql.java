package sql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class ControllerSql {

    private final String connectionUrl = "jdbc:postgresql://localhost:5432/checkups_db";
    private final String username = "postgres";
    private final String password = "postgres";

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

    public Connection connessioneDb() {
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
    

}
