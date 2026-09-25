package Simulatie_Code;

/**
 * Onveranderlijke positie in de wereld, met optionele wachttijd.
 * Wordt gebruikt als waypoint in de route van een Medewerker.
 */
public class Positie {
    public final int x;
    public final int y;

    //Aantal seconden dat een medewerker hier pauzeert na aankomst.
    public final double wachttijd;   // seconden wachten bij aankomst

    //Maakt een positie zonder wachttijd (wachttijd = 0).
    public Positie(int x, int y) {
        //this.x = x;
        //this.y = y;
        this(x, y, 0);
    }

    // Maakt een positie met een specifieke wachttijd.
    public Positie(int x, int y, double wachttijd) {
        this.x = x;
        this.y = y;
        this.wachttijd = wachttijd;
    }
}