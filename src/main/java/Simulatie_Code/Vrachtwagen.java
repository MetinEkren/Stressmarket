package Simulatie_Code;

import java.util.ArrayList;
import java.util.List;

public class Vrachtwagen {

    // Startpositie
    private final int startX;
    private final int startY;

    // Huidige positie
    private double x;
    private double y;

    // Snelheid in pixels per update-stap
    private double speed;

    // Lijst met bestemmingen waar de vrachtwagen naartoe moet
    private final List<Positie> route = new ArrayList<>();

    // Index van de huidige bestemming in de route
    private int targetIndex = 0;

    // Of de route herhaald moet worden als hij klaar is
    private boolean loopRoute = false;

    // Standaard wachttijd (seconden) die nieuwe bestemmingen krijgen
    private double standaardWachttijd = 1.0;   // seconde

    // Hoeveel seconden moet ik nog wachten voordat ik verder mag lopen
    private double wachtteller = 1;

    public Vrachtwagen(int startX, int startY, double speed, List<Positie> route) {

        this.startX = startX;
        this.startY = startY;
        this.x = startX;
        this.y = startY;
        this.speed = speed;

        if (route != null) {
            this.route.addAll(route);
        }
    }

    public void update(double dt) {

        //Als we nog aan het wachten zijn, alleen de teller verlagen.
        if (wachtteller > 0) {
            wachtteller -= dt;
            return;
        }

        //Als de route leeg is, niets doen.
        if (route.isEmpty()) {
            return;
        }

        // Als de route klaar is: herstarten (loop) of stoppen.
        if (targetIndex >= route.size()) {
            if (loopRoute) {
                targetIndex = 0;
            } else {
                return;
            }
        }

        Positie target = route.get(targetIndex);

        //Bereken de afstand naar de huidige bestemming.
        double dx = target.x - x;
        double dy = target.y - y;
        double distance = Math.hypot(dx, dy);

        // Als we dicht genoeg bij de bestemming zijn
        // Dichtbij? op de bestemming zetten, wachtteller starten,
        // doorgaan naar de volgende bestemming.
        if (distance <= speed) {
            x = target.x;
            y = target.y;
            wachtteller = target.wachttijd;   // <-- start de pauze
            targetIndex++;

            if (targetIndex >= route.size() && loopRoute) {
                targetIndex = 0;
            }
        } else {
            //Anders = een stap van 'speed' pixels richting de bestemming.
            // Beweging naar de bestemming toe
            x += (dx / distance) * speed;
            y += (dy / distance) * speed;
        }
    }

    public int getIntX() {
        return (int) Math.round(x);
    }

    //Geeft de y van de huidige positie afgerond op een hele pixel.
    public int getIntY() {
        return (int) Math.round(y);
    }

    public void setLoopRoute(boolean loopRoute) {
        this.loopRoute = loopRoute;
    }
}
