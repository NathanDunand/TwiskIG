package twisk.vues;

import javafx.application.Platform;
import javafx.scene.control.*;
import twisk.exceptions.TwiskException;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;

import java.util.ArrayList;

public class VueMenu extends MenuBar implements Observateur{
    private MondeIG monde;
    private Menu fichier;
    private MenuItem quitter;
    private Menu edition;
    private MenuItem supprimerSelection;
    private MenuItem renomer;
    private MenuItem effacerSelection;
    private Menu mondeMenu;
    private MenuItem entree;
    private MenuItem sortie;
    private Menu param;
    private MenuItem d;
    private MenuItem e;

    public VueMenu(MondeIG monde){
        super();
        this.monde=monde;
        this.monde.ajouterObservateur(this);

        this.fichier=new Menu("Fichier");
        this.quitter=new MenuItem("Quitter");
        this.quitter.setOnAction(event->Platform.exit());
        this.fichier.getItems().add(this.quitter);

        this.edition=new Menu("Edition");
        this.edition.setOnShowing(event->this.reagir());//pour disable ou pas le MenuItem Renommer
        this.supprimerSelection=new MenuItem("Supprimer la sélection");
        this.effacerSelection=new MenuItem("Effacer la sélection");
        this.renomer=new MenuItem("Renommer la sélection");
        this.supprimerSelection.setOnAction(event->this.monde.supprimerSelection());
        this.effacerSelection.setOnAction(event->this.monde.effacerSelection());
        this.renomer.setOnAction(event->{
            TextInputDialog t=new TextInputDialog();
            t.setTitle("Renommer une activité");
            t.setHeaderText("Entrez le nouveau nom ");
            t.showAndWait();
            //récupère le nom entré
            this.monde.renommerActivite(t.getEditor().getText());
        });

        this.mondeMenu=new Menu("Monde");
        this.entree=new MenuItem("Entrée");
        this.entree.setOnAction(e->{this.monde.ajouterEntree();});
        this.sortie=new MenuItem("Sortie");
        this.sortie.setOnAction(e->{this.monde.ajouterSortie();});
        this.mondeMenu.getItems().addAll(this.entree,this.sortie);

        this.param=new Menu("Paramètres");
        this.d=new MenuItem("Délais");
        this.d.setOnAction(e->{
            try {
                TextInputDialog t=new TextInputDialog();
                t.setTitle("Définir le délais");
                t.setHeaderText("Définir le délais");
                t.showAndWait();
                this.monde.setDelais(t.getEditor().getText());
            } catch (TwiskException exc){
                Alert a=new Alert(Alert.AlertType.ERROR, exc.getMessage(), ButtonType.CLOSE);
                a.showAndWait();
            }
        });
        this.e=new MenuItem("Ecart");
        this.e.setOnAction(e->{
            try {
                TextInputDialog t=new TextInputDialog();
                t.setTitle("Définir un écart");
                t.setHeaderText("Définir un écart");
                t.showAndWait();
                this.monde.setEcart(t.getEditor().getText());
            } catch (TwiskException exc){
                Alert a=new Alert(Alert.AlertType.ERROR, exc.getMessage(), ButtonType.CLOSE);
                a.showAndWait();
            }
        });
        this.param.getItems().addAll(this.d,this.e);

        this.edition.getItems().addAll(this.supprimerSelection,this.renomer,this.effacerSelection);

        this.getMenus().addAll(this.fichier,this.edition,this.mondeMenu,this.param);
    }

    @Override
    public void reagir() {
        ArrayList<EtapeIG> al=this.monde.getActiviteSelectionnees();
        if(al.size()==0 || al.size()>1){//s'il n'y a aucune activité sélectionnée
            this.renomer.setDisable(true);//on affiche pas
        } else {
            this.renomer.setDisable(false);//on affiche
        }
    }
}
