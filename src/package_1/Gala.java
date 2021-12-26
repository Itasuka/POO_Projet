package package_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Gala {
    private final LocalDate DATE;
    private static int taillepqueue = 200; //taille initiale de la priority queue qu'on double lors d'ajout si nécessaire
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

    public SortedMap<Particulier, Reservation> getLesReservations() {
        return lesReservations;
    }

    private SortedMap<Particulier, Reservation> lesReservations = new TreeMap<>();
    private SortedMap<Table, ArrayList<Particulier>> lesTablesEtu = new TreeMap<>();
    private SortedMap<Table, ArrayList<Particulier>> lesTablesPerso = new TreeMap<>();

    private static SortedSet<Table> lesTables = new TreeSet<>();

    public PriorityQueue<Etudiant> getEtudiantDemandeAttente() {
        return etudiantDemandeAttente;
    }

    public SortedSet<Etudiant> getEtudiantDemandeAcceptee() {
        return etudiantDemandeAcceptee;
    }


    public SortedMap<Table, ArrayList<Particulier>> getLesTablesEtu() {
        return lesTablesEtu;
    }

    public SortedMap<Table, ArrayList<Particulier>> getLesTablesPerso() {
        return lesTablesPerso;
    }

    public Gala(LocalDate date) throws FileNotFoundException {
        this.DATE = date;

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
        if (lesReservations.get(part) != null) {
            supprimerReservation(part);
        }
        if (lesEtudiantsInscrit.contains(part)) {
            lesEtudiantsInscrit.remove(part);
        }
        if (lePersonnelInscrit.contains(part)) {
            lePersonnelInscrit.remove(part);
        }
        throw new PasInscritException();
    }

    public int trouverUneTable(Particulier p, int nombreplaces) {
        if (lesEtudiants.contains(p)) {
            Set<Table> settableetu = lesTablesEtu.keySet();
            for (Table t : settableetu) {
                if (t.getNombrePlacesLibres() >= nombreplaces) {
                    return t.getNumTable();
                }
            }
        } else {
            Set<Table> settableperso = lesTablesPerso.keySet();
            for (Table t : settableperso) {
                if (t.getNombrePlacesLibres() >= nombreplaces) {
                    return t.getNumTable();
                }
            }
        }
        throw new PlusDePlaceDispoException();
    }

    public boolean creerReservation(Etudiant e, int nombrePlaces) {
        if (!lesReservations.containsKey(e)) {
            if (e.getAnnee() == 5 && nombrePlaces <= 4 && nombrePlaces >= 1 || e.getAnnee() < 5 && nombrePlaces <= 2 && nombrePlaces >= 1) {
                lesReservations.put(e, new Reservation(nombrePlaces));
                if (taillepqueue==etudiantDemandeAttente.size()){
                    taillepqueue*=2;
                }
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

    public void supprimerReservation(Particulier part) {
        if (lesReservations.get(part) != null) {
            int numeroTable = lesReservations.get(part).getNumeroTable();
            LocalDate dateReserv = lesReservations.get(part).getDateReservation();
            if (ChronoUnit.DAYS.between(dateReserv, DATE) >= 10) {
                if (lesEtudiantsInscrit.contains(part)) {
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
                int nbaccompagnant = lesReservations.get(part).getNombrePlaces() - 1;
                res += part.getNom() + " " + part.getPrenom() + ", " + nbaccompagnant + " accompagnant";
                if (nbaccompagnant > 1) {
                    res += "s";
                }
            }
            res += "Nombre de place restantes : " + table.getNombrePlacesLibres() + "\n";
        }
        return res;
    }

    public int nbPlacesPossible(Particulier p) {
        if (lesEtudiants.contains(p)) {
            Etudiant e = (Etudiant) p;
            Set<Table> settableetu = lesTablesEtu.keySet();
            if (e.getAnnee() == 5) {
                int nb = 4;
                int nbp = 0;
                for (Table t : settableetu) {
                    if (t.getNombrePlacesLibres() > nbp) {
                        nbp = t.getNombrePlacesLibres();
                    }
                }
                if (nb < nbp) {
                    return nb;
                } else {
                    return nbp;
                }
            } else {
                int nb = 2;
                int nbp = 0;
                for (Table t : settableetu) {
                    if (t.getNombrePlacesLibres() > nbp) {
                        nbp = t.getNombrePlacesLibres();
                    }
                }
                if (nb < nbp) {
                    return nb;
                } else {
                    return nbp;
                }
            }
        } else {
            Set<Table> settableperso = lesTablesPerso.keySet();
            int nb = 2;
            int nbp = 0;
            for (Table t : settableperso) {
                if (t.getNombrePlacesLibres() > nbp) {
                    nbp = t.getNombrePlacesLibres();
                }
            }
            if (nb < nbp) {
                return nb;
            } else {
                return nbp;
            }
        }
    }

    public String afficherNbPlacesPossible(Particulier p) {
        int nb = nbPlacesPossible(p);
        String s = "Vous pouvez reserver " + nb + " places maximum.";
        return s;
    }

    public Etudiant etuExiste(int numero) {
        for (Etudiant etu : lesEtudiants) {
            if (etu.getNumero() == numero) {
                return etu;
            }
        }
        return null;
    }

    public Personnel persExiste(int numero) {
        for (Personnel pers : lePersonnel) {
            if (pers.getNumero() == numero) {
                return pers;
            }
        }
        return null;
    }

    public boolean etuInscrit(int numero) {
        for (Etudiant etu : lesEtudiantsInscrit) {
            if (etu.getNumero() == numero) {
                return true;
            }
        }
        return false;
    }

    public boolean persInscrit(int numero) {
        for (Personnel pers : lePersonnelInscrit) {
            if (pers.getNumero() == numero) {
                return true;
            }
        }
        return false;
    }

    public void avancerLaQueue() {
        int nbplaces = nbPlacesTotalesDispoEtu;
        boolean drap = true;
        Set<Map.Entry<Particulier, Reservation>> set = lesReservations.entrySet();
        if (!etudiantDemandeAttente.isEmpty()) {
            while (drap) {
                Etudiant etudiant = etudiantDemandeAttente.peek();
                for (Map.Entry<Particulier, Reservation> entree : set) {
                    if (lesEtudiants.contains(entree.getKey())) {
                        Etudiant e = (Etudiant) entree.getKey();
                        if (etudiant.equals(e)) {
                            int nbplacesdemandees = entree.getValue().getNombrePlaces();
                            if (nbplaces - nbplacesdemandees >= 0) {
                                etudiantDemandeAcceptee.add(etudiant);
                                etudiantDemandeAttente.poll();
                            }
                            else{
                                drap=false;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}