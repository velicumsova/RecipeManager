module recipemanager.recipemanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires org.jsoup;
    requires sqlite.jdbc;


    opens recipemanager to javafx.fxml;
    exports recipemanager;
}