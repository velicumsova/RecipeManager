package recipemanager.recipe;

import java.util.Comparator;
import java.util.List;

public class RecipeSorter {

    public static List<RecipeSummary> sortByName(List<RecipeSummary> recipes) {
        recipes.sort(Comparator.comparing(recipe -> recipe.title.toLowerCase()));
        return recipes;
    }

    public static List<RecipeSummary> sortByDifficulty(List<RecipeSummary> recipes) {
        recipes.sort(new RecipeDifficultyComparator());
        return recipes;
    }

    public static List<RecipeSummary> sortByCookingTime(List<RecipeSummary> recipes) {
        recipes.sort(Comparator.comparingInt(recipe -> {
            String cookingTime = recipe.cookingTime;
            if (cookingTime.equals("Не указано")) {
                return Integer.MAX_VALUE;
            } else {
                String[] parts = cookingTime.split(" ");
                return Integer.parseInt(parts[0]);
            }
        }));
        return recipes;
    }

    public static List<RecipeSummary> sortByCuisine(List<RecipeSummary> recipes) {
        recipes.sort(Comparator.comparing(recipe -> recipe.cuisine.toLowerCase()));
        return recipes;
    }

    public static List<RecipeSummary> sortByCategory(List<RecipeSummary> recipes) {
        recipes.sort(Comparator.comparing(recipe -> recipe.category.toLowerCase()));
        return recipes;
    }

    private static class RecipeDifficultyComparator implements Comparator<RecipeSummary> {
        private static final String[] ORDER = {"Простой", "Средний", "Сложный", "Не определен"};

        @Override
        public int compare(RecipeSummary o1, RecipeSummary o2) {
            int index1 = getIndex(o1.difficulty);
            int index2 = getIndex(o2.difficulty);
            return Integer.compare(index1, index2);
        }

        private int getIndex(String difficulty) {
            for (int i = 0; i < ORDER.length; i++) {
                if (ORDER[i].equals(difficulty)) {
                    return i;
                }
            }
            return ORDER.length;
        }
    }
}
