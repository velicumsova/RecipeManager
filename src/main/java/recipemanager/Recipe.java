package recipemanager;
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
    private final RecipeIngredients ingredients = new RecipeIngredients();
    private final RecipeSteps steps = new RecipeSteps();

    public Recipe() {
        this.isFavourite = false;
        this.title = "Не указано";
        this.category = "Не указано";
        this.imagePath = "data/icons/image_placeholder.png";
        this.cuisine = "Не указано";
        this.difficulty = "Не указано";
        this.cookingTime = "Не указано";
        this.ingredients.setIngredients(Collections.singletonList(("Ингредиенты не указаны")));
        this.steps.setSteps(Collections.singletonList(("Шаги не указаны")));
        this.steps.setStepImagePaths(Collections.singletonList(("Изображения шагов не указаны")));
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
    public void setIngredients(List<String> ingredients) {
        this.ingredients.setIngredients(ingredients);
    }
    public void setSteps(List<String> steps) {
        this.steps.setSteps(steps);
    }
    public void setStepImagePaths(List<String> stepImagePaths) {
        this.steps.setStepImagePaths(stepImagePaths);
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
    public List<String> getIngredients() {
        return this.ingredients.getIngredients();
    }
    public List<String> getSteps() {
        return this.steps.getSteps();
    }
    public List<String> getStepImagePaths() {
        return this.steps.getStepImagePaths();
    }
}
