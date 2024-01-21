package Models.Tables;

import java.io.Serializable;
import java.util.List;

import Helpers.ClassHelper;

public class Reparto extends TablesId implements Serializable {

    private int idUnitaLocale;
    private String nome;
    private String data;

    public Reparto(int idReparto, int idUnitaLocale, String nome, String data) {
        super(idReparto);

        this.idUnitaLocale = idUnitaLocale;
        this.nome = nome;
        this.data = data;
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

    public String getData() {
        return data;
    }

    public void setData(String descrizione) {
        this.data = descrizione;
    }

    public List<Titolo> getTitoli() {
        return ClassHelper.getListTitolo().stream().filter(titolo -> titolo.getIdReparto() == getId()).toList();
    }
}
