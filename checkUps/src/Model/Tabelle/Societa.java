package Model.Tabelle;

import Model.interfaces.TableInterface;

public class Societa implements TableInterface{
    
    private int idSocieta;
    private String indirizzo;
    private String localita;
    private String provincia;
    private long telefono;
    private String descrizione;
    private String ente;

    public Societa(int idSocieta, String indirizzo, String localita, String provincia, String telefono, String descrizione, String ente) {
        this.idSocieta = idSocieta;
        this.indirizzo = indirizzo;
        this.localita = localita;
        this.provincia = provincia;
        this.telefono = Long.parseLong(telefono);
        this.descrizione = descrizione;
        this.ente = ente;
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

    public String getEnte() {
        return ente;
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

    public void setEnte(String ente) {
        this.ente = ente;
    }

    @Override
    public void modificaCampo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modificaCampo'");
    }

    @Override
    public void inserisciCampo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'inserisciCampo'");
    }

    @Override
    public void eliminaCampo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminaCampo'");
    }

    @Override
    public void visualizzaCampo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visualizzaCampo'");
    }


}

