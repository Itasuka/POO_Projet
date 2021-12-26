package package_1;

public class MauvaisNombrePlaceException extends IllegalArgumentException {
    public MauvaisNombrePlaceException(){
        super("Le nombre de places est incorrect par rapport à votre statut");
    }
}
