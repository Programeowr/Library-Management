import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Welcome implements ActionListener {
    JFrame frame = new JFrame();
    JButton loginButton = new JButton("Login");
    JButton registerButton = new JButton("Register");
    JButton exitButton = new JButton("Close Program");
    JLabel welcomeLabel = new JLabel("Welcome to E-Library");

    Welcome() {
        welcomeLabel.setBounds(100, 0, 300, 100);
        welcomeLabel.setFont(new Font(null, Font.ITALIC, 25));

        loginButton.setBounds(50, 200, 200, 100);
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);

        registerButton.setBounds(300, 200, 200, 100);
        registerButton.setFocusable(false);
        registerButton.addActionListener(this);

        exitButton.setBounds(175, 350, 200, 100);
        exitButton.setFocusable(false);
        exitButton.addActionListener(this);

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
        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(exitButton);

        frame.setContentPane(panel);
        frame.setTitle("LIBRARY MANAGEMENT SYSTEM"); //
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 550);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == loginButton) {
            frame.dispose();
            Login loginPage = new Login();
        }

        if(e.getSource() == registerButton) {
            frame.dispose();
            Register registerPage = new Register();
        }

        if(e.getSource() == exitButton) {
            frame.dispose();
        }
    }
}