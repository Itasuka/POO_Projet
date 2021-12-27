package package_1;

import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

public class Table {

    private int numTable;
    private int nombrePlacesLibres;


    public Table(int num){
        this.nombrePlacesLibres=8;
        this.numTable=num;
    }

    public void supprimerPlaces(int numTable, int places) {
        this.nombrePlacesLibres-=places;
    }

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
}