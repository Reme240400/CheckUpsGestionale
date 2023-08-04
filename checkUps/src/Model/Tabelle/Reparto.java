package Model.Tabelle;

import Model.interfaces.GeneralTable;

public class Reparto extends GeneralTable{
    private int idReparto;
    private int idUnitaLocale;
    private String nome;
    private String descrizione;

    public Reparto(int idReparto, int idUnitaLocale, String nome, String descrizione) {
        this.idReparto = idReparto;
        this.idUnitaLocale = idUnitaLocale;
        this.nome = nome;
        this.descrizione = descrizione;
    }

    //Metodo per modificare un campo
    public void modificaCampo(String campo, Object nuovoValore) {
        switch (campo.toLowerCase()) {
            case "idreparto":
                if (nuovoValore instanceof Integer) {
                    this.idReparto = (int) nuovoValore;
                } else {
                    throw new IllegalArgumentException("Il valore per il campo 'idReparto' deve essere di tipo Integer.");
                }
                break;

            case "idunitalocale":
                if (nuovoValore instanceof Integer) {
                    this.idUnitaLocale = (int) nuovoValore;
                } else {
                    throw new IllegalArgumentException("Il valore per il campo 'idUnitaLocale' deve essere di tipo Integer.");
                }
                break;

            case "nome":
                if (nuovoValore instanceof String) {
                    this.nome = (String) nuovoValore;
                } else {
                    throw new IllegalArgumentException("Il valore per il campo 'nome' deve essere di tipo String.");
                }
                break;

            case "descrizione":
                if (nuovoValore instanceof String) {
                    this.descrizione = (String) nuovoValore;
                } else {
                    throw new IllegalArgumentException("Il valore per il campo 'descrizione' deve essere di tipo String.");
                }
                break;

            default:
                throw new IllegalArgumentException("Campo non valido: " + campo);
        }
    }

    public int getIdReparto() {
        return idReparto;
    }

    public void setIdReparto(int idReparto) {
        this.idReparto = idReparto;
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
}
