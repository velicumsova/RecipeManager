package recipemanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

public class Controllers {
    @FXML
    private TabPane pagesList;
    @FXML
    private Label pageName;
    @FXML
    private Button recipeListPageButton;
    @FXML
    private Button makeRecipePageButton;

    @FXML
    private Button favPageButton;
    @FXML
    private Button cartPageButton;
    @FXML
    private Button selectionButton;

    private void selectButton(Button button, String styleClass, double layoutX, double layoutY) {
        this.recipeListPageButton.getStyleClass().remove("recipelist-active");
        this.makeRecipePageButton.getStyleClass().remove("edit-active");
        this.importRecipePageButton.getStyleClass().remove("import-active");
        this.favPageButton.getStyleClass().remove("favlist-active");
        this.cartPageButton.getStyleClass().remove("cart-active");

        button.getStyleClass().add(styleClass);

        this.selectionButton.setLayoutX(layoutX);
        this.selectionButton.setLayoutY(layoutY);
    }

    public void initialize() {
        this.selectButton(this.recipeListPageButton, "recipelist-active", -35.0, 130.0);
        this.pageName.setText("Список рецептов");
        this.pagesList.getSelectionModel().select(0);
    }

    // FAVOURITE RECIPE LIST PAGE
    // --------------------------
    public void onRecipeListPageClick(ActionEvent event) {
        this.selectButton(this.recipeListPageButton, "recipelist-active", -35.0, 130.0);
        this.pageName.setText("Список рецептов");
        this.pagesList.getSelectionModel().select(0);
    }

    // MAKE NEW RECIPE PAGE
    // --------------------------
    public void onMakeRecipePageClick(ActionEvent event) {
        this.selectButton(this.makeRecipePageButton, "edit-active", -35.0, 217.0);
        this.pageName.setText("Создание рецепта");
        this.pagesList.getSelectionModel().select(1);
    }

    // RECIPE IMPORT PAGE
    // --------------------------
    @FXML
    private Button importRecipePageButton;
    @FXML
    private Label previewRecipeTitleLabel;
    @FXML
    private TextField linkInput;
    @FXML
    private Button importButton;

    public void onImportRecipePageClick(ActionEvent event) {
        this.selectButton(this.importRecipePageButton, "import-active", -35.0, 304.0);
        this.pageName.setText("Импорт рецепта");
        this.pagesList.getSelectionModel().select(2);
    }

    public void onLinkInput() {
        String link = this.linkInput.getText();
        Recipe recipe = RecipeParser.parseRecipe(link);
        this.previewRecipeTitleLabel.setText(recipe.getTitle());
    }

    public void onImportClick(ActionEvent event) {
        this.linkInput.getStyleClass().remove("textinput-error");
        this.linkInput.getStyleClass().add("textinput");
        String link = this.linkInput.getText();
        Recipe recipe = RecipeParser.parseRecipe(link);

        if (recipe == null) {
            this.linkInput.getStyleClass().add("textinput-error");
        }
        else {
            System.out.println("Название: " + recipe.getTitle());
            System.out.println("Кухня: " + recipe.getCuisine());
            System.out.println("Сложность: " + recipe.getDifficulty());
            System.out.println("Время: " + recipe.getCookingTime());
            System.out.println("Ингредиенты:");
            for (String ingredient : recipe.getIngredients()) {
                System.out.println("- " + ingredient);
            }
            System.out.println("Шаги:");
            for (String step : recipe.getSteps()) {
                System.out.println("- " + step);
            }
            this.previewRecipeTitleLabel.setText(recipe.getTitle());
        }
    }

    // FAVOURITE RECIPE LIST PAGE
    // --------------------------
    public void onFavPageClick(ActionEvent event) {
        this.selectButton(this.favPageButton, "favlist-active", -35.0, 691.0);
        this.pageName.setText("Список избранных рецептов");
        this.pagesList.getSelectionModel().select(3);
    }

    // CART PAGE
    // --------------------------
    public void onCartPageClick(ActionEvent event) {
        this.selectButton(this.cartPageButton, "cart-active", 953.0, -35.0);
        this.pageName.setText("Корзина");
        this.pagesList.getSelectionModel().select(4);
    }
}