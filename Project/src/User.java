import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class User implements ActionListener {

    JFrame frame = new JFrame();
    JLabel welcomeLabel = new JLabel();
    JButton userProfile = new JButton("Profile");
    JButton userSearchBooks = new JButton("Search Books");
    JButton userAccount = new JButton("Check Account");
    JButton exitButton = new JButton("Exit");

    String userID;

    User(String name){

        userID = name;

        welcomeLabel.setBounds(100,0,300,100);
        welcomeLabel.setFont(new Font(null, Font.PLAIN, 25));
        welcomeLabel.setText("Welcome " +userID+ "!");

        userProfile.setBounds(50,150,200,100);
        userProfile.setFocusable(false);
        userProfile.addActionListener(this);

        userSearchBooks.setBounds(50,300,200,100);
        userSearchBooks.setFocusable(false);
        userSearchBooks.addActionListener(this);

        userAccount.setBounds(300,150,200,100);
        userAccount.setFocusable(false);
        userAccount.addActionListener(this);

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
        panel.add(userProfile);
        panel.add(userSearchBooks);
        panel.add(userAccount);
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

        if(e.getSource() == userProfile){
            frame.dispose();
            UserProfile userProfilePage = new UserProfile(userID);
        }

        if(e.getSource() == userSearchBooks){
            frame.dispose();
            Books bookPage = new Books(1, userID);
        }

        if(e.getSource() == userAccount){
            frame.dispose();
            UserAccount userAccountPage = new UserAccount(userID);
        }

        if(e.getSource() == exitButton) {
            frame.dispose();
            Welcome welcomePage = new Welcome();
        }
    }

}
