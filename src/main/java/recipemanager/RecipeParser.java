package recipemanager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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

        Elements listItems = doc.select("li[itemprop='itemListElement'] a span");
        if (listItems.size() >= 2) {
            Element listItem = listItems.get(2);
            String category = listItem.text();
            if (!category.isEmpty()) {
                recipe.setCategory(category);
            }
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
}

