package Simulatie_Code;

import java.lang.constant.Constable;
import java.util.List;

public class Vakkenvuller extends Medewerker{
    public Vakkenvuller(int startX, int startY, double speed)
    {
        super(startX, startY, speed);
    }

//    public static void checkSchap(Schap schap)
//    {
//            int i = 1;
//            do {
//                schap.productList.add(new Product( schap.productList.get(i).posX, schap.posY,
//                        schap.schapWidth / 15,
//                        schap.schapHeight / 5,
//                        schap.schapType,
//                        i));
//                i++;
//            }
//            while (schap.productList.size() < schap.productCapacity);
//    }

    public static void checkSchap(Schap schap)
    {
       if(schap.amountOfProducts < schap.productCapacity){
           int addNewProduct = schap.productCapacity - schap.amountOfProducts;

           //GaNaarSchap(addNewProduct, schap);
       }
    }

//    public static void GaNaarSchap(int hoeveelproduct, Schap schap)
//    {
//        vakkenvuller.addBestemming(schap.posX, schap.posY);
//        vakkenvuller.addBestemming(schap.getIntX(), schap.getIntY());
//        //vakkenvuller.addBestemming(getCurrentPositie());
//        if(vakkenvuller.)
//    }

    protected static void MaakVakkenvuller(List<NpcBeweging> NpcBewegingen, List<Schap> schappen){
        Vakkenvuller vakkenvuller = new Vakkenvuller(100, 200, 5);
//        vakkenvuller.addBestemming(450, 200);
//        vakkenvuller.addBestemming(450, 300);
//        vakkenvuller.addBestemming(100, 300);
//        vakkenvuller.addBestemming(100,200);

        vakkenvuller.setLoopRoute(true);
        NpcBewegingen.add(vakkenvuller);
    }


}
