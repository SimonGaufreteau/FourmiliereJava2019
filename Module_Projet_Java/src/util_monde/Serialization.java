package util_monde;

import util_fourmi.ProgrammeGenetique;

import java.io.*;

public class Serialization {

    public Serialization() {
    }

    /**
     * Serialisation d'un programme genetique dans un fichier afin de pouvoir le réutiliser
     *
     * @param programmeGenetique c'est le programme qu'on veut enregistrer
     * @param nomFichier         nom du fichier, idéal mettre sous la forme nomFichier.txt
     */


    public static void serialiseProgrammeGenetique(ProgrammeGenetique programmeGenetique, String nomFichier) throws IOException {
        // ouverture d'un flux de sortie vers le fichier et création de ce fichier
        FileOutputStream fos = new FileOutputStream(new File("Module_Projet_Java\\Sauvegardes\\" + nomFichier));

        // création d'un "flux objet" avec le flux fichier
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        try {
            // sérialisation : écriture de l'objet dans le flux de sortie
            oos.writeObject("L'arbre de la fourmi est le suivant : ");
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

    public static void deserialiseProgrammeGenetique(String nomFichier) throws IOException, ClassNotFoundException {
        ProgrammeGenetique programmeGenetique = null;
        // ouverture d'un flux d'entrée depuis le fichier indiqué
        FileInputStream fis = new FileInputStream(String.valueOf(new StringReader("Module_Projet_Java\\Sauvegardes\\" + nomFichier)));

        // création d'un "flux objet" avec le flux fichier
        ObjectInputStream ois = new ObjectInputStream(fis);
        try {
            // désérialisation : lecture de l'objet depuis le flux d'entrée
            programmeGenetique = (ProgrammeGenetique) ois.readObject();
        } finally {
            // on ferme les flux
            try {
                ois.close();
            } finally {
                fis.close();
            }
        }
    }

    public static void deserialiseCarte(String nomFichier) throws IOException, ClassNotFoundException {
        Carte carte = null;
        // ouverture d'un flux d'entrée depuis le fichier indiqué
        FileInputStream fis = new FileInputStream(String.valueOf(new StringReader("Module_Projet_Java\\Sauvegardes\\" + nomFichier)));

        // création d'un "flux objet" avec le flux fichier
        ObjectInputStream ois = new ObjectInputStream(fis);

        try {
            // désérialisation : lecture de l'objet depuis le flux d'entrée
            carte = (Carte) ois.readObject();
        } finally {
            // on ferme les flux
            try {
                ois.close();
            } finally {
                fis.close();
            }
        }
    }
}
