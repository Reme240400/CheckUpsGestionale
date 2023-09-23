package Controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Tables.Mansione;
import Tables.Oggetto;
import Tables.Provvedimento;
import Tables.Reparto;
import Tables.Rischio;
import Tables.Societa;
import Tables.Titolo;
import Tables.UnitaLocale;
import sql.ControllerSql;

public class ModelController extends ClassHelper{
    
    public static void popolaListaSocieta() {
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
                        Controller.popolaLista(societa);
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
        try (Connection connection = ControllerSql.connessioneDb()){
            if (connection != null) {
                try (Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM public.mansioni")) {

                    while (resultSet.next()) {
                        int idMansione = resultSet.getInt("id_mansione");
                        String nome = resultSet.getString("nome");
                        String responsabile = resultSet.getString("responsabile");

                        Mansione mansione = new Mansione(idMansione, nome, responsabile);
                        Controller.popolaLista(mansione);
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void popolaListaTitoli() {
        try(Connection connection = ControllerSql.connessioneDb()){
            if (connection != null) {
                try (Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM public.titoli")) {

                    while (resultSet.next()) {
                        int idTitolo = resultSet.getInt("id_titolo");
                        String descrizione = resultSet.getString("descrizione");
                        int idReparto = resultSet.getInt("id_reparto");

                        Titolo titolo = new Titolo(idTitolo, descrizione, idReparto);
                        Controller.popolaLista(titolo);
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
        try(Connection connection = ControllerSql.connessioneDb()){
        if (connection != null) {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM public.reparti")) {

                while (resultSet.next()) {
                    int idReparto = resultSet.getInt("id_reparto");
                    int idUnitaLocale = resultSet.getInt("id_unita_locale");
                    String nome = resultSet.getString("nome");
                    String descrizione = resultSet.getString("descrizione");

                    Reparto reparto = new Reparto(idReparto, idUnitaLocale, nome, descrizione);
                    Controller.popolaLista(reparto);
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
        try(Connection connection = ControllerSql.connessioneDb()){
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
                    Controller.popolaLista(rischio);
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
        try(Connection connection = ControllerSql.connessioneDb()){
        if (connection != null) {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM public.oggetti")) {

                while (resultSet.next()) {
                    int idOggetto = resultSet.getInt("id_oggetto");
                    String nome = resultSet.getString("nome");
                    int idTitolo = resultSet.getInt("id_titolo");

                    Oggetto oggetto = new Oggetto(idOggetto, nome, idTitolo);
                    Controller.popolaLista(oggetto);
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
        try(Connection connection = ControllerSql.connessioneDb()){
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
                    Controller.popolaLista(provvedimento);
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
        try(Connection connection = ControllerSql.connessioneDb()){
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
                    

                    UnitaLocale unitaLocale = new UnitaLocale(idUnitaLocale, provincia ,nome, indirizzo, localita, idSocieta);
                    Controller.popolaLista(unitaLocale);
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
}