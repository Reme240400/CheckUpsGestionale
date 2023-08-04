package Model.Tabelle;

public class ElencoRischi {
    private int idProvvedimento;
    private int idRischio;

    public ElencoRischi(int idProvvedimento, int idRischio) {
        this.idProvvedimento = idProvvedimento;
        this.idRischio = idRischio;
    }

    public int getIdProvvedimento() {
        return idProvvedimento;
    }

    public void setIdProvvedimento(int idProvvedimento) {
        this.idProvvedimento = idProvvedimento;
    }

    public int getIdRischio() {
        return idRischio;
    }

    public void setIdRischio(int idRischio) {
        this.idRischio = idRischio;
    }
}
