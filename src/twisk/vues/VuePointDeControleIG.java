package twisk.vues;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import twisk.exceptions.TwiskException;
import twisk.mondeIG.MondeIG;
import twisk.mondeIG.PointDeControleIG;

public class VuePointDeControleIG extends Circle implements Observateur {
    private MondeIG monde;
    private PointDeControleIG pt;

    public VuePointDeControleIG(MondeIG monde, PointDeControleIG pt){
        super();
        this.monde=monde;
        this.pt=pt;

        this.setCenterX(this.pt.getPosCentreX());
        this.setCenterY(this.pt.getPosCentreY());
        this.setRadius(7);
        this.setFill(Color.DARKCYAN);
        this.setOnMouseClicked(event -> {
            try {
                this.monde.ajouterArc(this.pt);
            } catch (TwiskException e) {
                Alert a=new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
                a.showAndWait();
            }
        });
    }

    @Override
    public void reagir() {

    }
}
