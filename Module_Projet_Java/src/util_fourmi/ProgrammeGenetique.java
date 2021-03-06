package util_fourmi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;


public class ProgrammeGenetique implements Serializable, Cloneable {
    private int id;
    private static int nbTempNoeud = 1;
    private static int nbTempFeuille = 1;
    private Noeud valeur;
    private ProgrammeGenetique aGauche;
    private ProgrammeGenetique aDroite;

    // Constructeur avec seulement une valeur (sans fils)
    public ProgrammeGenetique(Noeud valeur) {
        this.valeur = valeur;
        this.aGauche = null;
        this.aDroite = null;
    }

    // Constructeur avec une valeur, un fils droit et un fils gauche
    public ProgrammeGenetique(Noeud valeur, ProgrammeGenetique aGauche, ProgrammeGenetique aDroite) {
        this.valeur = valeur;
        this.aGauche = aGauche;
        this.aDroite = aDroite;
    }

    //Constructeur qui génère un ProgrammeGénétique avec un certains nombre de noeuds (utilisé pour les tests)
    public ProgrammeGenetique(int nbNoeuds) throws IOException {
        Random r = new Random();
        Noeud allActions[] = recupererActTab();
        Noeud allConditions[] = recupererCondTab();

        //On crée un arbre de base avec une condition et deux actions
        int aleatCond = (int) (Math.random() * allConditions.length);
        int aleatAct1 = (int) (Math.random() * allActions.length);
        int aleatAct2 = (int) (Math.random() * allActions.length);
        this.valeur = allConditions[aleatCond];
        this.aGauche = new ProgrammeGenetique(allActions[aleatAct1]);
        this.aDroite = new ProgrammeGenetique(allActions[aleatAct2]);

        //Boucle sur le nombre de noeud pour ajouter une condition (aléatoire) à chaque itération
        for (int i = 0; i < nbNoeuds - 1; i++) {
            add(allActions, allConditions);
        }
        simplifierProgramme();
        simplifierProgramme();
        numerotationFeuille();
        numerotationNoeud();
    }

    //Méthode permmettant d'ajouter une condition à un programme (utilise la méthodé récursive ci-dessous)
    public void add(Noeud[] allActions, Noeud[] allConditions) {
        Random r = new Random();
        int lr = (int) (Math.random() * 2); // 0 or 1
        if (lr == 0) this.aGauche = addRecursive(this.aGauche, allActions, allConditions);
        else this.aDroite = addRecursive(this.aDroite, allActions, allConditions);
    }

    //Méthode récursive permettant de placer une condition aléatoirement en fin d'arbre
    private ProgrammeGenetique addRecursive(ProgrammeGenetique current, Noeud[] allActions, Noeud[] allConditions) {
        if (current == null) {
            int aleatAct = (int) (Math.random() * allActions.length);
            current = new ProgrammeGenetique(allActions[aleatAct]);
            return current;
        }
        Random r = new Random();
        //Une chance sur deux d'aller à droite (resp. à gauche)
        int lr = (int) (Math.random() * 2); // 0 or 1

        //Si on était sur une action, on crée une condition à la place avec deux actions comme suites.
        if (current.valeur instanceof Action) {
            int aleatCond = (int) (Math.random() * allConditions.length);
            current.valeur = allConditions[aleatCond];
            int aleatAct1 = (int) (Math.random() * allActions.length);
            int aleatAct2 = (int) (Math.random() * allActions.length);

            current.aGauche = new ProgrammeGenetique(allActions[aleatAct1]);
            current.aDroite = new ProgrammeGenetique(allActions[aleatAct2]);
            return current;
        }
        //On continue si on est toujours pas sur une feuille (i.e. une action)
        if (lr == 0) {
            current.aGauche = addRecursive(current.aGauche, allActions, allConditions);
        } else if (lr == 1) {
            current.aDroite = addRecursive(current.aDroite, allActions, allConditions);
        }
        return current;
    }


    public ProgrammeGenetique() throws IOException {
        this(new Random().nextInt(100));
    }

    public Noeud getNoeud() {
        return valeur;
    }

    public String getValeurNoeud() {
        return valeur.getText();
    }

    public ProgrammeGenetique getAGauche() {
        return aGauche;
    }

