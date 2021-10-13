package twisk.mondeIG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import twisk.exceptions.MemeClasse;
import twisk.exceptions.PointsIdentiques;
import twisk.exceptions.TwiskException;
import twisk.outils.FabriqueIdentifiant;
import twisk.vues.SujetObserve;
import twisk.vues.Observateur;

public class MondeIG extends SujetObserve implements Iterable<EtapeIG>{

    private HashMap<String, EtapeIG> etapesIG;
    private ArrayList<ArcIG> arcs;
    private ArrayList<EtapeIG> activiteSelectionnees;
    private ArrayList<ArcIG> arcSelectionnees;
    private PointDeControleIG p1;
    private EtapeIG e;
    private ArrayList<EtapeIG> sorties;
    private ArrayList<EtapeIG> entrees;

    public MondeIG() {
        super();
        this.etapesIG = new HashMap<String, EtapeIG>();
        String identifiant = FabriqueIdentifiant.getInstance().getIdentifiantEtape();
        this.etapesIG.put(identifiant, new ActiviteIG("Activité", identifiant, TailleComposants.getInstance().getLargeur(), TailleComposants.getInstance().getHauteur()));
        this.obs = new ArrayList<Observateur>();
        this.arcs = new ArrayList<ArcIG>();
        this.arcSelectionnees = new ArrayList<ArcIG>();
        this.sorties=new ArrayList<EtapeIG>();
        this.entrees=new ArrayList<EtapeIG>();

        this.activiteSelectionnees = new ArrayList<EtapeIG>();
    }

    @Override
    public void ajouterObservateur(Observateur o) {
        this.obs.add(o);
    }

    public void ajouter(String type) {
        String identifiant = FabriqueIdentifiant.getInstance().getIdentifiantEtape();
        if (type.equals("Activité")) {
            this.etapesIG.put(identifiant, new ActiviteIG("Activité default" + identifiant, identifiant, TailleComposants.getInstance().getLargeur(), TailleComposants.getInstance().getHauteur()));
        }
        //debug
        /*Iterator it=this.etapesIG.entrySet().iterator();
        for(Map.Entry<String, EtapeIG> entry:this.etapesIG.entrySet()){
            System.out.println(entry.getValue().toString());
        }*/
        this.notifierObservateurs();
    }

    public void ajouterArc(PointDeControleIG p) throws TwiskException {
        if (this.p1 != null) {//on lie les deux arcs
            if (this.p1 == p) {
                this.p1 = null;
                throw new PointsIdentiques("Les deux points sélectionnés sont identiques");
            } else if (this.p1.getEtapeIG() == p.getEtapeIG()) {//vérification de si les deux points appartiennent à la même Etape
                this.p1 = null;
                throw new MemeClasse("Les deux points appartiennent à la même étape");
            } else {
                this.arcs.add(new ArcIG(this.p1, p));//liaison
            }
            this.p1 = null;
            this.notifierObservateurs();
        } else {
            this.p1 = p;
        }
    }

    public void renommerActivite(String s) {
        if (this.activiteSelectionnees.size() == 1) {
            //on ne peut renommer qu'une seule activité
            EtapeIG e = this.activiteSelectionnees.get(0);
            //on set son nom ici ensuite
            e.setNom(s);
            //on vide l'AL temporaire
            this.activiteSelectionnees.clear();
        }
        this.notifierObservateurs();
    }

    public EtapeIG getEtapeById(String id) {
        return this.etapesIG.get(id);
    }

    public void ajouterSortie(){
        this.sorties.clear();
        this.sorties.addAll(this.activiteSelectionnees);
        /*for(EtapeIG e:this.sorties){
            System.out.println(e.toString());
        }*/
    }

    public void setDelais(String s) throws TwiskException{
        /*if(Integer.parseInt(s)==null){//si sstring non valide
            throw new SetDelaiEcart("Entrée non valide");
        } else {
            this.activiteSelectionnees.get(0).setDelais(s);
        }*/
    }

    public void setEcart(String s) throws TwiskException{
        /*if(Integer.parseInt(s)==null){
            throw new SetDelaiEcart("Entrée non valide");
        } else {
            this.activiteSelectionnees.get(0).setDelais(s);
        }*/
    }

    public void ajouterEntree(){
        this.entrees.clear();
        this.entrees.addAll(this.activiteSelectionnees);
        /*for(EtapeIG e:this.entrees){
            System.out.println(e.toString());
        }*/
    }

