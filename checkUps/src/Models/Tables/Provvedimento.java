package Models.Tables;

import java.io.Serializable;

public class Provvedimento extends TablesId implements Serializable {

    private String nome;
    private int idOggetto;
    private String rischio;
    private String soggettiEsposti;
    private int stima_r;
    private int stima_d;
    private int stima_p;

    public Provvedimento(int idProvvedimento, int idOggetto, String rischio, String nome,
            String soggettiEsposti, int stima_r, int stima_d, int stima_p) {
        super(idProvvedimento);

        this.nome = nome;
        this.idOggetto = idOggetto;
        this.rischio = rischio;
        this.soggettiEsposti = soggettiEsposti;
        this.stima_r = stima_r;
        this.stima_d = stima_d;
        this.stima_p = stima_p;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdOggetto() {
        return idOggetto;
    }

    public void setIdOggetto(int idOggetto) {
        this.idOggetto = idOggetto;
    }

    public String getSoggettiEsposti() {
        return soggettiEsposti;
    }

    public void setSoggettiEsposti(String soggettiEsposti) {
        this.soggettiEsposti = soggettiEsposti;
    }

    public String getRischio() {
        return rischio;
    }

    public void setRischio(String rischio) {
        this.rischio = rischio;
    }

    public int getStimaR() {
        return stima_r;
    }

    public void setStimaR(int stima_r) {
        this.stima_r = stima_r;
    }

    public int getStimaD() {
        return stima_d;
    }

    public void setStimaD(int stima_d) {
        this.stima_d = stima_d;
    }

    public int getStimaP() {
        return stima_p;
    }

    public void setStimaP(int stima_p) {
        this.stima_p = stima_p;
    }

}
