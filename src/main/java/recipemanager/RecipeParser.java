package recipemanager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class RecipeParser {

    public static Recipe parseRecipe(String link) {
        Recipe recipe = new Recipe();
        Document doc;

        try {
            doc = Jsoup.connect(link).get();
        } catch (Exception e) {
            return null;
        }

        String title = doc.select(".fn").text();
        if (!title.isEmpty()) {
            recipe.setTitle(title);
        }

        String category = doc.select(".itemprop").text();
        if (!category.isEmpty()) {
            System.out.println(category);
            recipe.setCategory(category);
        }

        String imageUrl = doc.select(".b-recept__main-img img").attr("src");
        if (!imageUrl.isEmpty()) {
            recipe.setImagePath("https://www.koolinar.ru" + imageUrl);
        }

        String cuisine = doc.select(".p-tooltip[data-tooltip=\"Кухня\"]").text();
        if (!cuisine.isEmpty()) {
            recipe.setCuisine(cuisine);
        }

        String difficulty = doc.select(".p-tooltip[data-tooltip=\"Сложность приготовления\"]").text();
        if (!difficulty.isEmpty()) {
            recipe.setDifficulty(difficulty);
        }

        String cookingTime = doc.select(".p-tooltip[data-tooltip=\"Время приготовления\"]").text();
        if (!cookingTime.isEmpty()) {
            recipe.setCookingTime(cookingTime);
        }

        Elements ingredients = doc.select(".ingredient");
        if (!ingredients.isEmpty()) {
            recipe.setIngredients(ingredients.eachText());
        }

        Elements steps = doc.select(".instruction");
        if (!steps.isEmpty()) {
            recipe.setSteps(steps.eachText());
        }

        Elements stepImages = doc.select(".instruction");
        if (!steps.isEmpty()) {
            recipe.setSteps(steps.eachText());
        }


        return recipe;

    }

    public static void main (String[] args) {
        parseRecipe("https://www.koolinar.ru/recipe/view/109844");
    }

}

