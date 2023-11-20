package recipemanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Controllers {
    DataBaseHandler dbHandler = new DataBaseHandler();
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
    // RECIPE PAGE
    // --------------------------
    @FXML
    private Button addToCartButton;
    @FXML
    private Label recipeTitle;
    @FXML
    private ImageView recipeImage;
    @FXML
    private Label recipeCuisine;
    @FXML
    private Label recipeDifficulty;
    @FXML
    private Label recipeCookingTime;
    @FXML
    private Label recipeBJU;
    @FXML
    private TextArea recipeIngredients;
    @FXML
    private TextArea recipeSteps;

    public void openRecipe(Recipe recipe) {
        this.pageName.setText("Просмотр рецепта");
        this.recipeTitle.setText(recipe.getTitle());
        this.recipeCuisine.setText(recipe.getCuisine() + " кухня");
        this.recipeDifficulty.setText(recipe.getDifficulty());
        this.recipeCookingTime.setText(recipe.getCookingTime());

        String ingredients = "";
        for (String ingredient : recipe.getIngredients()) {
            ingredients = ingredients + "· " + ingredient + "\n";
        }
        this.recipeIngredients.setText(ingredients);
        this.recipeBJU.setText("~ " + String.format("%.1f", recipe.getIngredients().size() * 2.105) + "г. | " +
                String.format("%.1f",recipe.getIngredients().size() * 1.457) + "г. | " +
                String.format("%.1f",recipe.getIngredients().size() * 0.571) + "г.");

        String steps = "";
        for (String step : recipe.getSteps()) {
            steps = steps + "· " + step + "\n";
        }
        this.recipeSteps.setText(steps);

        try {
            this.recipeImage.setImage(new Image("https://www.koolinar.ru" + recipe.getImagePath()));
        } catch (Exception e) {
            this.recipeImage.setImage(new Image(String.valueOf(MainApplication.class.getResource("data/icons/image_placeholder.png"))));
        }

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
            this.dbHandler.addRecipe(recipe);
            this.openRecipe(recipe);
            this.pagesList.getSelectionModel().select(5);
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