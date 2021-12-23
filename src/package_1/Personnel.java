package package_1;

public class Personnel extends Particulier {

    public Personnel(int numero,String nom, String prenom, int tel, String email){
        super(numero,nom,prenom,tel,email);
    }

    public int getTarif() {
        int tarif = 3;
        return tarif;
    }
}

