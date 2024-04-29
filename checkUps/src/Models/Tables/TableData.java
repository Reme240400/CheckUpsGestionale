package Models.Tables;

public abstract class TableData {
    private int id;
    private String tableName;
    private String primaryKey;

    public TableData(int id, String tableName, String primaryKey) {
        this.id = id;
        this.tableName = tableName;
        this.primaryKey = primaryKey;
    }

    public int getId() {
        return this.id;
    }

    public String getTableName() {
        return tableName;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public abstract void selfRemoveFromList();
}
