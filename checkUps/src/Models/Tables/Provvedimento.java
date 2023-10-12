package Models.Tables;

public class Provvedimento {
    private int idProvvedimento;
    private String nome;
    private int idMansione;
    private int idOggetto;
    private String Rischio;
    private String soggettiEsposti;
    private int stima;

    public Provvedimento(int idProvvedimento, String nome, int idMansione, int idOggetto, String Rischio,
            String soggettiEsposti, int stima) {
        this.idProvvedimento = idProvvedimento;
        this.nome = nome;
        this.idMansione = idMansione;
        this.idOggetto = idOggetto;
        this.Rischio = Rischio;
        this.soggettiEsposti = soggettiEsposti;
        this.stima = stima;
    }

    public int getIdProvvedimento() {
        return idProvvedimento;
    }

    public void setIdProvvedimento(int idProvvedimento) {
        this.idProvvedimento = idProvvedimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdMansione() {
        return idMansione;
    }

    public void setIdMansione(int idMansione) {
        this.idMansione = idMansione;
    }

    public int getIdOggetto() {
        return idOggetto;
    }

    public void setIdOggetto(int idOggetto) {
        this.idOggetto = idOggetto;
    }

    public String getSoggettiEsposti() {
        return soggettiEsposti;
    }

    public void setSoggettiEsposti(String soggettiEsposti) {
        this.soggettiEsposti = soggettiEsposti;
    }

    public String getRischio() {
        return Rischio;
    }

    public void setRischio(String rischio) {
        this.Rischio = rischio;
    }
    public int getStima() {
        return stima;
    }

    public void setStima(int stima) {
        this.stima = stima;
    }

}
