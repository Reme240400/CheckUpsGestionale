package Models.Tables;

import java.io.Serializable;

public abstract class TableField implements Serializable {
    private int id;

    public TableField(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
