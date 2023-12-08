package recipemanager.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import recipemanager.MainApplication;
import recipemanager.dataprocessing.DatabaseHandler;
import recipemanager.recipe.Recipe;
import recipemanager.recipe.RecipeIngredients;
import recipemanager.recipe.RecipeParser;
import recipemanager.recipe.RecipeSummary;

import java.io.File;
import java.util.List;

public class MainControllers {

    @FXML
    TabPane pagesList;
    @FXML
    Label pageName;
    @FXML
    private Button recipeListPageButton;
    @FXML
    private Button makeRecipePageButton;

    @FXML
    Button favPageButton;
    @FXML
    Button cartPageButton;
    @FXML
    private Button selectionButton;

    void selectButton(Button button, String styleClass, double layoutX, double layoutY) {
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
        List<RecipeSummary> recipes = DatabaseHandler.getAllRecipeSummaries();
        loadRecipeList(recipes);
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
    private AnchorPane recipeMainPane;
    @FXML
    private AnchorPane recipeIngredientsPane;
    @FXML
    private Label recipeStepsLabel;
    @FXML
    private AnchorPane recipeStepsPane;

    public void openRecipe(Recipe recipe) {
        this.pagesList.getSelectionModel().select(5);
        this.pageName.setText("Просмотр рецепта");

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

        // Отображение ингредиентов
        recipeIngredientsPane.getChildren().clear();
        double yPositionIngredients = 25;
        RecipeIngredients ingredients = recipe.getIngredients();
        for (String ingredient : ingredients.ingredients) {
            Label ingredientLabel = new Label("· " + ingredient);
            ingredientLabel.setStyle("{" +
                    "    -fx-font-family: 'Roboto Medium';" +
                    "    -fx-font-size: 20px;" +
                    "    -fx-text-fill: #656565;" +
                    "    -fx-max-width: 825;" +
                    "    -fx-ellipses-string: \"...\";" +
                    "    -fx-wrap-text: true;" +
                    "}");

            HBox stepBox = new HBox(10, ingredientLabel);
            AnchorPane.setTopAnchor(stepBox, yPositionIngredients);
            AnchorPane.setLeftAnchor(stepBox, 25.0);
            recipeIngredientsPane.getChildren().add(stepBox);
            yPositionIngredients += 50;
        }

        recipeIngredientsPane.setMinHeight(yPositionIngredients);
        recipeIngredientsPane.setPrefHeight(yPositionIngredients);

        recipeStepsLabel.setLayoutY(yPositionIngredients + 535);
        recipeStepsPane.setLayoutY(yPositionIngredients + 575);

        this.recipeBJU.setText("~ " + String.format("%.1f", ingredients.ingredients.size() * 4.105) + "г. | " +
                String.format("%.1f", ingredients.ingredients.size() * 2.457) + "г. | " +
                String.format("%.1f", ingredients.ingredients.size() * 1.571) + "г.");

        // Отображение каждого шага
        recipeStepsPane.getChildren().clear();
        double yPositionSteps = 25;
        if (!recipe.getSteps().getImagePaths().isEmpty()) {
            for (String url : recipe.getSteps().getImagePaths()) {
                ImageView imageView;
                imageView = new ImageView(new Image(url));

                imageView.setFitWidth(200);
                imageView.setLayoutX(10);
                imageView.setPreserveRatio(true);
                Label stepLabel = new Label("· " + recipe.getSteps().getDescriptions().get(recipe.getSteps().getImagePaths().indexOf(url)));
                stepLabel.setStyle("{" +
                        "    -fx-font-family: 'Roboto Medium';" +
                        "    -fx-font-size: 20px;" +
                        "    -fx-text-fill: #656565;" +
                        "    -fx-max-width: 645;" +
                        "    -fx-ellipses-string: \"...\";" +
                        "    -fx-wrap-text: true;" +
                        "}");

                HBox stepBox = new HBox(10, imageView, stepLabel);
                AnchorPane.setTopAnchor(stepBox, yPositionSteps);
                AnchorPane.setLeftAnchor(stepBox, 25.0);
                recipeStepsPane.getChildren().add(stepBox);
                if (stepLabel.getText().length() / 38 > 4) {
                    yPositionSteps += 25 + imageView.getBoundsInLocal().getHeight() + stepLabel.getText().length() / 2.75;
                } else {
                    yPositionSteps += 25 + imageView.getBoundsInLocal().getHeight();
              }
            }
        } else {
            for (String step : recipe.getSteps().getDescriptions()) {
                Label stepLabel = new Label("· " + step);
                stepLabel.setStyle("{" +
                        "    -fx-font-family: 'Roboto Medium';" +
                        "    -fx-font-size: 20px;" +
                        "    -fx-text-fill: #656565;" +
                        "    -fx-max-width: 825;" +
                        "    -fx-ellipses-string: \"...\";" +
                        "    -fx-wrap-text: true;" +
                        "}");

                HBox stepBox = new HBox(10, stepLabel);
                AnchorPane.setTopAnchor(stepBox, yPositionSteps);
                AnchorPane.setLeftAnchor(stepBox, 25.0);
                recipeStepsPane.getChildren().add(stepBox);
                yPositionSteps += 50 + (stepLabel.getText().length() - 1) / 2.0;
            }
        }
        recipeStepsPane.setMinHeight(yPositionSteps);
        recipeStepsPane.setPrefHeight(yPositionSteps);

        recipeMainPane.setMinHeight(yPositionSteps + yPositionIngredients + 600);
        recipeMainPane.setPrefHeight(yPositionSteps + yPositionIngredients + 600);
    }

    // RECIPE LIST PAGE
    // --------------------------
    @FXML
    private GridPane recipeListGrid;
    @FXML
    private ComboBox<String> sortingBox;
    @FXML
    private ComboBox<String> filterDifficulty;
    @FXML
    private ComboBox<String> filterCategory;
    @FXML
    private ComboBox<String> filterCuisine;
    @FXML
    private ComboBox<String> filterIngredients;

    private ColumnConstraints newColumn() {
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPrefWidth(288);
        return columnConstraints;
    }

    private Pane newRecipePreviewPane(RecipeSummary recipe_sum) {
        Pane pane = new Pane();
        pane.getStyleClass().add("recipeplane");
        Recipe recipe =  DatabaseHandler.getRecipeByRecipeId(recipe_sum.id);
        pane.setOnMouseClicked(event -> openRecipe(recipe));

        Label title = new Label(recipe_sum.title);
        title.setLayoutX(15);
        title.setLayoutY(15);
        title.getStyleClass().add("recipePreviewTitle");
        title.setPrefWidth(288);
        title.setAlignment(Pos.CENTER);

        Label category_cuisine = new Label(recipe_sum.category + " - " + recipe_sum.cuisine);
        category_cuisine.setLayoutX(15);
        category_cuisine.setLayoutY(215);
        category_cuisine.getStyleClass().add("recipePreviewCuisineCategory");
        category_cuisine.setPrefWidth(288);
        category_cuisine.setAlignment(Pos.CENTER);

        Label time = new Label(recipe_sum.cookingTime);
        time.setLayoutX(50);
        time.setLayoutY(255);
        time.getStyleClass().add("recipePreviewDifficultyTime");
        time.setPrefWidth(288);

        Button timeIcon = new Button();
        timeIcon.setLayoutX(5);
        timeIcon.setLayoutY(245);
        timeIcon.getStyleClass().add("time");
        timeIcon.setOpacity(0.3);
        timeIcon.setPrefWidth(45);
        timeIcon.setPrefHeight(45);

        Label difficulty = new Label(recipe_sum.difficulty);
        difficulty.setLayoutX(194);
        difficulty.setLayoutY(255);
        difficulty.getStyleClass().add("recipePreviewDifficultyTime");
        difficulty.setPrefWidth(288);

        Button difficultyIcon = new Button();
        difficultyIcon.setLayoutX(149);
        difficultyIcon.setLayoutY(245);
        difficultyIcon.getStyleClass().add("difficulty");
        difficultyIcon.setOpacity(0.3);
        difficultyIcon.setPrefWidth(45);
        difficultyIcon.setPrefHeight(45);

        ImageView imagePreview;
        imagePreview = new ImageView(new Image(recipe_sum.imagepath));
        imagePreview.setFitWidth(258);
        imagePreview.setLayoutX(15);
        imagePreview.setLayoutY(50);
        imagePreview.setPreserveRatio(true);
        Rectangle clip = new Rectangle(258, 165);
        clip.setArcWidth(25);
        clip.setArcHeight(25);
        Rectangle bclip = new Rectangle(288, 190);
        bclip.setArcWidth(25);
        bclip.setArcHeight(25);
        imagePreview.setClip(clip);

        imagePreview.setOnMouseEntered(e -> {
            imagePreview.setClip(bclip);
            imagePreview.setTranslateX(-15);
            imagePreview.setFitWidth(288);
        });

        imagePreview.setOnMouseExited(e -> {
            imagePreview.setClip(clip);
            imagePreview.setTranslateX(0);
            imagePreview.setFitWidth(258);
        });

        pane.getChildren().add(title);
        pane.getChildren().add(category_cuisine);
        pane.getChildren().add(time);
        pane.getChildren().add(timeIcon);
        pane.getChildren().add(difficulty);
        pane.getChildren().add(difficultyIcon);
        pane.getChildren().add(imagePreview);
        return pane;
    }

    private void loadRecipeList(List<RecipeSummary> recipes) {
        sortingBox.setValue("По умолчанию");
        ObservableList<String> items = FXCollections.observableArrayList("По умолчанию", "По названию", "По сложности", "По БЖУ", "По ингредиентам", "По времени готовки", "По кухне", "По категории");
        sortingBox.setItems(items);
        this.selectButton(this.recipeListPageButton, "recipelist-active", -35.0, 130.0);
        this.pageName.setText("Список рецептов");
        this.pagesList.getSelectionModel().select(0);

        recipeListGrid.getChildren().clear();
        recipeListGrid.getColumnConstraints().clear();
        recipeListGrid.getRowConstraints().clear();
        recipeListGrid.setHgap(10);
        recipeListGrid.setVgap(10);

        int rows = recipes.size() / 3;
        recipeListGrid.setMinHeight(rows * 300 + 75);
        recipeListGrid.getColumnConstraints().addAll(newColumn(), newColumn(), newColumn());

        for (int row = 0; row < rows; row++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(300);

            for (int col = 0; col < 3; col++) {
                Pane pane = newRecipePreviewPane(recipes.get(row + col));
                recipeListGrid.add(pane, col, row);
            }
            recipeListGrid.getRowConstraints().add(rowConstraints);
        }
    }
    public void onRecipeListPageClick(ActionEvent event) {
        List<RecipeSummary> recipes = DatabaseHandler.getAllRecipeSummaries();
        loadRecipeList(recipes);
    }


    // MAKE NEW RECIPE PAGE
    // --------------------------

    @FXML
    public AnchorPane stepAnchorPane;

    @FXML
    public VBox recipeElementsVBox;

    @FXML
    public AnchorPane createRecipeAnchorPane;

    @FXML
    public ImageView recipeImage1;


    public void onMakeRecipePageClick(ActionEvent event) {
        this.selectButton(this.makeRecipePageButton, "edit-active", -35.0, 217.0);
        this.pageName.setText("Создание рецепта");
        this.pagesList.getSelectionModel().select(1); // Предполагается, что здесь осуществляется переключение на страницу для создания рецепта


        recipeImage1.setOnMouseClicked(mouseEvent -> {
            FileChooser fileChooser = new FileChooser();

// Добавляем фильтры для файлов изображений
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
            fileChooser.getExtensionFilters().addAll(extFilterPNG, extFilterJPG);

            Stage stage = (Stage) recipeElementsVBox.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                try {
                    Image image = new Image(selectedFile.toURI().toString());
                    recipeImage1.setImage(image);
                } catch (Exception e) {
                    e.printStackTrace(); // Обработка ошибок загрузки изображения
                }
            }

        });
    }


