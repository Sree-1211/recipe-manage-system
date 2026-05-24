//import javax.swing.*;
//import java.awt.*;
//import java.sql.*;
//
//public class AddRecipeFrame extends JFrame {
//
//    private String userId;
//    private JTextField txtName;
//    private JTextArea txtIngredients, txtInstructions;
//    private JComboBox<String> comboType;
//    private JButton btnSubmit;
//
//    public AddRecipeFrame(String userId) {
//        this.userId = userId;
//
//        // ---------- FRAME SETTINGS ----------
//        setTitle("Add New Recipe");
//        setSize(600, 650);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setLayout(new BorderLayout(10, 10));
//        getContentPane().setBackground(new Color(250, 250, 250)); // off-white background
//
//        // ---------- TOP PANEL (Back Button + Title) ----------
//        JPanel topPanel = new JPanel(new BorderLayout());
//        topPanel.setBackground(new Color(232, 245, 233)); // light green shade
//        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
//
//        JButton btnBack = new JButton("← Back");
//        styleButton(btnBack);
//        btnBack.setPreferredSize(new Dimension(100, 35));
//        btnBack.addActionListener(e -> {
//            dispose();
//            new DashboardFrame(userId).setVisible(true);
//        });
//        topPanel.add(btnBack, BorderLayout.WEST);
//
//        JLabel lblTitle = new JLabel("Add New Recipe", JLabel.CENTER);
//        lblTitle.setFont(new Font("Serif", Font.BOLD, 22));
//        lblTitle.setForeground(new Color(46, 46, 46));
//        topPanel.add(lblTitle, BorderLayout.CENTER);
//
//        add(topPanel, BorderLayout.NORTH);
//
//        // ---------- FORM PANEL ----------
//        JPanel formPanel = new JPanel(new GridBagLayout());
//        formPanel.setBackground(new Color(250, 250, 250)); // off-white section
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(10, 10, 10, 10);
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//
//        // Recipe Name
//        gbc.gridx = 0; gbc.gridy = 0;
//        formPanel.add(new JLabel("Recipe Name:"), gbc);
//        txtName = new JTextField(20);
//        gbc.gridx = 1;
//        formPanel.add(txtName, gbc);
//
//        // Type
//        gbc.gridx = 0; gbc.gridy = 1;
//        formPanel.add(new JLabel("Type:"), gbc);
//        String[] recipeTypes = {"Main Course", "Side Dish", "Dessert", "Snack", "Beverage"};
//        comboType = new JComboBox<>(recipeTypes);
//        gbc.gridx = 1;
//        formPanel.add(comboType, gbc);
//
//        // Ingredients
//        gbc.gridx = 0; gbc.gridy = 2;
//        formPanel.add(new JLabel("Ingredients:"), gbc);
//        txtIngredients = new JTextArea(5, 20);
//        JScrollPane scrollIngredients = new JScrollPane(txtIngredients);
//        gbc.gridx = 1;
//        formPanel.add(scrollIngredients, gbc);
//
//        // Instructions
//        gbc.gridx = 0; gbc.gridy = 3;
//        formPanel.add(new JLabel("Instructions:"), gbc);
//        txtInstructions = new JTextArea(5, 20);
//        JScrollPane scrollInstructions = new JScrollPane(txtInstructions);
//        gbc.gridx = 1;
//        formPanel.add(scrollInstructions, gbc);
//
//        // Submit Button
//        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
//        gbc.anchor = GridBagConstraints.CENTER;
//        btnSubmit = new JButton("Save Recipe");
//        styleButton(btnSubmit);
//        btnSubmit.setPreferredSize(new Dimension(140, 40));
//        formPanel.add(btnSubmit, gbc);
//
//        add(formPanel, BorderLayout.CENTER);
//
//        // ---------- ACTION ----------
//        btnSubmit.addActionListener(e -> insertRecipe());
//
//        setVisible(true);
//    }
//
//    // ---------- STYLED BUTTON ----------
//    private void styleButton(JButton button) {
//        Color borderColor = new Color(76, 175, 80); // fresh green
//        Color hoverColor = new Color(76, 175, 80);
//        Color textColor = borderColor;
//        Color bgColor = new Color(245, 245, 245); // light neutral
//
//        button.setBackground(bgColor);
//        button.setForeground(textColor);
//        button.setFont(new Font("SansSerif", Font.BOLD, 15));
//        button.setFocusPainted(false);
//        button.setBorder(BorderFactory.createLineBorder(borderColor, 2));
//        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//
//        button.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseEntered(java.awt.event.MouseEvent evt) {
//                button.setBackground(hoverColor);
//                button.setForeground(Color.WHITE);
//            }
//            public void mouseExited(java.awt.event.MouseEvent evt) {
//                button.setBackground(bgColor);
//                button.setForeground(textColor);
//            }
//        });
//    }
//
//    // ---------- INSERT INTO DATABASE ----------
//    private void insertRecipe() {
//        String name = txtName.getText().trim();
//        String type = (String) comboType.getSelectedItem();
//        String ingredients = txtIngredients.getText().trim();
//        String instructions = txtInstructions.getText().trim();
//
//        if (name.isEmpty() || type.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Please fill all fields!");
//            return;
//        }
//
//        try (Connection conn = DriverManager.getConnection(
//                "jdbc:mysql://localhost:3306/recipe_db", "root", "99952")) {
//
//            String sql = "INSERT INTO recipes (user_id, name, type, ingredients, instructions) VALUES (?, ?, ?, ?, ?)";
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setInt(1, Integer.parseInt(userId));
//            stmt.setString(2, name);
//            stmt.setString(3, type);
//            stmt.setString(4, ingredients);
//            stmt.setString(5, instructions);
//
//            int rows = stmt.executeUpdate();
//            if (rows > 0) {
//                JOptionPane.showMessageDialog(this, "Recipe added successfully!");
//                txtName.setText("");
//                comboType.setSelectedIndex(0);
//                txtIngredients.setText("");
//                txtInstructions.setText("");
//            } else {
//                JOptionPane.showMessageDialog(this, "Failed to add recipe.");
//            }
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
//        }
//    }
//
//    // ---------- EXTRA CONSTRUCTOR (if needed) ----------
//    public AddRecipeFrame(String userId, String selected) {}
//    
//    // ---------- MAIN (for testing) ----------
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new AddRecipeFrame("1"));
//    }
//}

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.sql.*;

