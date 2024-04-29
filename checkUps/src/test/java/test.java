package test.java;

import java.util.ArrayList;
import java.util.List;

import Controllers.ControllerDb;
import Helpers.ClassHelper;
import Models.ModelListe;
import Models.Tables.Societa;

public class test {

    public static void main(String[] args) {

        ControllerDb.popolaListaSocietaDaDb();
        ModelListe.salvaListeSuFile("C:\\dev\\ProgettoCheckUp\\CheckUpsGestionale\\checkUps\\src\\test\\prova.csv",
                ClassHelper.getListSocieta());
        System.out.println("PROVA");
        System.out.println(ClassHelper.getListSocieta().size());
        ClassHelper.svuotaListaSocieta();
        List<?> prova = new ArrayList<>();
        prova = ModelListe
                .caricaListaDaFile("C:\\dev\\ProgettoCheckUp\\CheckUpsGestionale\\checkUps\\src\\test\\prova.csv");
        System.out.println(((Societa) prova.get(14)).getNome());

    }

}
