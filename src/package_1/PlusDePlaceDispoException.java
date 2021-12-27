package package_1;

public class PlusDePlaceDispoException extends IllegalArgumentException {
    /** description de la fonction <b>PlusDePlaceDispoException</b>
     * envoie "Il n'y a plus de places" si l'exception est appelée
     */
    public PlusDePlaceDispoException(){
        super("Il n'y a plus de places");
    }
}
