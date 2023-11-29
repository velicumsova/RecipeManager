package recipemanager;
import java.util.List;

public class RecipeIngredients {
    private List<String> ingredients;

    // SETTER
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    // GETTER
    public List<String> getIngredients() {
        return this.ingredients;
    }
}
