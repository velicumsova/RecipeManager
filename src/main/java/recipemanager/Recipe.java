package recipemanager;
import java.util.List;

public class Recipe {

    private boolean isFavourite;
    private String title;
    private String imagePath;
    private String cuisine;
    private String difficulty;
    private String cookingTime;
    private List<String> ingredients;
    private List<String> steps;
    private List<String> stepImagePaths;

    public Recipe() {
        this.isFavourite = false;
        this.title = "Не указано";
        this.imagePath = "data/icons/image_placeholder.png";
        this.cuisine = "Не указано";
        this.difficulty = "Не указано";
        this.cookingTime = "Не указано";
    }


    // SETTERS
    public void setFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }
    public void setTitle(String title) {
        this.title = title;
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
        this.ingredients = ingredients;
    }
    public void setSteps(List<String> steps) {
        this.steps = steps;
    }
    public void setStepImagePaths(List<String> stepImagePaths) {
        this.stepImagePaths = stepImagePaths;
    }


    // GETTERS
    public boolean getFavourite() {
        return this.isFavourite;
    }
    public String getTitle() {
        return this.title;
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
        return this.ingredients;
    }
    public List<String> getSteps() {
        return this.steps;
    }
    public List<String> getStepImagePaths() {
        return this.stepImagePaths;
    }
}
