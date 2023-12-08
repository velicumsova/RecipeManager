package recipemanager.recipe;
import java.util.List;

public class RecipeSteps {
    private List<String> steps;
    private List<String> stepImagePaths;

    // SETTERS
    public void setDescriptions(List<String> steps) {
        this.steps = steps;
    }
    public void setImagePaths(List<String> stepImagePaths) {
        this.stepImagePaths = stepImagePaths;
    }

    // GETTERS
    public List<String> getDescriptions() {
        return this.steps;
    }
    public List<String> getImagePaths() {
        return this.stepImagePaths;
    }
}
