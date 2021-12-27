package package_1;

import java.io.Serializable;

public class PasDeReservation extends IllegalArgumentException implements Serializable {
    public PasDeReservation(){
        super("Vous n'avez pas de réservations");
    }
}
