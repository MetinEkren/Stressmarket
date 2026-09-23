package Simulatie_Code;

import java.util.List;

public class Kassiere extends Medewerker{
    public Kassiere(int startX, int startY) {
        super(startX, startY);
    }

    //Een kassiere staat stil bij de kassa en verdwijnt nooit.
    @Override
    public boolean isRouteFinished() {
        return false;
    }

    public static void MaakKassiere(List<NpcBeweging> npcBewegings) {

        Kassiere kassiere = new Kassiere(150, 150);

        kassiere.setLoopRoute(false);
        npcBewegings.add(kassiere);
    }
}
