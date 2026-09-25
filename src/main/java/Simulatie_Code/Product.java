package Simulatie_Code;

public class Product {
    protected double posX;
    protected double posY;
    protected int productWidth;
    protected int productHeight;
    protected String productType;
    protected int productAmount;
    protected int productId;

    protected Product(double posX, double posY, int productWidth, int productHeight, String productType, int productAmount, int productId)
    {
        this.posX = posX;
        this.posY = posY;
        this.productWidth = productWidth;
        this.productHeight = productHeight;
        this.productType = productType;
        this.productAmount = productAmount;
        this.productId = productId;
    }
}
