package Utile_Monde;

import Exceptions_Monde.OutOfMapException;

import java.awt.desktop.SystemEventListener;
import java.io.BufferedReader;
import java.io.FileReader;

public class Main {

    public static void main (String[] args){
        int hauteur = 5;
        int largeur = 5;
        //Monde monde= new Monde(hauteur,largeur,5);
        CaseFourmiliere F1 = new CaseFourmiliere(1,2);
        CaseNourriture N1 = new CaseNourriture(0,0);
        CaseNourriture N2 = new CaseNourriture(0,2);
        CaseNourriture N3 = new CaseNourriture(1,3);
        CaseNourriture N4 = new CaseNourriture(4,0);
        CaseNourriture N5 = new CaseNourriture(4,4);
        //CaseNourriture N6 = new CaseNourriture(5,4);
        //CaseNourriture N7 = new CaseNourriture(0,5);
        Case[] cases = {F1,N1,N2,N3,N4,N5};
        /*try {
            monde.setCases(cases);
            System.out.println(monde);
        }
        catch (OutOfMapException e){
            System.out.println(e.getMessage());
        }*/

        String nomCarte ="Cartes\\Carte_Test.carte";

        try {
            Monde monde1 = new Monde(nomCarte, 5);
            System.out.println(monde1);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }


    }
}
