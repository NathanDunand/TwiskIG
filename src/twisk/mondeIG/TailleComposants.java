package twisk.mondeIG;

public class TailleComposants {
    private static TailleComposants instance=new TailleComposants();
    int largeur;
    int hauteur;

    int hauteurLabel;

    int fleche;

    private TailleComposants(){
        //centralisation des dimensions
        this.hauteur=50;
        this.largeur=120;

        this.hauteurLabel=20;//hauteur du label sur l'interface graphique

        this.fleche=20;//taille de la flèche pour les arcs
    }

    public static TailleComposants getInstance(){
        return instance;
    }

    public int getHauteur(){
        return this.hauteur;
    }

    public int getLargeur(){
        return this.largeur;
    }

    public int getHauteurLabel(){
        return this.hauteurLabel;
    }

    public int getFleche(){
        return this.fleche;
    }
}
