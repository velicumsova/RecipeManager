module com.example.recipemanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.recipemanager to javafx.fxml;
    exports com.example.recipemanager;
}