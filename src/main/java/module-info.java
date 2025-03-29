module com.lauradev.gestorcontactosuq {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.desktop;
    requires java.base;

    opens com.lauradev.gestorcontactosuq to javafx.fxml;
    opens com.lauradev.gestorcontactosuq.controllers to javafx.fxml;

    exports com.lauradev.gestorcontactosuq;
    exports com.lauradev.gestorcontactosuq.controllers;
    exports com.lauradev.gestorcontactosuq.model;

}