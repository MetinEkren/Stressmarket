package Simulatie_Code;
import java.util.List;

public class Medewerker extends NpcBeweging {

    /**
     * Een medewerker in het magazijn. Erft alle beweeglogica van Beweger.
     *
     */
    public Medewerker(int startX, int startY) {
        super(startX, startY);
    }

    public Medewerker(int startX, int startY, double speed) {
        super(startX, startY, speed);
    }

    public Medewerker(int startX, int startY, double speed, List<Positie> route) {
        super(startX, startY, speed, route);
    }
}