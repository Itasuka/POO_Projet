package package_1;

import java.io.Serializable;

public class Table implements Serializable,Comparable<Table> {

    private final int numTable;
    private int nombrePlacesLibres;


    public Table(int num){
        this.nombrePlacesLibres=8;
        this.numTable=num;
    }

    /** description de la fonction <b>supprimerPlaces</b>
     * cette fonction permet de supprimer une place du nombre de place libre

     * @param places le nombres de places que l'on doit retirer à la table
     */
    public void supprimerPlaces(int places) {
        this.nombrePlacesLibres-=places;
    }

    /** description de la fonction <b>ajouterPlaces</b>
     * cette fonction permet d'ajouter une place au nombre de place libre
     * @param places le nombres de places que l'on doit remettre à la table
     */
    public void ajouterPlaces(int places) {
        this.nombrePlacesLibres+=places;
    }

    public int getNombrePlacesLibres() {
        return nombrePlacesLibres;
    }

    public int getNumTable() {
        return numTable;
    }

    /**
     *
     * @return l'état d'une table sous la forme d'un String
     */
    public String toString(){
        return "Table n°"+numTable+" ayant "+nombrePlacesLibres+" places libres";
    }

    @Override
    public int compareTo(Table t){
        return Integer.compare(this.numTable, t.numTable);
    }


}