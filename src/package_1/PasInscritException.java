package package_1;

import java.io.Serializable;

public class PasInscritException extends IllegalArgumentException implements Serializable {
    public PasInscritException(){
        super("Vous n'ètes pas inscrit");
    }
}
