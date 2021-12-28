package package_1;

import java.io.Serializable;

public abstract class Particulier implements Comparable<Particulier>, Serializable {
    private final int numero;
    private final String nom;
    private final String prenom;
    private final int tel;
    private final String email;

    public Particulier(int numero, String nom, String prenom, int tel, String email){
        this.numero=numero;
        this.nom=nom;
        this.prenom=prenom;
        this.tel=tel;
        this.email=email;
    }

    public abstract int getTarif();

    @Override
    public int compareTo(Particulier o) {
        return this.numero-o.numero;
    }

    public int getNumero() { return numero; }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getTel() {
        return tel;
    }

    public String getEmail() {
        return email;
    }

    /**
     *
     * @return l'état d'un particulier sous la forme d'un String
     */
    @Override
    public String toString(){
        String s="";
        return s+=this.numero+" "+this.nom+" "+this.prenom+" "+this.tel+" "+this.email;
    }

}