import java.sql.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class UserEditProfile implements ActionListener {
    JFrame frame = new JFrame();

    JButton backButton = new JButton("Go Back");
    JButton saveButton = new JButton("Save");

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

    UserEditProfile(String name) {

        userID = name;

        messageLabel.setBounds(50, 0, 300,50);
        messageLabel.setFont(new Font(null, Font.PLAIN, 25));

        backButton.setBounds(150, 750, 200, 25);
        backButton.setFocusable(false);
        backButton.addActionListener(this);

        saveButton.setBounds(450, 750, 200, 25);
        saveButton.setFocusable(false);
        saveButton.addActionListener(this);

        userIDLabel.setBounds(50, 150, 300,50);
        userIDLabel.setFont(new Font(null, Font.PLAIN, 25));

        nameLabel.setBounds(50, 250, 300,50);
        nameLabel.setFont(new Font(null, Font.PLAIN, 25));

        ageLabel.setBounds(50, 350, 300,50);
        ageLabel.setFont(new Font(null, Font.PLAIN, 25));

        genderLabel.setBounds(50, 450, 100,50);
        genderLabel.setFont(new Font(null, Font.PLAIN, 25));

        contactLabel.setBounds(50, 550, 300,50);
        contactLabel.setFont(new Font(null, Font.PLAIN, 25));

        emailLabel.setBounds(50, 650, 300,50);
        emailLabel.setFont(new Font(null, Font.PLAIN, 25));

        userIDField.setEnabled(false);
        userIDField.setFont(new Font(null, Font.PLAIN, 25));
        userIDField.setBounds(400, 150, 300, 50);

        nameField.setFont(new Font(null, Font.PLAIN, 25));
        nameField.setBounds(400, 250, 300, 50);

        ageField.setFont(new Font(null, Font.PLAIN, 25));
        ageField.setBounds(400, 350, 300, 50);

        genderField.setFont(new Font(null, Font.PLAIN, 25));
        genderField.setBounds(400, 450, 300, 50);

        contactField.setFont(new Font(null, Font.PLAIN, 25));
        contactField.setBounds(400, 550, 300, 50);

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
            AdminProfile adminProfilePage = new AdminProfile(userID);
        }

        if(e.getSource() == saveButton){
            updateData();
        }
    }

    private void updateData(){
        String user = userIDField.getText();
        String name = nameField.getText();
        String age = ageField.getText();
        String gender = genderField.getText();
        String contact = contactField.getText();
        String email = emailField.getText();

        try {
            String url = "jdbc:mysql://localhost:3306/lib";
            String username = "root";
            String password = "#Tarundoom123";
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "UPDATE UserTable SET NAME = ?, AGE = ?, GENDER = ?, CONTACT = ?, EMAIL = ? WHERE ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, age);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4, contact);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, user);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error updating record in the database: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }


    }

}