package Models.Tables;

import java.io.Serializable;

public class Titolo extends TablesId implements Serializable{

    private String descrizione;
    private int idReparto;

    public Titolo(int idTitolo, int idReparto, String descrizione) {
        super(idTitolo);
        
        this.descrizione = descrizione;
        this.idReparto = idReparto;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getIdReparto() {
        return idReparto;
    }

    public void setIdReparto(int idReparto) {
        this.idReparto = idReparto;
    }

}
