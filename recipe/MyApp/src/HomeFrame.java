import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class HomeFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public HomeFrame() {
        setTitle("Recipe Management System - Home");
        setSize(600, 400);
        setMinimumSize(new Dimension(500, 350));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ---------- TOP PANEL ----------
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(232, 245, 233)); // light green
        topPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        JLabel lblTitle = new JLabel("🍲 Welcome to Recipe Management System", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Serif", Font.BOLD, 24));
        lblTitle.setForeground(new Color(46, 46, 46)); // dark gray
        topPanel.add(lblTitle, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // ---------- CENTER PANEL ----------
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(250, 250, 250)); // off-white
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ---------- LOGIN BUTTON ----------
        JButton btnLogin = new JButton("Login");
        styleButton(btnLogin);
        gbc.gridy = 0;
        centerPanel.add(btnLogin, gbc);

        // ---------- REGISTER BUTTON ----------
        JButton btnRegister = new JButton("Register");
        styleButton(btnRegister);
        gbc.gridy = 1;
        centerPanel.add(btnRegister, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // ---------- FOOTER ----------
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(232, 245, 233));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel lblFooter = new JLabel("Your daily dose of delicious recipes.", SwingConstants.CENTER);
        lblFooter.setFont(new Font("SansSerif", Font.ITALIC, 12));
        lblFooter.setForeground(new Color(70, 70, 70));
        footerPanel.add(lblFooter);

        add(footerPanel, BorderLayout.SOUTH);

        // ---------- ACTIONS ----------
        // FIXED: Changed unused lambda parameter from 'e' to '_'
        btnLogin.addActionListener(_ -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        // FIXED: Changed unused lambda parameter from 'e' to '_'
        btnRegister.addActionListener(_ -> {
            new UserRegistrationFrame().setVisible(true);
            dispose();
        });

        // Center the frame
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ---------- STYLING METHOD ----------
    private void styleButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBackground(new Color(245, 245, 220)); // beige
        button.setForeground(new Color(46, 46, 46));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 2));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(180, 40));

        // Hover effect - Fixed MouseAdapter with proper @Override
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(76, 175, 80)); // green
                button.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(245, 245, 220)); // beige
                button.setForeground(new Color(46, 46, 46));
            }
        });
    }

    // ---------- MAIN ----------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(HomeFrame::new);
    }
}