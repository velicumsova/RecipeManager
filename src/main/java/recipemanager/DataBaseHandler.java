package recipemanager;

import java.sql.*;

public class DataBaseHandler {
    static Connection dbConnection;

    public static Connection getDbConnection()  throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        dbConnection = DriverManager.getConnection("jdbc:sqlite:recipemanager.s3db");
        return dbConnection;
    }

    public static void addRecipe(Recipe recipe){
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
                    for (String ingredient : recipe.getIngredients()) {
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
                    for (int i = 0; i < recipe.getSteps().size(); i++) {
                        String step = recipe.getSteps().get(i);
                        String img = recipe.getStepImagePaths().get(i);

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
}
