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
    protected List<Schap> schappen;
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

    private final List<NpcBeweging> npcBewegingen = new ArrayList<>();

//    Schap sodaSchap = new Schap(50,50,250,75,15, 16,"Soda");
//    Schap jamSchap = new Schap(350, 50, 250, 75, 16, 16, "Jam");
//    Schap melkSchap = new Schap(50, 350, 250, 75, 16, 16, "Milk");
//    Schap vleesSchap = new Schap(350, 350, 250, 75, 0, 16,"Vlees");

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
        spawnTimer = new SpawnTimer(5.0, () -> Klant.MaakKlant(npcBewegingen));//() -> MaakNpc()//this::MaakNpc
        spawnTimer.start();
    }

    public void MaakNpc() {

//        bestemmingen = new ArrayList<>();
//        bestemmingen.add(sodaSchap);
//        bestemmingen.add(jamSchap);
//        bestemmingen.add(melkSchap);
//        bestemmingen.add(vleesSchap);

        schappen = Schap.MaakSchap();
        Vakkenvuller.MaakVakkenvuller(npcBewegingen, schappen);
        Klant.MaakKlant(npcBewegingen);
        Kassiere.MaakKassiere(npcBewegingen);
        Vrachtwagen.maakVrachtwagen(npcBewegingen);
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

        for (NpcBeweging b : npcBewegingen) {
            b.update(UPDATE_STEP);
            if (b instanceof Vakkenvuller) {
                for (Schap schap : schappen) {
                    Vakkenvuller.checkSchap(schap);
                }
            }
        }

        // 2. Verwijder alle bewegers die klaar zijn
        npcBewegingen.removeIf(b -> b.isRouteFinished() && !b.isLoopRoute());

        //test functie
        //Randomizer.getRandomNumber();
    }

    //Tekent één frame: zwarte achtergrond + witte medewerker.
    //Wordt elke keer aangeroepen als AnimationTimer een frame tekent.
    public void draw() {

        GraphicsContext gc = this.getGraphicsContext2D();

        // Wis de canvas en maak hem zwart
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, screenWidth, screenHeight);


        for (NpcBeweging b : npcBewegingen) {
            if (b instanceof Kassiere) {
                gc.setFill(Color.WHITE);
            } else if (b instanceof Vrachtwagen) {
                gc.setFill(Color.BLUE);
            } else if (b instanceof Klant) {
                gc.setFill(Color.RED);
            }
            else if(b instanceof Vakkenvuller){
                gc.setFill(Color.GOLD);
            }

            gc.fillRect(b.getIntX(), b.getIntY(), 32, 32);
        }

        // Teken alle schappen
        for (Schap schap : schappen) {
            // Teken het schap zelf
            gc.setFill(Color.WHITE);
            gc.fillRect(schap.posX, schap.posY, schap.schapWidth, schap.schapHeight);

            // Bepaal de kleur van de producten op basis van het schapType
            Color productKleur = getProductKleur(schap.schapType);
            gc.setFill(productKleur);

            // Teken alle producten in dit schap
            for (Product product : schap.productList) {
                gc.fillRect(product.posX, product.posY, product.productWidth, product.productHeight);
            }
        }
    }

    private Color getProductKleur(String schapType) {
        switch (schapType) {
            case "Soda":
                return Color.ORANGE;
            case "Jam":
                return Color.RED;
            case "Milk":
                return Color.BLUE;
            case "Vlees":
                return Color.PINK;
            default:
                return Color.GRAY;
        }
    }
}
