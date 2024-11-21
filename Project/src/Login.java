import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Login implements ActionListener {
    JFrame frame = new JFrame();
    JButton userButton = new JButton("User");
    JButton adminButton = new JButton("Librarian");
    JButton backButton = new JButton("Go Back");
    JLabel welcomeLabel = new JLabel("Welcome to E-Library");

    Login() {
        welcomeLabel.setBounds(100, 0, 300, 100);
        welcomeLabel.setFont(new Font(null, Font.ITALIC, 25));

        userButton.setBounds(50, 200, 200, 100);
        userButton.setFocusable(false);
        userButton.addActionListener(this);

        adminButton.setBounds(300, 200, 200, 100);
        adminButton.setFocusable(false);
        adminButton.addActionListener(this);

        backButton.setBounds(225, 400, 100, 50);
        backButton.setFocusable(false);
        backButton.addActionListener(this);

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
        panel.add(userButton);
        panel.add(adminButton);
        panel.add(backButton);

        frame.setContentPane(panel);
        frame.setTitle("LIBRARY MANAGEMENT SYSTEM"); //
        frame.setResizable(false);
        frame.add(welcomeLabel);
        frame.add(userButton);
        frame.add(adminButton);
        frame.add(backButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 550);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.getContentPane().setBackground(Color.ORANGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == userButton) {
            frame.dispose();
            UserLogin loginPage = new UserLogin();
        }

        if(e.getSource() == adminButton) {
            frame.dispose();
            AdminLogin loginPage = new AdminLogin();
        }

        if(e.getSource() == backButton) {
            frame.dispose();
            Welcome welcomePage = new Welcome();
        }
    }
}