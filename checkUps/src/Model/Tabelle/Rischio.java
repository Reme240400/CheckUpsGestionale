package Model.Tabelle;


public class Rischio {
    private int idRischio;
    private String nome;
    private int p;
    private int d;
    private int r;
    private int idReparto;

    public Rischio(int idRischio, String nome, int p, int d, int r, int idReparto) {
        this.idRischio = idRischio;
        this.nome = nome;
        this.p = p;
        this.d = d;
        this.r = r;
        this.idReparto = idReparto;
    }

    public int getIdRischio() {
        return idRischio;
    }

    public void setIdRischio(int idRischio) {
        this.idRischio = idRischio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getIdReparto() {
        return idReparto;
    }

    public void setIdReparto(int idReparto) {
        this.idReparto = idReparto;
    }

}
