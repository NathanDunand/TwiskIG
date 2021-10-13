package twisk.vues;

import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;

public class VueActiviteIG extends VueEtapeIG implements Observateur {
    private Label label;
    private boolean select;

    public VueActiviteIG(MondeIG monde, EtapeIG etapeIG){
        super(monde,etapeIG);
        this.label=new Label(this.etapeIG.toString());
        this.select=false;//par défaut rien de sélectionné

        this.setPrefSize(etapeIG.getLargeur(), etapeIG.getHauteur());
        this.setStyle("-fx-border-color: #0059FF;-fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
        int posX=etapeIG.getPosX();
        int posY=etapeIG.getPosY();
        this.relocate(posX,posY);

        //ajout de l'écouteur qui permet de sélectionner une Activité
        this.setOnMouseClicked(event->selectionnerActivite(event));

        this.setOnDragDetected(event->{
            Dragboard db=startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content=new ClipboardContent();
            //on place l'id dedans
            content.putString(this.etapeIG.getIdentifiant());
            //on place les coordonnées de l'étape dedans
            /*String x=Integer.toString(this.etapeIG.getPosX());
            String y=Integer.toString(this.etapeIG.getPosY());*/

            WritableImage capture=this.snapshot(null,null);
            content.putImage(capture);
            db.setContent(content);
            event.consume();
        });

        this.getChildren().addAll(this.label);
    }

    public void selectionnerActivite(MouseEvent mouse){
        this.select=!this.select;//on inverse
        if(this.select){//set style si une act est sélectionnée
            this.setStyle("-fx-border-color: #00AAAF;-fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;-fx-background-color: LIGHTGREY");
        } else {
            this.setStyle("-fx-border-color: #0059FF;-fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
        }
        mouse.consume();//obligatoire ?

        this.monde.ajouterActiviteSelection(this.etapeIG);
    }
}
