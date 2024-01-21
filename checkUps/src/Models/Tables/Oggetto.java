package Models.Tables;

import java.io.Serializable;
import java.util.List;

import Helpers.ClassHelper;

public class Oggetto extends TablesId implements Serializable {

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

    public List<Provvedimento> getProvvedimenti() {
        return ClassHelper.getListProvvedimento().stream().filter(provv -> provv.getIdOggetto() == getId()).toList();
    }

}
