package Controllers;

import java.util.ArrayList;
import java.util.List;

import Tables.Mansione;
import Tables.Oggetto;
import Tables.Provvedimento;
import Tables.Reparto;
import Tables.Rischio;
import Tables.Societa;
import Tables.Titolo;
import Tables.UnitaLocale;

//? Classe che conterra' i metodi per avete oggetti globali da passare tra le classi
public class ClassHelper {

    private static List<Mansione> listMansione = new ArrayList<>();
    private static List<Titolo> listTitolo = new ArrayList<>();
    private static List<Reparto> listReparto = new ArrayList<>();
    private static List<Rischio> listRischio = new ArrayList<>();
    private static List<Societa> listSocieta = new ArrayList<>();
    private static List<Oggetto> listOggetto = new ArrayList<>();
    private static List<Provvedimento> listProvvedimento = new ArrayList<>();
    private static List<UnitaLocale> listUnitaLocale = new ArrayList<>();

    // Metodi di accesso alle liste
    public static List<Mansione> getListMansione() {
        return listMansione;
    }

    public static List<Titolo> getListTitolo() {
        return listTitolo;
    }

    public static List<Reparto> getListReparto() {
        return listReparto;
    }

    public static List<Rischio> getListRischio() {
        return listRischio;
    }

    public static List<Societa> getListSocieta() {
        return listSocieta;
    }

    public static List<Oggetto> getListOggetto() {
        return listOggetto;
    }

    public static List<Provvedimento> getListProvvedimento() {
        return listProvvedimento;
    }

    public static List<UnitaLocale> getListUnitaLocale() {
        return listUnitaLocale;
    }

}
