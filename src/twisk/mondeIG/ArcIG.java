package twisk.mondeIG;

import twisk.outils.FabriqueIdentifiant;

public class ArcIG {
    private PointDeControleIG p1;
    private PointDeControleIG p2;
    private String id;

    public ArcIG(PointDeControleIG p1, PointDeControleIG p2){
        this.p1=p1;
        this.p2=p2;
        this.id=FabriqueIdentifiant.getInstance().getIdentifiantArc();
    }

    public PointDeControleIG getP1(){
        return this.p1;
    }

    public PointDeControleIG getP2(){
        return this.p2;
    }

    public String getIdentifiant(){
        return this.id;
    }

    public String toString(){
        return this.id;
    }
}
