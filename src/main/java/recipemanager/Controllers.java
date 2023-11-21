package recipemanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
    private Label recipeCategory;
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
    private ScrollPane recipeMainScrollPane;
    @FXML
    private AnchorPane recipeMainPane;
    @FXML
    private AnchorPane recipeStepsPane;

    public void openRecipe(Recipe recipe) {
        this.pageName.setText("Просмотр рецепта");
        System.out.println(recipe.getSteps());
        System.out.println(recipe.getStepImagePaths());

        try {
            this.recipeImage.setImage(new Image(recipe.getImagePath()));
        } catch (Exception e) {
            this.recipeImage.setImage(new Image(String.valueOf(MainApplication.class.getResource("data/icons/image_placeholder.png"))));
        }

        this.pageName.setText("Просмотр рецепта");
        this.recipeTitle.setText(recipe.getTitle());
        this.recipeCategory.setText(recipe.getCategory());
        this.recipeCuisine.setText(recipe.getCuisine() + " кухня");
        this.recipeDifficulty.setText(recipe.getDifficulty());
        this.recipeCookingTime.setText(recipe.getCookingTime());

        String ingredients = "";
        for (String ingredient : recipe.getIngredients()) {
            ingredients = ingredients + "· " + ingredient + "\n";
        }
        this.recipeIngredients.setText(ingredients);
        this.recipeBJU.setText("~ " + String.format("%.1f", recipe.getIngredients().size() * 4.105) + "г. | " +
                String.format("%.1f", recipe.getIngredients().size() * 2.457) + "г. | " +
                String.format("%.1f", recipe.getIngredients().size() * 1.571) + "г.");

        // Отображение каждого шага
        double yPosition = 25;
        if (!recipe.getStepImagePaths().isEmpty()) {
            for (String url : recipe.getStepImagePaths()) {
                ImageView imageView;
                imageView = new ImageView(new Image(url));

                imageView.setFitWidth(200);
                imageView.setLayoutX(10);
                imageView.setPreserveRatio(true);

                Label stepLabel = new Label(recipe.getSteps().get(recipe.getStepImagePaths().indexOf(url)));
                stepLabel.setStyle("{" +
                        "    -fx-font-family: 'Roboto Medium';" +
                        "    -fx-font-size: 20px;" +
                        "    -fx-text-fill: #656565;" +
                        "    -fx-max-width: 645;" +
                        "    -fx-ellipses-string: \"...\";" +
                        "    -fx-wrap-text: true;" +
                        "}");

                HBox stepBox = new HBox(10, imageView, stepLabel);
                AnchorPane.setTopAnchor(stepBox, yPosition);
                AnchorPane.setLeftAnchor(stepBox, 25.0);
                recipeStepsPane.getChildren().add(stepBox);
                if (stepLabel.getText().length() / 38 > 4) {
                    yPosition += 25 + imageView.getBoundsInLocal().getHeight() + stepLabel.getText().length() / 2.75;
                } else {
                    yPosition += 25 + imageView.getBoundsInLocal().getHeight();
                }
            }
            recipeStepsPane.setMinHeight(yPosition);
            recipeStepsPane.setPrefHeight(yPosition);

            recipeMainPane.setMinHeight(yPosition + 925);
            recipeMainPane.setPrefHeight(yPosition + 925);
        } else {
            for (String step : recipe.getSteps()) {
                Label stepLabel = new Label(step);
                stepLabel.setStyle("{" +
                        "    -fx-font-family: 'Roboto Medium';" +
                        "    -fx-font-size: 20px;" +
                        "    -fx-text-fill: #656565;" +
                        "    -fx-max-width: 825;" +
                        "    -fx-ellipses-string: \"...\";" +
                        "    -fx-wrap-text: true;" +
                        "}");

                HBox stepBox = new HBox(10, stepLabel);
                AnchorPane.setTopAnchor(stepBox, yPosition);
                AnchorPane.setLeftAnchor(stepBox, 25.0);
                recipeStepsPane.getChildren().add(stepBox);
                if (stepLabel.getText().length() / 38 > 4) {
                    yPosition += 75 + stepLabel.getText().length() / 2.75;
                } else {
                    yPosition += 75;
                }
            }
            recipeStepsPane.setMinHeight(yPosition);
            recipeStepsPane.setPrefHeight(yPosition);

            recipeMainPane.setMinHeight(yPosition + 925);
            recipeMainPane.setPrefHeight(yPosition + 925);
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

        this.linkInput.getStyleClass().remove("textinput-error");
        this.linkInput.getStyleClass().add("textinput");
        this.linkInput.clear();
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
            // DataBaseHandler.addRecipe(recipe);
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