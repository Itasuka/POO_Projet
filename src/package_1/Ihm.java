package package_1;

import java.util.Scanner;

public class Ihm {
    public int choisirGroupe1(){
        Scanner saisieUtilisateur = new Scanner(System.in);
        System.out.println("Etes vous un étudiant ou un personnel ?");
        System.out.println("Ecriver 1 pour étudiant et 2 pour personnel");
        int EouP = saisieUtilisateur.nextInt();
        return EouP;
    }
    public boolean ChoisirGroupe(){
        Scanner saisieUtilisateur = new Scanner(System.in);
        int EouP=choisirGroupe1();
        System.out.println("Veiller écrire votre numero d'identification");
        int NumeroDonée = saisieUtilisateur.nextInt();
        if(EouP == 1){
            if(chercherEtudiant(NumeroDonée)){
                return true;
            }
            else {
                return false;
            }
        }else if (EouP == 2){
            if (chercherPersonnel(NumeroDonée)){
                return true;
            }
            else {
                return false;
            }
        }
        return false;//j'ai mis ca car je ne savais pas quoi mettre comme exception ici
    }
    public String PasInscris (){
        Scanner saisieUtilisateur = new Scanner(System.in);
        System.out.println("voulez-vous vous inscrire ou voulez vous quitter l'application ?");
        System.out.println("Ecriver 1 pour vous inscrire et 2 pour quitter l'application");
        int IouA = saisieUtilisateur.nextInt();
        int EouP=choisirGroupe1();
        if(IouA==1){
            if(EouP==1){//inscripition d'un Etudiant qui n'est pas dans la liste
                System.out.println("veuillez saisir votre numero");
                int NumeroEtu = saisieUtilisateur.nextInt();
                System.out.println("veuiller saisir votre nom");
                String NomEtu = saisieUtilisateur.next();
                System.out.println("veuillez saisir votre prenom");
                String PrenomEtu = saisieUtilisateur.next();
                System.out.println("veuillez saisir votre email");
                String EmailEtu = saisieUtilisateur.next();
                System.out.println("veuillez saisir votre tel");
                int telEtu = saisieUtilisateur.nextInt();
                System.out.println("veuillez saisir votre année");
                int AnnéeEtu= saisieUtilisateur.nextInt();
                inscriptionEtudiant(NumeroEtu,NomEtu,PrenomEtu,EmailEtu,telEtu,AnnéeEtu);//faut juste qu'elle soit implementé
            }
            else if(EouP==2){//inscription d'un personnel qui n'est pas dans la liste
                System.out.println("veuillez saisir votre numero");
                int NumeroPer = saisieUtilisateur.nextInt();
                System.out.println("veuiller saisir votre nom");
                String NomPer = saisieUtilisateur.next();
                System.out.println("veuillez saisir votre prenom");
                String PrenomPer = saisieUtilisateur.next();
                System.out.println("veuillez saisir votre email");
                String EmailPer = saisieUtilisateur.next();
                System.out.println("veuillez saisir votre tel");
                int telPer = saisieUtilisateur.nextInt();
                inscriptionPersonnel(NumeroPer,NomPer,PrenomPer,EmailPer,telPer);//faut juste qu'elle soit implementé
            }

        }else if(IouA==2){
            System.exit(0);
        }
    }


}
