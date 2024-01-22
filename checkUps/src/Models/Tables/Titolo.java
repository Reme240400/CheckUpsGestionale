package Models.Tables;

import java.util.List;

import Helpers.ClassHelper;

public class Titolo extends TableField {
    private int idReparto;
    private String descrizione;

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

    public List<Oggetto> getOggetti() {
        return ClassHelper.getListOggetto().stream().filter(oggetto -> oggetto.getIdTitolo() == getId()).toList();
    }
}
