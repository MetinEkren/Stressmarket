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
    protected List<Product> productList;

    protected Schap(double posX, double posY, int schapWidth, int schapHeight, int amountOfProducts, String schapType)
    {
        this.posX = posX;
        this.posY = posY;
        this.schapWidth = schapWidth;
        this.schapHeight = schapHeight;
        this.schapType = schapType;
        this.amountOfProducts = amountOfProducts;
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
                    1, i));
        }
    }
}
