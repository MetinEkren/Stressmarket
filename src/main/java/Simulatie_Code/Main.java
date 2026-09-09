package Simulatie_Code;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        SimulatiePanel simulatiePanel = new SimulatiePanel();

        StackPane root = new StackPane();
        root.getChildren().add(simulatiePanel);
//        // Achtergrondkleur van de container instellen (vergelijkbaar met setBackground)
//        root.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(root, simulatiePanel.screenWidth, simulatiePanel.screenHeight);

        primaryStage.setTitle("Market Simulatie");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();

        simulatiePanel.startSimulatieThread();
    }
}
