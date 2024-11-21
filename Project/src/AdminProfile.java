import java.sql.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class AdminProfile implements ActionListener {
    JFrame frame = new JFrame();

    JButton backButton = new JButton("Go Back");
    JButton editButton = new JButton("Edit Profile");

    JLabel messageLabel = new JLabel("Your Profile");
    JLabel userIDLabel = new JLabel("User ID");
    JLabel nameLabel = new JLabel("Username");
    JLabel ageLabel = new JLabel("Age");
    JLabel genderLabel = new JLabel("Gender");
    JLabel contactLabel = new JLabel("Contact Number");
    JLabel emailLabel = new JLabel("Email ID");

    JTextField userIDField = new JTextField();
    JTextField nameField = new JTextField();
    JTextField ageField = new JTextField();
    JTextField genderField = new JTextField();
    JTextField contactField = new JTextField();
    JTextField emailField = new JTextField();

    String userID;

    AdminProfile(String name) {

        userID = name;

        messageLabel.setBounds(50, 0, 300,50);
        messageLabel.setFont(new Font(null, Font.PLAIN, 25));

        backButton.setBounds(150, 750, 200, 25);
        backButton.setFocusable(false);
        backButton.addActionListener(this);

        editButton.setBounds(450, 750, 200,25);
        editButton.setFocusable(false);
        editButton.addActionListener(this);

        userIDLabel.setBounds(50, 150, 300,50);
        userIDLabel.setFont(new Font(null, Font.PLAIN, 25));
        userIDLabel.setForeground(Color.GREEN);

        nameLabel.setBounds(50, 250, 300,50);
        nameLabel.setFont(new Font(null, Font.PLAIN, 25));
        nameLabel.setForeground(Color.GREEN);

        ageLabel.setBounds(50, 350, 300,50);
        ageLabel.setFont(new Font(null, Font.PLAIN, 25));
        ageLabel.setForeground(Color.GREEN);

        genderLabel.setBounds(50, 450, 100,50);
        genderLabel.setFont(new Font(null, Font.PLAIN, 25));
        genderLabel.setForeground(Color.GREEN);

        contactLabel.setBounds(50, 550, 300,50);
        contactLabel.setFont(new Font(null, Font.PLAIN, 25));
        contactLabel.setForeground(Color.GREEN);

        emailLabel.setBounds(50, 650, 300,50);
        emailLabel.setFont(new Font(null, Font.PLAIN, 25));
        emailLabel.setForeground(Color.GREEN);

        userIDField.setEnabled(false);
        userIDField.setFont(new Font(null, Font.PLAIN, 25));
        userIDField.setBounds(400, 150, 300, 50);

        nameField.setEnabled(false);
        nameField.setFont(new Font(null, Font.PLAIN, 25));
        nameField.setBounds(400, 250, 300, 50);

        ageField.setEnabled(false);
        ageField.setFont(new Font(null, Font.PLAIN, 25));
        ageField.setBounds(400, 350, 300, 50);

        genderField.setEnabled(false);
        genderField.setFont(new Font(null, Font.PLAIN, 25));
        genderField.setBounds(400, 450, 300, 50);

        contactField.setEnabled(false);
        contactField.setFont(new Font(null, Font.PLAIN, 25));
        contactField.setBounds(400, 550, 300, 50);

        emailField.setEnabled(false);
        emailField.setFont(new Font(null, Font.PLAIN, 25));
        emailField.setBounds(400, 650, 300, 50);

        try {
            String url = "jdbc:mysql://localhost:3306/lib";
            String user = "root";
            String password = "#Tarundoom123";

            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from AdminTable");

            if (resultSet.next()) {
                userIDField.setText(resultSet.getString(1));
                nameField.setText(resultSet.getString(2));
                ageField.setText(resultSet.getString(3));
                genderField.setText(resultSet.getString(4));
                contactField.setText(resultSet.getString(5));
                emailField.setText(resultSet.getString(6));

            } else {
                JOptionPane.showMessageDialog(frame, "No data found", "Error", JOptionPane.ERROR_MESSAGE);
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
            panel.add(editButton);
            panel.add(userIDField);
            panel.add(userIDLabel);
            panel.add(nameField);
            panel.add(nameLabel);
            panel.add(ageField);
            panel.add(ageLabel);
            panel.add(genderField);
            panel.add(genderLabel);
            panel.add(contactField);
            panel.add(contactLabel);
            panel.add(emailField);
            panel.add(emailLabel);

            frame.setContentPane(panel);
            frame.setSize(800, 800);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setLayout(null);
            frame.setVisible(true);
            frame.getContentPane().setBackground(Color.PINK);

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backButton){
            frame.dispose();
            Admin adminPage = new Admin(userID);
        }

        if(e.getSource() == editButton){
            frame.dispose();
            AdminEditProfile adminEPpage = new AdminEditProfile(userID);
        }

    }

}
