//import javax.swing.*;
//import java.awt.*;
//import java.sql.*;
//
//public class LoginFrame extends JFrame {
//
//    private JTextField txtUsername;
//    private JPasswordField txtPassword;
//    private JButton btnLogin, btnRegister;
//
//    public LoginFrame() {
//        setTitle("Recipe Management System - Login");
//        setSize(450, 300);
//        setMinimumSize(new Dimension(400, 250));
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//        setLayout(new BorderLayout());
//
//        // ---------- TITLE PANEL ----------
//        JPanel titlePanel = new JPanel(new BorderLayout());
//        titlePanel.setBackground(new Color(232, 245, 233)); // soft green
//        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
//
//        JLabel lblTitle = new JLabel("Login to Recipe Manager", JLabel.CENTER);
//        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
//        lblTitle.setForeground(new Color(102, 51, 0)); // dark brown
//        titlePanel.add(lblTitle, BorderLayout.CENTER);
//
//        add(titlePanel, BorderLayout.NORTH);
//
//        // ---------- INPUT PANEL ----------
//        JPanel inputPanel = new JPanel(new GridBagLayout());
//        inputPanel.setBackground(new Color(250, 250, 250)); // off-white
//        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(10, 10, 10, 10);
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.weightx = 1;
//
//        JLabel lblUsername = new JLabel("Username:");
//        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 16));
//        txtUsername = new JTextField();
//        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 16));
//
//        JLabel lblPassword = new JLabel("Password:");
//        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
//        txtPassword = new JPasswordField();
//        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
//
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        inputPanel.add(lblUsername, gbc);
//        gbc.gridx = 1;
//        inputPanel.add(txtUsername, gbc);
//
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        inputPanel.add(lblPassword, gbc);
//        gbc.gridx = 1;
//        inputPanel.add(txtPassword, gbc);
//
//        add(inputPanel, BorderLayout.CENTER);
//
//        // ---------- BUTTON PANEL ----------
//        JPanel buttonPanel = new JPanel(new GridBagLayout());
//        //buttonPanel.setBackground(new Color(250, 250, 250)); // off-white
//        buttonPanel.setBackground(new Color(232, 245, 233)); // soft green
//
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.gridwidth = 2;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.insets = new Insets(15, 50, 15, 50);
//
//        btnLogin = new JButton("Login");
//        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
//        btnLogin.setBackground(new Color(76, 175, 80)); // green
//
//        btnLogin.setForeground(Color.WHITE);
//        btnLogin.setFocusPainted(false);
//
//        btnRegister = new JButton("Register");
//        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 16));
//        btnRegister.setBackground(new Color(76, 175, 80)); // green
//
//        btnRegister.setForeground(Color.WHITE);
//        btnRegister.setFocusPainted(false);
//
//        gbc.gridy = 0;
//        buttonPanel.add(btnLogin, gbc);
//        gbc.gridy = 1;
//        buttonPanel.add(btnRegister, gbc);
//
//        add(buttonPanel, BorderLayout.SOUTH);
//     // Hover effect for Login button
//        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseEntered(java.awt.event.MouseEvent evt) {
//                btnLogin.setBackground(new Color(56, 142, 60)); // darker green
//            }
//            public void mouseExited(java.awt.event.MouseEvent evt) {
//                btnLogin.setBackground(new Color(76, 175, 80)); // green
//            }
//        });
//
//        // Hover effect for Register button
//        btnRegister.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseEntered(java.awt.event.MouseEvent evt) {
//                btnRegister.setBackground(new Color(56, 142, 60)); // darker green
//            }
//            public void mouseExited(java.awt.event.MouseEvent evt) {
//                btnRegister.setBackground(new Color(76, 175, 80)); // green
//            }
//        });
//
//        // ---------- BUTTON ACTIONS ----------
//        btnLogin.addActionListener(e -> authenticateUser());
//        btnRegister.addActionListener(e -> {
//            new UserRegistrationFrame().setVisible(true);
//            dispose();
//        });
//
//        setVisible(true);
//    }
//
//    private void authenticateUser() {
//        String username = txtUsername.getText().trim();
//        String password = new String(txtPassword.getPassword());
//
//        if (username.isEmpty() || password.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Enter both username and password!");
//            return;
//        }
//
//        try {
//            Connection conn = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/recipe_db", "root", "99952");
//
//            String sql = "SELECT id FROM users WHERE username = ? AND password = ?";
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setString(1, username);
//            stmt.setString(2, password);
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                String userId = rs.getString("id");
//                JOptionPane.showMessageDialog(this, "Login successful!");
//                new DashboardFrame(userId).setVisible(true);
//                dispose();
//            } else {
//                JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//
//            rs.close();
//            stmt.close();
//            conn.close();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
//        }
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new LoginFrame());
//    }
//}
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.sql.*;

public class LoginFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnRegister;

    public LoginFrame() {
        setTitle("Recipe Management System - Login");
        setSize(450, 300);
        setMinimumSize(new Dimension(400, 250));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ---------- TITLE PANEL ----------
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(232, 245, 233)); // soft green
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel lblTitle = new JLabel("Login to Recipe Manager", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(102, 51, 0)); // dark brown
        titlePanel.add(lblTitle, BorderLayout.CENTER);

        add(titlePanel, BorderLayout.NORTH);

        // ---------- INPUT PANEL ----------
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(250, 250, 250)); // off-white
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(lblUsername, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(lblPassword, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtPassword, gbc);

        add(inputPanel, BorderLayout.CENTER);

        // ---------- BUTTON PANEL ----------
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(232, 245, 233)); // soft green

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 50, 15, 50);

        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLogin.setBackground(new Color(76, 175, 80)); // green
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);

        btnRegister = new JButton("Register");
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnRegister.setBackground(new Color(76, 175, 80)); // green
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);

        gbc.gridy = 0;
        buttonPanel.add(btnLogin, gbc);
        gbc.gridy = 1;
        buttonPanel.add(btnRegister, gbc);

        add(buttonPanel, BorderLayout.SOUTH);

        // Hover effect for Login button - FIXED: Proper MouseAdapter with @Override
        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                btnLogin.setBackground(new Color(56, 142, 60)); // darker green
            }
            
            @Override
            public void mouseExited(MouseEvent evt) {
                btnLogin.setBackground(new Color(76, 175, 80)); // green
            }
        });

        // Hover effect for Register button - FIXED: Proper MouseAdapter with @Override
        btnRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                btnRegister.setBackground(new Color(56, 142, 60)); // darker green
            }
            
            @Override
            public void mouseExited(MouseEvent evt) {
                btnRegister.setBackground(new Color(76, 175, 80)); // green
            }
        });

        // ---------- BUTTON ACTIONS ----------
        // FIXED: Changed unused lambda parameters from 'e' to '_'
        btnLogin.addActionListener(_ -> authenticateUser());
        btnRegister.addActionListener(_ -> {
            new UserRegistrationFrame().setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private void authenticateUser() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter both username and password!");
            return;
        }

        // FIXED: Use try-with-resources for automatic resource management
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/recipe_db", "root", "99952");
             PreparedStatement stmt = conn.prepareStatement("SELECT id FROM users WHERE username = ? AND password = ?")) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String userId = rs.getString("id");
                    JOptionPane.showMessageDialog(this, "Login successful!");
                    new DashboardFrame(userId).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    // FIXED: Changed unused lambda parameter from 'args' to '_'
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}