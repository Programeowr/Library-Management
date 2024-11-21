import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Admin implements ActionListener {

    ImageIcon image = new ImageIcon("Library.png");
    JFrame frame = new JFrame();
    JLabel welcomeLabel = new JLabel("Hello!");
    JButton adminProfile = new JButton("Profile");
    JButton adminSearchBooks = new JButton("Search Books");
    JButton adminSearchUser = new JButton("Search Users");
    JButton exitButton = new JButton("Exit");

    String userID;

    Admin(String name){

        userID = name;

        welcomeLabel.setBounds(100,0,300,100);
        welcomeLabel.setFont(new Font(null, Font.PLAIN, 25));
        welcomeLabel.setText("Welcome " +name+ "!");

        adminProfile.setBounds(50,150,200,100);
        adminProfile.setFocusable(false);
        adminProfile.addActionListener(this);

        adminSearchUser.setBounds(50,300,200,100);
        adminSearchUser.setFocusable(false);
        adminSearchUser.addActionListener(this);

        adminSearchBooks.setBounds(300, 150, 200, 100);
        adminSearchBooks.setFocusable(false);
        adminSearchBooks.addActionListener(this);

        exitButton.setBounds(250, 500, 100, 25);
        exitButton.setFocusable(false);
        exitButton.addActionListener(this);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load the image
                Image backgroundImage = new ImageIcon("/Users/desurohan/Desktop/UserAdmin.jpeg").getImage();
                // Draw the image
                g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };

        panel.setLayout(null);
        panel.add(welcomeLabel);
        panel.add(adminProfile);
        panel.add(adminSearchBooks);
        panel.add(adminSearchUser);
        panel.add(exitButton);

        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.getContentPane().setBackground(Color.GRAY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == adminProfile) {
            frame.dispose();
            AdminProfile adminProfilePage = new AdminProfile(userID);
        }

        if(e.getSource() == adminSearchBooks) {
            frame.dispose();
            Books booksPage = new Books(0, userID);
        }

        if(e.getSource() == adminSearchUser) {
            frame.dispose();
            AdminSearchUser adminSUPage = new AdminSearchUser(userID);
        }

        if(e.getSource() == exitButton) {
            frame.dispose();
            Welcome welcomePage = new Welcome();
        }
    }
}
