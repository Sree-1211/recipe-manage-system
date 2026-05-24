import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class RecipeSearchFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JComboBox<String> comboType;
    private JButton btnSearch;
    private JPanel recipeContainer;
    private String userId;

    public RecipeSearchFrame(String userId) {
        this.userId = userId;

        // ---------- FRAME SETTINGS ----------
        setTitle("Search Recipes with Ratings");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(250, 250, 250));

        // ---------- TOP PANEL ----------
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(232, 245, 233));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Back Button
        JButton btnBack = new JButton("⬅ Back");
        styleButton(btnBack);
        btnBack.setPreferredSize(new Dimension(100, 35));
        btnBack.addActionListener(_ -> {
            dispose();
            new DashboardFrame(userId).setVisible(true);
        });
        topPanel.add(btnBack, BorderLayout.WEST);

        // Title
        JLabel lblTitle = new JLabel("🔍 Search Recipes with Ratings", JLabel.CENTER);
        lblTitle.setFont(new Font("Serif", Font.BOLD, 22));
        lblTitle.setForeground(new Color(46, 46, 46));
        topPanel.add(lblTitle, BorderLayout.CENTER);

        // Filter Panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        filterPanel.setBackground(new Color(250, 250, 250));
        filterPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JLabel lblFilter = new JLabel("Select Type:");
        lblFilter.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblFilter.setForeground(new Color(46, 46, 46));
        filterPanel.add(lblFilter);

        String[] recipeTypes = {"All", "Main Course", "Side Dish", "Dessert", "Snack", "Beverage"};
        comboType = new JComboBox<>(recipeTypes);
        comboType.setFont(new Font("SansSerif", Font.PLAIN, 13));
        comboType.setBackground(new Color(245, 245, 220));
        comboType.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 2));
        filterPanel.add(comboType);

        btnSearch = new JButton("Search");
        styleButton(btnSearch);
        btnSearch.setPreferredSize(new Dimension(120, 35));
        filterPanel.add(btnSearch);

        topPanel.add(filterPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        // ---------- RECIPE CONTAINER ----------
        recipeContainer = new JPanel();
        recipeContainer.setLayout(new WrapLayout(FlowLayout.LEFT, 15, 15));
        recipeContainer.setBackground(new Color(250, 250, 250));

        JScrollPane scrollPane = new JScrollPane(recipeContainer);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        // ---------- ACTION ----------
        btnSearch.addActionListener(_ -> searchRecipes());

        setVisible(true);
    }

    // ---------- BUTTON STYLE ----------
    private void styleButton(JButton button) {
        Color mainGreen = new Color(76, 175, 80);
        Color beige = new Color(245, 245, 220);

        button.setBackground(beige);
        button.setForeground(mainGreen);
        button.setFont(new Font("SansSerif", Font.BOLD, 15));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(mainGreen, 2));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(mainGreen);
                button.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(beige);
                button.setForeground(mainGreen);
            }
        });
    }

    // ---------- SEARCH RECIPES WITH RATINGS ----------
    private void searchRecipes() {
        recipeContainer.removeAll();
        String selectedType = (String) comboType.getSelectedItem();

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/recipe_db", "root", "99952")) {

            String sql;
            PreparedStatement stmt;
            
            // SQL to get recipes with average ratings
            if (selectedType.equals("All")) {
                sql = "SELECT r.recipe_id, r.name, r.type, u.username, " +
                      "COALESCE(AVG(f.rating), 0) as avg_rating, " +
                      "COUNT(f.feedback_id) as review_count " +
                      "FROM recipes r " +
                      "JOIN users u ON r.user_id = u.id " +
                      "LEFT JOIN feedback f ON r.recipe_id = f.recipe_id " +
                      "GROUP BY r.recipe_id, r.name, r.type, u.username " +
                      "ORDER BY avg_rating DESC";
                stmt = conn.prepareStatement(sql);
            } else {
                sql = "SELECT r.recipe_id, r.name, r.type, u.username, " +
                      "COALESCE(AVG(f.rating), 0) as avg_rating, " +
                      "COUNT(f.feedback_id) as review_count " +
                      "FROM recipes r " +
                      "JOIN users u ON r.user_id = u.id " +
                      "LEFT JOIN feedback f ON r.recipe_id = f.recipe_id " +
                      "WHERE r.type = ? " +
                      "GROUP BY r.recipe_id, r.name, r.type, u.username " +
                      "ORDER BY avg_rating DESC";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, selectedType);
            }

            ResultSet rs = stmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                int recipeId = rs.getInt("recipe_id");
                String name = rs.getString("name");
                String type = rs.getString("type");
                String createdBy = rs.getString("username");
                double avgRating = rs.getDouble("avg_rating");
                int reviewCount = rs.getInt("review_count");

                recipeContainer.add(createRecipeCard(recipeId, name, type, createdBy, avgRating, reviewCount));
            }

            if (!found) {
                JLabel noLabel = new JLabel("No recipes found for this type.");
                noLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
                noLabel.setForeground(new Color(90, 90, 90));
                recipeContainer.add(noLabel);
            }

            recipeContainer.revalidate();
            recipeContainer.repaint();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    // ---------- CARD CREATION WITH RATINGS ----------
    private JPanel createRecipeCard(int recipeId, String name, String type, String createdBy, 
                                   double avgRating, int reviewCount) {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(220, 180));
        card.setLayout(new BorderLayout());
        card.setBackground(new Color(245, 245, 220));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(76, 175, 80), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(245, 245, 220));

        JLabel lblName = new JLabel("<html><b>" + name + "</b></html>", JLabel.CENTER);
        lblName.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblName.setForeground(new Color(46, 46, 46));

        JLabel lblType = new JLabel("Type: " + type, JLabel.CENTER);
        lblType.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblType.setForeground(new Color(70, 70, 70));

        JLabel lblUser = new JLabel("By: " + createdBy, JLabel.CENTER);
        lblUser.setFont(new Font("SansSerif", Font.ITALIC, 11));
        lblUser.setForeground(new Color(90, 90, 90));

        // Rating panel
        JPanel ratingPanel = new JPanel(new GridLayout(2, 1));
        ratingPanel.setBackground(new Color(245, 245, 220));
        ratingPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        // Star rating
        String stars = getStarRating(avgRating);
        // FIXED: Avoid boxing warning
        String ratingText = String.format("%.1f", Double.valueOf(avgRating));
        JLabel lblRating = new JLabel("<html><font color='#FF9529'>" + stars + "</font> (" + ratingText + ")</html>", JLabel.CENTER);
        lblRating.setFont(new Font("SansSerif", Font.PLAIN, 11));

        // Review count
        JLabel lblReviews = new JLabel(reviewCount + " reviews", JLabel.CENTER);
        lblReviews.setFont(new Font("SansSerif", Font.PLAIN, 10));
        lblReviews.setForeground(new Color(120, 120, 120));

        ratingPanel.add(lblRating);
        ratingPanel.add(lblReviews);

        contentPanel.add(lblName, BorderLayout.NORTH);
        contentPanel.add(lblType, BorderLayout.CENTER);
        contentPanel.add(lblUser, BorderLayout.SOUTH);

        card.add(contentPanel, BorderLayout.CENTER);
        card.add(ratingPanel, BorderLayout.SOUTH);

        // Hover + Click
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(232, 245, 233));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(new Color(245, 245, 220));
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    new RecipeDetailsFrame(recipeId);
                }
            }
        });

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

    // ---------- MAIN FOR TEST ----------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecipeSearchFrame("1"));
    }
}