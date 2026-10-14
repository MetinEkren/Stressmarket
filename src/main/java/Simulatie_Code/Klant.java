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

    public static void MaakKlant(List<NpcBeweging> npcBewegingen) {

        Klant klant = new Klant(100, 100, 4);

        klant.setLoopRoute(false);
        npcBewegingen.add(klant);

        //Boodschappen lijst
        List<Randomizer.Boodschap> lijst = Randomizer.getRandomShoppinglist();
        for (Randomizer.Boodschap b : lijst) {
            System.out.println(b.aantal + "x " + b.product.productType);
        }

    }
}
