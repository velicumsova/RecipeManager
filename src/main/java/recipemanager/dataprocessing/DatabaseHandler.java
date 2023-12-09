package recipemanager.dataprocessing;

import recipemanager.MainApplication;
import recipemanager.recipe.Recipe;
import recipemanager.recipe.RecipeIngredients;
import recipemanager.recipe.RecipeSteps;
import recipemanager.recipe.RecipeSummary;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private static Connection dbConnection;

    public static Connection getDbConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        dbConnection = DriverManager.getConnection("jdbc:sqlite:recipemanager.s3db");
        createTablesIfNotExist();
        return dbConnection;
    }

    private static void createTablesIfNotExist() throws SQLException {
        try (Statement statement = dbConnection.createStatement()) {

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS recipes (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "isfav BOOLEAN," +
                    "title VARCHAR(255)," +
                    "cuisine VARCHAR(255)," +
                    "difficulty VARCHAR(255)," +
                    "cookingtime INTEGER," +
                    "imagepath VARCHAR(255)," +
                    "category VARCHAR(255)" +
                    ");");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS ingredients (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "recipe_id INTEGER," +
                    "ingredient VARCHAR(255)," +
                    "FOREIGN KEY (recipe_id) REFERENCES recipes(id)" +
                    ");");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS steps (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "recipe_id INTEGER," +
                    "step VARCHAR(255)," +
                    "image VARCHAR(255)," +
                    "FOREIGN KEY (recipe_id) REFERENCES recipes(id)" +
                    ");");
        }
    }

    public static void addRecipe(Recipe recipe) {
        String insertRecipe = "INSERT INTO recipes (isfav, title, category, imagepath, cuisine, difficulty, cookingtime) VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insertRecipe, Statement.RETURN_GENERATED_KEYS);
            prSt.setBoolean(1, recipe.getFavourite());
            prSt.setString(2, recipe.getTitle());
            prSt.setString(3, recipe.getCategory());
            prSt.setString(4, recipe.getImagePath());
            prSt.setString(5, recipe.getCuisine());
            prSt.setString(6, recipe.getDifficulty());
            prSt.setString(7, recipe.getCookingTime());
            prSt.executeUpdate();

            ResultSet generatedKeys = prSt.getGeneratedKeys();
            if (generatedKeys.next()) {
                long recipeId = generatedKeys.getLong(1);

                String insertIngredients = "INSERT INTO ingredients (recipe_id, ingredient) VALUES(?,?)";
                try {
                    PreparedStatement prSt_ing = getDbConnection().prepareStatement(insertIngredients);
                    for (String ingredient : recipe.getIngredients().ingredients) {
                        prSt_ing.setLong(1, recipeId);
                        prSt_ing.setString(2, ingredient);
                        prSt_ing.executeUpdate();
                    }

                } catch (ClassNotFoundException | SQLException e) {
                    System.out.println("Произошла ошибка: " + e);
                }

                String insertSteps = "INSERT INTO steps (recipe_id, step, image) VALUES(?,?,?)";
                try {
                    PreparedStatement prSt_stp = getDbConnection().prepareStatement(insertSteps);
                    for (int i = 0; i < recipe.getSteps().getDescriptions().size(); i++) {
                        String step = recipe.getSteps().getDescriptions().get(i);
                        String img = recipe.getSteps().getImagePaths().get(i);
                        prSt_stp.setLong(1, recipeId);
                        prSt_stp.setString(2, step);
                        prSt_stp.setString(3, img);
                        prSt_stp.executeUpdate();
                    }

                } catch (ClassNotFoundException | SQLException e) {
                    System.out.println("Произошла ошибка: " + e);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Произошла ошибка: " + e);
        }
    }

    public static List<RecipeSummary> getAllRecipeSummaries() {
        List<RecipeSummary> recipeList = new ArrayList<>();
        String selectRecipes = "SELECT id, title, isfav, imagepath, cuisine, category, difficulty, cookingtime  FROM recipes";
        try (Connection connection = getDbConnection();
             PreparedStatement prSt = connection.prepareStatement(selectRecipes);
             ResultSet resultSet = prSt.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                boolean isfav = resultSet.getBoolean("isfav");
                String imagepath = resultSet.getString("imagepath");
                String cuisine = resultSet.getString("cuisine");
                String category = resultSet.getString("category");
                String difficulty = resultSet.getString("difficulty");
                String cookingTime = resultSet.getString("cookingtime");

                recipeList.add(new RecipeSummary(id, title, isfav, imagepath, cuisine, category, difficulty, cookingTime));
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Произошла ошибка: " + e);
        }
        return recipeList;
    }

    public static List<String> getAllDifficulties() {
        List<String> difficultyList = new ArrayList<>();
        String selectCuisines = "SELECT DISTINCT difficulty FROM recipes";
        try (Connection connection = getDbConnection();
             PreparedStatement prSt = connection.prepareStatement(selectCuisines);
             ResultSet resultSet = prSt.executeQuery()) {

            while (resultSet.next()) {
                String difficulty = resultSet.getString("cuisine");
                difficultyList.add(difficulty);
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Произошла ошибка: " + e);
        }
        return difficultyList;
    }
    public static List<String> getAllCuisines() {
        List<String> cuisineList = new ArrayList<>();
        String selectCuisines = "SELECT DISTINCT cuisine FROM recipes";
        try (Connection connection = getDbConnection();
             PreparedStatement prSt = connection.prepareStatement(selectCuisines);
             ResultSet resultSet = prSt.executeQuery()) {

            while (resultSet.next()) {
                String cuisine = resultSet.getString("cuisine");
                cuisineList.add(cuisine);
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Произошла ошибка: " + e);
        }
        return cuisineList;
    }
    public static List<String> getAllCategories() {
        List<String> cuisineList = new ArrayList<>();
        String selectCuisines = "SELECT DISTINCT category FROM recipes";
        try (Connection connection = getDbConnection();
             PreparedStatement prSt = connection.prepareStatement(selectCuisines);
             ResultSet resultSet = prSt.executeQuery()) {

            while (resultSet.next()) {
                String cuisine = resultSet.getString("category");
                cuisineList.add(cuisine);
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Произошла ошибка: " + e);
        }
        return cuisineList;
    }
    public static List<String> getAllIngredients() {
        List<String> cuisineList = new ArrayList<>();
        String selectCuisines = "SELECT DISTINCT ingredient FROM ingredients";
        try (Connection connection = getDbConnection();
             PreparedStatement prSt = connection.prepareStatement(selectCuisines);
             ResultSet resultSet = prSt.executeQuery()) {

            while (resultSet.next()) {
                String cuisine = resultSet.getString("ingredient");
                cuisineList.add(cuisine);
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Произошла ошибка: " + e);
        }
        return cuisineList;
    }
    public static Recipe getRecipeByRecipeId(int recipeId) {
        Recipe recipe = null;
        String selectRecipe = "SELECT * FROM recipes WHERE id = ?";
        try (Connection connection = getDbConnection();
             PreparedStatement prSt = connection.prepareStatement(selectRecipe)) {
            prSt.setInt(1, recipeId);
            try (ResultSet resultSet = prSt.executeQuery()) {
                if (resultSet.next()) {
                    recipe = new Recipe();
                    recipe.setFavourite(resultSet.getBoolean("isfav"));
                    recipe.setTitle(resultSet.getString("title"));
                    recipe.setCategory(resultSet.getString("category"));
                    recipe.setImagePath(resultSet.getString("imagePath"));
                    recipe.setCuisine(resultSet.getString("cuisine"));
                    recipe.setDifficulty(resultSet.getString("difficulty"));
                    recipe.setCookingTime(resultSet.getString("cookingTime"));
                    recipe.setIngredients(getIngredientsByRecipeId(recipeId));
                    recipe.setSteps(getStepsByRecipeId(recipeId));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Произошла ошибка: " + e);
        }
        return recipe;
    }

    public static RecipeIngredients getIngredientsByRecipeId(int recipeId) {
        RecipeIngredients ingredients = new RecipeIngredients();
        String selectIngredients = "SELECT ingredient FROM ingredients WHERE recipe_id = ?";

        try (Connection connection = getDbConnection();
             PreparedStatement prSt = connection.prepareStatement(selectIngredients)) {
            prSt.setInt(1, recipeId);
            List<String> ingrs = new ArrayList<>();
            try (ResultSet resultSet = prSt.executeQuery()) {
                while (resultSet.next()) {
                    ingrs.add(resultSet.getString("ingredient"));
                }
            }

            ingredients.setIngredients(ingrs);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Произошла ошибка: " + e);
        }
        return ingredients;
    }

    public static RecipeSteps getStepsByRecipeId(int recipeId) {
        RecipeSteps steps = new RecipeSteps();
        String selectSteps = "SELECT step, image FROM steps WHERE recipe_id = ?";
        List<String> step_desrc = new ArrayList<>();
        List<String> step_imgs = new ArrayList<>();

        try (Connection connection = getDbConnection();
             PreparedStatement prSt = connection.prepareStatement(selectSteps)) {
            prSt.setInt(1, recipeId);
            try (ResultSet resultSet = prSt.executeQuery()) {
                while (resultSet.next()) {
                    step_desrc.add(resultSet.getString("step"));
                    step_imgs.add(resultSet.getString("image"));
                }
            }
            steps.setDescriptions(step_desrc);
            steps.setImagePaths(step_imgs);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Произошла ошибка: " + e);
        }
        return steps;
    }

}