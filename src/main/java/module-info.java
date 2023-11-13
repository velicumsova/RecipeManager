module recipemanager.recipemanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens recipemanager to javafx.fxml;
    exports recipemanager;
}