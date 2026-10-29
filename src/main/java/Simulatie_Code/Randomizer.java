package Simulatie_Code;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Randomizer {

    private static final Random random = new Random();

    // Klein klas: één regel van de boodschappenlijst
    public static class Boodschap {
        public int aantal;
        public Product product;

        public Boodschap(int aantal, Product product) {
            this.aantal = aantal;
            this.product = product;
        }
    }

    public static int getRandomNumber(int maxProduct) {
        int randomNumber = random.nextInt(maxProduct) + 1; // 1 t/m maxProduct
        //System.out.println(randomNumber);
        return randomNumber;
    }

    public static Product getRandomProductType() {
        List<Product> producten = Product.MaakProduct();
        Product randomProduct = producten.get(random.nextInt(producten.size()));
        //System.out.println(randomProduct.productType);
        return randomProduct;
    }

    public static Boodschap getRandomProduct(int maxProduct) {
        int hoeVeel = getRandomNumber(maxProduct);
        Product randomProduct = getRandomProductType();

        return new Boodschap(hoeVeel, randomProduct);
    }

    public static List<Boodschap> getRandomShoppinglist() {

        int maxProduct = 4;
        List<Boodschap> boodschappenlijst = new ArrayList<>();

        // Blijf items toevoegen totdat het totaal 4 is
        while (maxProduct > 0) {
            // Nooit meer vragen dan er nog over is
            Boodschap b = getRandomProduct(maxProduct);

            boodschappenlijst.add(b);
            maxProduct -= b.aantal;   // <-- hier verlaag je het budget
        }

        return boodschappenlijst;
    }
}
