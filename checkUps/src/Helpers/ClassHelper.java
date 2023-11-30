package Helpers;

import java.util.ArrayList;
import java.util.List;

import Models.Tables.Mansione;
import Models.Tables.Oggetto;
import Models.Tables.Provvedimento;
import Models.Tables.Reparto;
import Models.Tables.Rischio;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;

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

    public static void svuotaListaUnitaLocali() {
        listUnitaLocale.clear();
    }

    public static void svuotaListaReparti() {
        listReparto.clear();
    }

    public static void svuotaListaRischi() {
        listRischio.clear();
    }

    public static void svuotaListaMansioni() {
        listMansione.clear();
    }

    public static void svuotaListaOggetti() {
        listOggetto.clear();
    }

    public static void svuotaListaProvvedimenti() {
        listProvvedimento.clear();
    }

    public static void svuotaListaSocieta() {
        listSocieta.clear();
    }

    public static void svuotaListaTitoli() {
        listTitolo.clear();
    }

}
