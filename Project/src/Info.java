import java.sql.*;

public class Info {
    String url = "jdbc:mysql://localhost:3306/lib";
    String user = "root";
    String password = "#Tarundoom123";

    public boolean userCheckCredentials(String username, String pwd) {
        String sql = "SELECT * FROM UserTable WHERE ID = ? AND PASSWORD = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, pwd);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();  // If there's a result, return true (credentials are correct)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;  // If there's an exception or no result, return false
    }

    public boolean adminCheckCredentials(String username, String pwd) {
        String sql = "SELECT * FROM AdminTable WHERE ID = ? AND PASSWORD = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, pwd);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();  // If there's a result, return true (credentials are correct)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;  // If there's an exception or no result, return false
    }

}