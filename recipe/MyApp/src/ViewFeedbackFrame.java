//import javax.swing.*;
//import java.awt.*;
//import java.sql.*;
//
//public class ViewFeedbackFrame extends JFrame {
//
//    private static final long serialVersionUID = 1L;
//    private int recipeId;
//    private JPanel feedbackPanel;
//
//    public ViewFeedbackFrame(int recipeId) {
//        this.recipeId = recipeId;
//
//        setTitle("Recipe Reviews & Ratings");
//        setSize(700, 600);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setLayout(new BorderLayout(10, 10));
//        getContentPane().setBackground(new Color(250, 250, 250));
//
//        // ---------- TOP PANEL ----------
//        JPanel topPanel = new JPanel(new BorderLayout());
//        topPanel.setBackground(new Color(232, 245, 233));
//        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
//
//        JButton btnBack = new JButton("← Back to Recipes");
//        btnBack.setBackground(new Color(76, 175, 80));
//        btnBack.setForeground(Color.WHITE);
//        btnBack.setFont(new Font("SansSerif", Font.BOLD, 13));
//        btnBack.setFocusPainted(false);
//        btnBack.setBorder(BorderFactory.createCompoundBorder(
//            BorderFactory.createLineBorder(new Color(56, 142, 60), 2),
//            BorderFactory.createEmptyBorder(8, 15, 8, 15)
//        ));
//        btnBack.addActionListener(_ -> {
//        	dispose();
//        	new RecipeSearchFrame(currentUserId).setVisible(true);
//        	});
//        topPanel.add(btnBack, BorderLayout.WEST);
//
//        JLabel lblTitle = new JLabel("⭐ Recipe Reviews", JLabel.CENTER);
//        lblTitle.setFont(new Font("Serif", Font.BOLD, 22));
//        lblTitle.setForeground(new Color(46, 46, 46));
//        topPanel.add(lblTitle, BorderLayout.CENTER);
//
//        add(topPanel, BorderLayout.NORTH);
//
//        // ---------- FEEDBACK PANEL ----------
//        feedbackPanel = new JPanel();
//        feedbackPanel.setLayout(new BoxLayout(feedbackPanel, BoxLayout.Y_AXIS));
//        feedbackPanel.setBackground(new Color(250, 250, 250));
//        feedbackPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
//
//        JScrollPane scrollPane = new JScrollPane(feedbackPanel);
//        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
//        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
//        add(scrollPane, BorderLayout.CENTER);
//
//        loadFeedback();
//
//        setVisible(true);
//    }
//
//    private void loadFeedback() {
//        feedbackPanel.removeAll();
//
//        try (Connection conn = DriverManager.getConnection(
//                "jdbc:mysql://localhost:3306/recipe_db", "root", "99952")) {
//
//            // Get average rating and total reviews
//            String statsSql = "SELECT AVG(rating) as avg_rating, COUNT(*) as total_reviews FROM feedback WHERE recipe_id = ?";
//            PreparedStatement statsStmt = conn.prepareStatement(statsSql);
//            statsStmt.setInt(1, recipeId);
//            // FIXED: Changed from 'statsStatsStmt' to 'statsStmt'
//            ResultSet statsRs = statsStmt.executeQuery();
//
//            // Add statistics panel
//            JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
//            statsPanel.setBackground(new Color(255, 248, 225));
//            statsPanel.setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createLineBorder(new Color(255, 152, 0), 2),
//                BorderFactory.createEmptyBorder(15, 20, 15, 20)
//            ));
//            statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
//
//            if (statsRs.next()) {
//                double avgRating = statsRs.getDouble("avg_rating");
//                int totalReviews = statsRs.getInt("total_reviews");
//                
//                if (!statsRs.wasNull()) {
//                    String stars = "★".repeat((int) avgRating) + "☆".repeat(5 - (int) avgRating);
//                    JLabel lblStats = new JLabel("<html><center><b>Overall Rating: " + String.format("%.1f", avgRating) + "/5</b><br>" +
//                                               stars + "<br>" + totalReviews + " reviews</center></html>");
//                    lblStats.setFont(new Font("SansSerif", Font.BOLD, 14));
//                    lblStats.setForeground(new Color(46, 46, 46));
//                    statsPanel.add(lblStats);
//                }
//            }
//
//            feedbackPanel.add(statsPanel);
//            feedbackPanel.add(Box.createVerticalStrut(15));
//
//            // Get individual feedback
//            String feedbackSql = "SELECT f.comment, f.rating, f.created_at, u.username " +
//                                "FROM feedback f " +
//                                "JOIN users u ON f.user_id = u.id " +
//                                "WHERE f.recipe_id = ? " +
//                                "ORDER BY f.created_at DESC";
//            PreparedStatement feedbackStmt = conn.prepareStatement(feedbackSql);
//            feedbackStmt.setInt(1, recipeId);
//            ResultSet feedbackRs = feedbackStmt.executeQuery();
//
//            boolean hasFeedback = false;
//            while (feedbackRs.next()) {
//                hasFeedback = true;
//                String comment = feedbackRs.getString("comment");
//                int rating = feedbackRs.getInt("rating");
//                String username = feedbackRs.getString("username");
//                String createdAt = feedbackRs.getTimestamp("created_at").toString();
//
//                feedbackPanel.add(createFeedbackCard(username, rating, comment, createdAt));
//                feedbackPanel.add(Box.createVerticalStrut(10));
//            }
//
//            if (!hasFeedback) {
//                JPanel noFeedbackPanel = new JPanel(new BorderLayout());
//                noFeedbackPanel.setBackground(Color.WHITE);
//                noFeedbackPanel.setBorder(BorderFactory.createCompoundBorder(
//                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
//                    BorderFactory.createEmptyBorder(30, 20, 30, 20)
//                ));
//                
//                JLabel noFeedback = new JLabel("📝 No reviews yet for this recipe.", JLabel.CENTER);
//                noFeedback.setFont(new Font("SansSerif", Font.ITALIC, 16));
//                noFeedback.setForeground(new Color(120, 120, 120));
//                noFeedbackPanel.add(noFeedback, BorderLayout.CENTER);
//                
//                feedbackPanel.add(noFeedbackPanel);
//            }
//
//            feedbackPanel.revalidate();
//            feedbackPanel.repaint();
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
//        }
//    }
//
//    private JPanel createFeedbackCard(String username, int rating, String comment, String date) {
//        JPanel card = new JPanel(new BorderLayout());
//        card.setBackground(new Color(255, 255, 255));
//        card.setBorder(BorderFactory.createCompoundBorder(
//            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
//            BorderFactory.createEmptyBorder(15, 20, 15, 20)
//        ));
//        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
//
//        // Header with username, rating, and date
//        JPanel headerPanel = new JPanel(new BorderLayout());
//        headerPanel.setBackground(Color.WHITE);
//        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
//
//        JLabel lblUser = new JLabel("👤 " + username);
//        lblUser.setFont(new Font("SansSerif", Font.BOLD, 13));
//        lblUser.setForeground(new Color(76, 175, 80));
//
//        String stars = "★".repeat(rating) + "☆".repeat(5 - rating);
//        JLabel lblRating = new JLabel("<html><font color='#FF9529' size='4'>" + stars + "</font></html>");
//        lblRating.setFont(new Font("SansSerif", Font.BOLD, 12));
//
//        String formattedDate = date.substring(0, 16).replace(" ", " at ");
//        JLabel lblDate = new JLabel(formattedDate);
//        lblDate.setFont(new Font("SansSerif", Font.ITALIC, 10));
//        lblDate.setForeground(new Color(120, 120, 120));
//
//        headerPanel.add(lblUser, BorderLayout.WEST);
//        headerPanel.add(lblRating, BorderLayout.CENTER);
//        headerPanel.add(lblDate, BorderLayout.EAST);
//
//        // Comment
//        JTextArea txtComment = new JTextArea(comment);
//        txtComment.setLineWrap(true);
//        txtComment.setWrapStyleWord(true);
//        txtComment.setEditable(false);
//        txtComment.setBackground(Color.WHITE);
//        txtComment.setFont(new Font("SansSerif", Font.PLAIN, 13));
//        txtComment.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
//        txtComment.setForeground(new Color(60, 60, 60));
//
//        card.add(headerPanel, BorderLayout.NORTH);
//        card.add(txtComment, BorderLayout.CENTER);
//
//        return card;
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new ViewFeedbackFrame(1));
//    }
//}


