package Utile_Fourmi;

import Exceptions_Monde.InvalidFileFormatException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class ProgrammeGenetique implements  Serializable {
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

        int aleatNoeud = (int) (Math.random() * 2); // On tire au sort un numéro entre 0 et 1 (0:action, 1:condition)
        //System.out.println("Valeur aleatoire : " + aleatNoeud);

        if(aleatNoeud == 0){ // On sélectionne une action
            int aleatAct = (int) (Math.random() * 8);
            valeur = allActions[aleatAct];
            aGauche = null;
            aDroite = null;
        }

        else { // On sélectionne une condition
            int aleatCond = (int) (Math.random() * 3);
            valeur = allConditions[aleatCond];
            aGauche = new ProgrammeGenetique();
            aDroite = new ProgrammeGenetique();
        }
    }

    public Noeud getNoeud(){
        return valeur;
    }

    public String getValeurNoeud() { return valeur.getText(); }

    public ProgrammeGenetique getAGauche(){
        return aGauche;
    }

    public ProgrammeGenetique getADroite(){
        return aDroite;
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

    // Fonction permettant d'afficher l'arbre en console
    public void afficherArbre(int hauteur, String espace){
        System.out.println(espace + hauteur + "." + valeur.getText());
        if(valeur.getClass().getName() == "Utile_Fourmi.Condition"){
            aGauche.afficherArbre(hauteur+1, espace+="  ");
            aDroite.afficherArbre(hauteur+1, espace);
        }
    }

    /*// Fonction permettant de sauvegarder un arbre dans un fichier texte
    public void sauvegarder(String nomFichier) throws IOException {
        nomFichier = System.getProperty("user.dir")+ "\\Module_Projet_Java\\Sauvegardes\\"+nomFichier;

        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nomFichier), StandardCharsets.UTF_8));
        writer.write(this.toString(1,""));
        writer.close();
    }*/

    public void serialiser(String nomFichier) {
        nomFichier = System.getProperty("user.dir")+ "\\Module_Projet_Java\\Sauvegardes\\"+ nomFichier;
        try {
            FileOutputStream fs = new FileOutputStream(nomFichier);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(this);
            os.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void charger(String nomFichier) throws IOException {

    }

    // Fonction qui permet de simplifier un arbre en supprimant les conditions qui se répètent
    public void simplifier () {
        if(valeur.getClass().getName() == "Utile_Fourmi.Condition") {
            if (valeur.getText().equals(getAGauche().getValeurNoeud())) { // Si la valeur du noeud est égale à la valeur du noeud gauche, supprime le noeud et on remplace par l'arbre gauche
                aGauche = aGauche.getAGauche();
                this.simplifier();
            }
            if (valeur.getText().equals(getADroite().getValeurNoeud())) {
                aDroite = aDroite.getADroite();
                this.simplifier();
            }
            aGauche.simplifier();
            aDroite.simplifier();
        }
    }

    public String toString(int hauteur, String espace) {
        String S = "";
        S += espace + hauteur + "." + valeur.getText() + "\n";
        if(valeur.getClass().getName() == "Utile_Fourmi.Condition"){
            S+=aGauche.toString(hauteur+1, espace+="  ");
            S+=aDroite.toString(hauteur+1, espace);
        }
        return S;
    }
}
