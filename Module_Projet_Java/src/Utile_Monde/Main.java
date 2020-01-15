package Utile_Monde;

import Exceptions_Monde.OutOfMapException;
import Utile_Fourmi.*;


import java.io.*;

public class Main {

    public static void main (String[] args){
        /*int hauteur = 5;
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
        Case[] cases = {F1,N1,N2,N3,N4,N5};*/
        /*try {
            monde.setCases(cases);
            System.out.println(monde);
        }
        catch (OutOfMapException e){
            System.out.println(e.getMessage());
        }

        String nomCarte ="Cartes\\Carte_Test.carte";*/
        try {
            // Génétation de la fourmi parfaite
            Noeud C1 = new Condition("cond_nourriture");
            Noeud C2 = new Condition("cond_fourmiliere");
            Noeud C3 = new Condition("cond_possedeNourriture");
            Noeud A1 = new Action("act_rentrer");
            Noeud A2 = new Action("act_deposer");
            Noeud A3 = new Action("act_allerAleat");
            Noeud A4 = new Action("act_ramasser");
            ProgrammeGenetique P1 = new ProgrammeGenetique(A1);
            ProgrammeGenetique P2 = new ProgrammeGenetique(A2);
            ProgrammeGenetique P3 = new ProgrammeGenetique(A3);
            ProgrammeGenetique P4 = new ProgrammeGenetique(A4);
            ProgrammeGenetique P5 = new ProgrammeGenetique(C2,P2,P1);
            ProgrammeGenetique P6 = new ProgrammeGenetique(C1,P4,P3);
            ProgrammeGenetique P7 = new ProgrammeGenetique(C3,P5,P6);
            P7.numerotationFeuille();
            P7.numerotationNoeud();


            Fourmi f = new Fourmi(P7);
            f.afficherIntelligence();
            f.agir();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
