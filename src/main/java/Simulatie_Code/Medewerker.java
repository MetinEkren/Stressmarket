package Simulatie_Code;
import java.util.ArrayList;
import java.util.List;

public class Medewerker extends NpcBeweging {

    /**
     * Een medewerker in het magazijn. Erft alle beweeglogica van Beweger.
     *
     */
    public Medewerker(int startX, int startY) {
        super(startX, startY);
    }

    //Maakt een medewerker met startpositie en snelheid.
    public Medewerker(int startX, int startY, double speed) {
        super(startX, startY, speed);
    }

    /**
     * Maakt een medewerker met startpositie, snelheid en een route.
     * Roept eerst de 3-parameter constructor aan en voegt dan de route toe.
     */
    public Medewerker(int startX, int startY, double speed, List<Positie> route) {
        super(startX, startY, speed, route);
    }
}