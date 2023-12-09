package recipemanager;

public class RecipeSummary {
    public long id;
    public String title;
    public String imagepath;

    public RecipeSummary(long id, String title, String imagepath) {
        this.id = id;
        this.title = title;
        this.imagepath = imagepath;
    }
}
