import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class DashboardFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    @SuppressWarnings("unused")
    private String userId;
    private JPanel upperPanel, lowerPanel;
    private JLabel welcomeLabel;
    private JButton btnSearch, btnAdd, btnMyRecipes, btnLogout;

    public DashboardFrame(String userId) {
        this.userId = userId;

        // ---------- FRAME SETTINGS ----------
        setTitle("Recipe Management Dashboard");
        setSize(900, 500);
        setMinimumSize(new Dimension(700, 400));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1)); // two equal horizontal panels

        // ---------- UPPER PANEL (WELCOME) ----------
        upperPanel = new JPanel(new BorderLayout());
        upperPanel.setBackground(new Color(250, 250, 250)); // off-white (#FAFAFA)

        JLabel title = new JLabel("🍲 Welcome to Your Recipe Dashboard", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setForeground(new Color(46, 46, 46));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        upperPanel.add(title, BorderLayout.NORTH);

        welcomeLabel = new JLabel(
                "<html><div style='text-align: center;'>Explore delicious recipes,<br>add your favorites, and cook with joy!</div></html>",
                JLabel.CENTER
        );
        welcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        welcomeLabel.setForeground(new Color(46, 46, 46));
        upperPanel.add(welcomeLabel, BorderLayout.CENTER);

        // ---------- LOWER PANEL (BUTTONS) ----------
        lowerPanel = new JPanel(new GridBagLayout());
        lowerPanel.setBackground(new Color(232, 245, 233)); // light green (#E8F5E9)

        btnSearch = createStyledButton("Search Recipes", new Color(245, 245, 245));
        btnAdd = createStyledButton("Add Recipe", new Color(245, 245, 245));
        btnMyRecipes = createStyledButton("My Recipes", new Color(245, 245, 245));
        btnLogout = createStyledButton("Logout", new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 25, 10, 25);
        gbc.gridy = 0;

        gbc.gridx = 0; lowerPanel.add(btnSearch, gbc);
        gbc.gridx = 1; lowerPanel.add(btnAdd, gbc);
        gbc.gridx = 2; lowerPanel.add(btnMyRecipes, gbc);
        gbc.gridx = 3; lowerPanel.add(btnLogout, gbc);

        // ---------- ADD PANELS ----------
        add(upperPanel);
        add(lowerPanel);

        // ---------- BUTTON ACTIONS ----------
        // FIXED: Changed unused lambda parameters from 'e' to '_'
        btnSearch.addActionListener(_ -> {
            dispose();
            new RecipeSearchFrame(userId).setVisible(true);
        });

        btnAdd.addActionListener(_ -> {
            dispose();
            new AddRecipeFrame(userId).setVisible(true);
        });

        btnMyRecipes.addActionListener(_ -> {
            dispose();
            new MyRecipesFrame(userId).setVisible(true);
        });

        btnLogout.addActionListener(_ -> {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to log out?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION
            );
            if (choice == JOptionPane.YES_OPTION) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });

        // ---------- RESPONSIVE BEHAVIOR ----------
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustLayout(getWidth());
            }
        });
    }

    // ---------- STYLED BUTTON ----------
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(new Color(76, 175, 80)); // fresh green text
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 2));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover effect - FIXED: Proper MouseAdapter with imports and @Override
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(76, 175, 80));
                button.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(bgColor);
                button.setForeground(new Color(76, 175, 80));
            }
        });
        return button;
    }

    // ---------- RESPONSIVE ADJUSTMENTS ----------
    private void adjustLayout(int width) {
        double scale = Math.max(0.8, Math.min(1.3, width / 900.0));
        welcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, (int) (18 * scale)));

        JButton[] buttons = {btnSearch, btnAdd, btnMyRecipes, btnLogout};
        for (JButton btn : buttons) {
            btn.setFont(new Font("SansSerif", Font.BOLD, (int) (16 * scale)));
            btn.setPreferredSize(new Dimension((int) (160 * scale), (int) (50 * scale)));
        }
        lowerPanel.revalidate();
        lowerPanel.repaint();
    }

    // ---------- MAIN ----------
    // FIXED: Changed unused lambda parameter from 'args' to '_'
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashboardFrame("1").setVisible(true));
    }
}