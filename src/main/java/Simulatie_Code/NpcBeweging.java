package Simulatie_Code;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstracte basis voor alles wat over een route van Posities beweegt.
 * Bevat de volledige beweeg- en wachtlogica. Subclasses (Medewerker,
 * Vrachtwagen, ...) kunnen extra velden en gedrag toevoegen.
 */
public abstract class NpcBeweging {

    // Startpositie
    private final int startX;
    private final int startY;

    // Huidige positie
    private double x;
    private double y;

    // Snelheid in pixels per update-stap
    private double speed;

    // Route van bestemmingen
    private final List<Positie> route = new ArrayList<>();

    // Index van de huidige bestemming
    private int targetIndex = 0;

    // Route herhalen als hij klaar is?
    private boolean loopRoute = false;

    // Standaard wachttijd voor nieuwe bestemmingen
    private double standaardWachttijd = 1.0;

    // Resterende wachttijd voordat we weer mogen bewegen
    private double wachtteller = 1;

    public NpcBeweging(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
        this.x = startX;
        this.y = startY;
    }

    public NpcBeweging(int startX, int startY, double speed) {
        this.startX = startX;
        this.startY = startY;
        this.x = startX;
        this.y = startY;
        this.speed = speed;
    }

    public NpcBeweging(int startX, int startY, double speed, List<Positie> route) {
        this(startX, startY, speed);
        if (route != null) {
            this.route.addAll(route);
        }
    }

    public void addBestemming(int x, int y) {
        route.add(new Positie(x, y, standaardWachttijd));
    }

    public void addBestemming(Positie positie) {
        route.add(positie);
    }

    public void setStandaardWachttijd(double seconden) {
        this.standaardWachttijd = seconden;
    }

    public void reset() {
        x = startX;
        y = startY;
        targetIndex = 0;
    }

    /**
     * Eén simulatiedraai. Wordt 60x per seconde aangeroepen met dt = 1/60.
     */
    public void update(double dt) {

        // Nog aan het wachten? Alleen de teller verlagen.
        if (wachtteller > 0) {
            wachtteller -= dt;
            return;
        }

        if (route.isEmpty()) {
            return;
        }

        // Route klaar? Herstarten of stoppen.
        if (targetIndex >= route.size()) {
            if (loopRoute) {
                targetIndex = 0;
            } else {
                return;
            }
        }

        Positie target = route.get(targetIndex);

        double dx = target.x - x;
        double dy = target.y - y;
        double distance = Math.hypot(dx, dy);

        if (distance <= speed) {
            // Op de bestemming: stop, start de pauze, ga naar de volgende.
            x = target.x;
            y = target.y;
            wachtteller = target.wachttijd;
            targetIndex++;

            if (targetIndex >= route.size() && loopRoute) {
                targetIndex = 0;
            }
        } else {
            // Een stap van 'speed' pixels richting de bestemming.
            x += (dx / distance) * speed;
            y += (dy / distance) * speed;
        }
    }

    public int getStartX() { return startX; }
    public int getStartY() { return startY; }

    public double getX() { return x; }
    public double getY() { return y; }

    public int getIntX() { return (int) Math.round(x); }
    public int getIntY() { return (int) Math.round(y); }

    public Positie getStartPositie() { return new Positie(startX, startY); }
    public Positie getCurrentPositie() { return new Positie(getIntX(), getIntY()); }

    public List<Positie> getRoute() { return new ArrayList<>(route); }

    public boolean isRouteFinished() { return targetIndex >= route.size(); }
    public boolean isLoopRoute() { return loopRoute; }

    public void setLoopRoute(boolean loopRoute) { this.loopRoute = loopRoute; }
    public void setSpeed(double speed) { this.speed = speed; }
}
