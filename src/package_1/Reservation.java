package package_1;

import java.io.Serializable;
import java.time.LocalDate;

public class Reservation implements Serializable {
    private final int ANNEE;
    private final int MOIS;
    private final int JOUR;
    private int numeroTable;
    private int nombrePlaces;
    private double montantReservation;

    /** CONSTRUCTEUR POUR PREMIER RESERVATION DES ETUDIANTS
     *
     * @param nombrePlaces
     */
    public Reservation(int nombrePlaces, double montant) {
        this.ANNEE=LocalDate.now().getYear();
        this.MOIS=LocalDate.now().getMonthValue();
        this.JOUR=LocalDate.now().getDayOfMonth();
        this.nombrePlaces=nombrePlaces;
        this.montantReservation=montant;
    }

    /** CONSTRUCTEUR POUR FINALISER LA RESERVATION
     *
     * @param r
     * @param montant
     * @param numeroTable
     */
    public Reservation(Reservation r, double montant, int numeroTable){
        this.ANNEE=r.ANNEE;
        this.MOIS=r.MOIS;
        this.JOUR=r.JOUR;
        this.nombrePlaces=r.nombrePlaces;
        this.montantReservation=montant;
        this.numeroTable=numeroTable;
    }

    /**
     *
     * @return l'état d'une réservation sous la forme d'un String
     */
    public String toString(){
        String s="";
        if (numeroTable==0){
            s = "faite le "+JOUR+"/"+MOIS+"/"+ANNEE+", table indéfini pour "+nombrePlaces+" personne(s) s'élevant à un montant de "+montantReservation+"€";
        }else{
            s = "faite le "+JOUR+"/"+MOIS+"/"+ANNEE+", table n°"+numeroTable+" pour "+nombrePlaces+" personne(s) s'élevant à un montant de "+montantReservation+"€";
        }
        return s;
    }

    public LocalDate getDateReservation() {
        return LocalDate.of(ANNEE,MOIS,JOUR);
    }

    public int getNumeroTable() {
        return numeroTable;
    }

    public int getNombrePlaces() {
        return nombrePlaces;
    }
}