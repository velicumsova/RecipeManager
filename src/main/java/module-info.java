module recipemanager.recipemanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires org.jsoup;
    requires mysql.connector.j;


    opens recipemanager to javafx.fxml;
    exports recipemanager;
}