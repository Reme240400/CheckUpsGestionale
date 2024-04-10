package Models.others;

import Models.Tables.Provvedimento;
import Models.Tables.Societa;

public class ScadenzaElement {
    private Societa societa;
    private Provvedimento provvedimento;

    public ScadenzaElement(Societa soc, Provvedimento prov) {
        this.societa = soc;
        this.provvedimento = prov;
    }

    public Societa getSocieta() {
        return societa;
    }

    public Provvedimento getProvvedimento() {
        return provvedimento;
    }
}
