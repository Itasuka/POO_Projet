package package_1;

import java.io.Serializable;

public class MauvaiseTableException extends IllegalArgumentException implements Serializable {
    public MauvaiseTableException(){
        super("Le numéro de table n'est pas valide");
    }
}