    public void supprimerSelection() {
        Iterator<Map.Entry<String, EtapeIG>> it = this.etapesIG.entrySet().iterator();

        //suppression des activités
        while (it.hasNext()) {
            Map.Entry<String, EtapeIG> entry = it.next();
            for (EtapeIG e : this.activiteSelectionnees) {
                if (entry.getKey().equals(e.getIdentifiant())) {
                    //on supprime les activités
                    it.remove();
                }
            }
        }

        //suppression des arcs
        Iterator<ArcIG> it2 = this.iteratorArc();
        while (it2.hasNext()) {
            ArcIG aIG = it2.next();
            for(ArcIG a:this.arcSelectionnees){
                //je préfère ça à un long if
                if(aIG.getP1().getPosCentreX()==a.getP1().getPosCentreX() && aIG.getP1().getPosCentreY()==a.getP1().getPosCentreY()){
                    //si les deux points de départ des deux arcs sont identiques ...
                    if(aIG.getP2().getPosCentreX()==a.getP2().getPosCentreX() && aIG.getP2().getPosCentreY()==a.getP2().getPosCentreY()){
                        //... et les deux points d'arrivé -> alors il faut supprimer
                        this.arcs.remove(a);
                    }
                }
            }
        }

        //on vide l'AL temporaire
        this.activiteSelectionnees.clear();
        this.arcSelectionnees.clear();
        this.notifierObservateurs();
    }

    public void effacerSelection(){
        this.activiteSelectionnees.clear();
        this.arcSelectionnees.clear();
        this.notifierObservateurs();
    }

    public void ajouterActiviteSelection(EtapeIG eIG) {
        //il faut veiller à ce qu'un élément dans l'AL n'y figure pas 2 fois
        //en cas de sélection-désélection un élément ne DOIT PAS y figurer deux fois
        boolean flag = true;
        int i = 0;
        Iterator<EtapeIG> it = this.activiteSelectionnees.iterator();
        while (it.hasNext()) {
            EtapeIG e = it.next();
            if (e.getIdentifiant().equals(eIG.getIdentifiant())) {
                //s'il y a deux fois le même ID alors on ajoutera pas
                flag = false;

                //si un élèm qui est deux fois, ça veut dire qu'on a double cliqué dessus
                //donc on l'a sélectionné puis désélectionné, il faut donc le retirer de l'AL
                it.remove();
            }
            i++;
        }
        if (flag) {//il n'y a pas deux fois le même ID dans l'AL
            //on peut ajouter
            this.activiteSelectionnees.add(eIG);
        }
    }

    public void ajouterArcSelection(ArcIG aIG) {
        this.arcSelectionnees.add(aIG);
        System.out.println(this.arcSelectionnees.size());
        /*//il faut veiller à ce qu'un élément dans l'AL n'y figure pas 2 fois
        //en cas de sélection-désélection un élément ne DOIT PAS y figurer deux fois
        boolean flag = true;
        int i = 0;
        System.out.println("la liste des arcs : ");
        Iterator<ArcIG> it = this.iteratorArc();
        while(it.hasNext()){
            ArcIG a=it.next();
            System.out.print(a.toString());
            if(a.getIdentifiant().equals(aIG.getIdentifiant())){
                //s'il y a deux fois le même ID alors on ajoutera pas
                flag = false;

                //si un élèm qui est deux fois, ça veut dire qu'on a double cliqué dessus
                //donc on l'a sélectionné puis désélectionné, il faut donc le retirer de l'AL
                it.remove();
            }
            i++;
        }
        if (flag) {//il n'y a pas deux fois le même ID dans l'AL
            //on peut ajouter
            this.arcSelectionnees.add(aIG);
            //System.out.println("nb : "+this.arcSelectionnees.size());
        }
        System.out.println("\n");*/
    }

    public void retirerArcSelection(ArcIG aIG){
        this.arcSelectionnees.remove(aIG);
        System.out.println(this.arcSelectionnees.size());
    }

    public void notifierObservateurs() {
        for (Observateur o : this.obs) {
            o.reagir();
        }
    }

    public ArrayList<EtapeIG> getActiviteSelectionnees() {
        return this.activiteSelectionnees;
    }

    public ArrayList<ArcIG> getArcs(){
        return this.arcs;
    }

    @Override
    public Iterator<EtapeIG> iterator() {
        return this.etapesIG.values().iterator();
    }

    public Iterator<ArcIG> iteratorArc(){
        return this.arcSelectionnees.iterator();
    }

    public String toString(){
        StringBuilder s=new StringBuilder();
        for(EtapeIG e : this){
            s.append(e.toString()+"\n");
        }
        return s.toString();
    }


}
