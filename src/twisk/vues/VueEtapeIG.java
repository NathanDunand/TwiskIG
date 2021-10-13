package twisk.vues;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;

public abstract class VueEtapeIG extends VBox implements Observateur {
    protected MondeIG monde;
    protected EtapeIG etapeIG;
    protected Label label;

    public VueEtapeIG(MondeIG monde, EtapeIG etapeIG){
        super();
        this.monde=monde;
        this.etapeIG=etapeIG;
    }

    @Override
    public void reagir() {
        
    }
}
