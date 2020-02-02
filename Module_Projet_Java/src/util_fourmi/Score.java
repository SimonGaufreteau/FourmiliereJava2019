package util_fourmi;

public class Score {
    int point;


    public Score(){}
    public Score(int point){
        this.point=point;
    }

    public int getPoint() {
        return point;
    }

    public void augmenterScore (int aAjouter){
        point+=aAjouter;
    }

    public Score meilleurScore(Score[] tableau){
        Score max=tableau[0];

        for (int i=1;i<tableau.length;i++){
            if(tableau[i].getPoint()>max.getPoint())
                max=tableau[i];
        }
        return max;
    }
    public Score pireScore(Score[] tableau){
        Score min=tableau[0];

        for (int i=1;i<tableau.length;i++){
            if(tableau[i].getPoint()<min.getPoint())
                min=tableau[i];
        }
        return min;
    }

    public Score scoreMoyen(Score[] tableau){
        int moyennePoint=0;
        int longueur_tab=tableau.length;
        for (Score score : tableau) {
            moyennePoint += score.getPoint();
        }
        moyennePoint=moyennePoint/longueur_tab;
        return new Score(moyennePoint);
    }

    @Override
    public String toString() {
        return "point=" + point;
    }
}
