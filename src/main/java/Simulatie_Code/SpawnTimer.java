package Simulatie_Code;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class SpawnTimer {

    // De actie die elke interval wordt uitgevoerd.
    // Runnable = "iets zonder parameters en zonder returnwaarde".
    // Door dit als veld te bewaren, kunnen we de KeyFrame opnieuw
    // opbouwen wanneer de interval verandert.
    private final Runnable actie;

    // Huidige interval in seconden.
    private double intervalSeconden;

    // De JavaFX Timeline die het eigenlijke timen doet.
    // Eén Timeline kan meerdere KeyFrames hebben; wij gebruiken er precies één.
    private final Timeline timeline;

    //Maakt een nieuwe SpawnTimer
    //intervalSeconden: hoe vaak de actie moet draaien (in seconden)
    //actie: wat er moet gebeuren bij elke tik

    public SpawnTimer(double intervalSeconden, Runnable actie) {
        this.intervalSeconden = intervalSeconden;
        this.actie = actie;

        // Maak een lege Timeline aan.
        this.timeline = new Timeline();

        // INDEFINITE = blijf voor altijd herhalen.
        // Het alternatief is een getal, bijvoorbeeld 5, voor 5 herhalingen.
        this.timeline.setCycleCount(Timeline.INDEFINITE);

        // Voeg de KeyFrame toe die de actie bevat.
        bouwKeyFrame();
    }

    /**
     * bouwt de KeyFrame op basis van het huidige interval.
     * Wordt aangeroepen in de constructor en in setInterval().
     *
     * Een KeyFrame beschrijft "op tijdstip X moet het volgende gebeuren".
     * Hier: op tijdstip 'intervalSeconden' voer de actie uit.
     * Omdat cycleCount INDEFINITE is, herhaalt de Timeline dit frame
     * steeds opnieuw.
     */
    private void bouwKeyFrame() {
        timeline.getKeyFrames().setAll(
                new KeyFrame(Duration.seconds(intervalSeconden),// wanneer
                        e -> actie.run()// wat (lambda)
                )
        );
    }

    public void start() {
        timeline.play();
    }

}
