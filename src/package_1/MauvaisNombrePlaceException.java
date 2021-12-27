package package_1;

public class MauvaisNombrePlaceException extends IllegalArgumentException {
    /** description de la fonction <b>MauvaisNombrePlaceException</b>
     * envoie "Le nombre de places est incorrect par rapport à votre statut"
     */
    public MauvaisNombrePlaceException(){
        super("Le nombre de places est incorrect par rapport à votre statut");
    }
}
