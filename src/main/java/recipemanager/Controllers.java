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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;


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
    private ScrollPane recipeMainScrollPane;
    @FXML
    private AnchorPane recipeMainPane;
    @FXML
    private AnchorPane recipeIngredientsPane;
    @FXML
    private Label recipeStepsLabel;
    @FXML
    private AnchorPane recipeStepsPane;

    public void openRecipe(Recipe recipe) {
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
        for (String ingredient : recipe.getIngredients()) {
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

        this.recipeBJU.setText("~ " + String.format("%.1f", recipe.getIngredients().size() * 4.105) + "г. | " +
                String.format("%.1f", recipe.getIngredients().size() * 2.457) + "г. | " +
                String.format("%.1f", recipe.getIngredients().size() * 1.571) + "г.");

        // Отображение каждого шага
        recipeStepsPane.getChildren().clear();
        double yPositionSteps = 25;
        if (!recipe.getStepImagePaths().isEmpty()) {
            for (String url : recipe.getStepImagePaths()) {
                ImageView imageView;
                imageView = new ImageView(new Image(url));

                imageView.setFitWidth(200);
                imageView.setLayoutX(10);
                imageView.setPreserveRatio(true);

                Label stepLabel = new Label("· " + recipe.getSteps().get(recipe.getStepImagePaths().indexOf(url)));
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
            for (String step : recipe.getSteps()) {
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




    // FAVOURITE RECIPE LIST PAGE
    // --------------------------
    public void onRecipeListPageClick(ActionEvent event) {
        this.selectButton(this.recipeListPageButton, "recipelist-active", -35.0, 130.0);
        this.pageName.setText("Список рецептов");
        this.pagesList.getSelectionModel().select(0);
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
            DataBaseHandler.addRecipe(recipe);

            List<RecipeSummary> recipes = DataBaseHandler.getAllRecipes();
            for (RecipeSummary recipe_sum : recipes) {
                System.out.println(recipe_sum.id + recipe_sum.title + recipe_sum.imagepath);
            }

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