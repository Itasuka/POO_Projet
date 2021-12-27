package package_1;

import java.io.Serializable;

public class PlusDeTempsException extends IllegalArgumentException implements Serializable {
    public PlusDeTempsException(){
        super("Désinscription dans un délai de moins de 10 jours avant la date du gala impossible");
    }
}
