package package_1;

public class Etudiant extends Particulier {
    private final int annee;

    public Etudiant(int numero, String nom, String prenom, int tel, String email, int annee) {
        super(numero, nom, prenom, tel, email);
        this.annee = annee;
    }

    public int getAnnee(){return annee;}
    public int getTarif() {
        if (annee == 5) {
            return 1;
        }return 2;
    }

    /** description de la fonction <b>toString</b>
     * donne les informations de l'étudiant
     * @return les informations de l'étudiant
     */
    @Override
    public String toString(){
        return super.toString()+this.annee;
    }

}