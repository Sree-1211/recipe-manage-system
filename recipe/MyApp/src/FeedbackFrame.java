
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.sql.*;

public class FeedbackFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private final int recipeId;
    private final String userId;
    private JTextArea txtComment;
    private JComboBox<Integer> comboRating;
    private JButton btnSubmit;

    public FeedbackFrame(int recipeId, String userId) {
        this.recipeId = recipeId;
        this.userId = userId;

        setTitle("Add Feedback - Recipe Management System");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(250, 250, 250));

        // ---------- TOP PANEL ----------
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(232, 245, 233));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JButton btnBack = new JButton("← Back to Recipes");
        styleNavigationButton(btnBack);
        btnBack.setPreferredSize(new Dimension(150, 40));
        btnBack.addActionListener(e -> dispose());
        topPanel.add(btnBack, BorderLayout.WEST);

        JLabel lblTitle = new JLabel("💬 Recipe Feedback", JLabel.CENTER);
        lblTitle.setFont(new Font("Serif", Font.BOLD, 22));
        lblTitle.setForeground(new Color(46, 46, 46));
        topPanel.add(lblTitle, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // ---------- FEEDBACK FORM ----------
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(250, 250, 250));
        formPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Rating Section
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblRating = new JLabel("⭐ Rating:");
        lblRating.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblRating.setForeground(new Color(46, 46, 46));
        formPanel.add(lblRating, gbc);

        comboRating = new JComboBox<>(new Integer[]{5, 4, 3, 2, 1});
        comboRating.setFont(new Font("SansSerif", Font.BOLD, 14));
        comboRating.setBackground(new Color(255, 248, 225)); // light yellow
        comboRating.setForeground(new Color(255, 152, 0)); // orange text
        comboRating.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 152, 0), 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        comboRating.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel c = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Integer val) {
                    c.setText("★".repeat(val) + " (" + val + " stars)");
                }
                return c;
            }
        });
        gbc.gridx = 1;
        formPanel.add(comboRating, gbc);

        // Comment Section
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblComment = new JLabel("📝 Comment:");
        lblComment.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblComment.setForeground(new Color(46, 46, 46));
        formPanel.add(lblComment, gbc);

        txtComment = new JTextArea(5, 25);
        txtComment.setLineWrap(true);
        txtComment.setWrapStyleWord(true);
        txtComment.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtComment.setBackground(new Color(255, 253, 245)); // very light yellow
        txtComment.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(76, 175, 80), 2),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        txtComment.setText("Share your thoughts about this recipe...");
        txtComment.setForeground(new Color(150, 150, 150));

        txtComment.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtComment.getText().equals("Share your thoughts about this recipe...")) {
                    txtComment.setText("");
                    txtComment.setForeground(new Color(46, 46, 46));
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtComment.getText().isEmpty()) {
                    txtComment.setText("Share your thoughts about this recipe...");
                    txtComment.setForeground(new Color(150, 150, 150));
                }
            }
        });

        JScrollPane scrollComment = new JScrollPane(txtComment);
        scrollComment.setBorder(BorderFactory.createEmptyBorder());
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.6;
        formPanel.add(scrollComment, gbc);

        // Submit Button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(20, 12, 0, 12);
        btnSubmit = new JButton("💾 Submit Feedback");
        styleActionButton(btnSubmit);
        btnSubmit.setPreferredSize(new Dimension(200, 45));
        formPanel.add(btnSubmit, gbc);

        add(formPanel, BorderLayout.CENTER);

        loadExistingFeedback();

        btnSubmit.addActionListener(e -> submitFeedback());

        setVisible(true);
    }

    private void styleNavigationButton(JButton button) {
        button.setBackground(new Color(76, 175, 80));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(56, 142, 60), 2),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(56, 142, 60));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(76, 175, 80));
            }
        });
    }

    private void styleActionButton(JButton button) {
        button.setBackground(new Color(255, 152, 0)); // orange
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 126, 0), 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(230, 126, 0));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(255, 152, 0));
            }
        });
    }

    private void loadExistingFeedback() {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/recipe_db", "root", "99952")) {

            String sql = "SELECT comment, rating FROM feedback WHERE recipe_id = ? AND user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, recipeId);
            stmt.setInt(2, Integer.parseInt(userId));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String comment = rs.getString("comment");
                int rating = rs.getInt("rating");

                if (comment != null && !comment.isEmpty()) {
                    txtComment.setText(comment);
                    txtComment.setForeground(new Color(46, 46, 46));
                }
                comboRating.setSelectedItem(rating);
                btnSubmit.setText("✏️ Update Feedback");
            }

        } catch (SQLException ex) {
            ex.printStackTrace(); // ← now exception is used properly
        }
    }

    private void submitFeedback() {
        String comment = txtComment.getText().trim();
        Integer ratingObj = (Integer) comboRating.getSelectedItem();
        int rating = (ratingObj != null) ? ratingObj : 0;

        if (comment.isEmpty() || comment.equals("Share your thoughts about this recipe...")) {
            JOptionPane.showMessageDialog(this, "Please enter your feedback comment!",
                    "Missing Comment", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/recipe_db", "root", "99952")) {

            String checkSql = "SELECT * FROM feedback WHERE recipe_id = ? AND user_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, recipeId);
            checkStmt.setInt(2, Integer.parseInt(userId));
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                String updateSql = "UPDATE feedback SET comment = ?, rating = ?, created_at = CURRENT_TIMESTAMP WHERE recipe_id = ? AND user_id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setString(1, comment);
                updateStmt.setInt(2, rating);
                updateStmt.setInt(3, recipeId);
                updateStmt.setInt(4, Integer.parseInt(userId));
                updateStmt.executeUpdate();

                JOptionPane.showMessageDialog(this,
                        "✅ Your feedback has been updated successfully!\n\n" +
                                "Rating: " + "★".repeat(rating),
                        "Feedback Updated", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String insertSql = "INSERT INTO feedback (recipe_id, user_id, comment, rating) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setInt(1, recipeId);
                insertStmt.setInt(2, Integer.parseInt(userId));
                insertStmt.setString(3, comment);
                insertStmt.setInt(4, rating);
                insertStmt.executeUpdate();

                JOptionPane.showMessageDialog(this,
                        "✅ Thank you for your feedback!\n\n" +
                                "Rating: " + "★".repeat(rating),
                        "Feedback Submitted", JOptionPane.INFORMATION_MESSAGE);
            }

            dispose();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "❌ Database Error: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FeedbackFrame(1, "1"));
    }
}
