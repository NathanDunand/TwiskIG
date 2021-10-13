package twisk.vues;

import javafx.beans.Observable;

import java.util.ArrayList;

public abstract class SujetObserve {
    protected ArrayList<Observateur> obs;

    public SujetObserve(){

    }

    public abstract void ajouterObservateur(Observateur o);

    public abstract void notifierObservateurs();
}
