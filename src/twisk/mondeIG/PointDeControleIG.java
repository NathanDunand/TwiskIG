package twisk.mondeIG;

public class PointDeControleIG {
    private int posCentreX;
    private int posCentreY;
    private String id;
    private EtapeIG e;

    public PointDeControleIG(int posCentreX, int posCentreY, String id, EtapeIG e){
        this.posCentreX=posCentreX;//coordonnées du point
        this.posCentreY=posCentreY;
        this.id=id;
        this.e=e;
    }

    public int getPosCentreX(){
        return this.posCentreX;
    }

    public int getPosCentreY(){
        return this.posCentreY;
    }

    public void setPosCentreX(int x){
        this.posCentreX=x;
    }

    public void setPosCentreY(int y){
        this.posCentreY=y;
    }

    public String getId(){
        return this.id;
    }

    public EtapeIG getEtapeIG(){
        return this.e;
    }
}