import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ViewFeedbackFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private int recipeId;
    private String userId; // ADD THIS FIELD
    private JPanel feedbackPanel;

    // UPDATE CONSTRUCTOR TO ACCEPT USER ID
    public ViewFeedbackFrame(int recipeId, String userId) {
        this.recipeId = recipeId;
        this.userId = userId; // STORE THE USER ID

        setTitle("Recipe Reviews & Ratings");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(250, 250, 250));

        // ---------- TOP PANEL ----------
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(232, 245, 233));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JButton btnBack = new JButton("← Back to Recipes");
        btnBack.setBackground(new Color(76, 175, 80));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnBack.setFocusPainted(false);
        btnBack.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(56, 142, 60), 2),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        
        // FIXED: Pass the userId to RecipeSearchFrame
        btnBack.addActionListener(_ -> {
            dispose();
            //new RecipeSearchFrame(userId).setVisible(true); // USE THE STORED USER ID
        });
        
        topPanel.add(btnBack, BorderLayout.WEST);

        JLabel lblTitle = new JLabel("⭐ Recipe Reviews", JLabel.CENTER);
        lblTitle.setFont(new Font("Serif", Font.BOLD, 22));
        lblTitle.setForeground(new Color(46, 46, 46));
        topPanel.add(lblTitle, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // ---------- FEEDBACK PANEL ----------
        feedbackPanel = new JPanel();
        feedbackPanel.setLayout(new BoxLayout(feedbackPanel, BoxLayout.Y_AXIS));
        feedbackPanel.setBackground(new Color(250, 250, 250));
        feedbackPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JScrollPane scrollPane = new JScrollPane(feedbackPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        loadFeedback();

        setVisible(true);
    }

    private void loadFeedback() {
        feedbackPanel.removeAll();

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/recipe_db", "root", "99952")) {

            // Get average rating and total reviews
            String statsSql = "SELECT AVG(rating) as avg_rating, COUNT(*) as total_reviews FROM feedback WHERE recipe_id = ?";
            PreparedStatement statsStmt = conn.prepareStatement(statsSql);
            statsStmt.setInt(1, recipeId);
            ResultSet statsRs = statsStmt.executeQuery();

            // Add statistics panel
            JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            statsPanel.setBackground(new Color(255, 248, 225));
            statsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 152, 0), 2),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
            ));
            statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

            if (statsRs.next()) {
                double avgRating = statsRs.getDouble("avg_rating");
                int totalReviews = statsRs.getInt("total_reviews");
                
                if (!statsRs.wasNull() && totalReviews > 0) { // ADDED CHECK FOR totalReviews > 0
                    String stars = "★".repeat((int) avgRating) + "☆".repeat(5 - (int) avgRating);
                    JLabel lblStats = new JLabel("<html><center><b>Overall Rating: " + String.format("%.1f", avgRating) + "/5</b><br>" +
                                               stars + "<br>" + totalReviews + " reviews</center></html>");
                    lblStats.setFont(new Font("SansSerif", Font.BOLD, 14));
                    lblStats.setForeground(new Color(46, 46, 46));
                    statsPanel.add(lblStats);
                } else {
                    // ADDED: Show message when no reviews
                    JLabel lblStats = new JLabel("<html><center><b>No Reviews Yet</b><br>Be the first to review this recipe!</center></html>");
                    lblStats.setFont(new Font("SansSerif", Font.BOLD, 14));
                    lblStats.setForeground(new Color(120, 120, 120));
                    statsPanel.add(lblStats);
                }
            }

            feedbackPanel.add(statsPanel);
            feedbackPanel.add(Box.createVerticalStrut(15));

            // Get individual feedback - TEMPORARY FIX: Remove created_at
            String feedbackSql = "SELECT f.comment, f.rating, u.username " + // REMOVED created_at
                                "FROM feedback f " +
                                "JOIN users u ON f.user_id = u.id " +
                                "WHERE f.recipe_id = ? " +
                                "ORDER BY f.feedback_id DESC"; // ORDER BY ID INSTEAD OF created_at
            
            PreparedStatement feedbackStmt = conn.prepareStatement(feedbackSql);
            feedbackStmt.setInt(1, recipeId);
            ResultSet feedbackRs = feedbackStmt.executeQuery();

            boolean hasFeedback = false;
            while (feedbackRs.next()) {
                hasFeedback = true;
                String comment = feedbackRs.getString("comment");
                int rating = feedbackRs.getInt("rating");
                String username = feedbackRs.getString("username");
                // USE CURRENT TIMESTAMP SINCE created_at MIGHT NOT EXIST
                String createdAt = new java.util.Date().toString();

                feedbackPanel.add(createFeedbackCard(username, rating, comment, createdAt));
                feedbackPanel.add(Box.createVerticalStrut(10));
            }

            if (!hasFeedback) {
                JPanel noFeedbackPanel = new JPanel(new BorderLayout());
                noFeedbackPanel.setBackground(Color.WHITE);
                noFeedbackPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    BorderFactory.createEmptyBorder(30, 20, 30, 20)
                ));
                
                JLabel noFeedback = new JLabel("📝 No reviews yet for this recipe.", JLabel.CENTER);
                noFeedback.setFont(new Font("SansSerif", Font.ITALIC, 16));
                noFeedback.setForeground(new Color(120, 120, 120));
                noFeedbackPanel.add(noFeedback, BorderLayout.CENTER);
                
                feedbackPanel.add(noFeedbackPanel);
            }

            feedbackPanel.revalidate();
            feedbackPanel.repaint();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    private JPanel createFeedbackCard(String username, int rating, String comment, String date) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(255, 255, 255));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        // Header with username, rating, and date
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        JLabel lblUser = new JLabel("👤 " + username);
        lblUser.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblUser.setForeground(new Color(76, 175, 80));

        String stars = "★".repeat(rating) + "☆".repeat(5 - rating);
        JLabel lblRating = new JLabel("<html><font color='#FF9529' size='4'>" + stars + "</font></html>");
        lblRating.setFont(new Font("SansSerif", Font.BOLD, 12));

        // FORMAT DATE PROPERLY (handle potential null or long strings)
        String formattedDate = (date != null && date.length() > 16) ? 
                              date.substring(0, 16).replace(" ", " at ") : date;
        JLabel lblDate = new JLabel(formattedDate != null ? formattedDate : "Recently");
        lblDate.setFont(new Font("SansSerif", Font.ITALIC, 10));
        lblDate.setForeground(new Color(120, 120, 120));

        headerPanel.add(lblUser, BorderLayout.WEST);
        headerPanel.add(lblRating, BorderLayout.CENTER);
        headerPanel.add(lblDate, BorderLayout.EAST);

        // Comment
        JTextArea txtComment = new JTextArea(comment);
        txtComment.setLineWrap(true);
        txtComment.setWrapStyleWord(true);
        txtComment.setEditable(false);
        txtComment.setBackground(Color.WHITE);
        txtComment.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtComment.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        txtComment.setForeground(new Color(60, 60, 60));

        card.add(headerPanel, BorderLayout.NORTH);
        card.add(txtComment, BorderLayout.CENTER);

        return card;
    }

    // UPDATE MAIN METHOD FOR TESTING
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewFeedbackFrame(1, "1")); // ADD USER ID PARAMETER
    }
}