package Simulatie_Code;

import java.util.List;

public class Vrachtwagen extends NpcBeweging {

    public Vrachtwagen(int startX, int startY, double speed, List<Positie> route) {
        super(startX, startY, speed, route);
    }

    public static void maakVrachtwagen(List<NpcBeweging> npcBewegings){
        // De vrachtwagen die door de simulatie beweegt
        Vrachtwagen vrachtwagen = new Vrachtwagen(10, 10, 4, List.of(new Positie(352, 10, 4), new Positie(800, 10)));
        vrachtwagen.setLoopRoute(false);
        npcBewegings.add(vrachtwagen);
    }
}
