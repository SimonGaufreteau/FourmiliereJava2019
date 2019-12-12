package Utile_Fourmi;

public class ProgrammeGenetique {
    private Noeud valeur;
    private ProgrammeGenetique aGauche;
    private ProgrammeGenetique aDroite;

    public ProgrammeGenetique(Noeud valeur){ // Dans le cas d'un noeud contenant une action, on s'arrête ici
        this.valeur = valeur;
        this.aGauche = null;
        this.aDroite = null;
    }

    public ProgrammeGenetique(Noeud valeur, ProgrammeGenetique aGauche, ProgrammeGenetique aDroite){ // Dans le cas d'un noeud contenant une condition, on crée un fils gauche et droite
        this.valeur = valeur;
        this.aGauche = aGauche;
        this.aDroite = aDroite;
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

    public String getValeurNoeud(){
        return valeur.toString();
    }

    public String getValeurNoeudAGauche(){
        return aGauche.getValeurNoeud();
    }

    public String getValeurNoeudADroite(){
        return aDroite.getValeurNoeud();
    }
}
