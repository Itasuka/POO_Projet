package package_1;

import java.io.Serializable;

public class PlusDeTempsException extends IllegalArgumentException implements Serializable {
    /** description de la fonction <b>PlusDeTempsException</b>
     * envoie "Désinscription dans un délai de moins de 10 jours avant la date du gala impossible" si l'exception est appelée
     */
    public PlusDeTempsException(){
        super("Désinscription dans un délai de moins de 10 jours avant la date du gala impossible");
    }
}
