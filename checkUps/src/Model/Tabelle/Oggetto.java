package Model.Tabelle;

import Model.interfaces.TableInterface;

public class Oggetto implements TableInterface{
    private int idOggetto;
    private String nome;
    private int idTitolo;

    public Oggetto(int idOggetto, String nome, int idTitolo) {
        this.idOggetto = idOggetto;
        this.nome = nome;
        this.idTitolo = idTitolo;
    }

    public int getIdOggetto() {
        return idOggetto;
    }

    public void setIdOggetto(int idOggetto) {
        this.idOggetto = idOggetto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdTitolo() {
        return idTitolo;
    }

    public void setIdTitolo(int idTitolo) {
        this.idTitolo = idTitolo;
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
