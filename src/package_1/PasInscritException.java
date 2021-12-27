package package_1;

import java.io.Serializable;

public class PasInscritException extends IllegalArgumentException implements Serializable {
    /** description de la fonction <b>PasInscritException</b>
     * envoie "vous etes pas inscrit si l'exception est appelée"
     */
    public PasInscritException(){
        super("Vous n'ètes pas inscrit");
    }
}
