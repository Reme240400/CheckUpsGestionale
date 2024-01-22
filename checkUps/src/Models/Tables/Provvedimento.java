package Models.Tables;

public class Provvedimento extends TableField {
    private int idOggetto;
    private String nome;
    private String rischio;
    private String soggettiEsposti;
    private int stimaR;
    private int stimaD;
    private int stimaP;

    public Provvedimento(int idProvvedimento, int idOggetto, String rischio, String nome,
            String soggettiEsposti, int stima_r, int stima_d, int stima_p) {
        super(idProvvedimento);

        this.idOggetto = idOggetto;
        this.nome = nome;
        this.rischio = rischio;
        this.soggettiEsposti = soggettiEsposti;
        this.stimaR = stima_r;
        this.stimaD = stima_d;
        this.stimaP = stima_p;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
        return rischio;
    }

    public void setRischio(String rischio) {
        this.rischio = rischio;
    }

    public int getStimaR() {
        return stimaR;
    }

    public void setStimaR(int stima_r) {
        this.stimaR = stima_r;
    }

    public int getStimaD() {
        return stimaD;
    }

    public void setStimaD(int stima_d) {
        this.stimaD = stima_d;
    }

    public int getStimaP() {
        return stimaP;
    }

    public void setStimaP(int stima_p) {
        this.stimaP = stima_p;
    }

}
