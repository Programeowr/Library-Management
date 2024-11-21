import java.sql.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ReturnBooks implements ActionListener {
    JFrame frame = new JFrame();
    JLabel messageLabel = new JLabel("Books");
    JButton backButton = new JButton("Go Back");
    JButton returnButton = new JButton("Return");
    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);
    JScrollPane scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    int selectedRow;
    String userID;

    ReturnBooks(String name) {

        userID = name;
        messageLabel.setBounds(50, 0, 300,50);
        messageLabel.setFont(new Font(null, Font.PLAIN, 25));

        backButton.setBounds(250, 400, 200, 25);
        backButton.setFocusable(false);
        backButton.addActionListener(this);

        returnButton.setBounds(550, 400, 200, 25);
        returnButton.setFocusable(false);
        returnButton.addActionListener(this);

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
        panel.add(returnButton);
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

        if(e.getSource() == returnButton){
            selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                returnBook(selectedRow);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select the book you're returning.");
            }

        }


    }

    private void returnBook(int row) {

        try {

            String bookName = model.getValueAt(row, 1).toString();
            String bookID = model.getValueAt(row, 0).toString();
            int copies = Integer.parseInt(model.getValueAt(row, 4).toString());
            String copiesToReturnString = JOptionPane.showInputDialog(frame, "Enter the number of copies to return:");
            int copiesToReturn = Integer.parseInt(copiesToReturnString);

                if (copiesToReturn > 0) {
                    copies = copies + copiesToReturn;

                    updateDatabase(bookID, copies);
                    recordReturning(bookID, userID, bookName, copiesToReturn);
                    model.setValueAt(copies, row, 4); // Update the model

                } else {
                    JOptionPane.showMessageDialog(frame, "You did not borrow" +copiesToReturn+ "books");
                }
            }
           catch (NumberFormatException e) {
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

    private void recordReturning(String bookID, String userID, String bookName, int copiesReturned) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/lib";
        String user = "root";
        String password = "#Tarundoom123";
        
        int prevBorrowed;
        int currentborrowed = 0;
        try (Connection connection = DriverManager.getConnection(url, user, password)) {

            try (
                    PreparedStatement selectStmt = connection.prepareStatement("SELECT `COPIES BORROWED` FROM AccountTable WHERE `USER ID` = ? AND `BOOK ID` = ?")) {
                selectStmt.setString(1, userID);
                selectStmt.setString(2, bookID);
                ResultSet resultSet = selectStmt.executeQuery();
                
                if (resultSet.next()) {
                    prevBorrowed  = resultSet.getInt("COPIES BORROWED");
                    currentborrowed = prevBorrowed - copiesReturned;
                }
            }
            
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO AccountTable (`BOOK ID`, `USER ID`, `BOOK NAME`, `DATE RETURNED`, `COPIES RETURNED`) VALUES (?, ?, ?, NOW(), ?)" +
                            "ON DUPLICATE KEY UPDATE `DATE RETURNED`=NOW(), `COPIES BORROWED` = `COPIES BORROWED` - ?, `COPIES RETURNED`= `COPIES RETURNED` + ?, `STATUS` = ?")) {

                statement.setString(1, bookID);
                statement.setString(2, userID);
                statement.setString(3, bookName);
                statement.setInt(4, copiesReturned);
                statement.setInt(5, copiesReturned);
                statement.setInt(6, copiesReturned);

                    if (currentborrowed == 0) {
                        statement.setString(7, "Returned");
                    } else {
                        statement.setString(7, "Not Returned");
                    }
                statement.executeUpdate();
                }
            }
        }
    }

