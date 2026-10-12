package Simulatie_Code;

import java.util.ArrayList;
import java.util.List;

public class Product {
    protected double posX;
    protected double posY;
    protected int productWidth;
    protected int productHeight;
    protected String productType;
    protected int productId;

    protected Product(double posX, double posY, int productWidth, int productHeight, String productType, int productId)
    {
        this.posX = posX;
        this.posY = posY;
        this.productWidth = productWidth;
        this.productHeight = productHeight;
        this.productType = productType;
        this.productId = productId;
    }

    protected Product(String productType)
    {
        this.productType = productType;
    }

    public static List<Product> MaakProduct(){

        Product soda = new Product("Soda");
        Product jam = new Product("Jam");
        Product melk = new Product("Milk");
        Product vlees = new Product("Vlees");

        List<Product> productList = new ArrayList<>();
        productList.add(soda);
        productList.add(jam);
        productList.add(melk);
        productList.add(vlees);

        return productList;
    }
}
