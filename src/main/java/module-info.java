module com.example.teleaccesspro {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires jbcrypt;
    requires javafx.swing;
    requires java.rmi;

    opens com.example.teleaccesspro to javafx.fxml;
    exports com.example.teleaccesspro;
    exports com.example.teleaccesspro.client;
    exports com.example.teleaccesspro.server;
    exports com.example.teleaccesspro.server.event;
    exports com.example.teleaccesspro.client.event;
    exports com.example.teleaccesspro.event;
}