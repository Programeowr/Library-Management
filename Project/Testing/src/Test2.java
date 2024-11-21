import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Test2 {
    public static void main(String[] args) {

        // JDBC URL, username and password of MySQL server
        final String url = "jdbc:mysql://localhost:3306/test"; // Adjust as necessary
        final String user = "root"; // Your database user
        final String password = "#Tarundoom123"; // Your database password

        // Attempt to load the MySQL JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            return;
        }

        System.out.println("Enter jersey no.");
        Scanner scanner = new Scanner(System.in);
        int jersey = scanner.nextInt();

        System.out.println("Enter your first name:");
        String fName = scanner.next();

        System.out.println("Enter your last name:");
        String lName = scanner.next();

        System.out.println("Enter the number of runs");
        int runs = scanner.nextInt();

        System.out.println("Enter the number of wickets");
        int wic = scanner.nextInt();

        // SQL query to insert data
        String sql = "INSERT INTO new_table (`Jersey no`, `First Name`, `Last Name`, `Runs`, `Wickets`) VALUES (?, ?, ?, ?, ?)";

        // AutoCloseable resources in try-with-resources for automatic resource management
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set the values for placeholders
            preparedStatement.setInt(1, jersey);
            preparedStatement.setString(2, fName);
            preparedStatement.setString(3, lName);
            preparedStatement.setInt(4, runs);
            preparedStatement.setInt(5, wic);

            preparedStatement.addBatch();
            // Execute the insert operation
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Data inserted successfully.");
            } else {
                System.out.println("Data insertion failed.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
