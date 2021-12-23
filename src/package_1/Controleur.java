package package_1;

import java.io.FileNotFoundException;
import java.time.LocalDate;

public class Controleur {
    private int numeroEtu=0;
    private int type=0;
    private Gala leGala;
    private Ihm leIhm;

    public Controleur(LocalDate dateGala) throws FileNotFoundException {
        leGala=new Gala(dateGala);
        leIhm=new Ihm();
        System.out.println(leGala);
        lancerAppli();
    }

    public void lancerAppli(){
        boolean flag1=true;
        while(flag1){
            type=leIhm.etudiantOuPersonnel();
            if(type==2){
                //C'est un personnel
                boolean flag2=true;
                while(flag2){
                    int numero=leIhm.identification();
                    Personnel pers=leGala.persExiste(numero);
                    if(pers!=null){
                        boolean flag3=true;
                        while(flag3){
                            if(leGala.persInscrit(numero)){
                                boolean flag4=true;
                                while(flag4){
                                    //Gestion diner (à suivre)
                                    int menu=leIhm.menuGestion();
                                    if(menu==1){
                                        //Gros pavé d'en dessous
                                    }
                                    else if(menu==2){
                                        leGala.supprimerReservation(pers);
                                        leGala.desincrire(pers);
                                    }
                                    else if(menu==3){
                                        flag4=false;
                                        flag3=false;
                                        flag2=false;
                                    }
                                    else{
                                        System.out.println("Il faut écrire 1,2 ou 3 !");
                                    }
                                }
                            }
                            else{
                                int iOuQ=leIhm.inscriptionOuQuitter();
                                if(iOuQ==1){
                                    leGala.inscriptionPersonnel(numero, pers.getNom(), pers.getPrenom(), pers.getTel(), pers.getEmail());
                                }
                                if(iOuQ==2){
                                    flag3=false;
                                    flag2=false;
                                }
                                else{
                                    System.out.println("Il faut entrer i ou q");
                                }
                            }
                        }
                    }
                    else{
                        System.out.println("Le numero entré n'est pas valide");
                    }
                }
            }
            else if(type==1){
                //C'est un etudiant
                boolean flag2=true;
                while(flag2){
                    int numero=leIhm.identification();
                    Etudiant etu=leGala.etuExiste(numero);
                    if(etu!=null){
                        boolean flag3=true;
                        while(flag3){
                            if(leGala.etuInscrit(numero)){
                                //Gestion diner (à suivre)
                                boolean flag4=true;
                                while(flag4){
                                    //Gestion diner (à suivre)
                                    int menu=leIhm.menuGestion();
                                    if(menu==1){
                                        //Gros pavé d'en dessous
                                    }
                                    else if(menu==2){
                                        leGala.supprimerReservation(etu);
                                        leGala.desincrire(etu);
                                    }
                                    else if(menu==3){
                                        flag4=false;
                                        flag3=false;
                                        flag2=false;
                                    }
                                    else{
                                        System.out.println("Il faut écrire 1,2 ou 3 !");
                                    }
                                }
                            }else{
                                int iOuQ=leIhm.inscriptionOuQuitter();
                                if(iOuQ==1){
                                    leGala.inscriptionEtudiant(numero, etu.getNom(), etu.getPrenom(), etu.getTel(), etu.getEmail(),etu.getAnnee());
                                }
                                if(iOuQ==2){
                                    flag3=false;
                                    flag2=false;
                                }
                                else{
                                    System.out.println("Il faut entrer i ou q");
                                }
                            }
                        }
                    }
                    else{
                        System.out.println("Le numero entré n'est pas valide");
                    }
                }
            }
            else{
                System.out.println("Il faut entrer e ou p !");
                //C'est une merde
            }
        }
    }

}
