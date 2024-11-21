import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Register implements ActionListener {
    JFrame frame = new JFrame();

    JLabel userIDLabel = new JLabel("ID (All caps)");
    JLabel nameLabel = new JLabel("Full Name");
    JLabel ageLabel = new JLabel("Age");
    JLabel genderLabel = new JLabel("Gender");
    JLabel contactLabel = new JLabel("Contact Number");
    JLabel emailLabel = new JLabel("Email Id");
    JLabel passwordLabel = new JLabel("Password");
    JLabel warningLabel = new JLabel();

    JTextField userIDField = new JTextField();
    JTextField nameField = new JTextField();
    JTextField ageField = new JTextField();
    JTextField genderField = new JTextField();
    JTextField contactField = new JTextField();
    JTextField emailField = new JTextField();
    JPasswordField passwordField = new JPasswordField();

    JButton registerButton = new JButton("Register");
    JButton resetButton = new JButton("Reset");
    JButton backButton = new JButton("Go Back");
    JLabel welcomeLabel = new JLabel("Register if you are new!");

    Register() {
        welcomeLabel.setBounds(100, 0, 300, 50);
        welcomeLabel.setFont(new Font(null, Font.ITALIC, 25));

        userIDLabel.setBounds(50, 100, 100, 25);
        userIDLabel.setForeground(Color.YELLOW);
        nameLabel.setBounds(50, 150, 100, 25);
        nameLabel.setForeground(Color.YELLOW);
        ageLabel.setBounds(50, 200, 100, 25);
        ageLabel.setForeground(Color.YELLOW);
        genderLabel.setBounds(50, 250, 100, 25);
        genderLabel.setForeground(Color.YELLOW);
        contactLabel.setBounds(50, 300, 100, 25);
        contactLabel.setForeground(Color.YELLOW);
        emailLabel.setBounds(50, 350,100,25);
        emailLabel.setForeground(Color.YELLOW);
        passwordLabel.setBounds(50, 400, 100, 25);
        passwordLabel.setForeground(Color.YELLOW);

        userIDField.setBounds(200, 100, 300, 25);
        nameField.setBounds(200, 150, 300, 25);
        ageField.setBounds(200, 200, 300, 25);
        genderField.setBounds(200, 250, 300, 25);
        contactField.setBounds(200, 300, 300, 25);
        emailField.setBounds(200, 350,300,25);
        passwordField.setBounds(200, 400, 300, 25);

        resetButton.setBounds(100, 500, 100, 50);
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);

        registerButton.setBounds(300, 500, 100, 50);
        registerButton.setFocusable(false);
        registerButton.addActionListener(this);

        backButton.setBounds(200, 600, 100, 50);
        backButton.setFocusable(false);
        backButton.addActionListener(this);

        warningLabel.setBounds(200, 600, 100, 50);

        welcomeLabel.setFont(new Font(null, Font.ITALIC, 25));
        userIDField.setFont(new Font(null, Font.ITALIC, 25));
        nameField.setFont(new Font(null, Font.ITALIC, 25));
        ageField.setFont(new Font(null, Font.ITALIC, 25));
        genderField.setFont(new Font(null, Font.ITALIC, 25));
        contactField.setFont(new Font(null, Font.ITALIC, 25));
        emailField.setFont(new Font(null, Font.ITALIC, 25));
        passwordField.setFont(new Font(null, Font.ITALIC, 25));


        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load the image
                Image backgroundImage = new ImageIcon("/Users/desurohan/Desktop/Library.png").getImage();
                // Draw the image
                g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };

        panel.setLayout(null);
        panel.add(welcomeLabel);
        panel.add(resetButton);
        panel.add(registerButton);
        panel.add(userIDField);
        panel.add(userIDLabel);
        panel.add(passwordField);
        panel.add(passwordLabel);
        panel.add(nameField);
        panel.add(nameLabel);
        panel.add(ageField);
        panel.add(ageLabel);
        panel.add(contactField);
        panel.add(contactLabel);
        panel.add(emailField);
        panel.add(emailLabel);
        panel.add(genderField);
        panel.add(genderLabel);
        panel.add(backButton);

        frame.setContentPane(panel);
        frame.setTitle("LIBRARY MANAGEMENT SYSTEM"); //
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 700);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.getContentPane().setBackground(Color.pink);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == resetButton) {
            reset();
        }

        if(e.getSource() == backButton) {
            frame.dispose();
            Welcome welcomePage = new Welcome();
        }

        if (e.getSource() == registerButton) {
            String userID = userIDField.getText();
            String name = nameField.getText();
            String age = ageField.getText();
            String gender = genderField.getText();
            String contact = contactField.getText();
            String email = emailField.getText();
            String userPassword = String.valueOf(passwordField.getPassword());

            if (userID.trim().isEmpty() || name.trim().isEmpty() || age.trim().isEmpty() || gender.trim().isEmpty() || contact.trim().isEmpty() || email.trim().isEmpty() || userPassword.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Fill Every Bank");
                reset();
            } else {
                final String url = "jdbc:mysql://localhost:3306/lib";
                final String user = "root";
                final String password = "#Tarundoom123";

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url, user, password);
                    String sql = "INSERT INTO UserTable (`ID`, `NAME`, `AGE`, `GENDER`, `CONTACT`, `EMAIL`, `PASSWORD`) VALUES (?, ?, ?, ?, ?, ?, ?)";

                    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                        preparedStatement.setString(1, userID);
                        preparedStatement.setString(2, name);
                        preparedStatement.setString(3, age);
                        preparedStatement.setString(4, gender);
                        preparedStatement.setString(5, contact);
                        preparedStatement.setString(6, email);
                        preparedStatement.setString(7, userPassword);

                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, "User registered successfully! Please Login Again!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Registration failed.");
                        }
                    }
                } catch (SQLException f) {
                    JOptionPane.showMessageDialog(null, "Database error: " + f.getMessage());
                    f.printStackTrace();
                } catch (ClassNotFoundException f) {
                    JOptionPane.showMessageDialog(null, "MySQL JDBC Driver not found.");
                    f.printStackTrace();
                }

                frame.dispose();
                Info information = new Info();
                UserLogin loginPage = new UserLogin();
            }
        }

    }

    void reset() {
        userIDField.setText("");
        nameField.setText("");
        ageField.setText("");
        contactField.setText("");
        emailField.setText("");
        genderField.setText("");
        passwordField.setText("");
    }
}

