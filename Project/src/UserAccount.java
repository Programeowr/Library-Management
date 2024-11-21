import java.sql.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class UserAccount implements ActionListener {
    JFrame frame = new JFrame("User Account");
    JLabel borrowLabel = new JLabel("Borrowed Books");
    JLabel returnLabel = new JLabel("Returned Books");
    JButton backButton = new JButton("Go Back");
    DefaultTableModel borrowModel = new DefaultTableModel();
    DefaultTableModel returnModel = new DefaultTableModel();
    JTable borrowTable = new JTable(borrowModel);
    JTable returnTable = new JTable(returnModel);
    JScrollPane borrowScroll = new JScrollPane(borrowTable);
    JScrollPane returnScroll = new JScrollPane(returnTable);

    String userID;

    UserAccount(String name) {
        this.userID = name;
        initializeUI();
        borrowTable();
        returnTable();
    }

    private void initializeUI() {
        borrowLabel.setBounds(50, 0, 300, 50);
        borrowLabel.setFont(new Font(null, Font.PLAIN, 25));
        borrowLabel.setForeground(Color.green);

        returnLabel.setBounds(50,200, 300, 50);
        returnLabel.setFont(new Font(null, Font.PLAIN, 25));
        returnLabel.setForeground(Color.green);

        backButton.setBounds(450, 450, 100, 25);
        backButton.setFocusable(false);
        backButton.addActionListener(this);

        borrowModel.addColumn("Book ID");
        borrowModel.addColumn("Name");
        borrowModel.addColumn("Copies Borrowed");
        borrowModel.addColumn("Date Borrowed");
        borrowModel.addColumn("Borrowed Time");

        returnModel.addColumn("Book ID");
        returnModel.addColumn("Name");
        returnModel.addColumn("Copies Returned");
        returnModel.addColumn("Date Returned");
        returnModel.addColumn("Late Fee");

        borrowScroll.setBounds(50, 50, 900, 150);
        returnScroll.setBounds(50, 250, 900, 150);
        borrowTable.setEnabled(false);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load the image
                Image backgroundImage = new ImageIcon("/Users/desurohan/Desktop/AccountUserSearch.jpeg").getImage();
                // Draw the image
                g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };

        panel.setLayout(null);
        panel.add(borrowLabel);
        panel.add(returnLabel);
        panel.add(backButton);
        panel.add(borrowScroll);
        panel.add(returnScroll);

        frame.setContentPane(panel);
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.getContentPane().setBackground(Color.PINK);
    }

    private void borrowTable() {
        String url = "jdbc:mysql://localhost:3306/lib";
        String user = "root";
        String password = "#Tarundoom123";
        String query = "SELECT * FROM AccountTable WHERE `USER ID` = ? AND `STATUS` = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userID);
            preparedStatement.setString(2, "Not Returned");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    borrowModel.addRow(new Object[]{
                            resultSet.getString("BOOK ID"), resultSet.getString("BOOK NAME"),
                            resultSet.getInt("COPIES BORROWED"), resultSet.getString("DATE BORROWED"),
                            resultSet.getString("BORROWED TIME")
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error accessing database: " + e.getMessage());
        }
    }

    private void returnTable() {
        String url = "jdbc:mysql://localhost:3306/lib";
        String user = "root";
        String password = "#Tarundoom123";
        String query = "SELECT * FROM AccountTable WHERE `USER ID` = ? AND `STATUS` = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userID);
            preparedStatement.setString(2, "Returned");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    returnModel.addRow(new Object[]{
                            resultSet.getString("BOOK ID"), resultSet.getString("BOOK NAME"),
                            resultSet.getInt("COPIES RETURNED"), resultSet.getString("DATE RETURNED"),
                            resultSet.getString("LATE FEE")
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error accessing database: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            frame.dispose();
            new User(userID);
        }
    }
}