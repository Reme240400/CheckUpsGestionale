package Models.Tables;

import java.io.Serializable;

public class Societa extends TablesId implements Serializable {

    private String nome;
    private String indirizzo;
    private String localita;
    private String provincia;
    private String telefono;
    private String descrizione;
    private String partitaIva;
    private String codiceFiscale;
    private String bancaAppoggio;
    private String codiceAteco;
    private String logoUrl;

    public Societa(int idSocieta, String nome, String indirizzo, String localita, String provincia, String telefono,
            String descrizione, String pIva, String codFiscale, String bancaAppoggio, String codAteco, String logoUrl) {
        super(idSocieta);
        this.indirizzo = indirizzo;
        this.localita = localita;
        this.provincia = provincia;
        this.telefono = telefono;
        this.descrizione = descrizione;
        this.nome = nome;
        this.partitaIva = pIva;
        this.codiceFiscale = codFiscale;
        this.bancaAppoggio = bancaAppoggio;
        this.codiceAteco = codAteco;
        this.logoUrl = logoUrl;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getLocalita() {
        return localita;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getNome() {
        return nome;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public String getBancaAppoggio() {
        return bancaAppoggio;
    }

    public String getCodiceAteco() {
        return codiceAteco;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public void setLocalita(String localita) {
        this.localita = localita.toUpperCase();
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia.toUpperCase();
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDescrizione(String descrizione) {
        if (descrizione.length() > 255) {
            this.descrizione = descrizione.substring(0, 255);
        } else {
            this.descrizione = descrizione;
        }
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
