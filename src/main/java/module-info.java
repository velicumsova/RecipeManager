module recipemanager.recipemanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires org.jsoup;
    requires sqlite.jdbc;


    opens recipemanager to javafx.fxml;
    exports recipemanager;
    exports recipemanager.controllers;
    opens recipemanager.controllers to javafx.fxml;
    exports recipemanager.recipe;
    opens recipemanager.recipe to javafx.fxml;
    exports recipemanager.dataprocessing;
    opens recipemanager.dataprocessing to javafx.fxml;
}