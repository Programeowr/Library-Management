import java.sql.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdminSearchUser implements ActionListener {
    JFrame frame = new JFrame();
    JLabel messageLabel = new JLabel("Users");
    JButton backButton = new JButton("Go Back");
    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);
    JScrollPane scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    String userID;

    AdminSearchUser(String name) {

        userID = name;

        messageLabel.setBounds(50, 0, 300,50);
        messageLabel.setFont(new Font(null, Font.PLAIN, 25));
        messageLabel.setForeground(Color.GREEN);

        backButton.setBounds(400, 400, 200, 25);
        backButton.setFocusable(false);
        backButton.addActionListener(this);


        model.addColumn("User ID");
        model.addColumn("Name");
        model.addColumn("Age");
        model.addColumn("Gender");
        model.addColumn("Contact Number");
        model.addColumn("Email");

        scrollPane.setBounds(50,50,900,300);
        table.setEnabled(false);

        try {
            String url = "jdbc:mysql://localhost:3306/lib";
            String user = "root";
            String password = "#Tarundoom123";

            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from UserTable");

            while (resultSet.next()) {
                model.addRow(new Object[]{resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6)});
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
                Image backgroundImage = new ImageIcon("/Users/desurohan/Desktop/AccountUserSearch.jpeg").getImage();
                // Draw the image
                g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };

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
                frame.dispose();
                Admin adminPage = new Admin(userID);
        }
    }

}