package com.example.recipemanager;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class controllers {

    @FXML
    private Label myLabel;
    @FXML
    private Button RecipeListButton, MakeButton, FavButton, CartButton;
    @FXML
    public void onRecipeListClick(ActionEvent event) {

            RecipeListButton.getStyleClass().add("pressed");
            RecipeListButton.setStyle("-fx-opacity: 0.5");
    }
    @FXML
    public void onEditClick(ActionEvent event) {
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