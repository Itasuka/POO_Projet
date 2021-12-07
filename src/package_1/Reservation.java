package package_1;

import java.time.LocalDate;

public class Reservation {
    private final LocalDate dateReservation;
    private int numeroTable;
    private int nombrePlaces;
    private double montantReservation;

    /** CONSTRUCTEUR POUR RESERVATION SIMPLE
     *
     * @param nombrePlaces
     */
    Reservation(int nombrePlaces) {
        this.dateReservation=LocalDate.now();
        this.nombrePlaces=nombrePlaces;
    }

    /** CONSTRUCTEUR DE CONFIRMER RESERVATION
     *
     * @param r
     * @param montant
     * @param numeroTable
     */
    Reservation(Reservation r, double montant, int numeroTable){
        this.dateReservation=r.dateReservation;
        this.nombrePlaces=r.nombrePlaces;
        this.montantReservation=montant;
        this.numeroTable=numeroTable;
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public int getNumeroTable() {
        return numeroTable;
    }

    public int getNombrePlaces() {
        return nombrePlaces;
    }
}