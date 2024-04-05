package Models.Tables;

public abstract class TablesId {
    
    private int id;

    public TablesId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
