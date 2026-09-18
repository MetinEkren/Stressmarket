package Simulatie_Code;

import java.util.Random;

public class Randomizer {


    public static int getRandomNumber(){
        int min = 1;
        int max = 4;

        Random random = new Random();
        int randomNumber = random.nextInt(max) + 1;
        System.out.println(randomNumber);

        return randomNumber;
    }
}
