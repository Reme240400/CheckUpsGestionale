package Models.Tables;

import java.io.Serializable;

public class Oggetto extends TablesId implements Serializable{

    private String nome;
    private int idTitolo;

    public Oggetto(int idOggetto, String nome, int idTitolo) {
        super(idOggetto);

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

}
