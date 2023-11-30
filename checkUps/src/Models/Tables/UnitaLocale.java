package Models.Tables;

import java.io.Serializable;
import java.util.List;

import Helpers.ClassHelper;

public class UnitaLocale extends TablesId implements Serializable{
    private int idSocieta;
    private String nome;
    private String indirizzo;
    private String localita;
    private String provincia;
    private List<Reparto> listaReparti = ClassHelper.getListReparto();

    public UnitaLocale(int idUnitaLocale, String nome, String indirizzo, String localita, String provincia, int idSocieta) {
        super(idUnitaLocale);
        //this.idUnitaLocale = idUnitaLocale;
        this.idSocieta = idSocieta;
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.localita = localita;
        this.provincia = provincia;
    }

    public int getIdSocieta() {
        return idSocieta;
    }

    public void setIdSocieta(int idSocieta) {
        this.idSocieta = idSocieta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getLocalita() {
        return localita;
    }

    public void setLocalita(String localita) {
        this.localita = localita.toUpperCase();
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia.toUpperCase();
    }

    public List<Reparto> getReparti() {
        return listaReparti.stream().filter(reparto -> reparto.getIdUnitaLocale() == getId()).toList();
    }
}
