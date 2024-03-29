package edu.valenciacollege;

import java.sql.*;

/*
 * Class for constructing and handling Database. NOTE: I didn't have access to create file in C drive directly, so I
 * opted to make a folder "databases" in C drive instead using file explorer.
 */
public class QuizDatabase {
    private String url = "jdbc:sqlite:C:\\additionQuiz.db";
    private Connection connection = null;
    private Statement statement = null;

    // Creates new Database and configures table.
    public void connectToDb()  {
        try  {
            connection = DriverManager.getConnection(url);
            if (connection != null) {
                // Create SQL commands
                String createString = "CREATE TABLE IF NOT EXISTS USER_SCORES (\n"
                        + "UserID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                        + "Username varchar(255),\n"
                        + "Score int);";

                // Execute Statements
                statement = connection.createStatement();
                statement.executeUpdate(createString);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    // Adds data to Database.
    public void insertUserScore(String name, int score) {
        try {
            // Connect to db.
            connection = DriverManager.getConnection(url);

            // Insert Info
            String sqlInsert = "INSERT INTO USER_SCORES (Username, Score)\n"
                    + "VALUES(?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, score);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            // Close connection.
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Retrieves top 10 users by Score.
    public String getTopTen() {
        StringBuilder resultString = new StringBuilder();
        // Create Connection.
        try {
            connection = DriverManager.getConnection(url);

            // Create SQL String
            String sqlGetInfo = "SELECT Username, Score FROM USER_SCORES ORDER BY Score DESC;";

            // Update statement
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlGetInfo);

            // Loop through 10 results
            for (int i = 0; i < 10; i ++) {
                if (resultSet.next()) {
                    String text = resultSet.getString("Username") + "\t" +
                            resultSet.getInt("Score") + "\n";
                    resultString.append(text);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            // Close Connection.
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultString.toString();
    }
}
