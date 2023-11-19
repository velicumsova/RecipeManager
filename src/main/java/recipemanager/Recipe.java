package recipemanager;
import java.util.List;

public class Recipe {
    private String title;
    private String cuisine;
    private String difficulty;
    private String cookingTime;
    private List<String> ingredients;
    private List<String> steps;

    public Recipe() {
        this.title = "Не указано";
        this.cuisine = "Не указано";
        this.difficulty = "Не указано";
        this.cookingTime = "Не указано";
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getTitle() {
        return this.title;
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
}
