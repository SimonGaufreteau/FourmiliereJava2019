package Utile_Fourmi;

import Exceptions_Monde.InvalidFileFormatException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class ProgrammeGenetique {
    private Noeud valeur;
    private ProgrammeGenetique aGauche;
    private ProgrammeGenetique aDroite;

    // Dans le cas d'un noeud contenant une action, on s'arrête ici
    public ProgrammeGenetique(Noeud valeur){
        this.valeur = valeur;
        this.aGauche = null;
        this.aDroite = null;
    }


    // Dans le cas d'un noeud contenant une condition, on crée un fils gauche et droite
    public ProgrammeGenetique(Noeud valeur, ProgrammeGenetique aGauche, ProgrammeGenetique aDroite){
        this.valeur = valeur;
        this.aGauche = aGauche;
        this.aDroite = aDroite;
    }

    public ProgrammeGenetique() throws IOException{ // A voir si on a besoin de passer le nom des fichiers en paramètre ou les mettre directement comme des variables internes
        // Chemin des fichiers contenant les actions et les conditions
        String nomFichierActions = "Noeuds\\Actions.txt";
        String nomFichierConditions = "Noeuds\\Conditions.txt";

        // On récupère les conditions et les actions puis on les met chacun dans une liste
        List<String> listActions = getLignes(nomFichierActions);
        List<String> listConditions = getLignes(nomFichierConditions);

        Noeud allActions[] = new Action[listActions.size()];
        Noeud allConditions[] = new Condition[listConditions.size()];

        for(int i=0;i<listActions.size();i++) {
            allActions[i] = new Action(listActions.get(i));
        }

        for(int i=0;i<listConditions.size();i++) {
            allConditions[i] = new Condition(listConditions.get(i));
        }

        int aleat = (int)(Math.random()*13);
        System.out.println("Valeur aleatoire : " + aleat);

        if(aleat < 8){
            valeur = allActions[aleat];
            aGauche = null;
            aDroite = null;
        }
        else{
            valeur = allActions[aleat%8];
            aGauche = new ProgrammeGenetique();
            aDroite = new ProgrammeGenetique();
        }
    }

    // Fonction interne permettant de récupérer le texte présent dans les fichiers de Noeuds (Conditions et Actions)
    private  List<String> getLignes(String nomFichier) throws IOException { // Réutilisation de getLignes, présent dans la classe Case, faut-il le généraliser dans un classe mère ?
        List<String> lignes  = new ArrayList<String>();
        nomFichier = System.getProperty("user.dir")+ "\\Module_Projet_Java\\"+nomFichier;
        BufferedReader reader = new BufferedReader(new FileReader(nomFichier));
        String line;
        while ((line = reader.readLine()) != null) {
            lignes.add(line);
        }
        reader.close();
        return lignes;
    }

    public Noeud getNoeud(){
        return valeur;
    }

    public ProgrammeGenetique getAGauche(){
        return aGauche;
    }

    public ProgrammeGenetique getADroite(){
        return aDroite;
    }
    /*
    public String getValeurNoeud(){
        return valeur.toString();
    }

    public String getValeurNoeudAGauche(){
        return aGauche.getValeurNoeud();
    }

    public String getValeurNoeudADroite(){
        return aDroite.getValeurNoeud();
    }*/
}
