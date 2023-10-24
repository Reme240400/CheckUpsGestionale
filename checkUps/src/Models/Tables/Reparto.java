package Models.Tables;

import java.io.Serializable;

public class Reparto implements Serializable{
    private int idReparto;
    private int idUnitaLocale;
    private String nome;
    private String descrizione;

    public Reparto(int idReparto, int idUnitaLocale, String nome, String descrizione) {
        this.idReparto = idReparto;
        this.idUnitaLocale = idUnitaLocale;
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public int getIdReparto() {
        return idReparto;
    }

    public void setIdReparto(int idReparto) {
        this.idReparto = idReparto;
    }

    public int getIdUnitaLocale() {
        return idUnitaLocale;
    }

    public void setIdUnitaLocale(int idUnitaLocale) {
        this.idUnitaLocale = idUnitaLocale;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

}
