import java.sql.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Books implements ActionListener {
    JFrame frame = new JFrame();
    JLabel messageLabel = new JLabel("Books");
    JButton backButton = new JButton("Go Back");
    JButton editButton = new JButton("Update Books");
    JButton addButton = new JButton("Add Books");
    JButton borrowButton = new JButton("Borrow Books");
    JButton returnButton = new JButton("Return Books");
    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);
    JScrollPane scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    int check;
    String userID;

    Books(int n, String name) {

        check = n;
        userID = name;
        messageLabel.setBounds(50, 0, 300,50);
        messageLabel.setFont(new Font(null, Font.PLAIN, 25));

        backButton.setBounds(150, 400, 200, 25);
        backButton.setFocusable(false);
        backButton.addActionListener(this);

        editButton.setBounds(400, 400, 200, 25);
        editButton.setFocusable(false);
        editButton.addActionListener(this);

        addButton.setBounds(650, 400, 200, 25);
        addButton.setFocusable(false);
        addButton.addActionListener(this);

        borrowButton.setBounds(400, 400, 200, 25);
        borrowButton.setFocusable(false);
        borrowButton.addActionListener(this);

        returnButton.setBounds(650, 400, 200, 25);
        returnButton.setFocusable(false);
        returnButton.addActionListener(this);

        model.addColumn("Book ID");
        model.addColumn("Name");
        model.addColumn("Author");
        model.addColumn("Category");
        model.addColumn("Copies Available");

        scrollPane.setBounds(50,50,900,300);
        table.setEnabled(false);

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

        if(check == 0) {
            panel.add(editButton);
            panel.add(addButton);
        }

        if(check != 0) {
            panel.add(returnButton);
            panel.add(borrowButton);
        }

        panel.setLayout(null);

        panel.add(messageLabel);
        panel.add(backButton);
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
            if(check == 0) {
                frame.dispose();
                Admin adminPage = new Admin(userID);
            }

            else {
                frame.dispose();
                User userPage = new User(userID);
            }
        }

        if(e.getSource() == editButton){
            frame.dispose();
            EditBooks editBooksPage = new EditBooks(userID);
        }

        if(e.getSource() == addButton){
            frame.dispose();
            AddBooks addBooksPage = new AddBooks(userID);
        }

        if(e.getSource() == borrowButton){
            frame.dispose();
            BorrowBooks borrowPage = new BorrowBooks(userID);
        }

        if(e.getSource() == returnButton){
            frame.dispose();
            ReturnBooks returnPage = new ReturnBooks(userID);
        }

    }
}
