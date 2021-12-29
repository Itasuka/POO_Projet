package package_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Gala implements Serializable {
    private final int ANNEE;
    private final int MOIS;
    private final int JOUR;
    private static int taillepqueue = 200; //taille initiale de la priority queue qu'on double lors d'ajout si nécessaire
    private static final double tarif1 = 10.0;
    private static final double tarif2 = 15.0;
    private static final double tarif3 = 20.0;
    private static final int nbTotalTablesEtudiant = 15;
    private static final int nbTotalTablesPersonnel = 10;
    private static final int nbPlacesTotalesDispoEtu = nbTotalTablesEtudiant * 8;
    public Set<Etudiant> lesEtudiants = new HashSet<>();
    public Set<Personnel> lePersonnel = new HashSet<>();
    private Set<Etudiant> lesEtudiantsInscrit = new HashSet<>();
    private Set<Personnel> lePersonnelInscrit = new HashSet<>();
    private PriorityQueue<Etudiant> etudiantDemandeAttente = new PriorityQueue<>(taillepqueue, new Comparaison());
    private Set<Etudiant> etudiantDemandeAcceptee = new HashSet<>();
    private SortedMap<Particulier, Reservation> lesReservations = new TreeMap<>();
    private SortedMap<Table, ArrayList<Particulier>> lesTablesEtu = new TreeMap<>();
    private SortedMap<Table, ArrayList<Particulier>> lesTablesPerso = new TreeMap<>();

    public PriorityQueue<Etudiant> getEtudiantDemandeAttente() { return etudiantDemandeAttente; }

    public Set<Etudiant> getEtudiantDemandeAcceptee() {
        return etudiantDemandeAcceptee;
    }

    public SortedMap<Particulier, Reservation> getLesReservations() {
        return lesReservations;
    }

    public SortedMap<Table, ArrayList<Particulier>> getLesTablesEtu() {
        return lesTablesEtu;
    }

    public SortedMap<Table, ArrayList<Particulier>> getLesTablesPerso() {
        return lesTablesPerso;
    }
    //-----------------------------------------------------------------

    class Comparaison implements Comparator<Etudiant>, Serializable {
        @Override
        public int compare(Etudiant e1, Etudiant e2) {
            if (e1.getAnnee() == 5 && e2.getAnnee() != 5) {
                return -1;
            }
            if (e1.getAnnee() != 5 && e2.getAnnee() == 5) {
                return 1;
            }
            //if (e1.getAnnee()==5 && e2.getAnnee()==5 || e1.getAnnee()!=5 && e2.getAnnee()!=5)
            return e1.getReserv().getDateReservation().compareTo(e2.getReserv().getDateReservation());
        }
    }

    //--------------------------METHODES-------------------------------

    /** TO STRING GALA
     * @return l'état des conteneurs de Gala sont forme de String.
     */
    public String toString() {
        String s = "ETAT DU GALA AYANT LIEU LE "+JOUR+"/"+MOIS+"/"+ANNEE+" : \n";
      s += "-----------------LISTE DES ETUDIANTS INSCRITS AU GALA :----------------\n";
        for (Etudiant etudiantinscrit : lesEtudiantsInscrit){
            s += etudiantinscrit.toString() + "\n";
        }
        s+="\n";
        s+= "-----------------LISTE DU PERSONNEL INSCRIT AU GALA :-----------------\n";
        for (Personnel personnelinscrit : lePersonnelInscrit){
            s += personnelinscrit.toString() + "\n";
        }
        s+="\n";
        s+="----------------LISTE DES ETUDIANTS DANS LA FILE D'ATTENTE :------------\n";
        for (Etudiant etudiantatt : etudiantDemandeAttente)
        s+=etudiantatt.toString()+"\n";
        s+="\n";
        s+="----------LISTE DES ETUDIANTS DONT LA DEMANDE A ETE ACCEPTEE :----------\n";
        for (Etudiant etudiantaccepte : etudiantDemandeAcceptee){
            s += etudiantaccepte.toString() + "\n";
        }
        s+="\n";
        s+="-------------------------LISTE DES RESERVATIONS :-----------------------\n";
        Set<Map.Entry<Particulier,Reservation>> setreservations = lesReservations.entrySet();
        for (Map.Entry<Particulier,Reservation> entree : setreservations){
            s+= entree.getKey().toString() +" à la réservation suivante : "+ entree.getValue().toString()+"\n";
        }
        s+="\n";
        s+="-------LISTE DES TABLES POUR ETUDIANTS ET LEUR(S) OCCUPANT(S) :---------\n";
        s+=afficherPlanTable(lesTablesEtu);
        s+="\n";
        s+="-------LISTE DES TABLES POUR LE PERSONNEL ET LEUR(S) OCCUPANT(S) :------\n";
        s+=afficherPlanTable(lesTablesPerso);
        s+="\n";
        s+="------------------------FIN DE L'ETAT DU GALA--------------------------- \n";
        return s;
    }

    public Gala(LocalDate date) throws FileNotFoundException {

        this.ANNEE = date.getYear();
        this.MOIS = date.getMonthValue();
        this.JOUR = date.getDayOfMonth();

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
        for (int i = nbTotalTablesPersonnel + 1; i <= nbTotalTablesPersonnel + nbTotalTablesEtudiant; i++) {
            lesTablesEtu.put(new Table(i), new ArrayList<>());
        }
    }

    /** description de la fonction calculMontant
     * @param part le Particulier qui est l'utilisateur
     * @param nombrePlaces le nombre de places choisi par l'utilisateur
     * @return le montant de la réservation en fonction de son statut et nombre de places choisi
     */
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


    /**
     * description de la fonction inscription étudiant
     * permet d'ajouter un étudiant dans la liste des étudiants inscrit
     *
     * @param numeroetu le numéro etudiant de l'utilisateur
     * @param nom le nom de l'utilisateur
     * @param prenom le prénom de l'utilisateur
     * @param tel le numero de téléphone de l'utilisateur
     * @param email le email de l'utilisateur
     * @param annee l'année d'étude de l'utilisateur
     */
    public void inscriptionEtudiant(int numeroetu, String nom, String prenom, int tel, String email, int annee) {
        Etudiant etudiant = new Etudiant(numeroetu, nom, prenom, tel, email, annee);
        if (lesEtudiants.contains(etudiant) && !lesEtudiantsInscrit.contains(etudiant)) {
            lesEtudiantsInscrit.add(etudiant);
        }
    }

    /**
     * description de la fonction inscriptionPersonnel
     * permet d'ajouter un personnel dans la liste des personnels inscrit
     *
     * @param numero le numero d'identification du personnel
     * @param nom le nom du personnel
     * @param prenom le prenom du personnel
     * @param tel le numero de telephone du personnel
     * @param email le email du personnel
     */
    public void inscriptionPersonnel(int numero, String nom, String prenom, int tel, String email) {
        Personnel personnel = new Personnel(numero, nom, prenom, tel, email);
        if (lePersonnel.contains(personnel) && !lePersonnelInscrit.contains(personnel)) {
            lePersonnelInscrit.add(personnel);
        }
    }


    /**
     * description de la fonction desinscrire
     * cette fonction permet de désisncrire des listes un particulier
     *
     * @param part le Particulier qui est l'utilisateur
     * @throw PasInscrisException() si l'étudiant n'est pas inscrit
     */
    public void desincrire(Particulier part) {
        if (lesEtudiantsInscrit.contains(part)) {
            lesEtudiantsInscrit.remove(part);
        } else if (lePersonnelInscrit.contains(part)) {
            lePersonnelInscrit.remove(part);
        } else {
            throw new PasInscritException();
        }
    }

    /**
     * description de la fonction TrouverUneTable
     *
     * @param p le Particulier qui est l'utilisateur
     * @param nombreplaces le nombre de place choisi par l'utilisateur
     * @return donne le numero de la table ou il y a suffisament de place de libre
     * @throw lance PlusDePlaceDispoException() si il n'y a plus assez de places sur aucune table
     */
    public int trouverUneTable(Particulier p, int nombreplaces) {
        if (lesEtudiants.contains(p)) {
            Set<Table> settableetu = lesTablesEtu.keySet();
            for (Table t : settableetu) {
                if (t.getNombrePlacesLibres() >= nombreplaces) {
                    return t.getNumTable();
                }
            }
        } else if (lePersonnel.contains(p)) {
            Set<Table> settableperso = lesTablesPerso.keySet();
            for (Table t : settableperso) {
                if (t.getNombrePlacesLibres() >= nombreplaces) {
                    return t.getNumTable();
                }
            }
        }
        throw new PlusDePlaceDispoException();
    }


    /**
     * description de la fonction creerReservation
     * cette fonction permet de placer un étudiant dans le map des demandes en attente et de créer partiellement sa réservation
     *
     * @param e l'étudiant qui est l'utilisateur
     * @param nombrePlaces le nombre de place choisi par l'utilisateur
     * @throw lance MauvaisNombrePlaceException() si la personne n'a pas choisi le nombre de places possible selon son statut
     */
    public void creerReservation(Etudiant e, int nombrePlaces) {
        if (!lesReservations.containsKey(e)) {
            if (e.getAnnee() == 5 && nombrePlaces <= 4 && nombrePlaces >= 1 || e.getAnnee() < 5 && nombrePlaces <= 2 && nombrePlaces >= 1) {
                lesReservations.put(e, new Reservation(nombrePlaces, calculMontant(e, nombrePlaces)));
                e.setReserv(new Reservation(nombrePlaces, calculMontant(e, nombrePlaces)));
                if (taillepqueue == etudiantDemandeAttente.size()) {
                    taillepqueue *= 2;
                }
                etudiantDemandeAttente.add(e);
            }
            else{
                throw new MauvaisNombrePlaceException();
            }
        }
    }

    /**
     * description de la fonction confirmerReservation
     * cette fonction permet de supprimer l'étudiant des demandes en attente lorsqu'il finalisera sa réservation
     *
     * @param e l'Etudiant qui est l'utilisateur
     * @param reserv le premiere reservation qu'à faite l'étudiant lors de sa mise en attente
     * @param numeroTable le numéro de table choisi par l'étudiant
     * @throw lance PlusDePlaceDispoException() si il n'y a plus de places
     */
    //Pour les Etudiants
    //Avant appel vérifier s'il est dans la map étudiants accepté
    public void confirmerReservation(Etudiant e, Reservation reserv, int numeroTable) throws MauvaiseTableException {
        if (numeroTable < 11 || numeroTable > 25) {
            throw new MauvaiseTableException();
        }
        for (Table table : lesTablesEtu.keySet()) {
            if (table.getNumTable() == numeroTable) {
                if (table.getNombrePlacesLibres() >= reserv.getNombrePlaces()){
                    double montant = calculMontant(e, reserv.getNombrePlaces());
                    lesReservations.replace(e, reserv, new Reservation(reserv, montant, numeroTable));
                    lesTablesEtu.get(table).add(e);
                    table.supprimerPlaces(table.getNumTable(), reserv.getNombrePlaces());
                    etudiantDemandeAcceptee.remove(e);
                } else {
                    throw new PlusDePlaceDispoException();
                }
            }
        }
    }

    //Pour le Personnel (pas de réserv préalable)

    /**
     * description de la fonction creerReservation
     * cette fonction permet de creer une réservation pour un personnel
     *
     * @param pers le Personnel qui est l'utilisateur
     * @param nombrePlaces le nombre de place qu'a choisi l'utilisateur
     * @param numeroTable le numero de table qu'a choisi l'utilisateur
     * @throw lance PlusDePlaceDispoException() si il n'y a plus de places disponibles
     * @throw lance MauvaisNombrePlaceException() si la personne n'a pas choisi le nombre de places possible selon son statut
     */
    public void creerReservation(Personnel pers, int nombrePlaces, int numeroTable) throws MauvaiseTableException {
        if (!lesReservations.containsKey(pers)) {
            if (numeroTable < 0 || numeroTable > 10) {
                throw new MauvaiseTableException();
            }
            if (nombrePlaces <= 2 && nombrePlaces > 0) {
                for (Table table : lesTablesPerso.keySet()) {
                    if (table.getNumTable() == numeroTable && table.getNombrePlacesLibres() >= nombrePlaces) {
                        double montant = calculMontant(pers, nombrePlaces);
                        lesReservations.put(pers, new Reservation(new Reservation(nombrePlaces, montant), montant, numeroTable));
                        lesTablesPerso.get(table).add(pers);
                        table.supprimerPlaces(table.getNumTable(), nombrePlaces);
                    } else if (table.getNumTable() == numeroTable && table.getNombrePlacesLibres() < nombrePlaces) {
                        throw new PlusDePlaceDispoException();
                    }
                }
            }
            else{
                throw new MauvaisNombrePlaceException();
            }
        }
    }


    /**
     * description de la fonction supprimerReservation
     * cette fonction permet de supprimer la reversation d'un particulier
     *
     * @param part le Particulier qui est l'utilisateur
     * @throw lance PlusDeTempsException() si le particulier s'y prend 10 jours ou moins avant le Gala
     * @throw lance PasDeReservation() si il n'y a plus de places
     */
    public void supprimerReservation(Particulier part) {
        if (lesReservations.get(part) != null) {
            int numeroTable = lesReservations.get(part).getNumeroTable();
            if (ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.of(ANNEE, MOIS, JOUR)) >= 10) {
                if (lesEtudiantsInscrit.contains(part)) {
                    int nbPlaces = lesReservations.get(part).getNombrePlaces();
                    lesReservations.remove(part);
                    etudiantDemandeAttente.remove(part);
                    etudiantDemandeAcceptee.remove(part);
                    for (Table table : lesTablesEtu.keySet()) {
                        if (table.getNumTable() == numeroTable) {
                            lesTablesEtu.get(table).remove(part);
                            table.ajouterPlaces(nbPlaces);
                        }
                    }
                }
                for (Table table : lesTablesPerso.keySet()) {
                    if (table.getNumTable() == numeroTable) {
                        lesTablesPerso.get(table).remove(part);
                        table.ajouterPlaces(lesReservations.get(part).getNombrePlaces());
                        lesReservations.remove(part);
                    }
                }
            } else {
                throw new PlusDeTempsException();
            }
        } else {
            throw new PasDeReservation();
        }
    }


    /**
     * description de la fonction afficherPlanTable
     * cette fonction permet d'afficher le plan de table sous la forme d'un String
     *
     * @param planTable le plan de table qui lesTablesEtu ou lesTablesPerso selon si l'utilisateur est un personnel ou un etudiant
     * @return le nombre de place restantes par table avec le nom , prenom et nombre d'accompagnant
     */
    public String afficherPlanTable(SortedMap<Table, ArrayList<Particulier>> planTable) {
        String res = "";
        for (Table table : planTable.keySet()) {
            res += "Table numéro " + table.getNumTable() + " { ";
            for (Particulier part : planTable.get(table)) {
                int nbaccompagnant = lesReservations.get(part).getNombrePlaces() - 1;
                res += part.getNom() + " " + part.getPrenom() + ", " + nbaccompagnant + " accompagnant(s) ";
            }
            res += "Nombre de place restantes : " + table.getNombrePlacesLibres() + " } \n";
        }
        return res;
    }

    /**
     * description de la fonction nbPlacePossible
     * cette fonction donne le nombre de place possible pour les étudiants selon leur année et les personnels et les places libres aux tables
     *
     * @param p le Particulier qui est l'utilisateur
     * @return le nombre de place possibles à réserver par l'utilisateur
     */
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


    /**
     * description de la fonction afficherNbPlacesPossible
     * cette fonction affiche le nombre de places maximun que le particulier peut réserver
     *
     * @param p le Particulier qui est l'utilisateur
     * @return le string donnant le nombre de places
     */
    public String afficherNbPlacesPossible(Particulier p) {
        int nb = nbPlacesPossible(p);
        return "Vous pouvez reserver " + nb + " places maximum.";
    }


    /**
     * description de la fonction etuExiste
     * cette fonction vérifie si un étudiant est dans le map lesEtudiants
     *
     * @param numero le numero d'etudiant de l'utilisateur (si c'est un etudiant)
     * @return l'etudiant si il existe et sinon return null
     */
    public Etudiant etuExiste(int numero) {
        for (Etudiant etu : lesEtudiants) {
            if (etu.getNumero() == numero) {
                return etu;
            }
        }
        return null;
    }


    /**
     * description de la fonction persExiste
     * cette fonction vérifie si un personnel est dans le map lesPersonnel
     *
     * @param numero le numero d'identification de l'utilisateur (si c'est un personnel)
     * @return le personnel si c'est le cas et sinon return null
     */
    public Personnel persExiste(int numero) {
        for (Personnel pers : lePersonnel) {
            if (pers.getNumero() == numero) {
                return pers;
            }
        }
        return null;
    }


    /**
     * description de la fonction etuInscrit
     * cette fonction vérifie si un étudiant est dans le map lesEtudiantInscrit
     *
     * @param numero le numero d'etudiant de l'utilisateur (si c'est un etudiant)
     * @return true si il est dans la map et false dans le cas contraire
     */
    public boolean etuInscrit(int numero) {
        for (Etudiant etu : lesEtudiantsInscrit) {
            if (etu.getNumero() == numero) {
                return true;
            }
        }
        return false;
    }


    /**
     * description de la fonction persInscrit
     * cette fonction vérifie si un étudiant est dans le map lePersonnelInscrit
     *
     * @param numero le numero d'identification de l'utilisateur (si c'est un personnel)
     * @return true si il est dans la map et false dans le cas contraire
     */
    public boolean persInscrit(int numero) {
        for (Personnel pers : lePersonnelInscrit) {
            if (pers.getNumero() == numero) {
                return true;
            }
        }
        return false;
    }


    /**
     * description de la fonction avancerLaQueue
     * cette fonction permet de faire avancer la queue quand un étudiant à sa demander accepter
     */
    public void avancerLaQueue() {
        if (ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.of(ANNEE, MOIS, JOUR)) < 31) {
            int nbplaces = nbPlacesTotalesDispoEtu;
            Set<Map.Entry<Particulier, Reservation>> set = lesReservations.entrySet();
            while (!etudiantDemandeAttente.isEmpty()) {
                Etudiant etudiant = etudiantDemandeAttente.peek();
                for (Map.Entry<Particulier, Reservation> entree : set) {
                    if (lesEtudiants.contains(entree.getKey())) {
                        Etudiant e = (Etudiant) entree.getKey();
                        if (etudiant.equals(e)) {
                            int nbplacesdemandees = entree.getValue().getNombrePlaces();
                            if (nbplaces - nbplacesdemandees >= 0) {
                                etudiantDemandeAcceptee.add(etudiant);
                                etudiantDemandeAttente.poll();
                                nbplaces -= nbplacesdemandees;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}