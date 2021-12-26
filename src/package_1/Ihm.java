package package_1;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Ihm {
    public Scanner scan = new Scanner(System.in);

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