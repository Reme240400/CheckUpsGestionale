package Controller;

import java.util.ArrayList;
import java.util.List;

import Model.Tabelle.Mansione;
import Model.Tabelle.Oggetto;
import Model.Tabelle.Provvedimento;
import Model.Tabelle.Reparto;
import Model.Tabelle.Rischio;
import Model.Tabelle.Societa;
import Model.Tabelle.Titolo;
import Model.Tabelle.UnitaLocale;

// ? Classe che conterra' i metodi per avete oggetti globali da passare tra le classi
public class ClassHelper {
    
    public static List<Mansione> listMansione = new ArrayList<>();
    public static List<Titolo> listTitolo = new ArrayList<>();
    public static List<Reparto> listReparto = new ArrayList<>();
    public static List<Rischio> listRischio = new ArrayList<>();
    public static List<Societa> listSocieta = new ArrayList<>();
    public static List<Oggetto> listOggetto = new ArrayList<>();
    public static List<Provvedimento> listProvvedimento = new ArrayList<>();
    public static List<UnitaLocale> listUnitaLocale = new ArrayList<>();

}
