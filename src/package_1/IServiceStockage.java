package package_1;

import java.io.IOException;

public interface IServiceStockage {
    /**
     * Ecrit l'objet passé en paramètre dans un flux de sortie
     * @param object
     * @throws IOException Erreur liée aux entrées/sorties
     */
    void enregistrer(Object object) throws IOException;

    /**
     * Lit un objet à partir du flux d'entrée
     * @return l'objet lu
     * @throws IOException Erreur liée aux entrées/sorties
     * @throws ClassNotFoundException La classe d'un objet sérialisé ne peut être trouvée.
     */
    Object charger() throws IOException, ClassNotFoundException;
}
