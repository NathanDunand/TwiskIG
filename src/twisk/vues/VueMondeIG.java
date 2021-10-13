package twisk.vues;

import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import twisk.mondeIG.ArcIG;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import twisk.mondeIG.PointDeControleIG;

import java.util.ArrayList;
import java.util.Iterator;

public class VueMondeIG extends Pane implements Observateur {
    private MondeIG monde;
    private EtapeIG e;

    public VueMondeIG(MondeIG monde){
        super();
        this.monde=monde;
        this.monde.ajouterObservateur(this);
        Iterator<EtapeIG> it=this.monde.iterator();
        while(it.hasNext()){//ajout de toutes les Etapes du Monde
            /*Label label=new Label(it.next().toString());
            HBox box=new HBox();
            this.getChildren().addAll(label,box);*/
            this.e= it.next();
            VueEtapeIG vueEtapeIG=new VueActiviteIG(this.monde,this.e);
            this.getChildren().add(vueEtapeIG);
        }
        this.setOnDragOver(event->{
            Dragboard db=event.getDragboard();
            event.acceptTransferModes(TransferMode.MOVE);
            event.consume();
        });

        this.setOnDragDropped(event->{
            Dragboard db=event.getDragboard();
            String s= (String) db.getString();
            //déplacement véritable de l'activité
            EtapeIG e=this.monde.getEtapeById(s);
            e.setPosX((int)event.getX()-e.getLargeur()/2);
            e.setPosY((int)event.getY()-e.getHauteur()/2);

            event.setDropCompleted(true);
            event.consume();
            this.reagir();
        });
    }

    @Override
    public void reagir() {
        this.getChildren().clear();
        //les arcs
        ArrayList<ArcIG> arcs=this.monde.getArcs();
        int i=0;
        for(ArcIG a:arcs){
            VueArcIG vag=new VueArcIG(this.monde,a);
            this.getChildren().add(vag);
        }

        //afficher toutes les étapes
        for(EtapeIG e:this.monde){
            for(PointDeControleIG pt:e){//pour chaque ptdeControle
                VuePointDeControleIG vuePt=new VuePointDeControleIG(this.monde,pt);
                this.getChildren().add(vuePt);
            }
            VueEtapeIG vueEtapeIG=new VueActiviteIG(this.monde,e);
            this.getChildren().add(vueEtapeIG);
        }
    }
}