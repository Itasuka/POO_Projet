package package_1;

import java.io.Serializable;

public class PasDeReservation extends IllegalArgumentException implements Serializable {
    /** description de la fonction <b>PasDeReservation</b>
     * envoie "vous avez pas de réservation"
     */
    public PasDeReservation(){
        super("Vous n'avez pas de réservations");
    }
}
