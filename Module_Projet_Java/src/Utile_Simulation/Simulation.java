package Utile_Simulation;


/*
Fonctionnement de la classe Simulation :
    - Constructeurs pour la création d'une simulation (initialisatio du monde avec les paramètres définis)
    - La possibilité d’afficher, d’enregistrer (dans un format lisible dans un éditeur de texte, ce qui permettra
      sa modification) et de charger une carte du monde. (méthodes de la classe Monde)

    - La possibilité d’afficher (durant toute l’évolution ou à la fin), d’enregistrer (dans un format lisible dans
      un éditeur de texte, ce qui permettra sa modification) et de charger le comportement d’une fourmi, des
      meilleures fourmis ou de toute la population.

    - La possibilité de tester isolément le comportement d’une fourmi.

    - La possibilité d’afficher les scores (maximum, moyen et minimum) des fourmis au cours de l’évolution.

    - La possibilité d’enregister une évolution pour pouvoir la relancer plus tard (on demande juste l’enregistrement
      des paramètres permettant de relancer l’évolution à l’identique, on ne demande pas l’enregistrement de
      chacune des étapes de l’évolution).

    - Un fichier (modifiable dans un éditeur de texte) contenant l’ensemble des (hyper)paramètres du modèle
      et qui sera passé en paramètre de votre programme pour lancer la simulation.

On a donc un état de simulation avec la génération à laquelle la simulation se trouve (par défaut à 1).

La méthode avancerSimulation fait avancer toutes les fourmis dans le monde suivant nbCoups (définit en paramètre).
La méthode afficherResultats affiche le score de la génération actuelle, en réalisant un classement

 */

import Utile_Monde.Monde;

public class Simulation {
    private Monde monde;
    private int nbGeneration;

    /* Constructeur par défaut. On demande un nombre de fourmis, une hauteur et largeur de carte.
    */
    public Simulation(int hauteur,int largeur,int nbFourmis,int nbCoups){
        this.monde=new Monde(nbFourmis,hauteur,largeur);
        this.nbGeneration = 1;
    }




    public Monde getMonde() {
        return monde;
    }

    public void setMonde(Monde monde) {
        this.monde = monde;
    }



}
