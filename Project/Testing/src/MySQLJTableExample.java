import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class MySQLJTableExample extends JFrame {
    JTable table;

    public MySQLJTableExample() {
        setTitle("MySQL JTable Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a table model
        DefaultTableModel model = new DefaultTableModel();

        // Add columns to the model
        model.addColumn("Jersey no");
        model.addColumn("First Name");
        model.addColumn("Last Name");
        model.addColumn("Runs");
        model.addColumn("Wickets");

        // Create a JTable with the model
        table = new JTable(model);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Connect to MySQL database and retrieve data
        try {
            String url = "jdbc:mysql://localhost:3306/lib";
            String user = "root";
            String password = "#Tarundoom123";

            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from BookTable");

            while (resultSet.next()) {
                model.addRow(new Object[]{resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5)});
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MySQLJTableExample example = new MySQLJTableExample();
            example.setVisible(true);
        });
    }
}