    public ProgrammeGenetique getADroite() {
        return aDroite;
    }

    public int getId() {
        return id;
    }

    // Fonction interne permettant de récupérer le texte présent dans les fichiers de Noeuds (Conditions et Actions)
    private List<String> getLignes(String nomFichier) throws IOException { // Réutilisation de getLignes, présent dans la classe Case, faut-il le généraliser dans un classe mère ?
        List<String> lignes = new ArrayList<>();
        nomFichier = System.getProperty("user.dir") + "\\Module_Projet_Java\\" + nomFichier;
        BufferedReader reader = new BufferedReader(new FileReader(nomFichier));
        String line;
        while ((line = reader.readLine()) != null) {
            lignes.add(line);
        }
        reader.close();
        return lignes;
    }

    // Fonction permettant d'afficher l'arbre en console
    public void afficherArbre() {
        interneAfficherArbre(0, "");
    }

    private void interneAfficherArbre(int hauteur, String espace) {
        System.out.println(espace + hauteur + "." + valeur.getText() + "(" + id + ")");
        if (valeur.getClass().getName().equals("util_fourmi.Condition")) {
            aGauche.interneAfficherArbre(hauteur + 1, espace += "  ");
            aDroite.interneAfficherArbre(hauteur + 1, espace);
        }
    }



    public void simplifierProgramme() {
        //On simplifie les deux côtés de l'arbre de départ
        Map<String, String> m = new HashMap<>();
        m.put(this.valeur.getText(), "Left");
        this.aGauche = simplifierProgrammeRecursif(this.aGauche, m);
        //this.aGauche= simplifierProgrammeRecursif(this.aGauche,m);
        m.replace(this.valeur.getText(), "Right");
        this.aDroite = simplifierProgrammeRecursif(this.aDroite, m);
        //this.aDroite= simplifierProgrammeRecursif(this.aDroite,m);
    }

    private ProgrammeGenetique simplifierProgrammeRecursif(ProgrammeGenetique current, Map<String, String> map) {
        if (current == null) return null;
        //On rechrche si on est déjà passé par
        String value = map.get(current.valeur.getText());

        //Si on est déjà passé par la condition on simplifie directement en prenant le côté correspondant
        if (value != null) {
            if (value.equals("Right")) {
                return simplifierProgrammeRecursif(current.aDroite, map);
            } else {
                return simplifierProgrammeRecursif(current.aGauche, map);
            }
        }
        //Si on est sur une condition et que les deux côtés mènent à la même action, on simplifie en une Action.
        if (current.valeur instanceof Condition && current.aGauche.valeur instanceof Action && current.aDroite.valeur instanceof Action && current.aGauche.valeur.getText().equals(current.aDroite.valeur.getText()))
            return current.aGauche;

        //Si on est sur une condition on continue la simplification sur les conditions suivantes
        if (current.aGauche != null && !(current.aGauche.valeur instanceof Action)) {
            //On ajoute le passage par le noeud actuel à la map .
            //NOTE : on a pas besoin de vérifier si la valeur était déjà présente dans la carte puisque si c'était le cas on aurait déjà simplifié dans la condition value!=null
            map.put(current.valeur.getText(), "Right");
            current.aGauche = simplifierProgrammeRecursif(current.aGauche, map);
            //Une fois qu'on a simplifié à gauche on enleve la valeur de la map
            map.remove(current.valeur.getText());
        }
        if (current.aDroite != null && !(current.aDroite.valeur instanceof Action)) {
            //Meme chose qu'a gauche
            map.put(current.valeur.getText(), "Right");
            current.aDroite = simplifierProgrammeRecursif(current.aDroite, map);
            map.remove(current.valeur.getText());
        }
        return current;
    }

    // Fonction permettant de numéroter les noeuds (ici les conditions) de 1 à nbNoeuds afin de les retrouver par la suite
    public void numerotationNoeud() {
        interneNumerotationNoeud();
        nbTempNoeud = 1;
    }

    private void interneNumerotationNoeud() {
        if (valeur.getClass().getName().equals("util_fourmi.Condition")) {
            id = nbTempNoeud;
            nbTempNoeud++;
            aGauche.interneNumerotationNoeud();
            aDroite.interneNumerotationNoeud();
        }
    }

