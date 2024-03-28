package Models.imports;

import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;

public class ImportTitoloElement {
    private Titolo titolo;
    private Reparto reparto;
    private UnitaLocale unitaLocale;
    private Societa societa;

    public ImportTitoloElement(Titolo titolo, Reparto reparto, UnitaLocale unitaLocale, Societa societa) {
        this.titolo = titolo;
        this.reparto = reparto;
        this.unitaLocale = unitaLocale;
        this.societa = societa;
    }

    public Titolo getTitolo() {
        return titolo;
    }

    public Reparto getReparto() {
        return reparto;
    }

    public UnitaLocale getUnitaLocale() {
        return unitaLocale;
    }

    public Societa getSocieta() {
        return societa;
    }
}
