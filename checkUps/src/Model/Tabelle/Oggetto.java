package Model.Tabelle;

import Model.interfaces.GeneralTable;

public class Oggetto extends GeneralTable{
    private int idOggetto;
    private String nome;
    private int idTitolo;

    public Oggetto(int idOggetto, String nome, int idTitolo) {
        this.idOggetto = idOggetto;
        this.nome = nome;
        this.idTitolo = idTitolo;
    }

    public int getIdOggetto() {
        return idOggetto;
    }

    public void setIdOggetto(int idOggetto) {
        this.idOggetto = idOggetto;
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
}