    // Fonction permettant de numéroter les feuilles (ici les actions) de 1 à nbFeuilles afin de les retrouver par la suite
    public void numerotationFeuille() {
        interneNumerotationFeuille();
        nbTempFeuille = 1;
    }

    private void interneNumerotationFeuille() {
        if (valeur.getClass().getName().equals("util_fourmi.Condition")) {
            aGauche.interneNumerotationFeuille();
            aDroite.interneNumerotationFeuille();
        } else {
            id = nbTempFeuille + 100; // On ajoute temporairement 100 pour différencier les noeuds des feuilles ensuite
            nbTempFeuille++;
        }
    }

    // Fonction permettant de remplacer une condition par une autre afin de muter
    public void remplacerCondition() throws IOException {
        int nbCond = nbConditions();
        //System.out.println("\nNombre de conditions : " + nbCond);
        if (nbCond != 0) {
            int aleatCond = (int) (Math.random() * nbCond) + 1;
            //System.out.println("N° du noeud à modifier : " + aleatCond);
            interneRemplacerCondition(aleatCond);
        }
        simplifierProgramme();
        numerotationNoeud();
        numerotationFeuille();
    }

    private void interneRemplacerCondition(int aleatCond) throws IOException {
        if (id == aleatCond) {
            //System.out.println("Condition à modifier : " + valeur.getText());
            Noeud allConditions[] = recupererCondTab();

            int newNuCond = (int) (Math.random() * 3);
            while (allConditions[newNuCond].getText().equals(valeur.getText())) {
                newNuCond = (int) (Math.random() * 3);
            }
            valeur = allConditions[newNuCond];
        } else {
            if (valeur.getClass().getName().equals("util_fourmi.Condition")) {
                aGauche.interneRemplacerCondition(aleatCond);
                aDroite.interneRemplacerCondition(aleatCond);
            }
        }
    }

    // Fonction permettant de remplacer une action par une autre afin de muter
    public void remplacerAction() throws IOException {
        int nbAct = nbActions();
        int aleatAct = (int) (Math.random() * nbAct) + 101; // A modifier ensuite selon nos choix de numérotation
        interneRemplacerAction(aleatAct);
        simplifierProgramme();
        numerotationNoeud();
        numerotationFeuille();
    }

    private void interneRemplacerAction(int aleatAct) throws IOException {
        if (id == aleatAct) {
            Noeud allActions[] = recupererActTab();

            int newNuAct = (int) (Math.random() * 8);
            while (allActions[newNuAct].getText().equals(valeur.getText())) {
                newNuAct = (int) (Math.random() * 8);
            }
            valeur = allActions[newNuAct];
        } else {
            if (valeur.getClass().getName().equals("util_fourmi.Condition")) {
                aGauche.interneRemplacerAction(aleatAct);
                aDroite.interneRemplacerAction(aleatAct);
            }
        }
    }

    // Fonction permettant d'échanger deux sous arbres d'une condition afin de muter
    public void echangerSousArbres() throws IOException {
        int nbCond = nbConditions();
        if (nbCond != 0) {
            int aleatCond = (int) (Math.random() * nbCond) + 1;
            interneEchangerSousArbres(aleatCond);
        }
        simplifierProgramme();
        numerotationNoeud();
        numerotationFeuille();
    }

