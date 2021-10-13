package twisk.vues;

import javafx.scene.control.Alert;
import twisk.mondeIG.MondeIG;

public class VueAlert extends Alert implements Observateur {
    MondeIG monde;
    String message;

    public VueAlert(MondeIG monde, String message){
        super(AlertType.ERROR);
        this.monde=monde;
        this.message=message;
        this.setTitle("Error");
        this.setContentText(this.message);

        //System.out.println("eeeee");
    }

    @Override
    public void reagir() {

    }
}
