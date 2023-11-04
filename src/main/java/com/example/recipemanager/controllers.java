package com.example.recipemanager;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class controllers {

    @FXML
    private Label myLabel;
    @FXML
    private Button recipeListButton, makeRecipeButton, favButton, cartButton;
    @FXML
    public void onRecipeListClick(ActionEvent event) {

            recipeListButton.getStyleClass().add("pressed");
            recipeListButton.setStyle("-fx-opacity: 0.5");
            myLabel.setText("Здесь будет список рецептов");
    }
    @FXML
    public void onMakeRecipeClick(ActionEvent event) {
        myLabel.setText("Здесь будет меню для создания рецепта");
    }
    @FXML
    public void onFavClick(ActionEvent event) {
        myLabel.setText("Здесь будет список избранных рецептов");
    }
    @FXML
    public void onCartClick(ActionEvent event) {
        myLabel.setText("Здесь будет корзина");
    }
}