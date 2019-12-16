package Utile_Monde;

import Exceptions_Monde.OutOfMapException;
import Utile_Fourmi.Action;
import Utile_Fourmi.Condition;
import Utile_Fourmi.Noeud;
import Utile_Fourmi.ProgrammeGenetique;


import java.io.BufferedReader;
import java.io.FileReader;

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
            //Monde monde1 = new Monde(nomCarte, 5);
            //System.out.println(monde1);
            //monde1.sauvegarder("Save_Carte_Test.carte");
            /*ProgrammeGenetique prog = new ProgrammeGenetique();
            prog.afficherArbre(1, "");
            System.out.println("");
            prog.simplifier();
            prog.afficherArbre(1, "");
            //prog.sauvegarder("test_arbre.txt");*/

            ProgrammeGenetique prog = new ProgrammeGenetique();

            Noeud n1 = new Action("act_allerBas");
            Noeud n2 = new Action("act_rentrer");
            Noeud n3 = new Condition("cond_fourmiliere");
            Noeud n4 = new Condition("cond_nourriture");

            ProgrammeGenetique gen1 = new ProgrammeGenetique(n1);
            ProgrammeGenetique gen2 = new ProgrammeGenetique(n2);
            ProgrammeGenetique gen3 = new ProgrammeGenetique(n3,gen1,gen2);
            ProgrammeGenetique gen4 = new ProgrammeGenetique(n3,gen1,gen3);
            ProgrammeGenetique gen5 = new ProgrammeGenetique(n4,gen1,gen4);

            prog.afficherArbre(1,"");
            prog.simplifier();
            System.out.println("");
            prog.afficherArbre(1,"");

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