    private void interneEchangerSousArbres(int aleatCond) throws IOException {
        if (id == aleatCond) {
            ProgrammeGenetique temp = aGauche;
            aGauche = aDroite;
            aDroite = temp;
        } else {
            if (valeur.getClass().getName().equals("util_fourmi.Condition")) {
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
        if (nbCond1 != 0 && nbCond2 != 0) { // Ce cas ne devrait jamais arriver ensuite puisque la fonction sera appliquée sur les meilleurs arbres génétiques
            int aleatCond1 = (int) (Math.random() * nbCond1) + 1;
            int aleatCond2 = (int) (Math.random() * nbCond2) + 1;
            selectionNoeudAInserer(aleatCond1, aleatCond2, prog2);
        }
        simplifierProgramme();
        numerotationNoeud();
        numerotationFeuille();
    }

    private void selectionNoeudAInserer(int aleatCond1, int aleatCond2, ProgrammeGenetique prog2) throws IOException {
        if (prog2.getId() == aleatCond2) {
            interneCroiserProgrammes(aleatCond1, aleatCond2, prog2);
        } else {
            if (prog2.getNoeud().getClass().getName().equals("util_fourmi.Condition")) {
                selectionNoeudAInserer(aleatCond1, aleatCond2, prog2.getAGauche());
                selectionNoeudAInserer(aleatCond1, aleatCond2, prog2.getADroite());
            }
        }
    }

    private void interneCroiserProgrammes(int aleatCond1, int aleatCond2, ProgrammeGenetique prog2) throws IOException {
        if (id == aleatCond1) {
            valeur = prog2.getNoeud();
            aGauche = prog2.getAGauche();
            aDroite = prog2.getADroite();
        } else {
            if (valeur.getClass().getName().equals("util_fourmi.Condition")) {
                aGauche.interneCroiserProgrammes(aleatCond1, aleatCond2, prog2);
                aDroite.interneCroiserProgrammes(aleatCond1, aleatCond2, prog2);
            }
        }
    }

    // Fonction permettant de cloner l'arbre et d'en créer un nouveau ayant les mêmes valeurs
    public ProgrammeGenetique clone() throws CloneNotSupportedException {
        ProgrammeGenetique p = (ProgrammeGenetique) super.clone();
        if (aGauche != null) {
            p.aGauche = (ProgrammeGenetique) this.aGauche.clone();
            p.aDroite = (ProgrammeGenetique) this.aDroite.clone();
        }
        p.valeur = (Noeud) this.valeur.clone();
        return p;
    }

    // Fonction qui retourne la hauteur de l'arbre
    public int hauteur() {
        if (this.getADroite() == null && this.getAGauche() == null)
            return 0;
        else
            return (1 + Math.max(getAGauche().hauteur(), getADroite().hauteur()));
    }

    // Fonction qui retourne le nombre de conditions présentes dans l'arbre
    public int nbConditions() {
        if (valeur.getClass().getName().equals("util_fourmi.Condition")) {
            return 1 + aGauche.nbConditions() + aDroite.nbConditions();
        } else {
            return 0;
        }
    }

    // Fonction qui retourne le nombre d'actions présentes dans l'arbre
    public int nbActions() {
        return nbNoeudTotal() - nbConditions();
    }

    // Fonction qui retourne le nombre de noeuds (Actions + Conditions) présents dans l'arbre
    public int nbNoeudTotal() {
        if (valeur.getClass().getName().equals("util_fourmi.Condition")) {
            return 1 + aGauche.nbNoeudTotal() + aDroite.nbNoeudTotal();
        } else {
            return 1;
        }
    }

    private Noeud[] recupererCondTab() throws IOException {
        String nomFichierConditions = "Noeuds\\Conditions.txt";

        // On récupère les conditions puis on les met dans une liste
        List<String> listConditions = getLignes(nomFichierConditions);

        // La liste est transformée en tableau contenant des conditions
        Noeud allConditions[] = new Condition[listConditions.size()];

        for (int i = 0; i < listConditions.size(); i++) {
            allConditions[i] = new Condition(listConditions.get(i));
        }

        return allConditions;
    }

    private Noeud[] recupererActTab() throws IOException {
        String nomFichierActions = "Noeuds\\Actions.txt";

        // On récupère les actions puis on les met dans une liste
        List<String> listActions = getLignes(nomFichierActions);

        // La liste est transformée en tableau contenant des actions
        Noeud allActions[] = new Action[listActions.size()];

        for (int i = 0; i < listActions.size(); i++) {
            allActions[i] = new Action(listActions.get(i));
        }

        return allActions;
    }

    // Fonction toString du programme génétique
    public String toString() {
        return interneToString(0, "");
    }

    private String interneToString(int hauteur, String espace) {
        String S = "";
        S += espace + hauteur + "." + valeur.getText() + "\n";
        if (valeur.getClass().getName().equals("util_fourmi.Condition")) {
            S += aGauche.interneToString(hauteur + 1, espace += "  ");
            S += aDroite.interneToString(hauteur + 1, espace);
        }
        return S;
    }
}