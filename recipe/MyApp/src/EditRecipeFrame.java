////import javax.swing.*;
////import java.awt.*;
////import java.sql.*;
////
////public class EditRecipeFrame extends JFrame {
////
////    private int recipeId;
////    private String userId;
////
////    private JTextField txtName;
////    private JComboBox<String> comboType;
////    private JTextArea txtIngredients, txtInstructions;
////    private JButton btnUpdate;
////
////    public EditRecipeFrame(int recipeId, String userId) {
////        this.recipeId = recipeId;
////        this.userId = userId;
////
////        // ---------- FRAME SETTINGS ----------
////        setTitle("Edit Recipe");
////        setSize(600, 700);
////        setLocationRelativeTo(null);
////        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
////        setLayout(new BorderLayout(10, 10));
////        getContentPane().setBackground(new Color(250, 250, 250)); // off-white background
////
////        // ---------- TOP PANEL ----------
////        JPanel topPanel = new JPanel(new BorderLayout());
////        topPanel.setBackground(new Color(232, 245, 233)); // light green shade
////        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
////
////        // Back button
////        JButton btnBack = new JButton("⬅ Back");
////        btnBack.setFont(new Font("SansSerif", Font.BOLD, 14));
////        btnBack.setBackground(new Color(245, 245, 220)); // beige
////        btnBack.setForeground(new Color(46, 46, 46));
////        btnBack.setFocusPainted(false);
////        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
////        btnBack.setPreferredSize(new Dimension(100, 35));
////        btnBack.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 2));
////
////        // Hover effect for back button
////        btnBack.addMouseListener(new java.awt.event.MouseAdapter() {
////            public void mouseEntered(java.awt.event.MouseEvent evt) {
////                btnBack.setBackground(new Color(76, 175, 80)); // green
////                btnBack.setForeground(Color.WHITE);
////            }
////            public void mouseExited(java.awt.event.MouseEvent evt) {
////                btnBack.setBackground(new Color(245, 245, 220));
////                btnBack.setForeground(new Color(46, 46, 46));
////            }
////        });
////
////        btnBack.addActionListener(e -> {
////            dispose();
////            new MyRecipesFrame(userId).setVisible(true);
////        });
////        topPanel.add(btnBack, BorderLayout.WEST);
////
////        // Title
////        JLabel lblTitle = new JLabel("✏️ Edit Your Recipe", JLabel.CENTER);
////        lblTitle.setFont(new Font("Serif", Font.BOLD, 22));
////        lblTitle.setForeground(new Color(46, 46, 46));
////        topPanel.add(lblTitle, BorderLayout.CENTER);
////
////        add(topPanel, BorderLayout.NORTH);
////
////        // ---------- FORM PANEL ----------
////        JPanel formPanel = new JPanel(new GridBagLayout());
////        formPanel.setBackground(new Color(250, 250, 250)); // off-white
////        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
////
////        GridBagConstraints gbc = new GridBagConstraints();
////        gbc.insets = new Insets(10, 10, 10, 10);
////        gbc.fill = GridBagConstraints.HORIZONTAL;
////
////        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
////        Color labelColor = new Color(46, 46, 46);
////
////        // Recipe Name
////        gbc.gridx = 0; gbc.gridy = 0;
////        JLabel lblName = new JLabel("Recipe Name:");
////        lblName.setFont(labelFont);
////        lblName.setForeground(labelColor);
////        formPanel.add(lblName, gbc);
////
////        txtName = new JTextField(20);
////        styleTextField(txtName);
////        gbc.gridx = 1;
////        formPanel.add(txtName, gbc);
////
////        // Type
////        gbc.gridx = 0; gbc.gridy = 1;
////        JLabel lblType = new JLabel("Type:");
////        lblType.setFont(labelFont);
////        lblType.setForeground(labelColor);
////        formPanel.add(lblType, gbc);
////
////        String[] recipeTypes = {"Main Course", "Side Dish", "Dessert", "Snack", "Beverage"};
////        comboType = new JComboBox<>(recipeTypes);
////        comboType.setFont(new Font("SansSerif", Font.PLAIN, 13));
////        comboType.setBackground(new Color(245, 245, 220)); // beige
////        comboType.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 2));
////        gbc.gridx = 1;
////        formPanel.add(comboType, gbc);
////
////        // Ingredients
////        gbc.gridx = 0; gbc.gridy = 2;
////        JLabel lblIngredients = new JLabel("Ingredients:");
////        lblIngredients.setFont(labelFont);
////        lblIngredients.setForeground(labelColor);
////        formPanel.add(lblIngredients, gbc);
////
////        txtIngredients = new JTextArea(5, 20);
////        styleTextArea(txtIngredients);
////        JScrollPane scrollIngredients = new JScrollPane(txtIngredients);
////        gbc.gridx = 1;
////        formPanel.add(scrollIngredients, gbc);
////
////        // Instructions
////        gbc.gridx = 0; gbc.gridy = 3;
////        JLabel lblInstructions = new JLabel("Instructions:");
////        lblInstructions.setFont(labelFont);
////        lblInstructions.setForeground(labelColor);
////        formPanel.add(lblInstructions, gbc);
////
////        txtInstructions = new JTextArea(5, 20);
////        styleTextArea(txtInstructions);
////        JScrollPane scrollInstructions = new JScrollPane(txtInstructions);
////        gbc.gridx = 1;
////        formPanel.add(scrollInstructions, gbc);
////
////        // Update Button
////        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
////        btnUpdate = new JButton("💾 Update Recipe");
////        styleButton(btnUpdate, new Color(76, 175, 80)); // green tone
////        formPanel.add(btnUpdate, gbc);
////
////        add(formPanel, BorderLayout.CENTER);
////
////        // ---------- LOAD RECIPE ----------
////        loadRecipeData();
////
////        // ---------- ACTION ----------
////        btnUpdate.addActionListener(e -> updateRecipe());
////    }
////
////    // ---------- STYLE HELPERS ----------
////    private void styleTextField(JTextField field) {
////        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
////        field.setBackground(new Color(245, 245, 220)); // beige
////        field.setBorder(BorderFactory.createCompoundBorder(
////                BorderFactory.createLineBorder(new Color(76, 175, 80), 2),
////                BorderFactory.createEmptyBorder(5, 8, 5, 8)
////        ));
////    }
////
////    private void styleTextArea(JTextArea area) {
////        area.setFont(new Font("SansSerif", Font.PLAIN, 14));
////        area.setLineWrap(true);
////        area.setWrapStyleWord(true);
////        area.setBackground(new Color(245, 245, 220)); // beige
////        area.setBorder(BorderFactory.createCompoundBorder(
////                BorderFactory.createLineBorder(new Color(76, 175, 80), 2),
////                BorderFactory.createEmptyBorder(8, 8, 8, 8)
////        ));
////    }
////
////    private void styleButton(JButton button, Color bgColor) {
////        button.setBackground(new Color(245, 245, 220)); // beige
////        button.setForeground(new Color(76, 175, 80));
////        button.setFocusPainted(false);
////        button.setFont(new Font("SansSerif", Font.BOLD, 15));
////        button.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 2));
////        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
////
////        // Hover effect
////        button.addMouseListener(new java.awt.event.MouseAdapter() {
////            public void mouseEntered(java.awt.event.MouseEvent evt) {
////                button.setBackground(new Color(76, 175, 80));
////                button.setForeground(Color.WHITE);
////            }
////            public void mouseExited(java.awt.event.MouseEvent evt) {
////                button.setBackground(new Color(245, 245, 220));
////                button.setForeground(new Color(76, 175, 80));
////            }
////        });
////    }
////
////    // ---------- DATABASE ----------
////    private void loadRecipeData() {
////        try (Connection conn = DriverManager.getConnection(
////                "jdbc:mysql://localhost:3306/recipe_db", "root", "99952")) {
////
////            String sql = "SELECT name, type, ingredients, instructions FROM recipes WHERE recipe_id = ?";
////            PreparedStatement stmt = conn.prepareStatement(sql);
////            stmt.setInt(1, recipeId);
////            ResultSet rs = stmt.executeQuery();
////
////            if (rs.next()) {
////                txtName.setText(rs.getString("name"));
////                comboType.setSelectedItem(rs.getString("type"));
////                txtIngredients.setText(rs.getString("ingredients"));
////                txtInstructions.setText(rs.getString("instructions"));
////            }
////
////        } catch (SQLException ex) {
////            ex.printStackTrace();
////            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
////        }
////    }
////
////    private void updateRecipe() {
////        String name = txtName.getText().trim();
////        String type = (String) comboType.getSelectedItem();
////        String ingredients = txtIngredients.getText().trim();
////        String instructions = txtInstructions.getText().trim();
////
////        if (name.isEmpty() || type.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
////            JOptionPane.showMessageDialog(this, "Please fill all fields!");
////            return;
////        }
////
////        try (Connection conn = DriverManager.getConnection(
////                "jdbc:mysql://localhost:3306/recipe_db", "root", "99952")) {
////
////            String sql = "UPDATE recipes SET name=?, type=?, ingredients=?, instructions=? WHERE recipe_id=?";
////            PreparedStatement stmt = conn.prepareStatement(sql);
////            stmt.setString(1, name);
////            stmt.setString(2, type);
////            stmt.setString(3, ingredients);
////            stmt.setString(4, instructions);
////            stmt.setInt(5, recipeId);
////
////            int rows = stmt.executeUpdate();
////            if (rows > 0) {
////                JOptionPane.showMessageDialog(this, "✅ Recipe updated successfully!");
////                dispose();
////                new DashboardFrame(userId).setVisible(true);
////            } else {
////                JOptionPane.showMessageDialog(this, "❌ Failed to update recipe.");
////            }
////
////        } catch (SQLException ex) {
////            ex.printStackTrace();
////            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
////        }
////    }
////
////    public static void main(String[] args) {
////        SwingUtilities.invokeLater(() -> new EditRecipeFrame(1, "1").setVisible(true));
////    }
////}
//
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseAdapter;
//import java.sql.*;
//
//public class EditRecipeFrame extends JFrame {
//
//    private static final long serialVersionUID = 1L;
//    private int recipeId;
//    private String userId;
//
//    private JTextField txtName;
//    private JComboBox<String> comboType;
//    private JTextArea txtIngredients, txtInstructions;
//    private JButton btnUpdate;
//
//    public EditRecipeFrame(int recipeId, String userId) {
//        this.recipeId = recipeId;
//        this.userId = userId;
//
//        // ---------- FRAME SETTINGS ----------
//        setTitle("Edit Recipe");
//        setSize(600, 700);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setLayout(new BorderLayout(10, 10));
//        getContentPane().setBackground(new Color(250, 250, 250)); // off-white background
//
//        // ---------- TOP PANEL ----------
//        JPanel topPanel = new JPanel(new BorderLayout());
//        topPanel.setBackground(new Color(232, 245, 233)); // light green shade
//        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
//
//        // Back button
//        JButton btnBack = new JButton("⬅ Back");
//        btnBack.setFont(new Font("SansSerif", Font.BOLD, 14));
//        btnBack.setBackground(new Color(245, 245, 220)); // beige
//        btnBack.setForeground(new Color(46, 46, 46));
//        btnBack.setFocusPainted(false);
//        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        btnBack.setPreferredSize(new Dimension(100, 35));
//        btnBack.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 2));
//
//        // Hover effect for back button - FIXED: Proper MouseAdapter with @Override
//        btnBack.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseEntered(MouseEvent evt) {
//                btnBack.setBackground(new Color(76, 175, 80)); // green
//                btnBack.setForeground(Color.WHITE);
//            }
//            
//            @Override
//            public void mouseExited(MouseEvent evt) {
//                btnBack.setBackground(new Color(245, 245, 220));
//                btnBack.setForeground(new Color(46, 46, 46));
//            }
//        });
//
//        // FIXED: Changed unused lambda parameter from 'e' to '_'
//        btnBack.addActionListener(_ -> {
//            dispose();
//            new MyRecipesFrame(userId).setVisible(true);
//        });
//        topPanel.add(btnBack, BorderLayout.WEST);
//
//        // Title
//        JLabel lblTitle = new JLabel("✏️ Edit Your Recipe", JLabel.CENTER);
//        lblTitle.setFont(new Font("Serif", Font.BOLD, 22));
//        lblTitle.setForeground(new Color(46, 46, 46));
//        topPanel.add(lblTitle, BorderLayout.CENTER);
//
//        add(topPanel, BorderLayout.NORTH);
//
//        // ---------- FORM PANEL ----------
//        JPanel formPanel = new JPanel(new GridBagLayout());
//        formPanel.setBackground(new Color(250, 250, 250)); // off-white
//        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(10, 10, 10, 10);
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//
//        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
//        Color labelColor = new Color(46, 46, 46);
//
//        // Recipe Name
//        gbc.gridx = 0; gbc.gridy = 0;
//        JLabel lblName = new JLabel("Recipe Name:");
//        lblName.setFont(labelFont);
//        lblName.setForeground(labelColor);
//        formPanel.add(lblName, gbc);
//
//        txtName = new JTextField(20);
//        styleTextField(txtName);
//        gbc.gridx = 1;
//        formPanel.add(txtName, gbc);
//
//        // Type
//        gbc.gridx = 0; gbc.gridy = 1;
//        JLabel lblType = new JLabel("Type:");
//        lblType.setFont(labelFont);
//        lblType.setForeground(labelColor);
//        formPanel.add(lblType, gbc);
//
//        String[] recipeTypes = {"Main Course", "Side Dish", "Dessert", "Snack", "Beverage"};
//        comboType = new JComboBox<>(recipeTypes);
//        comboType.setFont(new Font("SansSerif", Font.PLAIN, 13));
//        comboType.setBackground(new Color(245, 245, 220)); // beige
//        comboType.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 2));
//        gbc.gridx = 1;
//        formPanel.add(comboType, gbc);
//
//        // Ingredients
//        gbc.gridx = 0; gbc.gridy = 2;
//        JLabel lblIngredients = new JLabel("Ingredients:");
//        lblIngredients.setFont(labelFont);
//        lblIngredients.setForeground(labelColor);
//        formPanel.add(lblIngredients, gbc);
//
//        txtIngredients = new JTextArea(5, 20);
//        styleTextArea(txtIngredients);
//        JScrollPane scrollIngredients = new JScrollPane(txtIngredients);
//        gbc.gridx = 1;
//        formPanel.add(scrollIngredients, gbc);
//
//        // Instructions
//        gbc.gridx = 0; gbc.gridy = 3;
//        JLabel lblInstructions = new JLabel("Instructions:");
//        lblInstructions.setFont(labelFont);
//        lblInstructions.setForeground(labelColor);
//        formPanel.add(lblInstructions, gbc);
//
//        txtInstructions = new JTextArea(5, 20);
//        styleTextArea(txtInstructions);
//        JScrollPane scrollInstructions = new JScrollPane(txtInstructions);
//        gbc.gridx = 1;
//        formPanel.add(scrollInstructions, gbc);
//
//        // Update Button
//        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
//        btnUpdate = new JButton("💾 Update Recipe");
//        styleButton(btnUpdate, new Color(76, 175, 80)); // green tone
//        formPanel.add(btnUpdate, gbc);
//
//        add(formPanel, BorderLayout.CENTER);
//
//        // ---------- LOAD RECIPE ----------
//        loadRecipeData();
//
//        // ---------- ACTION ----------
//        // FIXED: Changed unused lambda parameter from 'e' to '_'
//        btnUpdate.addActionListener(_ -> updateRecipe());
//        
//        setVisible(true);
//    }
//
//    // ---------- STYLE HELPERS ----------
//    private void styleTextField(JTextField field) {
//        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
//        field.setBackground(new Color(245, 245, 220)); // beige
//        field.setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createLineBorder(new Color(76, 175, 80), 2),
//                BorderFactory.createEmptyBorder(5, 8, 5, 8)
//        ));
//    }
//
//    private void styleTextArea(JTextArea area) {
//        area.setFont(new Font("SansSerif", Font.PLAIN, 14));
//        area.setLineWrap(true);
//        area.setWrapStyleWord(true);
//        area.setBackground(new Color(245, 245, 220)); // beige
//        area.setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createLineBorder(new Color(76, 175, 80), 2),
//                BorderFactory.createEmptyBorder(8, 8, 8, 8)
//        ));
//    }
//
//    private void styleButton(JButton button, Color bgColor) {
//        button.setBackground(new Color(245, 245, 220)); // beige
//        button.setForeground(new Color(76, 175, 80));
//        button.setFocusPainted(false);
//        button.setFont(new Font("SansSerif", Font.BOLD, 15));
//        button.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 2));
//        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//
//        // Hover effect - FIXED: Proper MouseAdapter with @Override
//        button.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseEntered(MouseEvent evt) {
//                button.setBackground(new Color(76, 175, 80));
//                button.setForeground(Color.WHITE);
//            }
//            
//            @Override
//            public void mouseExited(MouseEvent evt) {
//                button.setBackground(new Color(245, 245, 220));
//                button.setForeground(new Color(76, 175, 80));
//            }
//        });
//    }
//
//    // ---------- DATABASE ----------
//    private void loadRecipeData() {
//        try (Connection conn = DriverManager.getConnection(
//                "jdbc:mysql://localhost:3306/recipe_db", "root", "99952")) {
//
//            String sql = "SELECT name, type, ingredients, instructions FROM recipes WHERE recipe_id = ?";
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setInt(1, recipeId);
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                txtName.setText(rs.getString("name"));
//                comboType.setSelectedItem(rs.getString("type"));
//                txtIngredients.setText(rs.getString("ingredients"));
//                txtInstructions.setText(rs.getString("instructions"));
//            }
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
//        }
//    }
//
//    private void updateRecipe() {
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
//            String sql = "UPDATE recipes SET name=?, type=?, ingredients=?, instructions=? WHERE recipe_id=?";
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setString(1, name);
//            stmt.setString(2, type);
//            stmt.setString(3, ingredients);
//            stmt.setString(4, instructions);
//            stmt.setInt(5, recipeId);
//
//            int rows = stmt.executeUpdate();
//            if (rows > 0) {
//                JOptionPane.showMessageDialog(this, "✅ Recipe updated successfully!");
//                dispose();
//                new DashboardFrame(userId).setVisible(true);
//            } else {
//                JOptionPane.showMessageDialog(this, "❌ Failed to update recipe.");
//            }
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
//        }
//    }
//
//    // FIXED: Changed unused lambda parameter from 'args' to '_'
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new EditRecipeFrame(1, "1").setVisible(true));
//    }
//}

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.sql.*;

