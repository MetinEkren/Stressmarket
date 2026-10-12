package Simulatie_Code;

import java.util.ArrayList;
import java.util.List;

public class Schap {

    protected int schapWidth;
    protected int schapHeight;
    protected double posX;
    protected double posY;
    protected String schapType;
    protected int amountOfProducts;
    protected int productCapacity;
    protected List<Product> productList;

    protected Schap(double posX, double posY, int schapWidth, int schapHeight, int amountOfProducts, int productCapacity, String schapType)
    {
        this.posX = posX;
        this.posY = posY;
        this.schapWidth = schapWidth;
        this.schapHeight = schapHeight;
        this.schapType = schapType;
        this.amountOfProducts = amountOfProducts;
        this.productCapacity = productCapacity;
        productList = new ArrayList<>();
        posY += 15;
        for (int i = 1; i <= amountOfProducts; i++)
        {
            posX = (i == 1 || (i == (amountOfProducts / 2) + 1)) ? this.posX + 26.5 : posX + 26.5;
            posY = (i >= (amountOfProducts / 2) + 1) ? this.posY + 45 : posY;
//            posY = posX == 170 ? : posY + 10;
            productList.add(new Product(
                    posX, posY,
                    schapWidth / 15,
                    schapHeight / 5,
                    schapType,
                    i)
            );
        }
    }

    public static List<Schap> MaakSchap(){

        Schap sodaSchap = new Schap(50,50,250,75,15, 16,"Soda");
        Schap jamSchap = new Schap(350, 50, 250, 75, 16, 16, "Jam");
        Schap melkSchap = new Schap(50, 350, 250, 75, 16, 16, "Milk");
        Schap vleesSchap = new Schap(350, 350, 250, 75, 0, 16,"Vlees");

        List<Schap> schappen = new ArrayList<>();
        schappen.add(sodaSchap);
        schappen.add(jamSchap);
        schappen.add(melkSchap);
        schappen.add(vleesSchap);

        return schappen;
    }

    protected double getIntX()
    {
        return posX;
    }

    protected double getIntY()
    {
        return posY;
    }

    public Positie getCurrentPositie() { return new Positie((int)getIntX(), (int) getIntY()); }

}
