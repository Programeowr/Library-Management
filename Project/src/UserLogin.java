import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class UserLogin implements ActionListener {

    JFrame frame = new JFrame();  //creates a frame
    JButton loginButton = new JButton("Login");   //creates a button
    JButton resetButton = new JButton("Reset");
    JButton backButton = new JButton("Go Back");
    JTextField userIDField = new JTextField();   //creates a textbox
    JPasswordField userPasswordField = new JPasswordField();   //creates a passwordbox
    JLabel userIDLabel = new JLabel("User ID:");    //creates a label for the box
    JLabel userPasswordLabel = new JLabel("Password:");
    JLabel messageLabel = new JLabel();    //creates a message
    JLabel loginLabel = new JLabel("Login Page");

    UserLogin(){

        userIDLabel.setBounds(300,100,75,25);
        userIDLabel.setForeground(Color.GREEN);
        userPasswordLabel.setBounds(300,150,75,25);
        userPasswordLabel.setForeground(Color.GREEN);

        userIDField.setBounds(375,100,200,25);
        userPasswordField.setBounds(375,150,200,25);

        loginLabel.setBounds(375, 50, 400, 50);
        loginLabel.setFont(new Font(null, Font.PLAIN, 35));

        loginButton.setBounds(375,200,100,25);
        loginButton.setFocusable(false);   //doesn't "select" the text
        loginButton.addActionListener(this);    //Does the action mentioned

        resetButton.setBounds(475,200,100,25);
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);

        backButton.setBounds(425, 250, 100, 25);
        backButton.setFocusable(false);
        backButton.addActionListener(this);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image backgroundImage = new ImageIcon("/Users/desurohan/Desktop/Login.jpeg").getImage();
                g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };

        panel.setLayout(null);
        panel.add(userIDLabel);
        panel.add(userPasswordLabel);
        panel.add(messageLabel);
        panel.add(userIDField);
        panel.add(userPasswordField);
        panel.add(loginButton);
        panel.add(resetButton);
        panel.add(backButton);
        panel.add(loginLabel);

        frame.setContentPane(panel);
        frame.setTitle("LIBRARY MANAGEMENT SYSTEM"); //
        frame.setResizable(false);
        frame.add(userIDLabel);
        frame.add(userPasswordLabel);
        frame.add(messageLabel);
        frame.add(userIDField);
        frame.add(userPasswordField);
        frame.add(loginButton);
        frame.add(resetButton);
        frame.add(backButton);
        frame.add(loginLabel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,500);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.getContentPane().setBackground(Color.GREEN);

    }

    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource() == resetButton){
            userIDField.setText("");
            userPasswordField.setText("");
        }

        if(e.getSource() == loginButton){
            String userID = userIDField.getText();
            String userPassword = String.valueOf(userPasswordField.getPassword());

            Info information = new Info();

            if(information.userCheckCredentials(userID, userPassword)){
                JOptionPane.showMessageDialog(null, "Login successfull!");
                    frame.dispose();
                    User userPage = new User(userID);
            }
            else{
                JOptionPane.showMessageDialog(null, "Login Unsuccessfull! Try Again");
            }
        }

        if(e.getSource() == backButton){
            frame.dispose();
            Login loginPage = new Login();
        }
    }
}
