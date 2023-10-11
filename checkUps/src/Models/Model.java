package Models;

import Controllers.ClassHelper;
import Models.Tables.Mansione;
import Models.Tables.Oggetto;
import Models.Tables.Provvedimento;
import Models.Tables.Reparto;
import Models.Tables.Rischio;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;
import sql.ControllerDb;

public class Model {
    
     
    //Metodo per eliminare un elemento sia da db che nelle liste
    public static void rimuoviDaLista(Object obj, int id) {

        ControllerDb.eliminaRecordDaId(obj.getClass().getSimpleName().toLowerCase(), id);
        System.out.println(obj.getClass().getSimpleName().toLowerCase());
        switch (obj.getClass().getSimpleName()) {

            case "Mansione":
                ClassHelper.getListMansione().remove((Mansione) obj);
                break;
            case "Titolo":
                ClassHelper.getListTitolo().remove((Titolo) obj);
                break;
            case "Reparto":
                ClassHelper.getListReparto().remove((Reparto) obj);
                break;
            case "Rischio":
                ClassHelper.getListRischio().remove((Rischio) obj);
                break;
            case "Societa":
                ClassHelper.getListSocieta().remove((Societa) obj);
                break;
            case "Oggetto":
                ClassHelper.getListOggetto().remove((Oggetto) obj);
                break;
            case "Provvedimento":
                ClassHelper.getListProvvedimento().remove((Provvedimento) obj);
                break;
            case "UnitaLocale":
                ClassHelper.getListUnitaLocale().remove((UnitaLocale) obj);
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + obj.getClass().getSimpleName());
        }
    }

    //Metodo per modificare il valore di una campo sia nella lista che nel db
    public static void modificaCampo(Object obj, String campo, String valore, int id) {
        
        switch (obj.getClass().getSimpleName()) {

            case "Mansione":

                switch (campo) {
                    case "idMansione":
                        ((Mansione) obj).setIdMansione(Integer.parseInt(valore));
                        ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), id, campo,Integer.parseInt(valore));
                        break;
                    case "nome":
                        ((Mansione) obj).setNome(valore);
                        ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), id, campo,valore);
                        break;
                    case "responsabile":
                        ((Mansione) obj).setResponsabile(valore);
                        ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), id, campo,valore);
                        break;
                    default:
                        throw new IllegalArgumentException("Unexpected value: " + campo);
                }
                break;

            case "Titolo":
                switch (campo) {
                    case "idTitolo":
                        ((Titolo) obj).setIdTitolo(Integer.parseInt(valore));
                        ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), id, campo,Integer.parseInt(valore));
                        break;
                    case "idReparto":
                        ((Titolo) obj).setIdReparto(Integer.parseInt(valore));
                        ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), id, campo,Integer.parseInt(valore));
                        break;
                    case "descrizione":
                        ((Titolo) obj).setDescrizione(valore);
                        ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), id, campo,valore);
                        break;
                    default:
                        throw new IllegalArgumentException("Unexpected value: " + campo);
                }
                break;

            case "Reparto":
                switch (campo) {
                    case "idReparto":
                        ((Reparto) obj).setIdReparto(Integer.parseInt(valore));
                        ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), id, campo,Integer.parseInt(valore));
                        break;
                    case "nome":
                        ((Reparto) obj).setNome(valore);
                        ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), id, campo,valore);
                        break;
                    case "descrizione":
                        ((Reparto) obj).setDescrizione(valore);
                        ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), id, campo,valore);
                        break;
                    case "idUnitaLocale":
                        ((Reparto) obj).setIdUnitaLocale(Integer.parseInt(valore));
                        ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), id, campo,Integer.parseInt(valore));
                        break;
                    default:
                        throw new IllegalArgumentException("Unexpected value: " + campo);
                }
                break;

            case "Rischio":
                switch (campo) {
                    case "idRischio":
                        ((Rischio) obj).setIdRischio(Integer.parseInt(valore));
                        ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), id, campo,Integer.parseInt(valore));
                        break;
                    case "nome":
                        ((Rischio) obj).setNome(valore);
                        ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), id, campo,valore);
                        break;
                    case "p":
                        ((Rischio) obj).setP(Integer.parseInt(valore));
                        ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), id, campo,Integer.parseInt(valore));
                        break;
                    case "d":
                        ((Rischio) obj).setD(Integer.parseInt(valore));
                        ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), id, campo,Integer.parseInt(valore));
                        break;
                    case "idReparto":
                        ((Rischio) obj).setIdReparto(Integer.parseInt(valore));
                        ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), id, campo,Integer.parseInt(valore));
                        break;
                    default:
                        throw new IllegalArgumentException("Unexpected value: " + campo);
                }
                break;

            case "Societa":
                switch (campo) {
                    case "idSocieta":
                        ((Societa) obj).setIdSocieta(Integer.parseInt(valore));
                        ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), id, campo,Integer.parseInt(valore));
                        break;
                    case "indirizzo":
                        ((Societa) obj).setIndirizzo(valore);
                        ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), id, campo,valore);
                        break;
                    case "localita":
                        ((Societa) obj).setLocalita(valore);
                        ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), id, campo,valore);
                        break;
                    case "provincia":
                        ((Societa) obj).setProvincia(valore);
                        ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), id, campo,valore);
                        break;
                    case "telefono":
                        ((Societa) obj).setTelefono(valore);
                        ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), id, campo,valore);
                        break;
                    case "descrizione":
                        ((Societa) obj).setDescrizione(valore);
                        ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), id, campo,valore);
                        break;
                    case "ente":
                        ((Societa) obj).setEnte(valore);
                        ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), id, campo,valore);
                        break;
                    default:
                        throw new IllegalArgumentException("Unexpected value: " + campo);
                }
                break;

            case "Oggetto":
                switch (campo) {
                    case "idOggetto":
                        ((Oggetto) obj).setIdOggetto(Integer.parseInt(valore));
                        ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), id, campo,Integer.parseInt(valore));
                        break;
                    case "nome":
                        ((Oggetto) obj).setNome(valore);
                        ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), id, campo,valore);
                        break;
                    case "idTitolo":
                        ((Oggetto) obj).setIdTitolo(Integer.parseInt(valore));
                        ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), id, campo,Integer.parseInt(valore));
                        break;
                    default:
                        throw new IllegalArgumentException("Unexpected value: " + campo);
                }
                break;

            case "Provvedimento":
                switch (campo) {
                    case "idProvvedimento":
                        ((Provvedimento) obj).setIdProvvedimento(Integer.parseInt(valore));
                        ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), id, campo,Integer.parseInt(valore));
                        break;
                    case "nome":
                        ((Provvedimento) obj).setNome(valore);
                        ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), id, campo,valore);
                        break;
                    case "idMansione":
                        ((Provvedimento) obj).setIdMansione(Integer.parseInt(valore));
                        ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), id, campo,Integer.parseInt(valore));
                        break;
                    case "idOggetto":
                        ((Provvedimento) obj).setIdOggetto(Integer.parseInt(valore));
                        ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), id, campo,Integer.parseInt(valore));
                        break;
                    case "idElencoRischi":
                        ((Provvedimento) obj).setIdElencoRischi(Integer.parseInt(valore));
                        ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), id, campo,Integer.parseInt(valore));
                        break;
                    default:
                        throw new IllegalArgumentException("Unexpected value: " + campo);
                }
                break;

            case "UnitaLocale":
                switch (campo) {
                    case "idUnitaLocale":
                        ((UnitaLocale) obj).setIdUnitaLocale(Integer.parseInt(valore));
                        ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), id, campo,Integer.parseInt(valore));
                        break;
                    case "nome":
                        ((UnitaLocale) obj).setNome(valore);
                        ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), id, campo,valore);
                        break;
                    case "idSocieta":
                        ((UnitaLocale) obj).setIdSocieta(Integer.parseInt(valore));
                        ControllerDb.modificaCampoIntero(obj.getClass().getSimpleName().toLowerCase(), id, campo,Integer.parseInt(valore));
                        break;
                    case "indirizzo":
                        ((UnitaLocale) obj).setIndirizzo(valore);
                        ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), id, campo,valore);
                        break;
                    case "localita":
                        ((UnitaLocale) obj).setLocalita(valore);
                        ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), id, campo,valore);
                        break;
                    case "provincia":
                        ((UnitaLocale) obj).setProvincia(valore);
                        ControllerDb.modificaCampoStringa(obj.getClass().getSimpleName().toLowerCase(), id, campo,valore);
                        break;
                    default:
                        throw new IllegalArgumentException("Unexpected value: " + campo);
                }
                break;

            default:
                throw new IllegalArgumentException("Unexpected value: " + obj.getClass().getSimpleName());
        }
    }

    //Metodo per inserire un nuovo elemento sia nel DB che nella lista
    public static void inserisciRecordInLista(Object obj) {
        ControllerDb.inserisciRecord(obj);
        switch (obj.getClass().getSimpleName()) {
            case "Mansione":
                Mansione mansione = new Mansione(((Mansione) obj).getIdMansione(),
                        ((Mansione) obj).getNome(),
                        ((Mansione) obj).getResponsabile());

                ClassHelper.getListMansione().add(mansione);
                break;
            case "Titolo":
                Titolo titolo = new Titolo(((Titolo) obj).getIdTitolo(),
                        ((Titolo) obj).getDescrizione(),
                        ((Titolo) obj).getIdReparto());

                ClassHelper.getListTitolo().add(titolo);
                break;
            case "Reparto":
                Reparto reparto = new Reparto(((Reparto) obj).getIdReparto(),
                        ((Reparto) obj).getIdUnitaLocale(),
                        ((Reparto) obj).getDescrizione(),
                        ((Reparto) obj).getNome());

                ClassHelper.getListReparto().add(reparto);
                break;
            case "Rischio":
                Rischio rischio = new Rischio(((Rischio) obj).getIdRischio(),
                        ((Rischio) obj).getNome(),
                        ((Rischio) obj).getP(),
                        ((Rischio) obj).getD(),
                        ((Rischio) obj).getR(),
                        ((Rischio) obj).getIdReparto());

                ClassHelper.getListRischio().add(rischio);
                break;
            case "Societa":
                Societa societa = new Societa(((Societa) obj).getIdSocieta(),
                        ((Societa) obj).getIndirizzo(),
                        ((Societa) obj).getLocalita(),
                        ((Societa) obj).getProvincia(),
                        ((Societa) obj).getTelefono(),
                        ((Societa) obj).getDescrizione(),
                        ((Societa) obj).getEnte());

                ClassHelper.getListSocieta().add(societa);
                break;
            case "Oggetto":
                Oggetto oggetto = new Oggetto(((Oggetto) obj).getIdOggetto(),
                        ((Oggetto) obj).getNome(),
                        ((Oggetto) obj).getIdTitolo());

                ClassHelper.getListOggetto().add(oggetto);
                break;
            case "Provvedimento":
                Provvedimento provvedimento = new Provvedimento(((Provvedimento) obj).getIdProvvedimento(),
                        ((Provvedimento) obj).getNome(),
                        ((Provvedimento) obj).getIdMansione(),
                        ((Provvedimento) obj).getIdOggetto(),
                        ((Provvedimento) obj).getIdElencoRischi());

                ClassHelper.getListProvvedimento().add(provvedimento);
                break;
            case "UnitaLocale":
                UnitaLocale unitaLocale = new UnitaLocale(((UnitaLocale) obj).getIdUnitaLocale(),
                        ((UnitaLocale) obj).getNome(),
                        ((UnitaLocale) obj).getIndirizzo(),
                        ((UnitaLocale) obj).getLocalita(),
                        ((UnitaLocale) obj).getProvincia(),
                        ((UnitaLocale) obj).getIdSocieta());

                ClassHelper.getListUnitaLocale().add(unitaLocale);
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + obj.getClass().getSimpleName());
        }
    }
}

/* 
    
    public static void inserisciInLista(Object obj) {
        
        switch (obj.getClass().getSimpleName()) {

            case "Mansione":
                ClassHelper.getListMansione().add((Mansione) obj);
                
                break;
            case "Titolo":
                ClassHelper.getListTitolo().add((Titolo) obj);
                break;
            case "Reparto":
                ClassHelper.getListReparto().add((Reparto) obj);
                break;
            case "Rischio":
                ClassHelper.getListRischio().add((Rischio) obj);
                break;
            case "Societa":
                ClassHelper.getListSocieta().add((Societa) obj);
                break;
            case "Oggetto":
                ClassHelper.getListOggetto().add((Oggetto) obj);
                break;
            case "Provvedimento":
                ClassHelper.getListProvvedimento().add((Provvedimento) obj);
                break;
            case "UnitaLocale":
                ClassHelper.getListUnitaLocale().add((UnitaLocale) obj);
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + obj.getClass().getSimpleName());
        }
    }
*/

