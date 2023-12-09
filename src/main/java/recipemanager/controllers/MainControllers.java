package recipemanager.controllers;

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
import recipemanager.recipe.*;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        int i = 0;
        List<String> difficulties = Arrays.asList("Простой", "Средний", "Сложный", "Не определен");
        for (String difficulty : difficulties) {
            CheckBox difficultyBox = new CheckBox(difficulty);
            difficultyBox.setSelected(true);
            difficultyBox.setStyle("checkbox");
            difficultyBox.setLayoutX(10);
            difficultyBox.setLayoutY(i * 25 + 40);

            difficultyBox.setOnAction(event -> {
                if (difficultyBox.isSelected()) {
                    selectedDifficulties.add(difficulty);
                } else {
                    selectedDifficulties.remove(difficulty);
                }
            });

            selectedDifficulties.add(difficulty);
            difficultyList.getChildren().add(difficultyBox);
            i++;
        }

        i = 0;
        List<String> cuisines = DatabaseHandler.getAllCuisines();
        for (String cuisine : cuisines) {
            CheckBox cuisineBox = new CheckBox(cuisine);
            cuisineBox.setSelected(true);
            cuisineBox.setStyle("checkbox");
            cuisineBox.setLayoutY(i * 25);

            cuisineBox.setOnAction(event -> {
                if (cuisineBox.isSelected()) {
                    selectedCuisines.add(cuisine);
                } else {
                    selectedCuisines.remove(cuisine);
                }
            });

            selectedCuisines.add(cuisine);
            cuisineList.getChildren().add(cuisineBox);
            i++;
        }
        cuisineList.setMinHeight(i * 25);

        List<String> categories = DatabaseHandler.getAllCategories();
        i = 0;
        for (String category : categories) {
            CheckBox categoryBox = new CheckBox(category);
            categoryBox.setSelected(true);
            categoryBox.setStyle("checkbox");
            categoryBox.setLayoutY(i * 25);

            categoryBox.setOnAction(event -> {
                if (categoryBox.isSelected()) {
                    selectedCategories.add(category);
                } else {
                    selectedCategories.remove(category);
                }
            });

            selectedCategories.add(category);
            categoryList.getChildren().add(categoryBox);
            i++;
        }
        categoryList.setMinHeight(i * 25);
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
        int i = 0;
        for (String url : recipe.getSteps().getImagePaths()) {
            ImageView imageView;
            imageView = new ImageView(new Image(url));

            imageView.setFitWidth(200);
            imageView.setLayoutX(10);
            imageView.setPreserveRatio(true);
            Label stepLabel = new Label(recipe.getSteps().getDescriptions().get(i));
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
            i++;
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
    private AnchorPane difficultyList;
    @FXML
    private AnchorPane cuisineList;
    @FXML
    private AnchorPane categoryList;

    private ColumnConstraints newColumn() {
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPrefWidth(326);
        return columnConstraints;
    }

    private Pane newRecipePreviewPane(RecipeSummary recipe_sum) {
        Pane pane = new Pane();
        pane.getStyleClass().add("recipeplane");
        Recipe recipe = DatabaseHandler.getRecipeByRecipeId(recipe_sum.id);
        pane.setOnMouseClicked(event -> openRecipe(recipe));

        Label title = new Label(recipe_sum.title);
        title.setLayoutX(28);  // Уменьшено значение layoutX
        title.setLayoutY(15);
        title.getStyleClass().add("recipePreviewTitle");
        title.setPrefWidth(326);
        title.setAlignment(Pos.CENTER);

        Label category_cuisine = new Label(recipe_sum.category + " - " + recipe_sum.cuisine);
        category_cuisine.setLayoutX(28);  // Уменьшено значение layoutX
        category_cuisine.setLayoutY(215);
        category_cuisine.getStyleClass().add("recipePreviewCuisineCategory");
        category_cuisine.setPrefWidth(326);
        category_cuisine.setAlignment(Pos.CENTER);

        Label time = new Label(recipe_sum.cookingTime);
        time.setLayoutX(63);  // Уменьшено значение layoutX
        time.setLayoutY(255);
        time.getStyleClass().add("recipePreviewDifficultyTime");
        time.setPrefWidth(326);

        Button timeIcon = new Button();
        timeIcon.setLayoutX(18);  // Уменьшено значение layoutX
        timeIcon.setLayoutY(245);
        timeIcon.getStyleClass().add("time");
        timeIcon.setOpacity(0.3);
        timeIcon.setPrefWidth(45);
        timeIcon.setPrefHeight(45);

        Label difficulty = new Label(recipe_sum.difficulty);
        difficulty.setLayoutX(207);  // Уменьшено значение layoutX
        difficulty.setLayoutY(255);
        difficulty.getStyleClass().add("recipePreviewDifficultyTime");
        difficulty.setPrefWidth(288);

        Button difficultyIcon = new Button();
        difficultyIcon.setLayoutX(162);  // Уменьшено значение layoutX
        difficultyIcon.setLayoutY(245);
        difficultyIcon.getStyleClass().add("difficulty");
        difficultyIcon.setOpacity(0.3);
        difficultyIcon.setPrefWidth(45);
        difficultyIcon.setPrefHeight(45);

        ImageView imagePreview;
        imagePreview  = new ImageView(new Image(recipe_sum.imagepath));
        imagePreview.setFitWidth(271);  // Уменьшено значение FitWidth
        imagePreview.setLayoutX(28);  // Уменьшено значение layoutX
        imagePreview.setLayoutY(50);
        imagePreview.setPreserveRatio(true);
        Rectangle clip = new Rectangle(271, 165);  // Уменьшено значение dimensions
        clip.setArcWidth(25);
        clip.setArcHeight(25);
        Rectangle bclip = new Rectangle(301, 190);  // Уменьшено значение dimensions
        bclip.setArcWidth(25);
        bclip.setArcHeight(25);
        imagePreview.setClip(clip);

        imagePreview.setOnMouseEntered(e -> {
            imagePreview.setClip(bclip);
            imagePreview.setTranslateX(-15);
            imagePreview.setTranslateY(-15);
            imagePreview.setFitWidth(339);  // Уменьшено значение FitWidth
        });

        imagePreview.setOnMouseExited(e -> {
            imagePreview.setClip(clip);
            imagePreview.setTranslateX(0);
            imagePreview.setTranslateY(0);
            imagePreview.setFitWidth(271);  // Уменьшено значение FitWidth
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

    Set<String> selectedDifficulties = new HashSet<>();
    Set<String> selectedCuisines = new HashSet<>();
    Set<String> selectedCategories = new HashSet<>();
    private void loadRecipeList(List<RecipeSummary> recipes) {
        recipeListGrid.getChildren().clear();
        recipeListGrid.getColumnConstraints().clear();
        recipeListGrid.getRowConstraints().clear();
        recipeListGrid.setHgap(15);
        recipeListGrid.setVgap(15);

        int rows = (int) Math.ceil((double) recipes.size() / 2);
        recipeListGrid.setMinHeight(rows * 300 + 300);
        recipeListGrid.getColumnConstraints().addAll(newColumn(), newColumn());

        for (int row = 0; row < rows; row++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(300);

            for (int col = 0; col < 2; col++) {
                try {
                    Pane pane = newRecipePreviewPane(recipes.get(row * 2 + col));
                    recipeListGrid.add(pane, col, row);
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
            recipeListGrid.getRowConstraints().add(rowConstraints);
        }
    }

    public void onRecipeListPageClick(ActionEvent event) {
        this.selectButton(this.recipeListPageButton, "recipelist-active", -35.0, 130.0);
        this.pageName.setText("Список рецептов");
        this.pagesList.getSelectionModel().select(0);

        List<RecipeSummary> recipes = DatabaseHandler.getAllRecipeSummaries();
        loadRecipeList(recipes);
    }

    public void sortRecipes(ActionEvent event) {
        List<RecipeSummary> recipes = DatabaseHandler.getAllRecipeSummaries();
        recipes = RecipeFilter.filterRecipesByDifficulty(recipes, selectedDifficulties);
        recipes = RecipeFilter.filterRecipesByCuisine(recipes, selectedCuisines);
        recipes = RecipeFilter.filterRecipesByCategory(recipes, selectedCategories);
        String selectedOption = sortingBox.getValue();
        switch (selectedOption) {
            case "По умолчанию":
                loadRecipeList(recipes);
                break;
            case "По названию":
                loadRecipeList(RecipeSorter.sortByName(recipes));
                break;
            case "По сложности":
                loadRecipeList(RecipeSorter.sortByDifficulty(recipes));
                break;
            case "По времени готовки":
                loadRecipeList(RecipeSorter.sortByCookingTime(recipes));
                break;
            case "По кухне":
                loadRecipeList(RecipeSorter.sortByCuisine(recipes));
                break;
            case "По категории":
                loadRecipeList(RecipeSorter.sortByCategory(recipes));
                break;
            default:
                break;
        }
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
        Image placeholderImage = new Image(String.valueOf(MainApplication.class.getResource("/data/icons/image_placeholder.png")));
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
            this.openRecipe(recipe);
        }
    }
}