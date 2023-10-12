package Models.Tables;


public class Societa{
    
    private int idSocieta;
    private String indirizzo;
    private String localita;
    private String provincia;
    private long telefono;
    private String descrizione;
    private String nome;

    public Societa(int idSocieta, String indirizzo, String localita, String provincia, long telefono, String descrizione, String nome) {
        this.idSocieta = idSocieta;
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
    
    public long getTelefono() {
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
        this.telefono = Long.parseLong(telefono);
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

