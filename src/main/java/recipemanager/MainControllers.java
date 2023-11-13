package recipemanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;

public class MainControllers {
    @FXML
    private TabPane pagesList;
    @FXML
    private Label pageName;
    @FXML
    private Button recipeListButton;
    @FXML
    private Button makeRecipeButton;
    @FXML
    private Button importRecipeButton;
    @FXML
    private Button favButton;
    @FXML
    private Button cartButton;
    @FXML
    private Button selectionButton;

    public MainControllers() {
    }

    private void selectButton(Button button, String styleClass, double layoutX, double layoutY) {
        this.recipeListButton.getStyleClass().remove("recipelist-active");
        this.makeRecipeButton.getStyleClass().remove("edit-active");
        this.importRecipeButton.getStyleClass().remove("import-active");
        this.favButton.getStyleClass().remove("favlist-active");
        this.cartButton.getStyleClass().remove("cart-active");

        button.getStyleClass().add(styleClass);

        this.selectionButton.setLayoutX(layoutX);
        this.selectionButton.setLayoutY(layoutY);
    }

    @FXML
    public void initialize() {
        this.selectButton(this.recipeListButton, "recipelist-active", -35.0, 130.0);
        this.pageName.setText("Список рецептов");
        this.pagesList.getSelectionModel().select(0);
    }

    @FXML
    public void onRecipeListClick(ActionEvent event) {
        this.selectButton(this.recipeListButton, "recipelist-active", -35.0, 130.0);
        this.pageName.setText("Список рецептов");
        this.pagesList.getSelectionModel().select(0);
    }

    @FXML
    public void onMakeRecipeClick(ActionEvent event) {
        this.selectButton(this.makeRecipeButton, "edit-active", -35.0, 217.0);
        this.pageName.setText("Создание рецепта");
        this.pagesList.getSelectionModel().select(1);
    }

    @FXML
    public void onImportRecipeClick(ActionEvent event) {
        this.selectButton(this.importRecipeButton, "import-active", -35.0, 304.0);
        this.pageName.setText("Импорт рецепта");
        this.pagesList.getSelectionModel().select(2);
    }

    @FXML
    public void onFavClick(ActionEvent event) {
        this.selectButton(this.favButton, "favlist-active", -35.0, 691.0);
        this.pageName.setText("Список избранных рецептов");
        this.pagesList.getSelectionModel().select(3);
    }

    @FXML
    public void onCartClick(ActionEvent event) {
        this.selectButton(this.cartButton, "cart-active", 953.0, -35.0);
        this.pageName.setText("Корзина");
        this.pagesList.getSelectionModel().select(4);
    }
}