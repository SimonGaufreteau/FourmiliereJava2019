package Interfaces_Global;

import Exceptions_Monde.InvalidDirectionException;

/*
Interface Deplacer. Implémente la méthode "deplacer(int:direction):boolean" avec direction
l'entier correspondant à la direction dans laquelle on se déplace.
Cet entier correspond au sens horaire des directions :
0 : haut (-y)
1: droit (+x)
2 : bas (+y)
3 : gauche (-x)

Renvoie "true" si on peut se déplacer, renvoie "false" sinon.
 */
public interface Deplacer {
    boolean deplacer(char direction) throws InvalidDirectionException;
}
