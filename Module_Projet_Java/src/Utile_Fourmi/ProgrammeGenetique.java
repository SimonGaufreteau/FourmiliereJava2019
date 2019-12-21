package Utile_Fourmi;

import Exceptions_Monde.InvalidFileFormatException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class ProgrammeGenetique implements  Serializable, Cloneable {
    private int id;
    private static int nbTempNoeud = 1;
    private static int nbTempFeuille = 1;
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

    public int getId(){ return id; }

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
        System.out.println(espace + hauteur + "." + valeur.getText() + "(" + id + ")");
        if(valeur.getClass().getName() == "Utile_Fourmi.Condition"){
            aGauche.afficherArbre(hauteur+1, espace+="  ");
            aDroite.afficherArbre(hauteur+1, espace);
        }
    }

    // Fonction permettant de remplacer une condition par une autre afin de muter
    public void remplacerCondition() throws IOException {
        int nbCond = nbConditions();
        System.out.println("\nNombre de conditions : " + nbCond);
        if(nbCond != 0){
            int aleatCond = (int) (Math.random() * nbCond)+1;
            System.out.println("N° du noeud à modifier : " + aleatCond);
            interneRemplacerCondition(aleatCond);
        }
    }

    private void interneRemplacerCondition(int aleatCond) throws IOException {
        if(id == aleatCond){
            System.out.println("Condition à modifier : " + valeur.getText());
            String nomFichierConditions = "Noeuds\\Conditions.txt";
            List<String> listConditions = getLignes(nomFichierConditions);
            Noeud allConditions[] = new Condition[listConditions.size()];

            for(int i=0;i<listConditions.size();i++) {
                allConditions[i] = new Condition(listConditions.get(i));
            }
            int newNuCond = (int) (Math.random() * 3);
            while(allConditions[newNuCond].getText().equals(valeur.getText())){
                newNuCond = (int) (Math.random() * 3);
            }
            System.out.println("Nouvelle valeur : " + allConditions[newNuCond].getText());
            valeur = allConditions[newNuCond];
            //simplifier();
            //numerotationNoeud();
        }
        else{
            if(valeur.getClass().getName() == "Utile_Fourmi.Condition"){
                aGauche.interneRemplacerCondition(aleatCond);
                aDroite.interneRemplacerCondition(aleatCond);
            }
        }
    }

    // Fonction permettant de remplacer une action par une autre afin de muter
    public void remplacerAction() throws IOException {
        int nbAct = nbActions();
        System.out.println("\nNombre d'actions : " + nbAct);
        int aleatAct = (int) (Math.random() * nbAct)+101; // A modifier ensuite selon nos choix de numérotation
        System.out.println("N° de la feuille à modifier : " + aleatAct);
        interneRemplacerAction(aleatAct);
    }

    private void interneRemplacerAction(int aleatAct) throws IOException {
        if(id == aleatAct){
            System.out.println("Action à modifier : " + valeur.getText());
            String nomFichierActions = "Noeuds\\Actions.txt";
            List<String> listActions = getLignes(nomFichierActions);
            Noeud allActions[] = new Action[listActions.size()];

            for(int i=0;i<listActions.size();i++) {
                allActions[i] = new Action(listActions.get(i));
            }
            int newNuAct = (int) (Math.random() * 8);
            while(allActions[newNuAct].getText().equals(valeur.getText())){
                newNuAct = (int) (Math.random() * 8);
            }
            System.out.println("Nouvelle valeur : " + allActions[newNuAct].getText());
            valeur = allActions[newNuAct];
            //simplifier();
            //numerotationNoeud();
        }
        else{
            if(valeur.getClass().getName() == "Utile_Fourmi.Condition"){
                aGauche.interneRemplacerAction(aleatAct);
                aDroite.interneRemplacerAction(aleatAct);
            }
        }
    }

    // Fonction permettant d'échanger deux sous arbres d'une condition afin de muter
    public void echangerSousArbres() throws IOException {
        int nbCond = nbConditions();
        System.out.println("\nNombre de conditions : " + nbCond);
        if(nbCond != 0){
            int aleatCond = (int) (Math.random() * nbCond)+1;
            System.out.println("N° du noeud où échanger ses sous arbres : " + aleatCond);
            interneEchangerSousArbres(aleatCond);
        }
    }

    private void interneEchangerSousArbres(int aleatCond) throws IOException {
        if(id == aleatCond){
            System.out.println("Condition où échanger ses sous arbres : " + valeur.getText());
            ProgrammeGenetique temp = new ProgrammeGenetique(null);
            temp = aGauche;
            aGauche = aDroite;
            aDroite = temp;
            //simplifier();
            //numerotationNoeud();
        }
        else{
            if(valeur.getClass().getName() == "Utile_Fourmi.Condition"){
                aGauche.interneEchangerSousArbres(aleatCond);
                aDroite.interneEchangerSousArbres(aleatCond);
            }
        }
    }

    // Fonction permettant de remplacer un sous arbre d'un programme génétique par un sous arbre d'un autre programme génétique
    // Le programme génétique sur lequel on appelle la fonction sera celui sur lequel on remplace un sous-arbre
    // Le programme génétique passé en paramètre sera celui sur lequel on récupère le sous arbre
    public void croiserProgrammes(ProgrammeGenetique prog2) throws IOException {
        int nbCond1 = nbConditions();
        int nbCond2 = prog2.nbConditions();
        //System.out.println("\nNombre de conditions arbre 1 : " + nbCond1);
        //System.out.println("\nNombre de conditions arbre 2 : " + nbCond2);
        if(nbCond1 != 0 && nbCond2 != 0){ // Ce cas ne devrait jamais arriver ensuite puisque la fonction sera appliquée sur les meilleurs arbres génétiques
            int aleatCond1 = (int) (Math.random() * nbCond1)+1;
            int aleatCond2 = (int) (Math.random() * nbCond2)+1;
            System.out.println("\nN° du noeud à modifier (arbre 1) : " + aleatCond1);
            System.out.println("N° du noeud à muter (arbre 2) : " + aleatCond2);
            selectionNoeudAInserer(aleatCond1, aleatCond2, prog2);
        }
    }

    private void selectionNoeudAInserer(int aleatCond1, int aleatCond2, ProgrammeGenetique prog2) throws IOException {
        if(prog2.getId() == aleatCond2){
            interneCroiserProgrammes(aleatCond1, aleatCond2, prog2);
        }
        else {
            if(prog2.getNoeud().getClass().getName() == "Utile_Fourmi.Condition"){
                System.out.println(prog2.getNoeud().getClass().getName());
                selectionNoeudAInserer(aleatCond1, aleatCond2, prog2.getAGauche());
                selectionNoeudAInserer(aleatCond1, aleatCond2, prog2.getADroite());
            }
        }
    }

    private void interneCroiserProgrammes(int aleatCond1, int aleatCond2, ProgrammeGenetique prog2) throws IOException {
        if(id == aleatCond1){
            //System.out.println("Condition où insérer nouvel arbre : " + valeur.getText());
            //System.out.println("Condition de l'arbre à insérer : " + prog2.getValeurNoeud());
            valeur = prog2.getNoeud();
            aGauche = prog2.getAGauche();
            aDroite = prog2.getADroite();
        }
        else{
            if(valeur.getClass().getName() == "Utile_Fourmi.Condition"){
                aGauche.interneCroiserProgrammes(aleatCond1, aleatCond2, prog2);
                aDroite.interneCroiserProgrammes(aleatCond1, aleatCond2, prog2);
            }
        }
    }

    // Fonction permettant de numéroter les noeuds (ici les conditions) de 1 à nbNoeuds afin de les retrouver par la suite
    public void numerotationNoeud(){
        interneNumerotationNoeud();
        nbTempNoeud = 1;
    }

    private void interneNumerotationNoeud() {
        if(valeur.getClass().getName() == "Utile_Fourmi.Condition"){
            id = nbTempNoeud;
            nbTempNoeud++;
            aGauche.interneNumerotationNoeud();
            aDroite.interneNumerotationNoeud();
        }
    }

    // Fonction permettant de numéroter les feuilles (ici les actions) de 1 à nbFeuilles afin de les retrouver par la suite
    public void numerotationFeuille(){
        interneNumerotationFeuille();
        nbTempFeuille = 1;
    }

    private void interneNumerotationFeuille() {
        if(valeur.getClass().getName() == "Utile_Fourmi.Condition"){
            aGauche.interneNumerotationFeuille();
            aDroite.interneNumerotationFeuille();
        }
        else {
            id = nbTempFeuille + 100; // On ajoute temporairement 100 pour différencier les noeuds des feuilles ensuite
            nbTempFeuille++;
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

    // Fonction permettant de cloner l'arbre et d'en créer un nouveau ayant les mêmes valeurs
    public ProgrammeGenetique clone() {
        ProgrammeGenetique o = null;
        try {
            o = (ProgrammeGenetique) super.clone();
        }
        catch(CloneNotSupportedException cnse){
            cnse.printStackTrace(System.err);
        }
        return o;
    }

    public int hauteur() {
        if(this.getADroite() == null && this.getAGauche() == null)
            return 0;
        else
            return (1 + Math.max(getAGauche().hauteur(),getADroite().hauteur()));
    }

    // Fonction qui retourne le nombre de conditions présentes dans l'arbre
    public int nbConditions(){
        if(valeur.getClass().getName() == "Utile_Fourmi.Condition"){
            return 1 + aGauche.nbConditions() + aDroite.nbConditions();
        }
        else{
            return 0;
        }
    }

    public int nbActions(){
        return nbNoeudTotal() - nbConditions();
    }

    public int nbNoeudTotal(){
        if(valeur.getClass().getName() == "Utile_Fourmi.Condition"){
            return 1 + aGauche.nbNoeudTotal() + aDroite.nbNoeudTotal();
        }
        else{
            return 1;
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
