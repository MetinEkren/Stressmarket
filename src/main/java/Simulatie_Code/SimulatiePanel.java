package Simulatie_Code;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import Simulatie_Code.Randomizer;

public class SimulatiePanel extends Canvas {
    // Beeldinstellingen
    final int originalTileSize = 16;
    final int scale = 3;
    final int tileSize = originalTileSize * scale;          // 48

    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;        // 768
    final int screenHeight = tileSize * maxScreenRow;       // 576

    // NPC
    int npcX = 100;
    int npcY = 100;
    int npcSpeed = 4;  // pixels per update-stap

    private long lastTime = 0;

    // Vaste tijdsstap (60 updates per seconde)
    private final double UPDATE_STEP = 1.0 / 60.0;
    private double accumulator = 0.0;

    // FPS-teller
    private long lastFPSCheck = 0;
    private int frameCount = 0;

    public SimulatiePanel() {
        this.setWidth(screenWidth);
        this.setHeight(screenHeight);
    }

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

    public void update() {
        npcX += npcSpeed;
        if (npcX > screenWidth) {
            npcX = -tileSize;
        }

        //test
        //Randomizer.getRandomNumber();
    }

    public void draw() {

        GraphicsContext gc = this.getGraphicsContext2D();

        // Wis de canvas en maak hem zwart
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, screenWidth, screenHeight);

        // maak canvas transparant
        //gc.clearRect(0,0,screenWidth,screenHeight);

        // Teken de NPC
        gc.setFill(Color.WHITE);
        gc.fillRect(npcX, npcY, tileSize, tileSize);
    }
}
