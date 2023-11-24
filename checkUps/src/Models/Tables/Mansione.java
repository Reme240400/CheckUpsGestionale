package Models.Tables;

import java.io.Serializable;

public class Mansione extends TablesId implements Serializable{

    private String nome;
    private String responsabile;

    public Mansione(int idMansione, String nome, String responsabile) {
        super(idMansione);

        this.nome = nome;
        this.responsabile = responsabile;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getResponsabile() {
        return responsabile;
    }

    public void setResponsabile(String responsabile) {
        this.responsabile = responsabile;
    }

}
