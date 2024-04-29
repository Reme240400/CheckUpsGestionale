package Models.Tables;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Optional;

import Helpers.ClassHelper;

public class Reparto extends TableData implements Serializable {

    private int idUnitaLocale;
    private String nome;
    private String descrizione;
    private String revisione;
    private Optional<LocalDate> data;

    public Reparto(int idReparto, int idUnitaLocale, String nome, String descrizione, String revisione,
            Optional<LocalDate> data) {
        super(idReparto, "reparti", "id_reparto");

        this.idUnitaLocale = idUnitaLocale;
        this.nome = nome;
        this.descrizione = descrizione;
        this.revisione = revisione;
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

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getRevisione() {
        return this.revisione;
    }

    public void setRevisione(String revisione) {
        this.revisione = revisione;
    }

    public Optional<LocalDate> getData() {
        return this.data;
    }

    public void setData(Optional<LocalDate> data) {
        this.data = data;
    }

    @Override
    public void selfRemoveFromList() {
        ClassHelper.getListReparto().remove(this);
    }
}
