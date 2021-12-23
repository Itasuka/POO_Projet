package package_1;

public class PlusDeTempsException extends IllegalArgumentException {
    public PlusDeTempsException(){
        super("Désinscription dans un délai de moins de 10 jours avant la date du gala impossible");
    }
}
