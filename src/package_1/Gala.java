package package_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Gala {
    private final LocalDate DATE;
    private static int taillepqueue = 400; //taille initiale de la priority queue qu'on double lors d'ajout si nécessaire
    private static double tarif1 = 10.0;
    private static double tarif2 = 15.0;
    private static double tarif3 = 20.0;
    private static int nbTotalTablesEtudiant = 15;
    private static int nbTotalTablesPersonnel = 10;
    private static int nbPlacesTotalesDispoEtu = nbTotalTablesEtudiant * 8;
    private static int nbPlacesTotalesDispoPerso = nbTotalTablesPersonnel * 8;
    public SortedSet<Etudiant> lesEtudiants = new TreeSet<>();
    public SortedSet<Personnel> lePersonnel = new TreeSet<>();
    private SortedSet<Etudiant> lesEtudiantsInscrit = new TreeSet<>();
    private SortedSet<Personnel> lePersonnelInscrit = new TreeSet<>();
    private PriorityQueue<Etudiant> etudiantDemandeAttente = new PriorityQueue<Etudiant>(taillepqueue, new Comparator<Etudiant>() {
        @Override
        public int compare(Etudiant e1, Etudiant e2) {
            if (e1.getAnnee() == 5 && e2.getAnnee() != 5) {
                return -1;
            }
            if (e1.getAnnee() != 5 && e2.getAnnee() == 5) {
                return 1;
            }
            //if (e1.getAnnee()==5 && e2.getAnnee()==5 || e1.getAnnee()!=5 && e2.getAnnee()!=5)
            return lesReservations.get(e1).getDateReservation().compareTo(lesReservations.get(e2).getDateReservation());
        }
    });

    private SortedSet<Etudiant> etudiantDemandeAcceptee = new TreeSet<>();
    private SortedMap<Particulier, Reservation> lesReservations = new TreeMap<>();
    private SortedMap<Table, ArrayList<Etudiant>> lesTablesEtu = new TreeMap<>();
    private SortedMap<Table, ArrayList<Personnel>> lesTablesPerso = new TreeMap<>();

    private static SortedSet<Table> lesTables = new TreeSet<>();

    public Gala(LocalDate date) throws FileNotFoundException {
        this.DATE=date;

        Scanner sc = new Scanner(new File("src\\etudiants.txt"));
        while (sc.hasNextLine()) {
            int numero = Integer.parseInt(sc.next());
            String nom = sc.next();
            String prenom = sc.next();
            int tel = Integer.parseInt(sc.next());
            String email = sc.next();
            int annee = Integer.parseInt(sc.next());
            Etudiant etudiant = new Etudiant(numero, nom, prenom, tel, email, annee);
            if (!(lesEtudiants.contains(etudiant))) {
                lesEtudiants.add(etudiant);
            }
        }
        Scanner sc2 = new Scanner(new File("src\\personnel.txt"));
        while (sc2.hasNextLine()) {
            int numero = Integer.parseInt(sc2.next());
            String nom = sc2.next();
            String prenom = sc2.next();
            int tel = Integer.parseInt(sc2.next());
            String email = sc2.next();
            Personnel personnel = new Personnel(numero, nom, prenom, tel, email);
            if (!(lePersonnel.contains(personnel))) {
                lePersonnel.add(personnel);
            }
        }
        for (int i = 1; i <= nbTotalTablesPersonnel; i++) {
            lesTablesPerso.put(new Table(i), new ArrayList<>());
        }
        for (int i = nbTotalTablesPersonnel + 1; i <= nbTotalTablesEtudiant; i++) {
            lesTablesEtu.put(new Table(i), new ArrayList<>());
        }
    }

    public double calculMontant(Particulier part, int nombrePlaces) {
        double montant = 0;
        if (part.getTarif() == 1) {
            montant = tarif1 + (nombrePlaces - 1) * tarif3;
        }
        if (part.getTarif() == 2) {
            montant = tarif2 + (nombrePlaces - 1) * tarif3;
        }
        if (part.getTarif() == 3) {
            montant = tarif3 + (nombrePlaces - 1) * tarif3;
        }
        return montant;
    }


    public boolean inscriptionEtudiant(int numeroetu, String nom, String prenom, int tel, String email, int annee) {
        Etudiant etudiant = new Etudiant(numeroetu, nom, prenom, tel, email, annee);
        if (lesEtudiants.contains(etudiant) && !lesEtudiantsInscrit.contains(etudiant)) {
            lesEtudiantsInscrit.add(etudiant);
            return true;
        }
        return false;
    }


    public boolean inscriptionPersonnel(int numero, String nom, String prenom, int tel, String email) {
        Personnel personnel = new Personnel(numero, nom, prenom, tel, email);
        if (lePersonnel.contains(personnel) && !lePersonnelInscrit.contains(personnel)) {
            lePersonnelInscrit.add(personnel);
            return true;
        }
        return false;
    }

    public void desincrire(Particulier part) {
        if (lesReservations.get(part)!=null){
            supprimerReservation(part);
        }
        if (lesEtudiantsInscrit.contains(part)){
            lesEtudiantsInscrit.remove(part);
        }
        if (lePersonnelInscrit.contains(part)){
            lePersonnelInscrit.remove(part);
        }
        throw new PasInscritException();

    }


    public boolean creerReservation(Etudiant e, int nombrePlaces) {
        if (!lesReservations.containsKey(e)) {
            if (e.getAnnee() == 5 && nombrePlaces <= 4 && nombrePlaces >= 1 || e.getAnnee() < 5 && nombrePlaces <= 2 && nombrePlaces >= 1) {
                lesReservations.put(e, new Reservation(nombrePlaces));
                etudiantDemandeAttente.add(e);
                return true;
            }
            throw new MauvaisNombrePlaceException();
        }
        return false;
    }

    //Pour les Etudiants
    //Avant appel vérifier s'il est dans la map étudiants accepté
    public boolean confirmerReservation(Etudiant e, Reservation reserv, int numeroTable) {
        for (Table table : lesTables) {
            if (table.getNumTable() == numeroTable && table.getNombrePlacesLibres() >= reserv.getNombrePlaces()) {
                double montant = calculMontant(e, reserv.getNombrePlaces());
                lesReservations.replace(e, reserv, new Reservation(reserv, montant, numeroTable));
                lesTablesEtu.get(table).add(e);
                table.supprimerPlaces(table.getNumTable(), reserv.getNombrePlaces());
                etudiantDemandeAcceptee.remove(e);
                return true;
            }
            throw new PlusDePlaceDispoException();
        }
        return false;
    }

    //Pour le Personnel (pas de réserv préalable)
    public boolean creerReservation(Personnel pers, int nombrePlaces, int numeroTable) {
        if (!lesReservations.containsKey(pers)) {
            if (nombrePlaces <= 2 && nombrePlaces > 0) {
                for (Table table : lesTables) {
                    if (table.getNumTable() == numeroTable && table.getNombrePlacesLibres() >= nombrePlaces) {
                        double montant = calculMontant(pers, nombrePlaces);
                        lesReservations.put(pers, new Reservation(new Reservation(nombrePlaces), montant, numeroTable));
                        lesTablesPerso.get(table).add(pers);
                        table.supprimerPlaces(table.getNumTable(), nombrePlaces);
                        return true;
                    }
                    throw new PlusDePlaceDispoException();
                }
            }
            throw new MauvaisNombrePlaceException();
        }
        return false;
    }

    public void supprimerReservation(Particulier part){
        if (lesReservations.get(part)!=null){
            int numeroTable = lesReservations.get(part).getNumeroTable();
            LocalDate dateReserv = lesReservations.get(part).getDateReservation();
            if (ChronoUnit.DAYS.between(dateReserv,DATE)>=10){
                if (lesEtudiantsInscrit.contains(part)){
                    lesTablesEtu.get(numeroTable).remove(part);
                }
                lesTablesPerso.get(numeroTable).remove(part);
            }
            throw new PlusDeTempsException();
        }
        throw new PasDeReservation();
    }



    public String afficherPlanTable(SortedMap<Table, ArrayList<Particulier>> planTable) {
        String res = "";
        for (Table table : planTable.keySet()) {
            res += "Table numéro " + table.getNumTable() + " { ";
            for (Particulier part : planTable.get(table)) {
                int nbaccompagnant = lesReservations.get(part).getNombrePlaces()-1;
                res += part.getNom() + " " + part.getPrenom() + ", " + nbaccompagnant + " accompagnant";
                if (nbaccompagnant>1){
                    res+="s";
                }
            }
            res+="Nombre de place restantes : "+ table.getNombrePlacesLibres()+"\n";
        }
        return res;
    }

    public Etudiant etuExiste(int numero){
        for(Etudiant etu : lesEtudiants){
            if(etu.getNumero()==numero){
                return etu;
            }
        }
        return null;
    }
    public Personnel persExiste(int numero){
        for(Personnel pers : lePersonnel){
            if(pers.getNumero()==numero){
                return pers;
            }
        }
        return null;
    }

    public boolean etuInscrit(int numero){
        for(Etudiant etu : lesEtudiantsInscrit){
            if(etu.getNumero()==numero){
                return true;
            }
        }
        return false;
    }
    public boolean persInscrit(int numero){
        for(Personnel pers : lePersonnelInscrit){
            if(pers.getNumero()==numero){
                return true;
            }
        }
        return false;
    }

    //Cette classe et son itérateur implémentent toutes les méthodes optionnelles des interfaces Collectionet Iterator.
    // L'itérateur fourni dans la méthode iterator()n'est pas garanti pour parcourir les éléments de la file d'attente prioritaire dans un ordre particulier. Si vous avez besoin d'un parcours ordonné, envisagez d'utiliser Arrays.sort(pq.toArray()).

}