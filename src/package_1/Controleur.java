package package_1;

import java.io.IOException;
import java.time.LocalDate;

public class Controleur {
    private Gala leGala;
    private final Ihm leIhm;

    public Controleur(LocalDate dateGala) throws IOException, ClassNotFoundException {
        ServiceStockage sS = new ServiceStockage();
        leGala = (Gala) sS.charger();
        if(leGala==null){
            leGala = new Gala(dateGala);
        }
        leIhm = new Ihm();
        leGala.avancerLaQueue();
        System.out.println(leGala);
        lancerAppli();
        sS.enregistrer(leGala);
        System.out.println("Sauvegarde effectuée, FIN de l'appli");
    }

    public void lancerAppli() {
        boolean flag1 = true;
        while (flag1) {
            int type = leIhm.etudiantOuPersonnel();
            if (type == 2) {
                //C'est un personnel
                boolean flag2 = true;
                while (flag2) {
                    int numero = leIhm.identification();
                    Personnel pers = leGala.persExiste(numero);
                    if (pers != null) {
                        boolean flag3 = true;
                        while (flag3) {
                            if (leGala.persInscrit(numero)) {
                                boolean flag4 = true;
                                while (flag4) {
                                    //Gestion diner
                                    int menu = leIhm.menuGestion();
                                    if (menu == 1) {
                                        if (leGala.getLesReservations().containsKey(pers)) {
                                            int nbplacereserv = leGala.getLesReservations().get(pers).getNombrePlaces();
                                            int numTablereserv = leGala.getLesReservations().get(pers).getNumeroTable();
                                            System.out.println("Votre réservation est pour " + nbplacereserv + " à la table n°" + numTablereserv);
                                            flag3 = false;
                                            flag2 = false;
                                            break;
                                        } else {
                                            boolean flag5=true;
                                            while(flag5){
                                                int plan = leIhm.consulterPlanTable();
                                                if (plan == 1) {
                                                    System.out.println(leGala.afficherPlanTable(leGala.getLesTablesPerso()));
                                                    boolean flag6 = true;
                                                    while (flag6) {
                                                        int table = leIhm.choixTable();
                                                        if(table==0){
                                                            flag5 = false;
                                                            break;
                                                        }
                                                        System.out.println(leGala.afficherNbPlacesPossible(pers));
                                                        int nbPlaces = leIhm.choixPlaces();
                                                        try {
                                                            leGala.creerReservation(pers, nbPlaces, table);
                                                            System.out.println("Réservation effectué, FIN");
                                                            flag5 = false;
                                                            flag4 = false;
                                                            flag3 = false;
                                                            flag2 = false;
                                                            break;
                                                        } catch (MauvaiseTableException | MauvaisNombrePlaceException e) {
                                                            System.out.println(e.getMessage());
                                                        } catch (PlusDePlaceDispoException e) {
                                                            System.out.println(e.getMessage());
                                                            flag5 = false;
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (plan == 0) {
                                                    boolean flag6 = true;
                                                    while (flag6) {
                                                        System.out.println(leGala.afficherNbPlacesPossible(pers));
                                                        int nbPlaces = leIhm.choixPlaces();
                                                        try {
                                                            leGala.creerReservation(pers, nbPlaces, leGala.trouverUneTable(pers, nbPlaces));
                                                            System.out.println("Réservation effectué à la table numéro "+leGala.getLesReservations().get(pers).getNumeroTable()+", FIN");
                                                            flag5 = false;
                                                            flag4 = false;
                                                            flag3 = false;
                                                            flag2 = false;
                                                            break;
                                                        } catch (MauvaisNombrePlaceException | MauvaiseTableException e) {
                                                            System.out.println(e.getMessage());
                                                        } catch (PlusDePlaceDispoException e){
                                                            System.out.println(e.getMessage());
                                                            flag5 = false;
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else if (menu == 2) {
                                        try {
                                            leGala.supprimerReservation(pers);
                                            System.out.println("La réservation à votre nom a été supprimée");
                                            leGala.desinscrire(pers);
                                            System.out.println("Désinscription effectué, FIN");
                                        } catch (PasDeReservation e) {
                                            System.out.println(e.getMessage() + ", Désincription effectuée, FIN");
                                            leGala.desinscrire(pers);
                                        } catch (PlusDeTempsException e) {
                                            System.out.println(e.getMessage() + ", FIN");
                                        }
                                        flag4 = false;
                                        flag3 = false;
                                        flag2 = false;
                                    } else if (menu == 3) {
                                        flag4 = false;
                                        flag3 = false;
                                        flag2 = false;
                                    } else {
                                        System.out.println("Le numéro entré n'est pas valide");
                                    }
                                }
                            } else {
                                int iOuQ = leIhm.inscriptionOuQuitter();
                                if (iOuQ == 1) {
                                    leGala.inscriptionPersonnel(numero, pers.getNom(), pers.getPrenom(), pers.getTel(), pers.getEmail());
                                }
                                else if (iOuQ == 2) {
                                    flag3 = false;
                                    flag2 = false;
                                } else {
                                    System.out.println("Le numéro entré n'est pas valide");
                                }
                            }
                        }
                    } else {
                        System.out.println("Le numéro entré n'est pas valide");
                    }
                }
            } else if (type == 1) {
                //C'est un etudiant
                boolean flag2 = true;
                while (flag2) {
                    int numero = leIhm.identification();
                    Etudiant etu = leGala.etuExiste(numero);
                    if (etu != null) {
                        boolean flag3 = true;
                        while (flag3) {
                            if (leGala.etuInscrit(numero)) {
                                //Gestion diner (à suivre)
                                boolean flag4 = true;
                                while (flag4) {
                                    //Gestion diner
                                    int menu = leIhm.menuGestion();
                                    if (menu == 1) {
                                        if (leGala.getLesReservations().containsKey(etu)) {
                                            int nbplacereserv = leGala.getLesReservations().get(etu).getNombrePlaces();
                                            if(leGala.getEtudiantDemandeAttente().contains(etu)){
                                                System.out.println("Votre demande pour "+nbplacereserv+" places est toujours en attente");
                                            }
                                            else if(leGala.getEtudiantDemandeAcceptee().contains(etu)){
                                                boolean flag5=true;
                                                while (flag5){
                                                    int plan=leIhm.consulterPlanTable();
                                                    if(plan==1){
                                                        System.out.println(leGala.afficherPlanTable(leGala.getLesTablesEtu()));
                                                        System.out.println("Vous avez demandé "+leGala.getLesReservations().get(etu).getNombrePlaces()+" places");
                                                        boolean flag6 = true;
                                                        while (flag6) {
                                                            int table = leIhm.choixTable();
                                                            if(table==0){
                                                                flag5 = false;
                                                                break;
                                                            }
                                                            else{
                                                                try {
                                                                    leGala.confirmerReservation(etu,leGala.getLesReservations().get(etu),table);
                                                                    System.out.println("Réservation confirmée, FIN");
                                                                    flag5 = false;
                                                                    flag4 = false;
                                                                    flag3 = false;
                                                                    flag2 = false;
                                                                    break;
                                                                } catch (MauvaiseTableException | MauvaisNombrePlaceException e) {
                                                                    System.out.println(e.getMessage());
                                                                } catch (PlusDePlaceDispoException e) {
                                                                    System.out.println(e.getMessage());
                                                                    flag5 = false;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }if(plan==0){
                                                        int table = leGala.trouverUneTable(etu, leGala.getLesReservations().get(etu).getNombrePlaces());
                                                        try {
                                                            leGala.confirmerReservation(etu,leGala.getLesReservations().get(etu),table);
                                                        } catch (MauvaiseTableException e) {
                                                            System.out.println(e.getMessage());
                                                        } catch (PlusDePlaceDispoException e){
                                                            System.out.println(e.getMessage());
                                                            break;
                                                        }
                                                        System.out.println("Réservation confirmée à la table numéro "+leGala.getLesReservations().get(etu).getNumeroTable()+", FIN");
                                                        flag5 = false;
                                                        flag4 = false;
                                                        flag3 = false;
                                                        flag2 = false;
                                                        break;
                                                    }
                                                }
                                            }
                                            else{
                                                int numTablereserv = leGala.getLesReservations().get(etu).getNumeroTable();
                                                System.out.println("Votre réservation est pour " + nbplacereserv + " à la table n°" + numTablereserv);
                                            }
                                            flag3 = false;
                                            flag2 = false;
                                            break;
                                        }
                                        //Gros pavé d'en dessous
                                        else {
                                            boolean flag5 = true;
                                            while (flag5) {
                                                System.out.println(leGala.afficherNbPlacesPossible(etu));
                                                int nbPlaces = leIhm.choixPlaces();
                                                try {
                                                    leGala.creerReservation(etu, nbPlaces);
                                                    System.out.println("Réservation effectué, il faut la valider dans le derniers mois avant le gala, FIN");
                                                    flag4 = false;
                                                    flag3 = false;
                                                    flag2 = false;
                                                    break;
                                                } catch (MauvaisNombrePlaceException | PlusDePlaceDispoException e) {
                                                    System.out.println(e.getMessage());
                                                }
                                            }
                                        }
                                    } else if (menu == 2) {
                                            try {
                                                leGala.supprimerReservation(etu);
                                                System.out.println("La réservation à votre nom a été supprimée");
                                                leGala.desinscrire(etu);
                                                System.out.println("Désinscription effectué, FIN");
                                            } catch (PasDeReservation e) {
                                                System.out.println(e.getMessage() + ", Désincription effectuée, FIN");
                                                leGala.desinscrire(etu);
                                            } catch (PlusDeTempsException e) {
                                                System.out.println(e.getMessage() + ", FIN");
                                        }
                                            flag4 = false;
                                            flag3 = false;
                                            flag2 = false;
                                    } else if (menu == 3) {
                                        flag4 = false;
                                        flag3 = false;
                                        flag2 = false;
                                    } else {
                                        System.out.println("Le numéro entré n'est pas valide");
                                    }
                                }
                            } else {
                                int iOuQ = leIhm.inscriptionOuQuitter();
                                if (iOuQ == 1) {
                                    leGala.inscriptionEtudiant(numero, etu.getNom(), etu.getPrenom(), etu.getTel(), etu.getEmail(), etu.getAnnee());
                                }
                                else if (iOuQ == 2) {
                                    flag3 = false;
                                    flag2 = false;
                                } else {
                                    System.out.println("Le numéro entré n'est pas valide");
                                }
                            }
                        }
                    } else {
                        System.out.println("Le numero entré n'est pas valide");
                    }
                }
            }
            else if (type ==3){
                break;
            }
            else {
                System.out.println("Le numéro entré n'est pas valide");
            }
        }
    }

}
