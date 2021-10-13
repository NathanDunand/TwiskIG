package twisk.vues;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import twisk.mondeIG.MondeIG;

public class VueOutils extends TilePane implements Observateur {
    private MondeIG monde;
    private Button button;

    public VueOutils(MondeIG monde){
        super();
        this.monde=monde;
        this.monde.ajouterObservateur(this);
        this.button=new Button("");
        this.button.setTooltip(new Tooltip("Ajouter un élément"));
        Image img=new Image(getClass().getResourceAsStream("/twisk/ressources/plus.png"));
        ImageView icone=new ImageView(img);
        icone.setFitHeight(20);
        icone.setFitWidth(20);
        this.button.setGraphic(icone);
        this.button.setOnAction(event->this.monde.ajouter("Activité"));
        this.getChildren().add(this.button);
    }

    @Override
    public void reagir() {

    }
}
