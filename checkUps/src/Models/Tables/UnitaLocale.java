package Models.Tables;

import java.io.Serializable;

import Helpers.ClassHelper;

public class UnitaLocale extends TableData implements Serializable {
    private int idSocieta;
    private String nome;
    private String indirizzo;
    private String localita;
    private String provincia;
    private String telefono;
    private String email;

    public UnitaLocale(int idUnitaLocale, int idSocieta, String nome, String indirizzo, String localita,
            String provincia,
            String telefono, String email) {
        super(idUnitaLocale, "unita_locali", "id_unita_locale");
        // this.idUnitaLocale = idUnitaLocale;
        this.idSocieta = idSocieta;
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.localita = localita;
        this.provincia = provincia;
        this.telefono = telefono;
        this.email = email;
    }

    public int getIdSocieta() {
        return idSocieta;
    }

    public void setIdSocieta(int idSocieta) {
        this.idSocieta = idSocieta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getLocalita() {
        return localita;
    }

    public void setLocalita(String localita) {
        this.localita = localita.toUpperCase();
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia.toUpperCase();
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public void selfRemoveFromList() {
        ClassHelper.getListUnitaLocale().remove(this);
    }
}
