package twisk.mondeIG;

import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;

public abstract class EtapeIG implements Iterable<PointDeControleIG> {
    private String nom;
    private String identifiant;
    private int posX;
    private int posY;
    private int largeur;
    private int hauteur;
    private ArrayList<PointDeControleIG> pdcigs;
    protected int delais;
    protected int ecart;

    public EtapeIG(String nom, String identifiant, int largeur, int hauteur) {
        this.nom = nom;
        this.identifiant = identifiant;

        Random rand = new Random();
        int maxX = 700;//valeurs max pour placer aléatoirement la partie graphique
        int maxY = 700;
        this.posX = rand.nextInt(maxX);
        this.posY = rand.nextInt(maxY);
        this.largeur = largeur;
        this.hauteur = hauteur;

        //les +-10 servent à ce que les points ne soient pas collés à l'étape, plus joli
        PointDeControleIG h = new PointDeControleIG(this.posX + (this.largeur / 2), this.posY-10, "id", this);
        PointDeControleIG d = new PointDeControleIG(this.posX+10 + this.largeur, this.posY + (this.hauteur / 2), "id", this);
        PointDeControleIG b = new PointDeControleIG(this.posX + (this.largeur / 2), this.posY + this.hauteur+10, "id", this);
        PointDeControleIG g = new PointDeControleIG(this.posX-10, this.posY + (this.hauteur / 2), "id", this);

        this.pdcigs = new ArrayList<PointDeControleIG>(4);
        this.pdcigs.add(h);
        this.pdcigs.add(d);
        this.pdcigs.add(b);
        this.pdcigs.add(g);

        this.delais=0;
        this.ecart=0;
    }

    public void setDelais(String i){
        this.delais=Integer.parseInt(i);
    }

    public void setEcart(String i){
        this.ecart=Integer.parseInt(i);
    }

    @Override
    public Iterator<PointDeControleIG> iterator() {
        ArrayList<PointDeControleIG> al=new ArrayList<PointDeControleIG>();
        for(PointDeControleIG p:this.pdcigs){
            al.add(p);
        }
        return al.iterator();
    }

    public String getNom(){
        return this.nom;
    }

    public String getIdentifiant(){
        return this.identifiant;
    }

    public void setPosX(int x){
        this.posX=x;
        //il faut aussi set les positions des points pour le Drag and Drop
        PointDeControleIG h=this.pdcigs.get(0);
        h.setPosCentreX(this.posX + (this.largeur / 2));
        PointDeControleIG d=this.pdcigs.get(1);
        d.setPosCentreX(this.posX+10 + this.largeur);
        PointDeControleIG b=this.pdcigs.get(2);
        b.setPosCentreX(this.posX + (this.largeur / 2));
        PointDeControleIG g=this.pdcigs.get(3);
        g.setPosCentreX(this.posX-10);
    }

    public void setPosY(int y){
        this.posY=y;

        //il faut aussi set les positions des points pour le Drag and Drop
        PointDeControleIG h=this.pdcigs.get(0);
        h.setPosCentreY(this.posY-10);
        PointDeControleIG d=this.pdcigs.get(1);
        d.setPosCentreY(this.posY + (this.hauteur / 2));
        PointDeControleIG b=this.pdcigs.get(2);
        b.setPosCentreY(this.posY + this.hauteur+10);
        PointDeControleIG g=this.pdcigs.get(3);
        g.setPosCentreY(this.posY + (this.hauteur / 2));
    }

    public void setNom(String s){
        this.nom=s;
    }

    public int getPosX(){
        return this.posX;
    }

    public int getPosY(){
        return this.posY;
    }

    public int getLargeur(){
        return this.largeur;
    }

    public int getHauteur(){
        return this.hauteur;
    }

    public ArrayList<PointDeControleIG> getPdcigs(){
        return this.pdcigs;
    }

    public String toString(){
        return this.nom;
    }
}
