package Model;

import Controller.ClassHelper;
import Model.Tabelle.Mansione;
import Model.Tabelle.Oggetto;
import Model.Tabelle.Provvedimento;
import Model.Tabelle.Reparto;
import Model.Tabelle.Rischio;
import Model.Tabelle.Societa;
import Model.Tabelle.Titolo;
import Model.Tabelle.UnitaLocale;

public class ModelController extends ClassHelper{
    
    public void popolaLista(Object obj) {
        
        switch (obj.getClass().getSimpleName()) {

        case "Mansione":
            listMansione.add((Mansione) obj);
            break;
        case "Titolo":
            listTitolo.add((Titolo) obj);
            break;
        case "Reparto":
            listReparto.add((Reparto) obj);
            break;
        case "Rischio":
            listRischio.add((Rischio) obj);
            break;
        case "Societa":
            listSocieta.add((Societa) obj);
            break;
        case "Oggetto":
            listOggetto.add((Oggetto) obj);
            break;
        case "Provvedimento":
            listProvvedimento.add((Provvedimento) obj);
            break;
        case "UnitaLocale":
            listUnitaLocale.add((UnitaLocale) obj);
            break;
        default:
            throw new IllegalArgumentException("Unexpected value: " + obj.getClass().getSimpleName());
        }
    }

    public void rimuoviDaLista(Object obj) {
        
        switch (obj.getClass().getSimpleName()) {

        case "Mansione":
            listMansione.remove((Mansione) obj);
            break;
        case "Titolo":
            listTitolo.remove((Titolo) obj);
            break;
        case "Reparto":
            listReparto.remove((Reparto) obj);
            break;
        case "Rischio":
            listRischio.remove((Rischio) obj);
            break;
        case "Societa":
            listSocieta.remove((Societa) obj);
            break;
        case "Oggetto":
            listOggetto.remove((Oggetto) obj);
            break;
        case "Provvedimento":
            listProvvedimento.remove((Provvedimento) obj);
            break;
        case "UnitaLocale":
            listUnitaLocale.remove((UnitaLocale) obj);
            break;
        default:
            throw new IllegalArgumentException("Unexpected value: " + obj.getClass().getSimpleName());
        }
    }

    public void modificaCampo(Object obj, String campo, String valore) {
        
        switch (obj.getClass().getSimpleName()) {

        case "Mansione":
            switch (campo) {
                case "idMansione":
                    ((Mansione) obj).setIdMansione(Integer.parseInt(valore));
                    break;
                case "nome":
                    ((Mansione) obj).setNome(valore);
                    break;
                case "responsabile":
                    ((Mansione) obj).setResponsabile(valore);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected value: " + campo);
            }
        break;

        case "Titolo":
            switch (campo) {
                case "idTitolo":
                    ((Titolo) obj).setIdTitolo(Integer.parseInt(valore));
                    break;
                case "idReparto":
                    ((Titolo) obj).setIdReparto(Integer.parseInt(valore));
                    break;
                case "descrizione":
                    ((Titolo) obj).setDescrizione(valore);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected value: " + campo);
            }
        break;

        case "Reparto":
            switch (campo) {
                case "idReparto":
                    ((Reparto) obj).setIdReparto(Integer.parseInt(valore));
                    break;
                case "nome":
                    ((Reparto) obj).setNome(valore);
                    break;
                case "descrizione":
                    ((Reparto) obj).setDescrizione(valore);
                    break;
                case "idUnitaLocale":
                    ((Reparto) obj).setIdUnitaLocale(Integer.parseInt(valore));
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected value: " + campo);
            }
        break;

        case "Rischio":
            switch (campo) {
                case "idRischio":
                    ((Rischio) obj).setIdRischio(Integer.parseInt(valore));
                    break;
                case "nome":
                    ((Rischio) obj).setNome(valore);
                    break;
                case "p":
                    ((Rischio) obj).setP(Integer.parseInt(valore));
                    break;
                case "d":
                    ((Rischio) obj).setD(Integer.parseInt(valore));
                    break;
                case "idReparto":
                    ((Rischio) obj).setIdReparto(Integer.parseInt(valore));
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected value: " + campo);
            }
        break;

        case "Societa":
            switch (campo) {
                case "idSocieta":
                    ((Societa) obj).setIdSocieta(Integer.parseInt(valore));
                    break;
                case "indirizzo":
                    ((Societa) obj).setIndirizzo(valore);
                    break;
                case "localita":
                    ((Societa) obj).setLocalita(valore);
                    break;
                case "provincia":
                    ((Societa) obj).setProvincia(valore);
                    break;
                case "telefono":
                    ((Societa) obj).setTelefono(valore);
                    break;
                case "descrizione":
                    ((Societa) obj).setDescrizione(valore);
                    break;
                case "ente":
                    ((Societa) obj).setEnte(valore);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected value: " + campo);
            }
        break;

        case "Oggetto":
            switch (campo) {
                case "idOggetto":
                    ((Oggetto) obj).setIdOggetto(Integer.parseInt(valore));
                    break;
                case "nome":
                    ((Oggetto) obj).setNome(valore);
                    break;
                case "idTitolo":
                    ((Oggetto) obj).setIdTitolo(Integer.parseInt(valore));
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected value: " + campo);
            }
        break;

        case "Provvedimento":
            switch (campo) {
                case "idProvvedimento":
                    ((Provvedimento) obj).setIdProvvedimento(Integer.parseInt(valore));
                    break;
                case "nome":
                    ((Provvedimento) obj).setNome(valore);
                    break;
                case "idMansione":
                    ((Provvedimento) obj).setIdMansione(Integer.parseInt(valore));
                    break;
                case "idOggetto":
                    ((Provvedimento) obj).setIdOggetto(Integer.parseInt(valore));
                    break;
                case "idElencoRischi":
                    ((Provvedimento) obj).setIdElencoRischi(Integer.parseInt(valore));
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected value: " + campo);
            }
        break;

        case "UnitaLocale":
            switch (campo) {
                case "idUnitaLocale":
                    ((UnitaLocale) obj).setIdUnitaLocale(Integer.parseInt(valore));
                    break;
                case "nome":
                    ((UnitaLocale) obj).setNome(valore);
                    break;
                case "idSocieta":
                    ((UnitaLocale) obj).setIdSocieta(Integer.parseInt(valore));
                    break;
                case "indirizzo":
                    ((UnitaLocale) obj).setIndirizzo(valore);
                    break;
                case "localita":
                    ((UnitaLocale) obj).setLocalita(valore);
                    break;
                case "provincia":
                    ((UnitaLocale) obj).setProvincia(valore);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected value: " + campo);
            }
        break;

        default:
            throw new IllegalArgumentException("Unexpected value: " + obj.getClass().getSimpleName());
        }
    }
        
}
