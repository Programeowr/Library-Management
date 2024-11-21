import java.sql.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

public class EditBooks implements ActionListener {
    JFrame frame = new JFrame();
    JLabel messageLabel = new JLabel("Books");
    JButton backButton = new JButton("Go Back");
    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);
    JScrollPane scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    String userID;

    EditBooks(String name) {

        userID = name;

        messageLabel.setBounds(50, 0, 300,50);
        messageLabel.setFont(new Font(null, Font.PLAIN, 25));

        backButton.setBounds(400, 400, 200, 25);
        backButton.setFocusable(false);
        backButton.addActionListener(this);

        model.addColumn("Book ID");
        model.addColumn("Name");
        model.addColumn("Author");
        model.addColumn("Category");
        model.addColumn("Copies Available");

        scrollPane.setBounds(50,50,900,300);

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

        model.addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (column > 0) {
                    updateDatabase(row, column);
                }
            }
        });

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
                Books booksPage = new Books(0, userID);
        }
    }

    private void updateDatabase(int row, int column) {
        Object newValue = model.getValueAt(row, column);
        String bookId = model.getValueAt(row, 0).toString();
        String columnName = model.getColumnName(column);

        try {
            String url = "jdbc:mysql://localhost:3306/lib";
            String user = "root";
            String password = "#Tarundoom123";
            Connection connection = DriverManager.getConnection(url, user, password);
            String sql = "UPDATE BookTable SET `" + columnName + "` = ? WHERE `BOOK ID` = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            if (newValue instanceof Integer) {
                preparedStatement.setInt(1, (Integer) newValue);
            } else {
                preparedStatement.setString(1, newValue.toString());
            }
            preparedStatement.setString(2, bookId);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Error updating database: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

}