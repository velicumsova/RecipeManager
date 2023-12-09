package recipemanager.recipe;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RecipeFilter {

    public static List<RecipeSummary> filterRecipesByDifficulty(List<RecipeSummary> recipes, Set<String> selectedDifficulties) {
        return recipes.stream()
                .filter(recipe -> selectedDifficulties.contains(recipe.difficulty))
                .collect(Collectors.toList());
    }
    public static List<RecipeSummary> filterRecipesByCuisine(List<RecipeSummary> recipes, Set<String> selectedCuisines) {
        return recipes.stream()
                .filter(recipe -> selectedCuisines.contains(recipe.cuisine))
                .collect(Collectors.toList());
    }

    public static List<RecipeSummary> filterRecipesByCategory(List<RecipeSummary> recipes, Set<String> selectedCategories) {
        return recipes.stream()
                .filter(recipe -> selectedCategories.contains(recipe.category))
                .collect(Collectors.toList());
    }
}
