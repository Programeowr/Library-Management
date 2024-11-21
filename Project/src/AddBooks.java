import java.sql.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AddBooks implements ActionListener {
    JFrame frame = new JFrame();
    JLabel messageLabel = new JLabel("Books");
    JButton backButton = new JButton("Go Back");
    JButton addButton = new JButton("Add Books");
    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);
    JScrollPane scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    String userID;

    AddBooks(String name) {

        userID = name;

        messageLabel.setBounds(50, 0, 300, 50);
        messageLabel.setFont(new Font(null, Font.PLAIN, 25));

        backButton.setBounds(250, 400, 200, 25);
        backButton.setFocusable(false);
        backButton.addActionListener(this);

        addButton.setBounds(550, 400, 200, 25);
        addButton.setFocusable(false);
        addButton.addActionListener(this);

        model.addColumn("Book ID");
        model.addColumn("Name");
        model.addColumn("Author");
        model.addColumn("Category");
        model.addColumn("Copies Available");

        scrollPane.setBounds(50, 50, 900, 300);

        fillTable();

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load the image
                Image backgroundImage = new ImageIcon("/Users/desurohan/Desktop/Books.jpeg").getImage();
                // Draw the image
                g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };

        panel.setLayout(null);
        panel.add(messageLabel);
        panel.add(backButton);
        panel.add(addButton);
        panel.add(scrollPane);

        frame.setContentPane(panel);
        frame.setSize(1000, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.getContentPane().setBackground(Color.PINK);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            frame.dispose();
            Books booksPage = new Books(0, userID);
        }

        if (e.getSource() == addButton) {
            addNewBook();
        }
    }

    private void fillTable() {
        try {
            String url = "jdbc:mysql://localhost:3306/lib";
            String user = "root";
            String password = "#Tarundoom123";
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM BookTable");
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
    }

    private void addNewBook() {
        String bookID = JOptionPane.showInputDialog(frame, "Enter Book ID:");
        String name = JOptionPane.showInputDialog(frame, "Enter Book Name:");
        String author = JOptionPane.showInputDialog(frame, "Enter Author Name:");
        String category = JOptionPane.showInputDialog(frame, "Enter Category:");
        String copies = JOptionPane.showInputDialog(frame, "Enter Copies Available:");

        model.addRow(new Object[]{bookID, name, author, category, copies});

        updateDatabaseNewBook(bookID, name, author, category, copies);
    }

    private void updateDatabaseNewBook(String bookID, String name, String author, String category, String copies) {
        try {
            String url = "jdbc:mysql://localhost:3306/lib";
            String user = "root";
            String password = "#Tarundoom123";
            Connection connection = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO BookTable (`BOOK ID`, `NAME` , `AUTHOR`, `CATEGORY` , `COPIES AVAILABLE`) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bookID);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, author);
            preparedStatement.setString(4, category);
            preparedStatement.setInt(5, Integer.parseInt(copies));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Error adding book to the database: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

}

