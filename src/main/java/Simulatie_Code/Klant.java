package Simulatie_Code;

import java.util.List;

public class Klant extends NpcBeweging {

    public Klant(int startX, int startY) {
        super(startX, startY);
    }

    public Klant(int startX, int startY, double speed) {
        super(startX, startY, speed);
    }

    public Klant(int startX, int startY, double speed, List<Positie> route) {
        super(startX, startY, speed, route);
    }

    public static void MaakKlant(List<NpcBeweging> npcBewegings) {

        Klant klant = new Klant(100, 100, 4);

        klant.addBestemming(400, 100);
        klant.addBestemming(400, 200);
        klant.addBestemming(400, 300);
        klant.addBestemming(400, 400);
        klant.addBestemming(200, 300);

        klant.setLoopRoute(false);
        npcBewegings.add(klant);
    }
}
