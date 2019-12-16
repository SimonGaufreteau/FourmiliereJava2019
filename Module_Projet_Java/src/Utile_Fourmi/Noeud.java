package Utile_Fourmi;

import java.io.Serializable;

public class Noeud implements Serializable {
    protected String text;

    public Noeud(String text) {
        this.text = text;
    }

    public String getText () {
        return text;
    }

    public String toString() {
        return text;
    }
}
