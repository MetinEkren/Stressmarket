package Simulatie_Code;

import java.util.ArrayList;
import java.util.List;

public class Winkelwagen {
    public int aantal;
    public Product product;
    //List<Product> producten = new ArrayList<>();
    List<Winkelwagen> winkelwagenProducten = new ArrayList<>();

    public Winkelwagen(int aantal, Product product) {
        this.aantal = aantal;
        this.product = product;
    }

}
