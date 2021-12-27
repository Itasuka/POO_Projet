package package_1;


public class PasInscritException extends IllegalArgumentException {
    /** description de la fonction <b>PasInscritException</b>
     * envoie "vous etes pas inscrit"
     */
    public PasInscritException(){
        super("Vous n'ètes pas inscrit");
    }
}
