package Utile_Fourmi;

import java.io.Serializable;

public class Noeud implements Serializable, Cloneable {
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

    public Noeud clone() throws CloneNotSupportedException {
        Noeud n = (Noeud) super.clone();
        return n;
    }
}
