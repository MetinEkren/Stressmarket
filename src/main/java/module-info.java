module Simulatie_Code {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
//    requires javafx.web;
//
//    requires jdk.jsobject;
    opens Simulatie_Code to javafx.graphics, javafx.controls, javafx.fxml;
//    requires org.controlsfx.controls;
//    requires com.dlsc.formsfx;
//    requires net.synedra.validatorfx;
//    requires org.kordamp.ikonli.javafx;
//    requires org.kordamp.bootstrapfx.core;
//    requires eu.hansolo.tilesfx;
//    requires com.almasb.fxgl.all;

    exports Simulatie_Code;
}