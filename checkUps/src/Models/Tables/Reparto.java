package Models.Tables;

import java.io.Serializable;

public class Reparto extends TablesId implements Serializable{

    private int idUnitaLocale;
    private String nome;
    private String descrizione;

    public Reparto(int idReparto, int idUnitaLocale, String nome, String descrizione) {
        super(idReparto);

        this.idUnitaLocale = idUnitaLocale;
        this.nome = nome;
        this.descrizione = descrizione;
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
