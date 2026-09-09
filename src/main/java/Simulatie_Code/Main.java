package Simulatie_Code;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        SimulatiePanel simulatiePanel = new SimulatiePanel();

        Scene scene = new Scene(
                simulatiePanel,
                simulatiePanel.getScreenWidth(),
                simulatiePanel.getScreenHeight()
        );

        stage.setTitle("Market Simulatie");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        simulatiePanel.startGame();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
