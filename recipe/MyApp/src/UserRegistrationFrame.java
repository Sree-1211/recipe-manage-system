//import javax.swing.*;
//import java.awt.*;
//import java.sql.*;
//
//public class UserRegistrationFrame extends JFrame {
//    private JTextField txtUsername;
//    private JPasswordField txtPassword;
//    private JButton btnRegister, btnCancel;
//
//    public UserRegistrationFrame() {
//        setTitle("User Registration");
//        setSize(400, 250);
//        setMinimumSize(new Dimension(400, 250));
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setLayout(new GridBagLayout());
//        getContentPane().setBackground(new Color(255, 249, 240)); // Soft cream background
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(10, 10, 10, 10);
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//
//        // Username label
//        JLabel lblUsername = new JLabel("Username:");
//        lblUsername.setForeground(new Color(51, 51, 51));
//        lblUsername.setFont(new Font("Arial", Font.BOLD, 14));
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.weightx = 0;
//        add(lblUsername, gbc);
//
//        // Username text field
//        txtUsername = new JTextField();
//        txtUsername.setBackground(new Color(255, 238, 204)); // light orange background
//        gbc.gridx = 1;
//        gbc.weightx = 1.0;
//        add(txtUsername, gbc);
//
//        // Password label
//        JLabel lblPassword = new JLabel("Password:");
//        lblPassword.setForeground(new Color(51, 51, 51));
//        lblPassword.setFont(new Font("Arial", Font.BOLD, 14));
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        gbc.weightx = 0;
//        gbc.fill = GridBagConstraints.NONE;
//        add(lblPassword, gbc);
//
//        // Password field
//        txtPassword = new JPasswordField();
//        txtPassword.setBackground(new Color(255, 238, 204));
//        gbc.gridx = 1;
//        gbc.weightx = 1.0;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        add(txtPassword, gbc);
//
//        // Register button
//        btnRegister = new JButton("Register");
//        btnRegister.setBackground(new Color(255, 140, 66)); // orange
//        btnRegister.setForeground(Color.WHITE);
//        btnRegister.setFont(new Font("Arial", Font.BOLD, 14));
//        gbc.gridx = 2;
//        gbc.gridy = 2;
//        gbc.weightx = 0.5;
//        gbc.anchor = GridBagConstraints.CENTER;
//        gbc.fill = GridBagConstraints.NONE;
//        add(btnRegister, gbc);
//
//        // Cancel button
//        btnCancel = new JButton("Cancel");
//        btnCancel.setBackground(new Color(76, 175, 80)); // green
//        btnCancel.setForeground(Color.WHITE);
//        btnCancel.setFont(new Font("Arial", Font.BOLD, 14));
//        gbc.gridx = 0;
//        gbc.gridy = 2;
//        gbc.gridwidth = 2;
//        gbc.anchor = GridBagConstraints.CENTER;
//        gbc.fill = GridBagConstraints.NONE;
//        add(btnCancel, gbc);
//
//        // Register button action
//        btnRegister.addActionListener(e -> {
//            String username = txtUsername.getText().trim();
//            String password = String.valueOf(txtPassword.getPassword()).trim();
//
//            if (username.isEmpty() || password.isEmpty()) {
//                JOptionPane.showMessageDialog(this, "Please enter both username and password!");
//                return;
//            }
//
//            try (Connection conn = DBConnection.getConnection(username, password)) {
//                PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM users WHERE username=?");
//                checkStmt.setString(1, username);
//                ResultSet rs = checkStmt.executeQuery();
//
//                if (rs.next()) {
//                    JOptionPane.showMessageDialog(this, "Username already exists! Choose another.");
//                } else {
//                    PreparedStatement ps = conn.prepareStatement(
//                            "INSERT INTO users (username, password) VALUES (?, ?)");
//                    ps.setString(1, username);
//                    ps.setString(2, password);
//                    ps.executeUpdate();
//                    JOptionPane.showMessageDialog(this, "✅ Registration successful! You can now login.");
//                    dispose();
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(this, "❌ Error connecting to database.");
//            }
//        });
//
//        // Cancel button closes registration window
//        btnCancel.addActionListener(e -> dispose());
//
//        setLocationRelativeTo(null);
//        setVisible(true);
//    }
//
//    public static void main(String[] args) {
//        new UserRegistrationFrame();
//    }
//}


import javax.swing.*;
import java.awt.*;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseAdapter;
import java.sql.*;

public class UserRegistrationFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnRegister, btnCancel;

    public UserRegistrationFrame() {
        setTitle("User Registration");
        setSize(400, 250);
        setMinimumSize(new Dimension(400, 250));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(255, 249, 240)); // Soft cream background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username label
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setForeground(new Color(51, 51, 51));
        lblUsername.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        add(lblUsername, gbc);

        // Username text field
        txtUsername = new JTextField();
        txtUsername.setBackground(new Color(255, 238, 204)); // light orange background
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        add(txtUsername, gbc);

        // Password label
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setForeground(new Color(51, 51, 51));
        lblPassword.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        add(lblPassword, gbc);

        // Password field
        txtPassword = new JPasswordField();
        txtPassword.setBackground(new Color(255, 238, 204));
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(txtPassword, gbc);

        // Register button
        btnRegister = new JButton("Register");
        btnRegister.setBackground(new Color(255, 140, 66)); // orange
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        add(btnRegister, gbc);

        // Cancel button
        btnCancel = new JButton("Cancel");
        btnCancel.setBackground(new Color(76, 175, 80)); // green
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        add(btnCancel, gbc);

        // Register button action - FIXED: Changed unused lambda parameter from 'e' to '_'
        btnRegister.addActionListener(_ -> {
            String username = txtUsername.getText().trim();
            String password = String.valueOf(txtPassword.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both username and password!");
                return;
            }

            try (Connection conn = DBConnection.getConnection(username, password)) {
                PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM users WHERE username=?");
                checkStmt.setString(1, username);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Username already exists! Choose another.");
                } else {
                    PreparedStatement ps = conn.prepareStatement(
                            "INSERT INTO users (username, password) VALUES (?, ?)");
                    ps.setString(1, username);
                    ps.setString(2, password);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this, "✅ Registration successful! You can now login.");
                    dispose();
                   
                    }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "❌ Error connecting to database.");
            }
        });

        // Cancel button closes registration window - FIXED: Changed unused lambda parameter from 'e' to '_'
        btnCancel.addActionListener(_ -> {
        	dispose();
        	new HomeFrame().setVisible(true); // Open HomeFrame
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // FIXED: Changed unused lambda parameter from 'args' to '_'
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserRegistrationFrame());
    }
}