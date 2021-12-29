package package_1;

import java.io.IOException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        LocalDate dateGala = LocalDate.of(2022,12,31);
        new Controleur(dateGala);
    }
}
