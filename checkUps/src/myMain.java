
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class myMain {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        visualizzaDatiTabellaSocieta();
    }

    public static void visualizzaDatiTabellaSocieta() {
        String connectionUrl = "jdbc:postgresql://localhost:5432/checkups_db";
        String username = "postgres";
        String password = "1234";

        try (Connection connection = DriverManager.getConnection(connectionUrl, username, password)) {
            String query = "SELECT * FROM public.societa";

            try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

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
            }

        } catch (SQLException ex) {
            System.out.println("Errore di connessione al database: " + ex.getMessage());
        }
    }
}
