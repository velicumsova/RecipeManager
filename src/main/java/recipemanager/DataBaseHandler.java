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
        String insert = "INSERT INTO recipes (isfav, title, imagepath, cuisine, difficulty, cookingtime) VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setBoolean(1, recipe.getFavourite());
            prSt.setString(2, recipe.getTitle());
            prSt.setString(3, recipe.getImagePath());
            prSt.setString(4, recipe.getCuisine());
            prSt.setString(5, recipe.getDifficulty());
            prSt.setString(6, recipe.getCookingTime());
            prSt.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Произошла ошибка: " + e);
        }
    }
}
