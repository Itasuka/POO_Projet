package package_1;

import java.io.Serializable;

public class MauvaisNombrePlaceException extends IllegalArgumentException implements Serializable {
    public MauvaisNombrePlaceException(){
        super("Le nombre de places est incorrect par rapport à votre statut");
    }
}
