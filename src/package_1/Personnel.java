package package_1;

import java.io.Serializable;

public class Personnel extends Particulier implements Serializable {

    public Personnel(int numero,String nom, String prenom, int tel, String email){
        super(numero,nom,prenom,tel,email);
    }

    public int getTarif() {
        return 3;
    }
}

