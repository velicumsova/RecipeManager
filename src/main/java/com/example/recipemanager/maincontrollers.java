package com.example.recipemanager;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;

public class maincontrollers {
    @FXML
    private TabPane pagesList;
    @FXML
    private Button recipeListButton, makeRecipeButton, favButton, cartButton, selectionButton;

    private void selectButton(Button button, String styleClass, double layoutX, double layoutY) {
        recipeListButton.getStyleClass().remove("recipelist-active");
        makeRecipeButton.getStyleClass().remove("edit-active");
        favButton.getStyleClass().remove("favlist-active");
        cartButton.getStyleClass().remove("cart-active");
        button.getStyleClass().add(styleClass);
        selectionButton.setLayoutX(layoutX);
        selectionButton.setLayoutY(layoutY);
    }

    @FXML
    public void initialize() {
        selectButton(recipeListButton, "recipelist-active", -35, 130);
    }

    @FXML
    public void onRecipeListClick(ActionEvent event) {
        selectButton(recipeListButton, "recipelist-active", -35, 130);
    }

    @FXML
    public void onMakeRecipeClick(ActionEvent event) {
        selectButton(makeRecipeButton, "edit-active", -35, 217);
    }

    @FXML
    public void onFavClick(ActionEvent event) {
        selectButton(favButton, "favlist-active", -35, 691);
    }

    @FXML
    public void onCartClick(ActionEvent event) {
        selectButton(cartButton, "cart-active", 953, -35);
    }
}