public class EditRecipeFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private int recipeId;
    private String userId;

    private JTextField txtName;
    private JComboBox<String> comboType;
    private JTextArea txtIngredients, txtInstructions;
    private JButton btnUpdate;

    public EditRecipeFrame(int recipeId, String userId) {
        this.recipeId = recipeId;
        this.userId = userId;

        // ---------- FRAME SETTINGS ----------
        setTitle("Edit Recipe");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(250, 250, 250)); // off-white background

        // ---------- TOP PANEL ----------
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(232, 245, 233)); // light green shade
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Back button
        JButton btnBack = new JButton("⬅ Back");
        btnBack.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnBack.setBackground(new Color(245, 245, 220)); // beige
        btnBack.setForeground(new Color(46, 46, 46));
        btnBack.setFocusPainted(false);
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.setPreferredSize(new Dimension(100, 35));
        btnBack.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 2));

        // Hover effect for back button - FIXED: Proper MouseAdapter with @Override
        btnBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                btnBack.setBackground(new Color(76, 175, 80)); // green
                btnBack.setForeground(Color.WHITE);
            }
            
            @Override
            public void mouseExited(MouseEvent evt) {
                btnBack.setBackground(new Color(245, 245, 220));
                btnBack.setForeground(new Color(46, 46, 46));
            }
        });

        // FIXED: Changed unused lambda parameter from 'e' to '_'
        btnBack.addActionListener(_ -> {
            dispose();
            //new MyRecipesFrame(userId).setVisible(true);
        });
        topPanel.add(btnBack, BorderLayout.WEST);

        // Title
        JLabel lblTitle = new JLabel("✏️ Edit Your Recipe", JLabel.CENTER);
        lblTitle.setFont(new Font("Serif", Font.BOLD, 22));
        lblTitle.setForeground(new Color(46, 46, 46));
        topPanel.add(lblTitle, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // ---------- FORM PANEL ----------
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(250, 250, 250)); // off-white
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
        Color labelColor = new Color(46, 46, 46);

        // Recipe Name
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblName = new JLabel("Recipe Name:");
        lblName.setFont(labelFont);
        lblName.setForeground(labelColor);
        formPanel.add(lblName, gbc);

        txtName = new JTextField(20);
        styleTextField(txtName);
        gbc.gridx = 1;
        formPanel.add(txtName, gbc);

        // Type
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblType = new JLabel("Type:");
        lblType.setFont(labelFont);
        lblType.setForeground(labelColor);
        formPanel.add(lblType, gbc);

        String[] recipeTypes = {"Main Course", "Side Dish", "Dessert", "Snack", "Beverage"};
        comboType = new JComboBox<>(recipeTypes);
        comboType.setFont(new Font("SansSerif", Font.PLAIN, 13));
        comboType.setBackground(new Color(245, 245, 220)); // beige
        comboType.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 2));
        gbc.gridx = 1;
        formPanel.add(comboType, gbc);

        // Ingredients
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblIngredients = new JLabel("Ingredients:");
        lblIngredients.setFont(labelFont);
        lblIngredients.setForeground(labelColor);
        formPanel.add(lblIngredients, gbc);

        txtIngredients = new JTextArea(5, 20);
        styleTextArea(txtIngredients);
        JScrollPane scrollIngredients = new JScrollPane(txtIngredients);
        gbc.gridx = 1;
        formPanel.add(scrollIngredients, gbc);

        // Instructions
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblInstructions = new JLabel("Instructions:");
        lblInstructions.setFont(labelFont);
        lblInstructions.setForeground(labelColor);
        formPanel.add(lblInstructions, gbc);

        txtInstructions = new JTextArea(5, 20);
        styleTextArea(txtInstructions);
        JScrollPane scrollInstructions = new JScrollPane(txtInstructions);
        gbc.gridx = 1;
        formPanel.add(scrollInstructions, gbc);

        // Update Button
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        btnUpdate = new JButton("💾 Update Recipe");
        // FIXED: Removed unused parameter from method call
        styleButton(btnUpdate);
        formPanel.add(btnUpdate, gbc);

        add(formPanel, BorderLayout.CENTER);

        // ---------- LOAD RECIPE ----------
        loadRecipeData();

        // ---------- ACTION ----------
        // FIXED: Changed unused lambda parameter from 'e' to '_'
        btnUpdate.addActionListener(_ -> updateRecipe());
        
        setVisible(true);
    }

    // ---------- STYLE HELPERS ----------
    private void styleTextField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBackground(new Color(245, 245, 220)); // beige
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(76, 175, 80), 2),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
    }

    private void styleTextArea(JTextArea area) {
        area.setFont(new Font("SansSerif", Font.PLAIN, 14));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBackground(new Color(245, 245, 220)); // beige
        area.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(76, 175, 80), 2),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
    }

    // FIXED: Removed unused 'bgColor' parameter
    private void styleButton(JButton button) {
        button.setBackground(new Color(245, 245, 220)); // beige
        button.setForeground(new Color(76, 175, 80));
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 15));
        button.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 2));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover effect - FIXED: Proper MouseAdapter with @Override
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(76, 175, 80));
                button.setForeground(Color.WHITE);
            }
            
            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(245, 245, 220));
                button.setForeground(new Color(76, 175, 80));
            }
        });
    }

    // ---------- DATABASE ----------
    private void loadRecipeData() {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/recipe_db", "root", "99952")) {

            String sql = "SELECT name, type, ingredients, instructions FROM recipes WHERE recipe_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, recipeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                txtName.setText(rs.getString("name"));
                comboType.setSelectedItem(rs.getString("type"));
                txtIngredients.setText(rs.getString("ingredients"));
                txtInstructions.setText(rs.getString("instructions"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    private void updateRecipe() {
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

            String sql = "UPDATE recipes SET name=?, type=?, ingredients=?, instructions=? WHERE recipe_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, type);
            stmt.setString(3, ingredients);
            stmt.setString(4, instructions);
            stmt.setInt(5, recipeId);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "✅ Recipe updated successfully!");
                dispose();
                new DashboardFrame(userId).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "❌ Failed to update recipe.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    // FIXED: Changed unused lambda parameter from 'args' to '_'
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EditRecipeFrame(1, "1").setVisible(true));
    }
}