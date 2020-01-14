public class Score {
    int point;
    public Score(int point){
        this.point=point;
    }

    public int getPoint() {
        return point;
    }

    public void augmenterScore (int aAjouter){
        point+=aAjouter;
    }

    public Score meilleur_score(Score[] tableau){
        Score max=tableau[0];

        for (int i=1;i<tableau.length;i++){
            if(tableau[i].getPoint()>max.getPoint())
                max=tableau[i];
        }
        return max;
    }
    public Score pire_score(Score[] tableau){
        Score min=tableau[0];

        for (int i=1;i<tableau.length;i++){
            if(tableau[i].getPoint()<min.getPoint())
                min=tableau[i];
        }
        return min;
    }

    public Score score_moyen(Score[] tableau){
        int moyennePoint=0;
        int longueur_tab=tableau.length;
        for (Score score : tableau) {
            moyennePoint += score.getPoint();
        }
        moyennePoint=moyennePoint/longueur_tab;
        return new Score(moyennePoint);
    }
}
