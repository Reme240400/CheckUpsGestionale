package Model.Tabelle;

import Model.interfaces.TableInterface;

public class Titolo implements TableInterface{
    private int idTitolo;
    private String descrizione;
    private int idReparto;

    public Titolo(int idTitolo, String descrizione, int idReparto) {
        this.idTitolo = idTitolo;
        this.descrizione = descrizione;
        this.idReparto = idReparto;
    }

    public int getIdTitolo() {
        return idTitolo;
    }

    public void setIdTitolo(int idTitolo) {
        this.idTitolo = idTitolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getIdReparto() {
        return idReparto;
    }

    public void setIdReparto(int idReparto) {
        this.idReparto = idReparto;
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
