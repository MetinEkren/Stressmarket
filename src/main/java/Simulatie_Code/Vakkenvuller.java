package Simulatie_Code;

import java.lang.constant.Constable;

public class Vakkenvuller extends Medewerker{
    public Vakkenvuller(int startX, int startY, double speed)
    {
        super(startX, startY, speed);
    }

    protected void checkSchap(Schap schap)
    {
        if (schap.posY - getY() <= 50){
            int i = 1;
            do {
                schap.posX = this.getX();
                schap.posY = this.getX() ;
                schap.productList.add(new Product( schap.productList.get(i).posX, schap.posY,
                        schap.schapWidth / 15,
                        schap.schapHeight / 5,
                        schap.schapType,
                        i, i));
                i++;
            }
            while (schap.productList.size() < 16);
        }
    }
}
