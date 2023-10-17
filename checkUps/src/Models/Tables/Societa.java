package Models.Tables;


public class Societa{
    
    private int idSocieta;
    private String indirizzo;
    private String localita;
    private String provincia;
    private String telefono;
    private String descrizione;
    private String nome;

    public Societa( String indirizzo, String localita, String provincia, String telefono, String descrizione, String nome) {
        //this.idSocieta = idSocieta;
        this.indirizzo = indirizzo;
        this.localita = localita;
        this.provincia = provincia;
        this.telefono = telefono;
        this.descrizione = descrizione;
        this.nome = nome;
    }

    public int getIdSocieta() {
        return idSocieta;
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

    public void setIdSocieta(int idSocieta) {
        this.idSocieta = idSocieta;
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

