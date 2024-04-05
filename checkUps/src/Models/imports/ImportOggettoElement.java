package Models.imports;

import Models.Tables.Oggetto;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;

public class ImportOggettoElement {
    
    private Oggetto oggetto;
    private Titolo titolo;
    private Reparto reparto;
    private UnitaLocale unitaLocale;
    private Societa societa;
    
    public ImportOggettoElement(Oggetto oggetto, Titolo titolo, Reparto reparto, UnitaLocale unitaLocale, Societa societa) {
        this.oggetto = oggetto;
        this.titolo = titolo;
        this.reparto = reparto;
        this.unitaLocale = unitaLocale;
        this.societa = societa;
    }

    public Oggetto getOggetto() {
        return oggetto;
    }

    public Reparto getReparto() {
        return reparto;
    }

    public Titolo getTitolo() {
        return titolo;
    }

    public UnitaLocale getUnitaLocale() {
        return unitaLocale;
    }

    public Societa getSocieta() {
        return societa;
    }

}
