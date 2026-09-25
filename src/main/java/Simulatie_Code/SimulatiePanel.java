package Simulatie_Code;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import Simulatie_Code.Randomizer;
import java.util.ArrayList;
import java.util.List;


/**
 * Het tekenveld van de simulatie. Beheert de vaste-tijdstap update-loop,
 * tekent de medewerker en houdt de FPS bij.
 */
public class SimulatiePanel extends Canvas {
    // Beeldinstellingen
    final int originalTileSize = 16;
    final int scale = 3;
    //final int tileSize = originalTileSize;// 48
    final int tileSize = originalTileSize * scale; // 48

    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;        // 768
    final int screenHeight = tileSize * maxScreenRow;       // 576

    //private Medewerker medewerker;
    //private Vrachtwagen vrachtwagen;

    private final List<NpcBeweging> npcBewegings = new ArrayList<>();

    Vakkenvuller vakkenvuller = new Vakkenvuller(100, 200, 5);
    Schap sodaSchap = new Schap(50,50,250,75,15, "Soda");
    Schap jamSchap = new Schap(350, 50, 250, 75, 16, "Jam");
    Schap melkSchap = new Schap(50, 350, 250, 75, 16, "Milk");
    Schap vleesSchap = new Schap(350, 350, 250, 75, 0, "Vlees");
    private long lastTime = 0;

    // Vaste tijdsstap (60 updates per seconde)
    private final double UPDATE_STEP = 1.0 / 60.0;
    private double accumulator = 0.0;

    // FPS-teller
    private long lastFPSCheck = 0;
    private int frameCount = 0;

    private AnimationTimer simulatieLoop;
    private SpawnTimer spawnTimer;


    /**
     * Constructor: stelt de canvasgrootte in en geeft de medewerker
     * zijn route en gedrag mee.
     */
    public SimulatiePanel() {

        this.setWidth(screenWidth);
        this.setHeight(screenHeight);

        MaakNpc();

        // Daarna elke 5 seconden een nieuwe klant
        spawnTimer = new SpawnTimer(5.0, () -> Klant.MaakKlant(npcBewegings));//() -> MaakNpc()//this::MaakNpc
        spawnTimer.start();
    }

    public void MaakNpc() {

        Klant.MaakKlant(npcBewegings);
        Kassiere.MaakKassiere(npcBewegings);
        Vrachtwagen.maakVrachtwagen(npcBewegings);
    }

    /**
     * Start de animatielus. Gebruikt een accumulator zodat update()
     * altijd met een vaste tijdstap van 1/60 seconde draait,
     * ongeacht hoe vaak het scherm ververst (60Hz, 144Hz, ...).
     */
    public void startSimulatieThread() {

        lastTime = System.nanoTime();
        lastFPSCheck = lastTime;
        accumulator = 0.0;
        frameCount = 0;
        // Bereken verstreken tijd in seconden
        // Accumuleer tijd
        // Voer vaste updates uit zolang we genoeg tijd hebben
        // Teken het scherm
        // FPS-teller
        // Simulatie loop
        simulatieLoop = new AnimationTimer() {
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
                    try {
                        update();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    //medewerker.update(UPDATE_STEP); // geef de vaste tijdstap mee
                    accumulator -= UPDATE_STEP;
                }

                // Teken het scherm
                draw();

                // FPS-teller
                frameCount++;
                if (currentTime  - lastFPSCheck >= 1_000_000_000L) {
                    System.out.println("FPS: " + frameCount);
                    frameCount = 0;
                    lastFPSCheck = currentTime;
                }
            }
        };

        simulatieLoop.start();
    }

    /**
     * Stopt de animatielus. Wordt o.a. aangeroepen als het venster
     * geminimaliseerd wordt, zodat de CPU niet onnodig belast wordt.
     */
    public void stopSimulatieThread() {
        if (simulatieLoop != null) {
            simulatieLoop.stop();
            simulatieLoop = null;
        }
    }

    //Roept één simulatiedraai aan op de medewerker.
    // Draait met een vaste tijdstap van UPDATE_STEP.
    public void update() throws InterruptedException{

        for (NpcBeweging b : npcBewegings) {
            b.update(UPDATE_STEP);
        }

        // 2. Verwijder alle bewegers die klaar zijn
        npcBewegings.removeIf(b -> b.isRouteFinished() && !b.isLoopRoute());

        //test
        //Randomizer.getRandomNumber();
    }

    //Tekent één frame: zwarte achtergrond + witte medewerker.
    //Wordt elke keer aangeroepen als AnimationTimer een frame tekent.
    public void draw() {

        GraphicsContext gc = this.getGraphicsContext2D();
        GraphicsContext gc2 = this.getGraphicsContext2D();
        GraphicsContext gc3 = this.getGraphicsContext2D();

        // Wis de canvas en maak hem zwart
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, screenWidth, screenHeight);


        for (NpcBeweging b : npcBewegings) {
            if (b instanceof Kassiere) {
                gc.setFill(Color.WHITE);
            } else if (b instanceof Vrachtwagen) {
                gc.setFill(Color.BLUE);
            } else if (b instanceof Klant) {
                gc.setFill(Color.RED);
            }

            gc.fillRect(b.getIntX(), b.getIntY(), 32, 32);
        }

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
        gc.fillRect(melkSchap.posX, melkSchap.posY, melkSchap.schapWidth, melkSchap.schapHeight);
        for (Product product3: melkSchap.productList)
        {
            gc2.setFill(Color.BLUE);
            gc2.fillRect(product3.posX, product3.posY, product3.productWidth, product3.productHeight);
        }

        gc.setFill(Color.WHITE);
        gc.fillRect(vleesSchap.posX, vleesSchap.posY, vleesSchap.schapWidth, vleesSchap.schapHeight);
        for (Product product3: vleesSchap.productList)
        {
            gc2.setFill(Color.PINK);
            gc2.fillRect(product3.posX, product3.posY, product3.productWidth, product3.productHeight);
        }

        //teken de vakkenvuller
        gc3.setFill(Color.GOLD);
        gc3.fillRect(vakkenvuller.getIntX(), vakkenvuller.getIntY(), tileSize, tileSize);

    }
}
