package com.example.recipemanager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class controllers {
    @FXML
    private Label myLabel;

    @FXML
    protected void onRecipeListclick() {
        myLabel.setText("Welcome to JavaFX Application!");
    }
}