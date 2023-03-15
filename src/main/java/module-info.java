module com.example.socialnetworkgradlefx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.socialnetworkgradlefx to javafx.fxml;
    exports com.example.socialnetworkgradlefx;

    opens com.example.socialnetworkgradlefx.controller to javafx.fxml;
    exports com.example.socialnetworkgradlefx.controller;

    opens com.example.socialnetworkgradlefx.service to javafx.fxml;
    exports com.example.socialnetworkgradlefx.service;

    opens com.example.socialnetworkgradlefx.repo to javafx.fxml;
    exports com.example.socialnetworkgradlefx.repo;

    opens com.example.socialnetworkgradlefx.domain to javafx.fxml;
    exports com.example.socialnetworkgradlefx.domain;
}