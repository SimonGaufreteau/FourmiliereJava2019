package Utile_Monde;

import Utile_Fourmi.ProgrammeGenetique;

public class Main {

    public static void main (String[] args){
        try{
            ProgrammeGenetique p = new ProgrammeGenetique(10000);
            //System.out.println(p.toString(0," "));
            p.simplifierProgramme();
            System.out.println(p.toString(0," "));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
