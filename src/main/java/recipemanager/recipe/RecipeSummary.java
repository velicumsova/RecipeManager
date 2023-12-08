package recipemanager.recipe;

public class RecipeSummary {
    public int id;
    public String title;
    public String imagepath;
    public String cuisine;
    public String category;
    public String difficulty;
    public String cookingTime;

    public RecipeSummary(int id, String title, String imagepath, String cuisine, String category, String difficulty, String cookingTime) {
        this.id = id;
        this.title = title;
        this.imagepath = imagepath;
        this.cuisine = cuisine;
        this.category = category;
        this.difficulty = difficulty;
        this.cookingTime = cookingTime;
    }
}
