package package_1;

import java.io.Serializable;

public class PlusDePlaceDispoException extends IllegalArgumentException implements Serializable {
    public PlusDePlaceDispoException(){
        super("Il n'y a plus de places");
    }
}
