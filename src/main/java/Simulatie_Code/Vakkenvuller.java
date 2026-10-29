package Simulatie_Code;

import java.lang.constant.Constable;
import java.util.List;

public class Vakkenvuller extends Medewerker{
    public Vakkenvuller(int startX, int startY, double speed)
    {
        super(startX, startY, speed);
    }

    //Steffen
    public static void checkSchap(Schap schap)
    {
        if (schap.amountOfProducts <= 15)
        {
            vulSchap(schap);
        }
    }

    //Steffen
    protected static void vulSchap(Schap schap)
    {
//        int neededProducts = schap.productCapacity - schap.amountOfProducts;
//        System.out.println(neededProducts);
        double posX = schap.posX + 32;
        double posY = schap.posY + 15;

        int neededProducts = schap.productCapacity - schap.amountOfProducts;
        for (int i = schap.amountOfProducts; i < schap.productCapacity; i++) {
            if (i != 0) {
//                    posX = (i == (schap.productCapacity / 2) + 1) ? schap.posX : posX;
                if (neededProducts % 2 == 1){
                    posX = (i == ((schap.productCapacity / 2) + schap.amountOfProducts / 2)) ? schap.productList.get(i - 2).posX : posX + 26.5;
                    posY = (i >= (schap.productCapacity / 2) + (neededProducts / 2)) ? schap.posY + 45 : posY;
                }
//                    else posY = (i >= (schap.productCapacity / 2) + 2) ? schap.posY + 45 : posY;

                schap.productList.add(new Product(posX, posY,
                        schap.schapWidth / 15,
                        schap.schapHeight / 5,
                        schap.schapType,
                        i + 1));
            }
        }
    }

    protected static void MaakVakkenvuller(List<NpcBeweging> NpcBewegingen, List<Schap> schappen){
        Vakkenvuller vakkenvuller = new Vakkenvuller(100, 200, 5);
        vakkenvuller.addBestemming(450, 200);
        vakkenvuller.addBestemming(450, 300);
        vakkenvuller.addBestemming(100, 300);
        vakkenvuller.addBestemming(100,200);

        vakkenvuller.setLoopRoute(true);
        NpcBewegingen.add(vakkenvuller);
    }


}
