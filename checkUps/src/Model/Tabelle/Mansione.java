package Model.Tabelle;

import Model.interfaces.GeneralTable;

public class Mansione extends GeneralTable{
    private int idMansione;
    private String nome;
    private String responsabile;

    public Mansione(int idMansione, String nome, String responsabile) {
        this.idMansione = idMansione;
        this.nome = nome;
        this.responsabile = responsabile;
    }

    public int getIdMansione() {
        return idMansione;
    }

    public void setIdMansione(int idMansione) {
        this.idMansione = idMansione;
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
