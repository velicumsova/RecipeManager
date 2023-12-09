package recipemanager;
import java.util.List;

public class RecipeSteps {
    private List<String> steps;
    private List<String> stepImagePaths;

    // SETTERS
    public void setSteps(List<String> steps) {
        this.steps = steps;
    }
    public void setStepImagePaths(List<String> stepImagePaths) {
        this.stepImagePaths = stepImagePaths;
    }

    // GETTERS
    public List<String> getSteps() {
        return this.steps;
    }
    public List<String> getStepImagePaths() {
        return this.stepImagePaths;
    }
}
