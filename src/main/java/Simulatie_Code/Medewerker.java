package Simulatie_Code;
import java.util.ArrayList;
import java.util.List;

public class Medewerker {

    // Startpositie
    private final int startX;
    private final int startY;

    // Huidige positie
    private double x;
    private double y;

    // Snelheid in pixels per update-stap
    private double speed;

    // Lijst met bestemmingen waar de medewerker naartoe moet
    private final List<Positie> route = new ArrayList<>();

    // Index van de huidige bestemming in de route
    private int targetIndex = 0;

    // Of de route herhaald moet worden als hij klaar is
    private boolean loopRoute = false;

    // Standaard wachttijd (seconden) die nieuwe bestemmingen krijgen
    private double standaardWachttijd = 1.0;   // seconde

    // Hoeveel seconden moet ik nog wachten voordat ik verder mag lopen
    private double wachtteller = 1;


    /**
     * Maakt een medewerker met alleen een startpositie.
     * Snelheid blijft 0, beweegt niet tot je setSpeed() aanroept.
     */
    public Medewerker(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
        this.x = startX;
        this.y = startY;
    }

    //Maakt een medewerker met startpositie en snelheid.
    public Medewerker(int startX, int startY, double speed) {
        this.startX = startX;
        this.startY = startY;
        this.x = startX;
        this.y = startY;
        this.speed = speed;
    }

    /**
     * Maakt een medewerker met startpositie, snelheid en een route.
     * Roept eerst de 3-parameter constructor aan en voegt dan de route toe.
     */
    public Medewerker(int startX, int startY, double speed, List<Positie> route) {
        this(startX, startY, speed);
        if (route != null) {
            this.route.addAll(route);
        }
    }

    //Voegt een bestemming toe op (x, y) met de standaard wachttijd.
    public void addBestemming(int x, int y) {
        route.add(new Positie(x, y, standaardWachttijd));
    }

    //Verandert de standaard wachttijd voor bestemmingen die hierna worden toegevoegd.
    public void setStandaardWachttijd(double seconden) {
        this.standaardWachttijd = seconden;
    }

    //Voegt een bestaande Positie (met eigen wachttijd) toe aan de route.
    public void addBestemming(Positie positie) {
        route.add(positie);
    }


    //Zet de medewerker terug op zijn startpositie en laat hem
    public void reset() {
        x = startX;
        y = startY;
        targetIndex = 0;
    }

    //Eén simulatiedraai. Wordt 60x per seconde aangeroepen met dt = 1/60.


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

    //Geeft de x van de startpositie terug.
    public int getStartX() {
        return startX;
    }

    //Geeft de y van de startpositie terug
    public int getStartY() {
        return startY;
    }

    //Geeft de exacte (double) x van de huidige positie terug.
    public double getX() {
        return x;
    }

    //Geeft de exacte (double) y van de huidige positie terug.
    public double getY() {
        return y;
    }

    //Geeft de x van de huidige positie afgerond op een hele pixel.
    public int getIntX() {
        return (int) Math.round(x);
    }

    //Geeft de y van de huidige positie afgerond op een hele pixel.
    public int getIntY() {
        return (int) Math.round(y);
    }

    //Geeft de startpositie als een nieuw Positie-object.
    public Positie getStartPositie() {
        return new Positie(startX, startY);
    }

    // Geeft de huidige positie als een nieuw Positie-object.
    public Positie getCurrentPositie() {
        return new Positie(getIntX(), getIntY());
    }

    //Geeft een kopie van de route terug, zodat de aanroeper de
    // originele lijst niet per ongeluk kan aanpassen.
    public List<Positie> getRoute() {
        return new ArrayList<>(route);
    }

    //True als alle bestemmingen bezocht zijn (en loopRoute uit staat).
    public boolean isRouteFinished() {
        return targetIndex >= route.size();
    }

    //Zet aan/uit of de route herhaald moet worden.
    public void setLoopRoute(boolean loopRoute) {
        this.loopRoute = loopRoute;
    }

    // Verandert de loopsnelheid.
    public void setSpeed(double speed) {
        this.speed = speed;
    }
}