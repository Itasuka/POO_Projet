package package_1;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Ihm {
    public Scanner scan = new Scanner(System.in);

    /**description de la fonction etudiantOuPersonnel
     * cette fonction permet de savoir si l'utilisateur est un étudiant ou un personnel
     * @return 1 si la personne dis être  un étudiant et 2 si elle dis être  un personnel
     */
    public int etudiantOuPersonnel() {
        while (true) {
            System.out.println("Etes-vous étudiant ou personnel ? Entrez 1 pour étudiant, 2 pour personnel.");
            if (scan.hasNextInt()) {
                return scan.nextInt();
            } else {
                System.out.println("/!\\ Entrez un chiffre /!\\");
            }
        }
    }


    /**description de la fonction identification
     * cette fonction permet de récupérer le numéro de l'utilisateur à comparer
     * @return le numéro d'identificaiton de l'utilisateur
     */
    public int identification() {
        while (true) {
            System.out.println("Veuillez vous identifier avec votre numéro.");
            if (scan.hasNextInt()) {
                return scan.nextInt();
            } else {
                System.out.println("/!\\ Entrez votre numéro /!\\");
            }
        }
    }


    /** description de la fonction inscriptionOuQuitter
     *  cette fonction permet de savoir si l'utilisateur veux s'inscrire ou quitter l'application
     * @return 1 si il veut s'inscrire et 2 si il veut quitter l'application
     */
    public int inscriptionOuQuitter() {
        while (true) {
            System.out.println("Voulez-vous vous inscrire ou quitter ? Entrez 1 pour inscription, 2 pour quitter");
            if (scan.hasNextInt()) {
                return scan.nextInt();
            } else {
                System.out.println("/!\\ Veuillez rentrer 1 pour incription ou 2 pour quitter /!\\");
            }
        }
    }


    /** description de la fonction menuGestion
     * cette fonction permet de savoir si l'utilisateur veux : Gérer les places du dîner, se désinscrire ou quitter l'application
     * @return le choix fais par l'utilisateur
     */
    public int menuGestion() {
        while (true) {
            System.out.println("1 – Gérer les places du dîner 2 – Se désinscrire 3 – Quitter");
            if (scan.hasNextInt()) {
                return scan.nextInt();
            } else {
                System.out.println("/!\\ Veuillez rentrer soit 1 pour gérer les places du dîner, 2 se désinscrire, 3 pour quitter /!\\");
            }
        }
    }


    /** description de la fonction consulterPlanTable
     *Cette fonction permet de demander a l'utilisateur si il veut consulter le plan de table
     * @return la reponse à la question : 1 pour oui et 0 pour non
     */
    public int consulterPlanTable() {
        while (true) {
            System.out.println("Voulez-vous consulter le plan de table ? Entrez 1 pour oui ou 0 pour non");
            if (scan.hasNextInt()) {
                return scan.nextInt();
            } else {
                System.out.println("/!\\ Veuillez rentrer 1 pour oui ou 0 pour non /!\\");
            }
        }
    }


    /** description de la fonction choixTable
     *Cette fonction permet de récupéré le numero de la table choisie
     * @return le numero de la table
     */
    public int choixTable() {
        while (true) {
            System.out.println("Choisissez un numéro de table.");
            if (scan.hasNextInt()) {
                return scan.nextInt();
            } else {
                System.out.println("/!\\ Veuillez rentrer un numéro de table /!\\");
            }
        }
    }


    /**description de la fonction choixPlaces
     * Cette Fonction permet de recupéré un nombre de place choisie
     * @return le nombre de place
     */
    public int choixPlaces() {
        while (true) {
            System.out.println("Choisissez un nombre de place.");
            if (scan.hasNextInt()) {
                return scan.nextInt();
            } else {
                System.out.println("/!\\ Veuillez rentrer un nombre de place /!\\");
            }
        }
    }
}