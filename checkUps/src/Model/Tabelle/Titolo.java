package Model.Tabelle;


public class Titolo {
    private int idTitolo;
    private String descrizione;
    private int idReparto;

    public Titolo(int idTitolo, String descrizione, int idReparto) {
        this.idTitolo = idTitolo;
        this.descrizione = descrizione;
        this.idReparto = idReparto;
    }

    public int getIdTitolo() {
        return idTitolo;
    }

    public void setIdTitolo(int idTitolo) {
        this.idTitolo = idTitolo;
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
