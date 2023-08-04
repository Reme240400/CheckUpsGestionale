package Model;

import Model.interfaces.GeneralTable;

public class UnitaLocale extends GeneralTable{
    private int idUnitaLocale;
    private int idSocieta;
    private String nome;
    private String indirizzo;
    private String localita;
    private String provincia;

    public UnitaLocale(int idUnitaLocale, int idSocieta, String nome, String indirizzo, String localita, String provincia) {
        this.idUnitaLocale = idUnitaLocale;
        this.idSocieta = idSocieta;
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.localita = localita;
        this.provincia = provincia;
    }

    public int getIdUnitaLocale() {
        return idUnitaLocale;
    }

    public void setIdUnitaLocale(int idUnitaLocale) {
        this.idUnitaLocale = idUnitaLocale;
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
}
