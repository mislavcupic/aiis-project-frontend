module hr.algebra.semregprojectfrontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.web;
    requires spring.beans;
    requires spring.core;
    requires java.net.http;

    opens hr.algebra.semregprojectfrontend to javafx.graphics;
    opens hr.algebra.semregprojectfrontend.controller to javafx.fxml;

    exports hr.algebra.semregprojectfrontend;
    exports hr.algebra.semregprojectfrontend.domain;
}

/*
module hr.algebra.semregprojectfrontend {
        requires javafx.controls;
        requires javafx.fxml;
        requires spring.web;
        requires spring.beans;
        requires jackson.databind;
        requires jackson.core;
        requires jackson.annotations;

        exports hr.algebra.semregprojectfrontend; // Export the frontend package
        exports hr.algebra.semregprojectfrontend.domain; // Export the domain package to the unnamed module
        }
*/