package Simulatie_Code;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import Simulatie_Code.Randomizer;


/**
 * Het tekenveld van de simulatie. Beheert de vaste-tijdstap update-loop,
 * tekent de medewerker en houdt de FPS bij.
 */
public class SimulatiePanel extends Canvas {
    // Beeldinstellingen
    final int originalTileSize = 16;
    final int scale = 3;
    final int tileSize = originalTileSize * scale;          // 48

    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;        // 768
    final int screenHeight = tileSize * maxScreenRow;       // 576

    // De medewerker die door de simulatie beweegt
    Medewerker medewerker = new Medewerker(100, 100, 4);
    //    int npcX = 100;
    //    int npcY = 100;
    //    int npcSpeed = 4;  // pixels per update-stap

    private long lastTime = 0;

    // Vaste tijdsstap (60 updates per seconde)
    private final double UPDATE_STEP = 1.0 / 60.0;
    private double accumulator = 0.0;

    // FPS-teller
    private long lastFPSCheck = 0;
    private int frameCount = 0;


    /**
     * Constructor: stelt de canvasgrootte in en geeft de medewerker
     * zijn route en gedrag mee.
     */
    public SimulatiePanel() {

        this.setWidth(screenWidth);
        this.setHeight(screenHeight);

        // Startpositie is 100, 100
        // Hier voeg je de posities toe waar de medewerker naartoe moet
        medewerker.addBestemming(400, 100);
        medewerker.addBestemming(400, 300);
        medewerker.addBestemming(100, 300);

        //  Blijft de route oneindig herhalen als True niet dan False
        medewerker.setLoopRoute(true);
    }

    /**
     * Start de animatielus. Gebruikt een accumulator zodat update()
     * altijd met een vaste tijdstap van 1/60 seconde draait,
     * ongeacht hoe vaak het scherm ververst (60Hz, 144Hz, ...).
     */
    public void startSimulatieThread() {

        lastTime = System.nanoTime();
        // Bereken verstreken tijd in seconden
        // Accumuleer tijd
        // Voer vaste updates uit zolang we genoeg tijd hebben
        // Teken het scherm
        // FPS-teller
        // Simulatie loop
        AnimationTimer simulatieLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Bereken verstreken tijd in seconden
                long currentTime = System.nanoTime();
                double elapsed = (currentTime - lastTime) / 1_000_000_000.0;
                lastTime = currentTime;

                // Accumuleer tijd
                accumulator += elapsed;

                // Voer vaste updates uit zolang we genoeg tijd hebben
                while (accumulator >= UPDATE_STEP) {
                    update();
                    //medewerker.update(UPDATE_STEP); // geef de vaste tijdstap mee
                    accumulator -= UPDATE_STEP;
                }


                // Teken het scherm
                draw();

                // FPS-teller
                frameCount++;
                if (now - lastFPSCheck >= 1_000_000_000L) {
                    System.out.println("FPS: " + frameCount);
                    frameCount = 0;
                    lastFPSCheck = now;
                }
            }
        };

        simulatieLoop.start();
    }

    //Roept één simulatiedraai aan op de medewerker.
    // Draait met een vaste tijdstap van UPDATE_STEP.
    public void update() {
        //npcX += npcSpeed;
        //if (npcX > screenWidth) {
        //npcX = -tileSize;
        //}
        medewerker.update(UPDATE_STEP);

        //test
        //Randomizer.getRandomNumber();
    }

    //Tekent één frame: zwarte achtergrond + witte medewerker.
    //Wordt elke keer aangeroepen als AnimationTimer een frame tekent.
    public void draw() {

        Schap sodaSchap = new Schap(50,50,250,75,16, "Soda");
        Schap jamSchap = new Schap(350, 50, 250, 75, 16, "Jam");
        Schap milkSchap = new Schap(50, 350, 250, 75, 16, "Milk");
        GraphicsContext gc = this.getGraphicsContext2D();
        GraphicsContext gc2 = this.getGraphicsContext2D();

        // Wis de canvas en maak hem zwart
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, screenWidth, screenHeight);

        // maak canvas transparant Werkt niet
        //gc.clearRect(0,0,screenWidth,screenHeight);

        // Teken de medewerker
        gc.setFill(Color.WHITE);
        gc.fillRect(medewerker.getIntX(), medewerker.getIntY(), tileSize, tileSize);



        //Teken de schap
        gc.fillRect(sodaSchap.posX, sodaSchap.posY, sodaSchap.schapWidth, sodaSchap.schapHeight);
        for (Product product : sodaSchap.productList)
        {
            gc.setFill(Color.ORANGE);
            gc.fillRect(product.posX, product.posY, product.productWidth, product.productHeight);
        }


        gc.setFill(Color.WHITE);
        gc.fillRect(jamSchap.posX, jamSchap.posY, jamSchap.schapWidth, jamSchap.schapHeight);
        for (Product product2: jamSchap.productList)
        {
            gc2.setFill(Color.RED);
            gc2.fillRect(product2.posX, product2.posY, product2.productWidth, product2.productHeight);
        }

        gc.setFill(Color.WHITE);
        gc.fillRect(milkSchap.posX, milkSchap.posY, milkSchap.schapWidth, milkSchap.schapHeight);
        for (Product product3: milkSchap.productList)
        {
            gc2.setFill(Color.BLUE);
            gc2.fillRect(product3.posX, product3.posY, product3.productWidth, product3.productHeight);
        }
    }
}