    public void createNewStep(ActionEvent event) {
        // Создание текстового поля для шага с применением стиля
        TextArea stepTextArea = new TextArea();
        stepTextArea.setStyle("-fx-font-family: 'Roboto Medium';" +
                "-fx-font-size: 20px;" +
                "-fx-text-fill: #656565;" +
                "-fx-max-width: 825;" +
                "-fx-ellipses-string: \"...\";" +
                "-fx-wrap-text: true;");
        stepTextArea.setPromptText("Введите шаг");
        stepTextArea.setPrefSize(500, 250); // Настройка размеров текстового поля

        // Создание кнопки для загрузки изображения
        Button uploadImageButton = new Button("Загрузить изображение");
        ImageView imageView = new ImageView(); // Изначально пустое изображение
        imageView.setFitWidth(250); // Установка ширины изображения
        imageView.setFitHeight(170);

        // Установка изображения-заглушки
        Image placeholderImage = new Image(getClass().getResourceAsStream("/recipemanager/data/icons/image_placeholder.png"));
        imageView.setImage(placeholderImage);

        // Обработчик нажатия на изображение для загрузки нового изображения
        imageView.setOnMouseClicked(mouseEvent -> {
            FileChooser fileChooser = new FileChooser();

// Добавляем фильтры для файлов изображений
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
            fileChooser.getExtensionFilters().addAll(extFilterPNG, extFilterJPG);

            Stage stage = (Stage) recipeElementsVBox.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                try {
                    Image image = new Image(selectedFile.toURI().toString());
                    imageView.setImage(image);
                } catch (Exception e) {
                    e.printStackTrace(); // Обработка ошибок загрузки изображения
                }
            }

        });

        // Создание контейнера для текстового поля и изображения
        VBox textAndButtonBox = new VBox(); // VBox для вертикального расположения элементов
        textAndButtonBox.getChildren().addAll(stepTextArea, uploadImageButton); // Добавление элементов в VBox

        // Создание контейнера для текстового поля, кнопки и изображения
        HBox stepBox = new HBox(20); // HBox для горизонтального расположения элементов
        stepBox.getChildren().addAll(textAndButtonBox, imageView); // Добавление VBox и ImageView в HBox

        // Добавление созданного контейнера в VBox
        recipeElementsVBox.getChildren().add(stepBox);

        createRecipeAnchorPane.setPrefHeight(createRecipeAnchorPane.getPrefHeight() + stepBox.getHeight() + 200); // Примерный отступ между элементами

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


    // RECIPE IMPORT PAGE
    // --------------------------
    @FXML
    Button importRecipePageButton;
    @FXML
    private Label previewRecipeTitleLabel;
    @FXML
    TextField linkInput;
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
            DatabaseHandler.addRecipe(recipe);


            this.openRecipe(recipe);
        }
    }
}