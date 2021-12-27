package package_1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

public class Table implements Serializable,Comparable<Table> {

    private int numTable;
    private int nombrePlacesLibres;


    public Table(int num){
        this.nombrePlacesLibres=8;
        this.numTable=num;
    }

    /** description de la fonction <b>supprimerPlaces</b>
     * cette fonction permet de supprimer une place du nombre de place libre
     * @param numTable
     * @param places
     */
    public void supprimerPlaces(int numTable, int places) {
        this.nombrePlacesLibres-=places;
    }

    /** description de la fonction <b>ajouterPlaces</b>
     * cette fonction permet d'ajouter une place au nombre de place libre
     * @param numTable
     * @param places
     */
    public void ajouterPlaces(int numTable, int places) {
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
        String s = "Table n°"+numTable+" ayant "+nombrePlacesLibres+" places libres";
        return s;
    }

    @Override
    public int compareTo(Table t){
        if(this.numTable>t.numTable){
            return 1;
        }
        if(this.numTable<t.numTable){
            return -1;
        }
        return 0;
    }
}