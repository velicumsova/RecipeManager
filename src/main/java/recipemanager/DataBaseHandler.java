package recipemanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseHandler extends Config{
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }
    public void addRecipe(Recipe recipe){
        String insert = "INSERT INTO " + Constants.RECIPES_TABLE + "(" + Constants.RECIPE_TITLE + ","
                + Constants.RECIPE_CUISINE + "," + Constants.RECIPE_DIFFICULTY + ","
                + Constants.RECIPE_COOKING_TIME + ")" + "VALUES(?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, recipe.getTitle());
            prSt.setString(2, recipe.getCuisine());
            prSt.setString(3, recipe.getDifficulty());
            prSt.setString(4, recipe.getCookingTime());
            prSt.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Произошла ошибка: " + e);
        }

    }
}
