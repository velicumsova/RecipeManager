package com.example.recipemanager;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;

public class maincontrollers {
    @FXML
    private TabPane pagesList;
    @FXML
    private Label pageName;
    @FXML
    private Button recipeListButton, makeRecipeButton, importRecipeButton, favButton, cartButton, selectionButton;

    private void selectButton(Button button, String styleClass, double layoutX, double layoutY) {
        recipeListButton.getStyleClass().remove("recipelist-active");
        makeRecipeButton.getStyleClass().remove("edit-active");
        importRecipeButton.getStyleClass().remove("import-active");
        favButton.getStyleClass().remove("favlist-active");
        cartButton.getStyleClass().remove("cart-active");
        selectionButton.setLayoutX(layoutX);
        selectionButton.setLayoutY(layoutY);
    }

    @FXML
    public void initialize() {
        selectButton(recipeListButton, "recipelist-active", -35, 130);
        pageName.setText("Список рецептов");
        pagesList.getSelectionModel().select(0);
    }

    @FXML
    public void onRecipeListClick(ActionEvent event) {
        selectButton(recipeListButton, "recipelist-active", -35, 130);
        pageName.setText("Список рецептов");
        pagesList.getSelectionModel().select(0);
    }

    @FXML
    public void onMakeRecipeClick(ActionEvent event) {
        selectButton(makeRecipeButton, "edit-active", -35, 217);
        pageName.setText("Создание рецепта");
        pagesList.getSelectionModel().select(1);
    }

    @FXML
    public void onImportRecipeClick(ActionEvent event) {
        selectButton(makeRecipeButton, "import-active", -35, 217);
        pageName.setText("Импорт рецепта");
        pagesList.getSelectionModel().select(2);
    }

    @FXML
    public void onFavClick(ActionEvent event) {
        selectButton(favButton, "favlist-active", -35, 691);
        pageName.setText("Список избранных рецептов");
        pagesList.getSelectionModel().select(3);
    }

    @FXML
    public void onCartClick(ActionEvent event) {
        selectButton(cartButton, "cart-active", 953, -35);
        pageName.setText("Список покупок");
        pagesList.getSelectionModel().select(4);
    }
}