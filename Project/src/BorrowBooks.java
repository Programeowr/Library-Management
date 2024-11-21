import java.sql.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BorrowBooks implements ActionListener {
    JFrame frame = new JFrame();
    JLabel messageLabel = new JLabel("Books");
    JButton backButton = new JButton("Go Back");
    JButton borrowButton = new JButton("Borrow");
    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);
    JScrollPane scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    int selectedRow;
    String userID;

    BorrowBooks(String name) {

        userID = name;
        messageLabel.setBounds(50, 0, 300,50);
        messageLabel.setFont(new Font(null, Font.PLAIN, 25));

        backButton.setBounds(250, 400, 200, 25);
        backButton.setFocusable(false);
        backButton.addActionListener(this);

        borrowButton.setBounds(550, 400, 200, 25);
        borrowButton.setFocusable(false);
        borrowButton.addActionListener(this);

        model.addColumn("Book ID");
        model.addColumn("Name");
        model.addColumn("Author");
        model.addColumn("Category");
        model.addColumn("Copies Available");

        scrollPane.setBounds(50,50,900,300);

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
        panel.add(borrowButton);
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
        if(e.getSource() == backButton){
            frame.dispose();
            Books booksPage = new Books(1, userID);
        }

        if(e.getSource() == borrowButton){
            selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                borrowBook(selectedRow);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a book to borrow.");
            }

        }


    }

    private void borrowBook(int row) {

        try {
            String bookName = model.getValueAt(row, 1).toString();
            String bookID = model.getValueAt(row, 0).toString();
            int copies = Integer.parseInt(model.getValueAt(row, 4).toString());
            if (copies > 0) {
                String copiesToBorrowString = JOptionPane.showInputDialog(frame, "Enter the number of copies to borrow:");
                String borrowedTime = JOptionPane.showInputDialog(frame, "Enter the period of time");
                int copiesToBorrow = Integer.parseInt(copiesToBorrowString);
                if (copiesToBorrow > 0 && copiesToBorrow <= copies) {
                    copies = copies - copiesToBorrow;

                    updateDatabase(bookID, copies);
                    recordBorrowing(bookID, userID, bookName, copiesToBorrow, borrowedTime);

                    model.setValueAt(copies, row, 4); // Update the model

                } else {
                    JOptionPane.showMessageDialog(frame, +copiesToBorrow + "copies not available to borrow.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No copies available to borrow.");
            }
        }   catch (NumberFormatException e) {
            e.printStackTrace();
        }
            catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error updating database.");
        }
    }

    private void updateDatabase(String bookID, int copies) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/lib";
        String user = "root";
        String password = "#Tarundoom123";
        Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement statement = connection.prepareStatement("UPDATE BookTable SET `COPIES AVAILABLE` = ? WHERE `BOOK ID` = ?");
        statement.setInt(1, copies);
        statement.setString(2, bookID);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    private void recordBorrowing(String bookID, String userID, String bookName, int copiesBorrowed, String borrowedTime) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/lib";
        String user = "root";
        String password = "#Tarundoom123";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO AccountTable (`BOOK ID`, `USER ID`, `BOOK NAME`, `DATE BORROWED`, `COPIES BORROWED`, `STATUS`, `BORROWED TIME`) VALUES (?, ?, ?, NOW(), ?, ?, ?)" +
                             "ON DUPLICATE KEY UPDATE `DATE BORROWED`=NOW(), `COPIES BORROWED`= `COPIES BORROWED` + ?, `BORROWED TIME` = ?")) {
            statement.setString(1, bookID);
            statement.setString(2, userID);
            statement.setString(3, bookName);
            statement.setInt(4, copiesBorrowed);
            statement.setString(5, "Not Returned");
            statement.setString(6, borrowedTime);
            statement.setInt(7, copiesBorrowed);
            statement.setString(8, borrowedTime);
            statement.executeUpdate();
        }
    }
}

