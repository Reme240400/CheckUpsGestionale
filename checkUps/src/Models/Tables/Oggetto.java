package Models.Tables;

import java.io.Serializable;

import Helpers.ClassHelper;

public class Oggetto extends TableData implements Serializable {
    private String nome;
    private int idTitolo;

    public Oggetto(int idOggetto, String nome, int idTitolo) {
        super(idOggetto, "oggetti", "id_titolo");

        this.nome = nome;
        this.idTitolo = idTitolo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdTitolo() {
        return idTitolo;
    }

    public void setIdTitolo(int idTitolo) {
        this.idTitolo = idTitolo;
    }

    public void selfRemoveFromList() {
        ClassHelper.getListOggetto().remove(this);
    }
}
