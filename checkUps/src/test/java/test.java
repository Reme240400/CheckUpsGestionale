package test.java;

import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.crypto.engines.SM2Engine.Mode;

import Controllers.ClassHelper;
import Controllers.Controller;
import Controllers.ControllerDb;
import Models.Model;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.UnitaLocale;

public class test {

    public static void main(String[] args) {
        
        ControllerDb.popolaListaSocietaDaDb();
        Model.salvaListeSuFile("C:\\dev\\CheckUps\\CheckUpsGestionale\\checkUps\\src\\test\\prova.csv",ClassHelper.getListSocieta());
        System.out.println("PROVA");
        System.out.println(ClassHelper.getListSocieta().size());
        ClassHelper.svuotaListaSocieta();
        List<?> prova = new ArrayList<>(); 
        prova = Model.caricaListaDaFile("C:\\dev\\CheckUps\\CheckUpsGestionale\\checkUps\\src\\test\\prova.csv");
        System.out.println(((Societa)prova.get(14)).getNome());
        
    }
    
}