public class AddRecipeFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private String userId;
    private JTextField txtName;
    private JTextArea txtIngredients, txtInstructions;
    private JComboBox<String> comboType;
    private JButton btnSubmit;

    public AddRecipeFrame(String userId) {
        this.userId = userId;

        // ---------- FRAME SETTINGS ----------
        setTitle("Add New Recipe");
        setSize(600, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(250, 250, 250)); // off-white background

        // ---------- TOP PANEL (Back Button + Title) ----------
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(232, 245, 233)); // light green shade
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JButton btnBack = new JButton("← Back");
        styleButton(btnBack);
        btnBack.setPreferredSize(new Dimension(100, 35));
        
        // FIXED: Changed unused lambda parameter from 'e' to '_'
        btnBack.addActionListener(_ -> {
            dispose();
            new DashboardFrame(userId).setVisible(true);
        });
        topPanel.add(btnBack, BorderLayout.WEST);

        JLabel lblTitle = new JLabel("Add New Recipe", JLabel.CENTER);
        lblTitle.setFont(new Font("Serif", Font.BOLD, 22));
        lblTitle.setForeground(new Color(46, 46, 46));
        topPanel.add(lblTitle, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // ---------- FORM PANEL ----------
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(250, 250, 250)); // off-white section
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Recipe Name
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Recipe Name:"), gbc);
        txtName = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(txtName, gbc);

        // Type
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Type:"), gbc);
        String[] recipeTypes = {"Main Course", "Side Dish", "Dessert", "Snack", "Beverage"};
        comboType = new JComboBox<>(recipeTypes);
        gbc.gridx = 1;
        formPanel.add(comboType, gbc);

        // Ingredients
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Ingredients:"), gbc);
        txtIngredients = new JTextArea(5, 20);
        JScrollPane scrollIngredients = new JScrollPane(txtIngredients);
        gbc.gridx = 1;
        formPanel.add(scrollIngredients, gbc);

        // Instructions
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Instructions:"), gbc);
        txtInstructions = new JTextArea(5, 20);
        JScrollPane scrollInstructions = new JScrollPane(txtInstructions);
        gbc.gridx = 1;
        formPanel.add(scrollInstructions, gbc);

        // Submit Button
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btnSubmit = new JButton("Save Recipe");
        styleButton(btnSubmit);
        btnSubmit.setPreferredSize(new Dimension(140, 40));
        formPanel.add(btnSubmit, gbc);

        add(formPanel, BorderLayout.CENTER);

        // ---------- ACTION ----------
        // FIXED: Changed unused lambda parameter from 'e' to '_'
        btnSubmit.addActionListener(_ -> insertRecipe());

        setVisible(true);
    }

    // ---------- STYLED BUTTON ----------
    private void styleButton(JButton button) {
        Color borderColor = new Color(76, 175, 80); // fresh green
        Color hoverColor = new Color(76, 175, 80);
        Color textColor = borderColor;
        Color bgColor = new Color(245, 245, 245); // light neutral

        button.setBackground(bgColor);
        button.setForeground(textColor);
        button.setFont(new Font("SansSerif", Font.BOLD, 15));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(borderColor, 2));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // FIXED: Proper MouseAdapter with imports and @Override
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(hoverColor);
                button.setForeground(Color.WHITE);
            }
            
            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(bgColor);
                button.setForeground(textColor);
            }
        });
    }

    // ---------- INSERT INTO DATABASE ----------
    private void insertRecipe() {
        String name = txtName.getText().trim();
        String type = (String) comboType.getSelectedItem();
        String ingredients = txtIngredients.getText().trim();
        String instructions = txtInstructions.getText().trim();

        if (name.isEmpty() || type.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/recipe_db", "root", "99952")) {

            String sql = "INSERT INTO recipes (user_id, name, type, ingredients, instructions) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(userId));
            stmt.setString(2, name);
            stmt.setString(3, type);
            stmt.setString(4, ingredients);
            stmt.setString(5, instructions);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Recipe added successfully!");
                txtName.setText("");
                comboType.setSelectedIndex(0);
                txtIngredients.setText("");
                txtInstructions.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add recipe.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    // ---------- EXTRA CONSTRUCTOR (if needed) ----------
    // FIXED: Added @SuppressWarnings for unused parameter
    @SuppressWarnings("unused")
    public AddRecipeFrame(String userId, String selected) {
        // Constructor implementation if needed
    }
    
    // ---------- MAIN (for testing) ----------
    // FIXED: Changed unused lambda parameter from 'args' to '_'
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddRecipeFrame("1"));
    }
}