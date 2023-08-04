package Model.Tabelle;

import Model.interfaces.GeneralTable;

public class Provvedimento extends GeneralTable{
    private int idProvvedimento;
    private String nome;
    private int idMansione;
    private int idOggetto;
    private int idElencoRischi;

    public Provvedimento(int idProvvedimento, String nome, int idMansione, int idOggetto, int idElencoRischi) {
        this.idProvvedimento = idProvvedimento;
        this.nome = nome;
        this.idMansione = idMansione;
        this.idOggetto = idOggetto;
        this.idElencoRischi = idElencoRischi;
    }

    public int getIdProvvedimento() {
        return idProvvedimento;
    }

    public void setIdProvvedimento(int idProvvedimento) {
        this.idProvvedimento = idProvvedimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdMansione() {
        return idMansione;
    }

    public void setIdMansione(int idMansione) {
        this.idMansione = idMansione;
    }

    public int getIdOggetto() {
        return idOggetto;
    }

    public void setIdOggetto(int idOggetto) {
        this.idOggetto = idOggetto;
    }

    public int getIdElencoRischi() {
        return idElencoRischi;
    }

    public void setIdElencoRischi(int idElencoRischi) {
        this.idElencoRischi = idElencoRischi;
    }
}
