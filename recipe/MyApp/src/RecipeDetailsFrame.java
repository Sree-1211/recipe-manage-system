//////import javax.swing.*;
//////import java.awt.*;
//////import java.awt.event.*;
//////import java.sql.*;
//////
//////public class RecipeDetailsFrame extends JFrame {
//////
//////    private static final long serialVersionUID = 1L;
//////
//////    public RecipeDetailsFrame(int recipeId) {
//////        setTitle("Recipe Details");
//////        setSize(650, 600);
//////        setLocationRelativeTo(null);
//////        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//////        getContentPane().setBackground(new Color(250, 250, 250)); // off-white
//////
//////        // --- TITLE PANEL ---
//////        JPanel headerPanel = new JPanel(new BorderLayout());
//////        headerPanel.setBackground(new Color(232, 245, 233)); // light green
//////        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
//////
//////        JLabel lblHeader = new JLabel("🍲 Recipe Details", JLabel.CENTER);
//////        lblHeader.setFont(new Font("Serif", Font.BOLD, 24));
//////        lblHeader.setForeground(new Color(46, 46, 46));
//////        headerPanel.add(lblHeader, BorderLayout.CENTER);
//////        add(headerPanel, BorderLayout.NORTH);
//////
//////        // --- MAIN PANEL ---
//////        JPanel detailsPanel = new JPanel(new GridBagLayout());
//////        detailsPanel.setBackground(new Color(250, 250, 250));
//////        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
//////        GridBagConstraints gbc = new GridBagConstraints();
//////        gbc.insets = new Insets(10, 10, 10, 10);
//////        gbc.fill = GridBagConstraints.HORIZONTAL;
//////        gbc.anchor = GridBagConstraints.WEST;
//////        gbc.gridx = 0;
//////        gbc.gridy = 0;
//////        gbc.weightx = 1;
//////
//////        JLabel lblName = new JLabel();
//////        lblName.setFont(new Font("Serif", Font.BOLD, 20));
//////        lblName.setForeground(new Color(46, 46, 46));
//////        detailsPanel.add(lblName, gbc);
//////
//////        gbc.gridy++;
//////        JLabel lblType = new JLabel();
//////        lblType.setFont(new Font("SansSerif", Font.PLAIN, 14));
//////        lblType.setForeground(new Color(60, 60, 60));
//////        detailsPanel.add(lblType, gbc);
//////
//////        gbc.gridy++;
//////        JLabel lblUser = new JLabel();
//////        lblUser.setFont(new Font("SansSerif", Font.ITALIC, 13));
//////        lblUser.setForeground(new Color(90, 90, 90));
//////        detailsPanel.add(lblUser, gbc);
//////
//////        // --- Ingredients Section ---
//////        gbc.gridy++;
//////        JTextArea txtIngredients = new JTextArea();
//////        txtIngredients.setLineWrap(true);
//////        txtIngredients.setWrapStyleWord(true);
//////        txtIngredients.setEditable(false);
//////        txtIngredients.setFont(new Font("SansSerif", Font.PLAIN, 13));
//////        txtIngredients.setBackground(new Color(245, 245, 220)); // beige
//////        JScrollPane ingredientsScroll = new JScrollPane(txtIngredients);
//////        ingredientsScroll.setBorder(BorderFactory.createTitledBorder("Ingredients"));
//////        gbc.fill = GridBagConstraints.BOTH;
//////        gbc.weighty = 0.3;
//////        detailsPanel.add(ingredientsScroll, gbc);
//////
//////        // --- Instructions Section ---
//////        gbc.gridy++;
//////        JTextArea txtInstructions = new JTextArea();
//////        txtInstructions.setLineWrap(true);
//////        txtInstructions.setWrapStyleWord(true);
//////        txtInstructions.setEditable(false);
//////        txtInstructions.setFont(new Font("SansSerif", Font.PLAIN, 13));
//////        txtInstructions.setBackground(new Color(245, 245, 220)); // beige
//////        JScrollPane instructionsScroll = new JScrollPane(txtInstructions);
//////        instructionsScroll.setBorder(BorderFactory.createTitledBorder("Instructions"));
//////        gbc.weighty = 0.3;
//////        detailsPanel.add(instructionsScroll, gbc);
//////
//////        // --- FEEDBACK SECTION ---
//////        gbc.gridy++;
//////        JPanel feedbackPanel = new JPanel(new BorderLayout(10, 10));
//////        feedbackPanel.setBackground(new Color(232, 245, 233));
//////        feedbackPanel.setBorder(BorderFactory.createTitledBorder("💬 Feedback"));
//////
//////        // View existing feedback
//////        JTextArea feedbackList = new JTextArea();
//////        feedbackList.setEditable(false);
//////        feedbackList.setFont(new Font("SansSerif", Font.PLAIN, 13));
//////        feedbackList.setBackground(Color.WHITE);
//////        JScrollPane feedbackScroll = new JScrollPane(feedbackList);
//////        feedbackPanel.add(feedbackScroll, BorderLayout.CENTER);
//////
//////        // Add new feedback
//////        JPanel addFeedbackPanel = new JPanel(new BorderLayout(5, 5));
//////        JTextField txtFeedback = new JTextField();
//////        JButton btnAddFeedback = new JButton("Add Feedback");
//////        btnAddFeedback.setBackground(new Color(245, 245, 220));
//////        btnAddFeedback.setForeground(new Color(46, 46, 46));
//////        btnAddFeedback.setFont(new Font("SansSerif", Font.BOLD, 13));
//////        btnAddFeedback.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 2));
//////        btnAddFeedback.setFocusPainted(false);
//////        btnAddFeedback.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//////
//////        // Hover effect
//////        btnAddFeedback.addMouseListener(new MouseAdapter() {
//////            @Override
//////            public void mouseEntered(MouseEvent evt) {
//////                btnAddFeedback.setBackground(new Color(76, 175, 80));
//////                btnAddFeedback.setForeground(Color.WHITE);
//////            }
//////
//////            @Override
//////            public void mouseExited(MouseEvent evt) {
//////                btnAddFeedback.setBackground(new Color(245, 245, 220));
//////                btnAddFeedback.setForeground(new Color(46, 46, 46));
//////            }
//////        });
//////
//////        addFeedbackPanel.add(txtFeedback, BorderLayout.CENTER);
//////        addFeedbackPanel.add(btnAddFeedback, BorderLayout.EAST);
//////        feedbackPanel.add(addFeedbackPanel, BorderLayout.SOUTH);
//////
//////        gbc.weighty = 0.4;
//////        detailsPanel.add(feedbackPanel, gbc);
//////
//////        JScrollPane scrollPane = new JScrollPane(detailsPanel);
//////        scrollPane.setBorder(null);
//////        add(scrollPane, BorderLayout.CENTER);
//////
//////        // --- FETCH RECIPE DATA ---
//////        try (Connection conn = DriverManager.getConnection(
//////                "jdbc:mysql://localhost:3306/recipe_db", "root", "99952")) {
//////
//////            String sql = """
//////                SELECT r.name, r.type, r.ingredients, r.instructions, u.username
//////                FROM recipes r
//////                JOIN users u ON r.user_id = u.id
//////                WHERE r.recipe_id = ?
//////            """;
//////
//////            PreparedStatement stmt = conn.prepareStatement(sql);
//////            stmt.setInt(1, recipeId);
//////            ResultSet rs = stmt.executeQuery();
//////
//////            if (rs.next()) {
//////                lblName.setText("🍽 " + rs.getString("name"));
//////                lblType.setText("📂 Type: " + rs.getString("type"));
//////                lblUser.setText("👤 Created by: " + rs.getString("username"));
//////                txtIngredients.setText(rs.getString("ingredients"));
//////                txtInstructions.setText(rs.getString("instructions"));
//////            } else {
//////                JOptionPane.showMessageDialog(this, "Recipe not found!");
//////                dispose();
//////            }
//////
//////            // --- FETCH FEEDBACKS ---
//////            String feedbackSql = """
//////            		SELECT u.username, f.comment, f.rating
//////            		FROM feedback f
//////            		JOIN users u ON f.user_id = u.id
//////            		WHERE f.recipe_id = ?
//////            """;
//////            PreparedStatement fstmt = conn.prepareStatement(feedbackSql);
//////            fstmt.setInt(1, recipeId);
//////            ResultSet frs = fstmt.executeQuery();
//////            StringBuilder feedbacks = new StringBuilder();
//////            while (frs.next()) {
//////                feedbacks.append("👤 ").append(frs.getString("username"))
//////                        .append(": ").append(frs.getString("comment"))
//////                        .append("\n-------------------------\n");
//////            }
//////            if (feedbacks.length() == 0) {
//////                feedbackList.setText("No feedback yet. Be the first to comment!");
//////            } else {
//////                feedbackList.setText(feedbacks.toString());
//////            }
//////
//////            // --- ADD NEW FEEDBACK ---
//////            btnAddFeedback.addActionListener(e -> {
//////                String feedbackText = txtFeedback.getText().trim();
//////                if (feedbackText.isEmpty()) {
//////                    JOptionPane.showMessageDialog(this, "Please enter feedback!");
//////                    return;
//////                }
//////                try (Connection conn2 = DriverManager.getConnection(
//////                        "jdbc:mysql://localhost:3306/recipe_db", "root", "99952")) {
//////                    String insertSql = "INSERT INTO feedback (recipe_id, user_id, comment) VALUES (?, ?, ?)";
//////                    PreparedStatement insertStmt = conn2.prepareStatement(insertSql);
//////                    insertStmt.setInt(1, recipeId);
//////                    insertStmt.setInt(2, 1); // <-- Replace with actual logged-in user_id
//////                    insertStmt.setString(3, feedbackText);
//////                    insertStmt.executeUpdate();
//////
//////                    feedbackList.append("\n👤 You: " + feedbackText + "\n-------------------------\n");
//////                    txtFeedback.setText("");
//////                } catch (SQLException ex2) {
//////                    JOptionPane.showMessageDialog(this, "Error adding feedback: " + ex2.getMessage());
//////                }
//////            });
//////
//////        } catch (SQLException ex) {
//////            ex.printStackTrace();
//////            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
//////        }
//////
//////        // --- BOTTOM PANEL ---
//////        JPanel bottomPanel = new JPanel();
//////        bottomPanel.setBackground(new Color(232, 245, 233));
//////        JButton btnClose = new JButton("Close");
//////        btnClose.setBackground(new Color(245, 245, 220));
//////        btnClose.setForeground(new Color(46, 46, 46));
//////        btnClose.setFont(new Font("SansSerif", Font.BOLD, 14));
//////        btnClose.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 2));
//////        btnClose.setFocusPainted(false);
//////        btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//////
//////        btnClose.addMouseListener(new MouseAdapter() {
//////            @Override
//////            public void mouseEntered(MouseEvent evt) {
//////                btnClose.setBackground(new Color(76, 175, 80));
//////                btnClose.setForeground(Color.WHITE);
//////            }
//////
//////            @Override
//////            public void mouseExited(MouseEvent evt) {
//////                btnClose.setBackground(new Color(245, 245, 220));
//////                btnClose.setForeground(new Color(46, 46, 46));
//////            }
//////        });
//////
//////        btnClose.addActionListener(_ -> dispose());
//////        bottomPanel.add(btnClose);
//////        add(bottomPanel, BorderLayout.SOUTH);
//////
//////        setVisible(true);
//////    }
//////
//////    public static void main(String[] args) {
//////        SwingUtilities.invokeLater(() -> new RecipeDetailsFrame(1));
//////    }
//////}
////
////import javax.swing.*;
////import java.awt.*;
////import java.awt.event.*;
////import java.sql.*;
////
////public class RecipeDetailsFrame extends JFrame {
////
////    private static final long serialVersionUID = 1L;
////    private int recipeId;
////    private String recipeName;
////
////    public RecipeDetailsFrame(int recipeId) {
////        this.recipeId = recipeId;
////        
////        initializeUI();
////        loadRecipeDetails();
////    }
////
////    private void initializeUI() {
////        setTitle("Recipe Details");
////        setSize(700, 650);
////        setLocationRelativeTo(null);
////        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
////        getContentPane().setBackground(new Color(250, 250, 250));
////
////        // --- HEADER PANEL ---
////        JPanel headerPanel = new JPanel(new BorderLayout());
////        headerPanel.setBackground(new Color(232, 245, 233));
////        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
////
////        // Back button
////        JButton btnBack = new JButton("← Back to Recipes");
////        styleNavigationButton(btnBack);
////        btnBack.addActionListener(e -> dispose());
////        headerPanel.add(btnBack, BorderLayout.WEST);
////
////        // Title
////        JLabel lblHeader = new JLabel("🍲 Recipe Details", JLabel.CENTER);
////        lblHeader.setFont(new Font("Serif", Font.BOLD, 24));
////        lblHeader.setForeground(new Color(46, 46, 46));
////        headerPanel.add(lblHeader, BorderLayout.CENTER);
////
////        add(headerPanel, BorderLayout.NORTH);
////
////        // --- MAIN CONTENT PANEL ---
////        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
////        mainPanel.setBackground(new Color(250, 250, 250));
////        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
////
////        // Recipe Info Panel
////        JPanel infoPanel = createInfoPanel();
////        mainPanel.add(infoPanel, BorderLayout.NORTH);
////
////        // Ingredients & Instructions Panel
////        JPanel contentPanel = createContentPanel();
////        mainPanel.add(contentPanel, BorderLayout.CENTER);
////
////        JScrollPane scrollPane = new JScrollPane(mainPanel);
////        scrollPane.setBorder(null);
////        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
////        add(scrollPane, BorderLayout.CENTER);
////
////        // --- BOTTOM PANEL ---
////        JPanel bottomPanel = new JPanel(new FlowLayout());
////        bottomPanel.setBackground(new Color(232, 245, 233));
////        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
////
////        JButton btnViewFeedback = new JButton("💬 View Feedback");
////        styleActionButton(btnViewFeedback);
////        btnViewFeedback.addActionListener(e -> openFeedbackFrame());
////
////        JButton btnAddFeedback = new JButton("⭐ Add Feedback");
////        styleActionButton(btnAddFeedback);
////        btnAddFeedback.addActionListener(e -> openAddFeedback());
////
////        JButton btnClose = new JButton("Close");
////        styleActionButton(btnClose);
////        btnClose.addActionListener(e -> dispose());
////
////        bottomPanel.add(btnViewFeedback);
////        bottomPanel.add(btnAddFeedback);
////        bottomPanel.add(btnClose);
////
////        add(bottomPanel, BorderLayout.SOUTH);
////
////        setVisible(true);
////    }
////
////    private JPanel createInfoPanel() {
////        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 10, 5));
////        infoPanel.setBackground(new Color(245, 245, 245));
////        infoPanel.setBorder(BorderFactory.createCompoundBorder(
////            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
////            BorderFactory.createEmptyBorder(15, 15, 15, 15)
////        ));
////
////        JLabel lblName = new JLabel();
////        lblName.setFont(new Font("Serif", Font.BOLD, 22));
////        lblName.setForeground(new Color(46, 46, 46));
////        infoPanel.add(lblName);
////
////        JLabel lblType = new JLabel();
////        lblType.setFont(new Font("SansSerif", Font.PLAIN, 16));
////        lblType.setForeground(new Color(70, 70, 70));
////        infoPanel.add(lblType);
////
////        JLabel lblUser = new JLabel();
////        lblUser.setFont(new Font("SansSerif", Font.ITALIC, 14));
////        lblUser.setForeground(new Color(100, 100, 100));
////        infoPanel.add(lblUser);
////
////        return infoPanel;
////    }
////
////    private JPanel createContentPanel() {
////        JPanel contentPanel = new JPanel(new GridLayout(2, 1, 15, 15));
////        contentPanel.setBackground(new Color(250, 250, 250));
////
////        // Ingredients Panel
////        JPanel ingredientsPanel = new JPanel(new BorderLayout());
////        ingredientsPanel.setBackground(Color.WHITE);
////        ingredientsPanel.setBorder(BorderFactory.createCompoundBorder(
////            BorderFactory.createTitledBorder(
////                BorderFactory.createLineBorder(new Color(76, 175, 80), 2),
////                "📝 Ingredients"
////            ),
////            BorderFactory.createEmptyBorder(10, 10, 10, 10)
////        ));
////
////        JTextArea txtIngredients = new JTextArea();
////        txtIngredients.setEditable(false);
////        txtIngredients.setLineWrap(true);
////        txtIngredients.setWrapStyleWord(true);
////        txtIngredients.setFont(new Font("SansSerif", Font.PLAIN, 14));
////        txtIngredients.setBackground(new Color(252, 252, 245));
////        txtIngredients.setMargin(new Insets(10, 10, 10, 10));
////
////        JScrollPane ingredientsScroll = new JScrollPane(txtIngredients);
////        ingredientsScroll.setBorder(null);
////        ingredientsPanel.add(ingredientsScroll, BorderLayout.CENTER);
////
////        // Instructions Panel
////        JPanel instructionsPanel = new JPanel(new BorderLayout());
////        instructionsPanel.setBackground(Color.WHITE);
////        instructionsPanel.setBorder(BorderFactory.createCompoundBorder(
////            BorderFactory.createTitledBorder(
////                BorderFactory.createLineBorder(new Color(76, 175, 80), 2),
////                "👨‍🍳 Instructions"
////            ),
////            BorderFactory.createEmptyBorder(10, 10, 10, 10)
////        ));
////
////        JTextArea txtInstructions = new JTextArea();
////        txtInstructions.setEditable(false);
////        txtInstructions.setLineWrap(true);
////        txtInstructions.setWrapStyleWord(true);
////        txtInstructions.setFont(new Font("SansSerif", Font.PLAIN, 14));
////        txtInstructions.setBackground(new Color(252, 252, 245));
////        txtInstructions.setMargin(new Insets(10, 10, 10, 10));
////
////        JScrollPane instructionsScroll = new JScrollPane(txtInstructions);
////        instructionsScroll.setBorder(null);
////        instructionsPanel.add(instructionsScroll, BorderLayout.CENTER);
////
////        contentPanel.add(ingredientsPanel);
////        contentPanel.add(instructionsPanel);
////
////        return contentPanel;
////    }
////
////    private void styleNavigationButton(JButton button) {
////        button.setBackground(new Color(76, 175, 80));
////        button.setForeground(Color.WHITE);
////        button.setFont(new Font("SansSerif", Font.BOLD, 13));
////        button.setFocusPainted(false);
////        button.setBorder(BorderFactory.createCompoundBorder(
////            BorderFactory.createLineBorder(new Color(56, 142, 60), 2),
////            BorderFactory.createEmptyBorder(8, 15, 8, 15)
////        ));
////        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
////
////        button.addMouseListener(new MouseAdapter() {
////            @Override
////            public void mouseEntered(MouseEvent evt) {
////                button.setBackground(new Color(56, 142, 60));
////            }
////
////            @Override
////            public void mouseExited(MouseEvent evt) {
////                button.setBackground(new Color(76, 175, 80));
////            }
////        });
////    }
////
////    private void styleActionButton(JButton button) {
////        button.setBackground(new Color(245, 245, 220));
////        button.setForeground(new Color(46, 46, 46));
////        button.setFont(new Font("SansSerif", Font.BOLD, 14));
////        button.setFocusPainted(false);
////        button.setBorder(BorderFactory.createCompoundBorder(
////            BorderFactory.createLineBorder(new Color(76, 175, 80), 2),
////            BorderFactory.createEmptyBorder(10, 20, 10, 20)
////        ));
////        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
////
////        button.addMouseListener(new MouseAdapter() {
////            @Override
////            public void mouseEntered(MouseEvent evt) {
////                button.setBackground(new Color(76, 175, 80));
////                button.setForeground(Color.WHITE);
////            }
////
////            @Override
////            public void mouseExited(MouseEvent evt) {
////                button.setBackground(new Color(245, 245, 220));
////                button.setForeground(new Color(46, 46, 46));
////            }
////        });
////    }
////
////    private void loadRecipeDetails() {
////        try (Connection conn = DriverManager.getConnection(
////                "jdbc:mysql://localhost:3306/recipe_db", "root", "99952")) {
////
////            String sql = """
////                SELECT r.name, r.type, r.ingredients, r.instructions, u.username 
////                FROM recipes r 
////                JOIN users u ON r.user_id = u.id 
////                WHERE r.recipe_id = ?
////            """;
////
////            PreparedStatement stmt = conn.prepareStatement(sql);
////            stmt.setInt(1, recipeId);
////            ResultSet rs = stmt.executeQuery();
////
////            if (rs.next()) {
////                recipeName = rs.getString("name");
////                
////                // Update UI components
////                JPanel infoPanel = (JPanel) ((JScrollPane) getContentPane().getComponent(1))
////                    .getViewport().getComponent(0).getComponent(0);
////                
////                JLabel lblName = (JLabel) infoPanel.getComponent(0);
////                JLabel lblType = (JLabel) infoPanel.getComponent(1);
////                JLabel lblUser = (JLabel) infoPanel.getComponent(2);
////                
////                lblName.setText("🍽 " + recipeName);
////                lblType.setText("📂 Type: " + rs.getString("type"));
////                lblUser.setText("👤 Created by: " + rs.getString("username"));
////
////                // Get content panel
////                JPanel contentPanel = (JPanel) ((JScrollPane) getContentPane().getComponent(1))
////                    .getViewport().getComponent(0).getComponent(1);
////                
////                // Update ingredients
////                JPanel ingredientsPanel = (JPanel) contentPanel.getComponent(0);
////                JScrollPane ingredientsScroll = (JScrollPane) ingredientsPanel.getComponent(0);
////                JTextArea txtIngredients = (JTextArea) ingredientsScroll.getViewport().getComponent(0);
////                txtIngredients.setText(formatText(rs.getString("ingredients")));
////
////                // Update instructions
////                JPanel instructionsPanel = (JPanel) contentPanel.getComponent(1);
////                JScrollPane instructionsScroll = (JScrollPane) instructionsPanel.getComponent(0);
////                JTextArea txtInstructions = (JTextArea) instructionsScroll.getViewport().getComponent(0);
////                txtInstructions.setText(formatText(rs.getString("instructions")));
////
////                setTitle("Recipe Details - " + recipeName);
////
////            } else {
////                JOptionPane.showMessageDialog(this, 
////                    "Recipe not found!", "Error", JOptionPane.ERROR_MESSAGE);
////                dispose();
////            }
////
////        } catch (SQLException ex) {
////            ex.printStackTrace();
////            JOptionPane.showMessageDialog(this,
////                "Database Error: " + ex.getMessage() + 
////                "\nPlease check your database connection.",
////                "Database Error", JOptionPane.ERROR_MESSAGE);
////        } catch (Exception ex) {
////            ex.printStackTrace();
////            JOptionPane.showMessageDialog(this,
////                "Error loading recipe details: " + ex.getMessage(),
////                "Error", JOptionPane.ERROR_MESSAGE);
////        }
////    }
////
////    private String formatText(String text) {
////        if (text == null || text.trim().isEmpty()) {
////            return "No information available.";
////        }
////        return text.replace("\\n", "\n").trim();
////    }
////
////    private void openFeedbackFrame() {
////        new ViewFeedbackFrame(recipeId, "1").setVisible(true); // Replace "1" with actual userId
////    }
////
////    private void openAddFeedback() {
////        new FeedbackFrame(recipeId, "1").setVisible(true); // Replace "1" with actual userId
////    }
////
////    public static void main(String[] args) {
////        SwingUtilities.invokeLater(() -> new RecipeDetailsFrame(1));
////    }
////}
//
//
//import javax.swing.*;
//import java.awt.*;
//import java.sql.*;
//
//public class RecipeDetailsFrame extends JFrame {
//
//    private int recipeId;
//
//    public RecipeDetailsFrame(int recipeId) {
//        this.recipeId = recipeId;
//
//        setTitle("Recipe Details");
//        setSize(600, 500);
//        setLocationRelativeTo(null);
//        setLayout(new BorderLayout(10, 10));
//        getContentPane().setBackground(new Color(250, 250, 250));
//
//        // ---------- Fetch recipe details ----------
//        String name = "";
//        String type = "";
//        String ingredients = "";
//        String instructions = "";
//
//        try (Connection conn = DriverManager.getConnection(
//                "jdbc:mysql://localhost:3306/recipe_db", "root", "99952")) {
//
//            String sql = "SELECT name, type, ingredients, instructions FROM recipes WHERE recipe_id = ?";
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setInt(1, recipeId);
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                name = rs.getString("name");
//                type = rs.getString("type");
//                ingredients = rs.getString("ingredients");
//                instructions = rs.getString("instructions");
//            } else {
//                JOptionPane.showMessageDialog(this, "Recipe not found!");
//                dispose();
//                return;
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
//            dispose();
//            return;
//        }
//
//        // ---------- Display ----------
//        JPanel mainPanel = new JPanel();
//        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
//        mainPanel.setBackground(new Color(250, 250, 250));
//        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//
//        JLabel lblName = new JLabel("🍽 Recipe: " + name);
//        lblName.setFont(new Font("Serif", Font.BOLD, 22));
//        lblName.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//        JLabel lblType = new JLabel("Type: " + type);
//        lblType.setFont(new Font("SansSerif", Font.PLAIN, 16));
//        lblType.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//        JTextArea taIngredients = new JTextArea("Ingredients:\n" + ingredients);
//        taIngredients.setFont(new Font("SansSerif", Font.PLAIN, 14));
//        taIngredients.setEditable(false);
//        taIngredients.setLineWrap(true);
//        taIngredients.setWrapStyleWord(true);
//        taIngredients.setBackground(new Color(255, 245, 230));
//        taIngredients.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//        JTextArea taInstructions = new JTextArea("Instructions:\n" + instructions);
//        taInstructions.setFont(new Font("SansSerif", Font.PLAIN, 14));
//        taInstructions.setEditable(false);
//        taInstructions.setLineWrap(true);
//        taInstructions.setWrapStyleWord(true);
//        taInstructions.setBackground(new Color(255, 245, 230));
//        taInstructions.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//        mainPanel.add(lblName);
//        mainPanel.add(Box.createVerticalStrut(10));
//        mainPanel.add(lblType);
//        mainPanel.add(Box.createVerticalStrut(15));
//        mainPanel.add(taIngredients);
//        mainPanel.add(Box.createVerticalStrut(15));
//        mainPanel.add(taInstructions);
//
//        JScrollPane scrollPane = new JScrollPane(mainPanel);
//        scrollPane.setBorder(BorderFactory.createEmptyBorder());
//        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
//
//        add(scrollPane, BorderLayout.CENTER);
//
//        setVisible(true);
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new RecipeDetailsFrame(1)); // Test with recipe_id 1
//    }
//}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RecipeDetailsFrame extends JFrame {

    private int recipeId;

    public RecipeDetailsFrame(int recipeId) {
        this.recipeId = recipeId;

        setTitle("Recipe Details");
        setSize(600, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(250, 250, 250));

        // ---------- Fetch recipe details ----------
        String name = "";
        String type = "";
        String ingredients = "";
        String instructions = "";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/recipe_db", "root", "99952")) {

            String sql = "SELECT name, type, ingredients, instructions FROM recipes WHERE recipe_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, recipeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                name = rs.getString("name");
                type = rs.getString("type");
                ingredients = rs.getString("ingredients");
                instructions = rs.getString("instructions");
            } else {
                JOptionPane.showMessageDialog(this, "Recipe not found!");
                dispose();
                return;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
            dispose();
            return;
        }

        // ---------- Display ----------
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(250, 250, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblName = new JLabel("🍽 Recipe: " + name);
        lblName.setFont(new Font("Serif", Font.BOLD, 22));
        lblName.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblType = new JLabel("Type: " + type);
        lblType.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblType.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea taIngredients = new JTextArea("Ingredients:\n" + ingredients);
        taIngredients.setFont(new Font("SansSerif", Font.PLAIN, 14));
        taIngredients.setEditable(false);
        taIngredients.setLineWrap(true);
        taIngredients.setWrapStyleWord(true);
        taIngredients.setBackground(new Color(255, 245, 230));
        taIngredients.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea taInstructions = new JTextArea("Instructions:\n" + instructions);
        taInstructions.setFont(new Font("SansSerif", Font.PLAIN, 14));
        taInstructions.setEditable(false);
        taInstructions.setLineWrap(true);
        taInstructions.setWrapStyleWord(true);
        taInstructions.setBackground(new Color(255, 245, 230));
        taInstructions.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel.add(lblName);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(lblType);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(taIngredients);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(taInstructions);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        // ---------- BOTTOM PANEL WITH BUTTONS ----------
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottomPanel.setBackground(new Color(232, 245, 233));

        JButton btnAddFeedback = new JButton("⭐ Add Feedback");
        JButton btnViewFeedback = new JButton("💬 View Feedback");
        JButton btnClose = new JButton("Close");

        styleButton(btnAddFeedback, new Color(76, 175, 80));
        styleButton(btnViewFeedback, new Color(33, 150, 243));
        styleButton(btnClose, new Color(244, 67, 54));

        // Button actions
        btnAddFeedback.addActionListener(e -> {
            // Replace with actual feedback frame
            new FeedbackFrame(recipeId, "1").setVisible(true);
        });

        btnViewFeedback.addActionListener(e -> {
            // Replace with actual view feedback frame
            new ViewFeedbackFrame(recipeId, "1").setVisible(true);
        });

        btnClose.addActionListener(e -> dispose());

        bottomPanel.add(btnAddFeedback);
        bottomPanel.add(btnViewFeedback);
        bottomPanel.add(btnClose);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecipeDetailsFrame(1));
    }
}
