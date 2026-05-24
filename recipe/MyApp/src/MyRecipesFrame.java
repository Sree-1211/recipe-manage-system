
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.sql.*;

public class MyRecipesFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private String userId;
    private JPanel recipesPanel;

    public MyRecipesFrame(String userId) {
        this.userId = userId;

        // ---------- FRAME SETTINGS ----------
        setTitle("My Recipes with Ratings");
        setSize(1000, 700); // Increased size for ratings
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(250, 250, 250));
        setLayout(new BorderLayout(10, 10));

        // ---------- TOP PANEL WITH BACK BUTTON ----------
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(232, 245, 233));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Back Button (Left)
        JButton btnBack = new JButton("← Back to Dashboard");
        btnBack.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnBack.setBackground(new Color(76, 175, 80));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.setPreferredSize(new Dimension(200, 40));
        
        btnBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                btnBack.setBackground(new Color(56, 142, 60));
            }
            
            @Override
            public void mouseExited(MouseEvent evt) {
                btnBack.setBackground(new Color(76, 175, 80));
            }
        });
        
        btnBack.addActionListener(_ -> {
            dispose();
            new DashboardFrame(userId).setVisible(true);
        });
        topPanel.add(btnBack, BorderLayout.WEST);

        // Header
        JLabel header = new JLabel("🍴 My Recipes with Ratings", JLabel.CENTER);
        header.setFont(new Font("Serif", Font.BOLD, 26));
        header.setForeground(new Color(46, 46, 46));
        topPanel.add(header, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // ---------- RECIPE LIST PANEL ----------
        recipesPanel = new JPanel();
        recipesPanel.setLayout(new BoxLayout(recipesPanel, BoxLayout.Y_AXIS));
        recipesPanel.setBackground(new Color(250, 250, 250));

        JScrollPane scrollPane = new JScrollPane(recipesPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Load recipes
        fetchUserRecipes();

        setVisible(true);
    }

    private void fetchUserRecipes() {
        recipesPanel.removeAll();

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/recipe_db", "root", "99952")) {

            // Enhanced SQL to include ratings
            String sql = "SELECT r.recipe_id, r.name, r.type, " +
                        "COALESCE(AVG(f.rating), 0) as avg_rating, " +
                        "COUNT(f.feedback_id) as review_count " +
                        "FROM recipes r " +
                        "LEFT JOIN feedback f ON r.recipe_id = f.recipe_id " +
                        "WHERE r.user_id = ? " +
                        "GROUP BY r.recipe_id, r.name, r.type " +
                        "ORDER BY r.recipe_id DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(userId));
            ResultSet rs = stmt.executeQuery();

            boolean hasRecipes = false;
            while (rs.next()) {
                hasRecipes = true;
                int recipeId = rs.getInt("recipe_id");
                String name = rs.getString("name");
                String type = rs.getString("type");
                double avgRating = rs.getDouble("avg_rating");
                int reviewCount = rs.getInt("review_count");

                recipesPanel.add(createRecipeCard(recipeId, name, type, avgRating, reviewCount));
                recipesPanel.add(Box.createVerticalStrut(10));
            }

            if (!hasRecipes) {
                JLabel noRecipes = new JLabel("No recipes found. Start adding your first recipe!", JLabel.CENTER);
                noRecipes.setFont(new Font("SansSerif", Font.ITALIC, 16));
                noRecipes.setForeground(new Color(46, 46, 46));
                recipesPanel.add(Box.createVerticalStrut(20));
                recipesPanel.add(noRecipes);
            }

            recipesPanel.revalidate();
            recipesPanel.repaint();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    private JPanel createRecipeCard(int recipeId, String name, String type, double avgRating, int reviewCount) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(new Color(204, 153, 102), 2, true));
        card.setBackground(new Color(255, 245, 230));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120)); // Increased height for ratings

        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(255, 245, 230));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Recipe info with ratings
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBackground(new Color(255, 245, 230));

        // Recipe name and type
        JLabel lblInfo = new JLabel("<html><b style='font-size:14px'>" + name + "</b><br>Type: " + type + "</html>");
        infoPanel.add(lblInfo);

        // Ratings display
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        ratingPanel.setBackground(new Color(255, 245, 230));

        String stars = getStarRating(avgRating);
        String ratingText = String.format("%.1f", avgRating); // FIXED: Removed Double.valueOf()
        
        JLabel lblRating = new JLabel("<html><font color='#FF9529'>" + stars + "</font> " + ratingText + "/5</html>");
        lblRating.setFont(new Font("SansSerif", Font.PLAIN, 11));
        
        JLabel lblReviews = new JLabel("(" + reviewCount + " reviews)");
        lblReviews.setFont(new Font("SansSerif", Font.PLAIN, 10));
        lblReviews.setForeground(new Color(120, 120, 120));

        ratingPanel.add(lblRating);
        ratingPanel.add(lblReviews);
        infoPanel.add(ratingPanel);

        contentPanel.add(infoPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 5));
        btnPanel.setBackground(new Color(255, 245, 230));

        // View Details Button
        JButton btnView = new JButton("View");
        styleButton(btnView, new Color(76, 175, 80));
        btnView.addActionListener(_ -> new RecipeDetailsFrame(recipeId).setVisible(true));

        // View Feedback Button - FIXED: Added userId parameter
        JButton btnFeedback = new JButton("view feedback");
        styleButton(btnFeedback, new Color(255, 152, 0));
        btnFeedback.addActionListener(_ -> new ViewFeedbackFrame(recipeId, userId).setVisible(true));

        // Edit Button
        JButton btnEdit = new JButton("Edit");
        styleButton(btnEdit, new Color(33, 150, 243));
        btnEdit.addActionListener(_ -> new EditRecipeFrame(recipeId, userId).setVisible(true));

        // Delete Button
        JButton btnDelete = new JButton("Delete");
        styleButton(btnDelete, new Color(244, 67, 54));
        btnDelete.addActionListener(_ -> deleteRecipe(recipeId));

        btnPanel.add(btnView);
        btnPanel.add(btnFeedback);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);

        contentPanel.add(btnPanel, BorderLayout.EAST);
        card.add(contentPanel, BorderLayout.CENTER);

        return card;
    }

    // ---------- HELPER METHOD FOR STAR RATING ----------
    private String getStarRating(double rating) {
        StringBuilder stars = new StringBuilder();
        int fullStars = (int) rating;
        boolean hasHalfStar = (rating - fullStars) >= 0.5;
        
        for (int i = 0; i < fullStars; i++) {
            stars.append("★");
        }
        if (hasHalfStar) {
            stars.append("½");
        }
        for (int i = stars.length(); i < 5; i++) {
            stars.append("☆");
        }
        return stars.toString();
    }

    // ---------- BUTTON STYLING ----------
    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 11));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(darkerColor(bgColor), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(darkerColor(bgColor));
            }
            
            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }

    private Color darkerColor(Color color) {
        return new Color(
            Math.max(color.getRed() - 30, 0),
            Math.max(color.getGreen() - 30, 0),
            Math.max(color.getBlue() - 30, 0)
        );
    }

    private void deleteRecipe(int recipeId) {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this recipe?\nThis will also delete all associated reviews.",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/recipe_db", "root", "99952")) {
                
                // Delete feedback first (foreign key constraint)
                try (PreparedStatement deleteFeedback = conn.prepareStatement("DELETE FROM feedback WHERE recipe_id = ?")) {
                    deleteFeedback.setInt(1, recipeId);
                    deleteFeedback.executeUpdate();
                }
                
                // Then delete recipe
                try (PreparedStatement deleteRecipe = conn.prepareStatement("DELETE FROM recipes WHERE recipe_id = ?")) {
                    deleteRecipe.setInt(1, recipeId);
                    int rows = deleteRecipe.executeUpdate();
                    
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(this, "Recipe and all associated reviews deleted successfully!");
                        fetchUserRecipes();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to delete recipe.");
                    }
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MyRecipesFrame("1"));
    }
}