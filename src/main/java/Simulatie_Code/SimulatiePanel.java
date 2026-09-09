package Simulatie_Code;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class SimulatiePanel extends Pane {

    // Beeld settings
    final int originalTileSize = 16;
    final int scale = 3;

    final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;

    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    int FPS = 60;

    // NPC
    int npcX = 100;
    int npcY = 100;
    int npcSpeed = 4;

    private final Canvas canvas;
    private final GraphicsContext gc;

    private AnimationTimer gameLoop;

    public SimulatiePanel() {

        canvas = new Canvas(screenWidth, screenHeight);
        gc = canvas.getGraphicsContext2D();

        getChildren().add(canvas);

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, screenWidth, screenHeight);
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void startGame() {

        gameLoop = new AnimationTimer() {

            private long lastTime = 0;

            @Override
            public void handle(long currentTime) {

                if (lastTime == 0) {
                    lastTime = currentTime;
                    return;
                }

                double deltaTime =
                        (currentTime - lastTime) / 1_000_000_000.0;

                lastTime = currentTime;

                update(deltaTime);
                draw();
            }
        };

        gameLoop.start();
    }

    public void update(double deltaTime) {

        npcX += npcSpeed;
    }

    public void draw() {

        // Clear screen
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, screenWidth, screenHeight);

        // Draw NPC
        gc.setFill(Color.WHITE);
        gc.fillRect(
                npcX,
                npcY,
                tileSize,
                tileSize
        );
    }
}
