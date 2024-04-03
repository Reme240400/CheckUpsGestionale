package Models.Tables;

import java.io.Serializable;
import java.util.Optional;

import javafx.scene.image.Image;

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
    private Optional<Image> logoImage;

    public Societa(int idSocieta, String nome, String indirizzo, String localita, String provincia, String telefono,
            String descrizione, String pIva, String codFiscale, String bancaAppoggio, String codAteco,
            Optional<Image> logo) {
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
        this.logoImage = logo;
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

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getBancaAppoggio() {
        return bancaAppoggio;
    }

    public void setBancaAppoggio(String bancaAppoggio) {
        this.bancaAppoggio = bancaAppoggio;
    }

    public String getCodiceAteco() {
        return codiceAteco;
    }

    public void setCodiceAteco(String codiceAteco) {
        this.codiceAteco = codiceAteco;
    }

    public Image getLogoImage() {
        return this.logoImage.orElse(null);
    }

    public boolean hasImage() {
        return this.logoImage.isPresent();
    }
}
