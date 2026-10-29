package Simulatie_Code;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RandomizerTest {
    @Nested
    class HappyFlow {
        @Test
        void getRandomNumber_tussenNormaleWaarden() {
            for (int i = 0; i < 100; i++) {
                int result = Randomizer.getRandomNumber(4);
                assertTrue(result >= 1 && result <= 4, "Getal moet tussen 1 en 4 liggen, was: " + result);
            }
        }

        @Test
        void getRandomProductType_GeeftEenProductTerug() {
            Product product = Randomizer.getRandomProductType();
            assertNotNull(product, "Product mag niet null zijn");
            assertNotNull(product.productType, "Producttype mag niet null zijn");
        }
    }

    @Nested
    class EdgeCases {

        // Misschien verander je random.nextInt(maxProduct) + 1 per ongeluk in random.nextInt(maxProduct).
        //met random.nextInt(maxProduct). weet ik dat zo er 0 kan komen.
        @Test
        void getRandomNumber_met1_MoethijAlleen1Geven() {
            // Edge case: laagst mogelijke geldige waarde
            for (int i = 0; i < 50; i++) {
                assertEquals(1, Randomizer.getRandomNumber(1));
            }
        }

        @Test
        void getRandomNumber_MetNull_KanNiet() {
            // Edge case: ongeldige input
            assertThrows(IllegalArgumentException.class, () -> Randomizer.getRandomNumber(0));
        }

        @Test
        void getRandomNumber_MetNegativeNummers_KanNiet() {
            // Edge case: ongeldige negatieve input
            assertThrows(IllegalArgumentException.class, () -> Randomizer.getRandomNumber(-5));
        }

        @Test
        void getRandomProductType_AlleTypeKunnenTerugGeven() {
            // Edge case (statistisch): zorgen dat er geen dode code is en alle type is mogelijk tegen te komen.
            Set<String> gezien = new HashSet<>();
            for (int i = 0; i < 100; i++) {
                gezien.add(Randomizer.getRandomProductType().productType);
            }

            Set<String> verwacht = Set.of("Soda", "Jam", "Milk", "Vlees");
            assertEquals(verwacht, gezien, "Niet alle producttypes zijn gegenereerd. Gezien: " + gezien);
        }
    }
}