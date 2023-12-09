package recipemanager.recipe;
import java.util.Collections;
import java.util.List;

public class Recipe {

    private boolean isFavourite;
    private String title;
    private String category;
    private String imagePath;
    private String cuisine;
    private String difficulty;
    private String cookingTime;
    private RecipeIngredients ingredients = new RecipeIngredients();
    private RecipeSteps steps = new RecipeSteps();

    public Recipe() {
        this.isFavourite = false;
        this.title = "Не указано";
        this.category = "Не указано";
        this.imagePath = "data/icons/image_placeholder.png";
        this.cuisine = "Не указано";
        this.difficulty = "Не указано";
        this.cookingTime = "Не указано";
        this.ingredients.setIngredients(Collections.singletonList(("Ингредиенты не указаны")));
        this.steps.setDescriptions(Collections.singletonList(("Шаги не указаны")));
        this.steps.setImagePaths(Collections.singletonList(("Изображения шагов не указаны")));
    }


    // SETTERS
    public void setFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setImagePath(String image_path) {
        this.imagePath = image_path;
    }
    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }
    public void setIngredients(RecipeIngredients ingredients) {
        this.ingredients = ingredients;
    }
    public void setSteps(RecipeSteps steps) {
        this.steps = steps;
    }


    // GETTERS
    public boolean getFavourite() {
        return this.isFavourite;
    }
    public String getTitle() {
        return this.title;
    }
    public String getCategory() {
        return this.category;
    }
    public String getImagePath() {
        return this.imagePath;
    }
    public String getCuisine() {
        return this.cuisine;
    }
    public String getDifficulty() {
        return this.difficulty;
    }
    public String getCookingTime() {
        return this.cookingTime;
    }
    public RecipeIngredients getIngredients() {
        return this.ingredients;
    }
    public RecipeSteps getSteps() {
        return this.steps;
    }
}
