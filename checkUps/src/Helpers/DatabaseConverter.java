package Helpers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import com.healthmarketscience.jackcess.*;


public class DatabaseConverter {

    private static final String connectionUrl = "jdbc:postgresql://localhost:5432/checkups_db";
    private static final String username = "postgres";
    private static final String password = "postgres";

    public static void main(String[] args) throws SQLException, IOException {
        try (Connection connection = DriverManager.getConnection(connectionUrl, username, password)) {
            try (Statement statement = connection.createStatement()) {
                String queryMansione = "SELECT * FROM Mansioni";
                try (ResultSet resultSetMansione = statement.executeQuery(queryMansione)) {
                    createAccessDatabase(resultSetMansione, "Mansione.accdb");
                }

                // Riempi la tabella Oggetto
                String queryOggetto = "SELECT * FROM Oggetti";
                try (ResultSet resultSetOggetto = statement.executeQuery(queryOggetto)) {
                    createAccessDatabase(resultSetOggetto, "Oggetto.accdb");
                }

                // Riempi la tabella Provvedimento
                String queryProvvedimento = "SELECT * FROM Provvedimenti";
                try (ResultSet resultSetProvvedimento = statement.executeQuery(queryProvvedimento)) {
                    createAccessDatabase(resultSetProvvedimento, "Provvedimento.accdb");
                }

                // Riempi la tabella Reparto
                String queryReparto = "SELECT * FROM Reparti";
                try (ResultSet resultSetReparto = statement.executeQuery(queryReparto)) {
                    createAccessDatabase(resultSetReparto, "Reparto.accdb");
                }

                // Riempi la tabella Rischio
                String queryRischio = "SELECT * FROM Rischi";
                try (ResultSet resultSetRischio = statement.executeQuery(queryRischio)) {
                    createAccessDatabase(resultSetRischio, "Rischio.accdb");
                }

                // Riempi la tabella Societa
                String querySocieta = "SELECT * FROM Societa";
                try (ResultSet resultSetSocieta = statement.executeQuery(querySocieta)) {
                    createAccessDatabase(resultSetSocieta, "Societa.accdb");
                }

                // Riempi la tabella Titolo
                String queryTitolo = "SELECT * FROM Titolo";
                try (ResultSet resultSetTitolo = statement.executeQuery(queryTitolo)) {
                    createAccessDatabase(resultSetTitolo, "Titolo.accdb");
                }

                // Riempi la tabella UnitaLocale
                String queryUnitaLocale = "SELECT * FROM UnitaLocale";
                try (ResultSet resultSetUnitaLocale = statement.executeQuery(queryUnitaLocale)) {
                    createAccessDatabase(resultSetUnitaLocale, "UnitaLocale.accdb");
                }
            }
        }
    }

    private static void createAccessDatabase(ResultSet resultSet, String accessFileName) throws IOException {
        try (Database accessDB = DatabaseBuilder.create(Database.FileFormat.V2010, new File(accessFileName))) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            TableBuilder tableBuilder = new TableBuilder(metaData.getTableName(1));

            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                int columnType = metaData.getColumnType(i);
                DataType dataType = getJackcessDataType(columnType);
                tableBuilder.addColumn(new ColumnBuilder(columnName, dataType));
            }

            Table table = tableBuilder.toTable(accessDB);

            while (resultSet.next()) {
                List<Object> rowData = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    rowData.add(value);
                }
                table.addRow(rowData.toArray());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static DataType getJackcessDataType(int sqlType) {
        // Mappa i tipi di dati SQL a quelli di Jackcess
        switch (sqlType) {
            case Types.INTEGER:
                return DataType.LONG;
            case Types.VARCHAR:
            case Types.NVARCHAR:
                return DataType.TEXT;
            // Aggiungi altri tipi di dati secondo necessità
            default:
                return DataType.TEXT;
        }
    }
    
}
