package util_monde;

import util_fourmi.ProgrammeGenetique;

import java.io.*;

public class Serialization {

    public Serialization() {
    }

    /**
     * Serialisation d'un programme genetique dans un fichier afin de pouvoir le reutiliser
     * @param programmeGenetique c'est le programme qu'on veut enregistrer
     * @param nomFichier         nom du fichier, ideal mettre sous la forme nomFichier.txt
     */


    public static void serialiseProgrammeGenetique(ProgrammeGenetique programmeGenetique, String nomFichier) throws IOException {
        // ouverture d'un flux de sortie vers le fichier et creation de ce fichier
        FileOutputStream fos = new FileOutputStream(new File("Module_Projet_Java\\Sauvegardes\\" + nomFichier));

        // creation d'un "flux objet" avec le flux fichier
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        try {
            // serialisation : ecriture de l'objet dans le flux de sortie
            //oos.writeObject("L'arbre de la fourmi est le suivant : ");
            oos.writeObject(programmeGenetique.toString());
            // on vide le tampon
            oos.flush();
        } finally {
            //fermeture des flux
            try {
                oos.close();
            } finally {
                fos.close();
            }
        }
    }

    public static ProgrammeGenetique deserialiseProgrammeGenetique(String nomFichier) throws IOException, ClassNotFoundException {
        ProgrammeGenetique programmeGenetique;
        // ouverture d'un flux d'entree depuis le fichier indique
        FileInputStream fis = new FileInputStream("Module_Projet_Java\\Sauvegardes\\" + nomFichier);

        // creation d'un "flux objet" avec le flux fichier
        ObjectInputStream ois = new ObjectInputStream(fis);
        try {
            // deserialisation : lecture de l'objet depuis le flux d'entree
            programmeGenetique = (ProgrammeGenetique) ois.readObject();
            return programmeGenetique;
        } finally {
            // on ferme les flux
            try {
                ois.close();
            } finally {
                fis.close();
            }
        }
    }

    //La serialisation de Carte se fait dans la classe correspondante.
}
