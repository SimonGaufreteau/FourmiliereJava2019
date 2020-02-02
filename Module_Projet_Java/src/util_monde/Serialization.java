package util_monde;

import util_fourmi.ProgrammeGenetique;

import java.io.*;

public class Serialization {

    public Serialization() {
    }

    /**
     * Serialisation d'un programme genetique dans un fichier afin de pouvoir le r�utiliser
     *
     * @param programmeGenetique c'est le programme qu'on veut enregistrer
     * @param nomFichier         nom du fichier, id�al mettre sous la forme nomFichier.txt
     */


    public static void serialiseProgrammeGenetique(ProgrammeGenetique programmeGenetique, String nomFichier) throws IOException {
        // ouverture d'un flux de sortie vers le fichier et cr�ation de ce fichier
        FileOutputStream fos = new FileOutputStream(new File("Module_Projet_Java\\Sauvegardes\\" + nomFichier));

        // cr�ation d'un "flux objet" avec le flux fichier
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        try {
            // s�rialisation : �criture de l'objet dans le flux de sortie
            //oos.writeObject("L'arbre de la fourmi est le suivant : ");
            oos.writeObject(programmeGenetique);
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
        // ouverture d'un flux d'entr�e depuis le fichier indiqu�
        FileInputStream fis = new FileInputStream("Module_Projet_Java\\Sauvegardes\\" + nomFichier);

        // cr�ation d'un "flux objet" avec le flux fichier
        ObjectInputStream ois = new ObjectInputStream(fis);
        try {
            // d�s�rialisation : lecture de l'objet depuis le flux d'entr�e
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
