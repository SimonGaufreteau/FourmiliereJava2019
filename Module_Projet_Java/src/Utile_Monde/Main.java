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
            //Monde monde1 = new Monde(nomCarte, 5);
            //System.out.println(monde1);
            //monde1.sauvegarder("Save_Carte_Test.carte");
            /*ProgrammeGenetique tabProg[] = new ProgrammeGenetique[100];
            for(int i=0;i<100;i++){
                tabProg[i] = new ProgrammeGenetique();
                System.out.println(tabProg[i].nbConditions());
            }*/

            Fourmi pika = new Fourmi();
            pika.getIntelligence().afficherArbre();
            pika.agir();

            /*prog.remplacerAction();
            System.out.println("\n\nARBRE 1 REMPLACE ACT :");
            prog.afficherArbre(0, "");*/
            /*
            System.out.println("");

            prog2.afficherArbre(0, "");*/
            /*System.out.println(prog.nbConditions());
            System.out.println("Nombre de noeuds : " + prog.nbConditions());

            int aleatCond = (int) (Math.random() * prog.nbConditions()); // On choisit un nombre entre 1 et le nombre de conditions
            System.out.println("Noeud de condition choisi : " + aleatCond);*/

            //prog.serialiser("test_arbre.ser");

            /*
            Noeud n1 = new Action("act_allerBas");
            Noeud n2 = new Action("act_allerDroite");
            Noeud n3 = new Action("act_allerHaut");
            Noeud n4 = new Action("act_allerGauche");
            Noeud n5 = new Condition("cond_nourriture");

            ProgrammeGenetique gen1 = new ProgrammeGenetique(n1);
            ProgrammeGenetique gen2 = new ProgrammeGenetique(n2);
            ProgrammeGenetique gen3 = new ProgrammeGenetique(n3);
            ProgrammeGenetique gen4 = new ProgrammeGenetique(n4);
            ProgrammeGenetique gen5 = new ProgrammeGenetique(n5,gen3,gen1);
            ProgrammeGenetique gen6 = new ProgrammeGenetique(n5,gen2,gen5);
            ProgrammeGenetique gen7 = new ProgrammeGenetique(n5,gen4,gen6);

            gen7.afficherArbre(1,"");
            gen7.simplifier();
            System.out.println("");
            gen7.afficherArbre(1,"");
            */

        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
