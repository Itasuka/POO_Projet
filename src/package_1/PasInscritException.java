package package_1;

public class PasInscritException extends IllegalArgumentException {
    public PasInscritException(){
        super("Vous n'ètes pas inscrit");
    }
}
