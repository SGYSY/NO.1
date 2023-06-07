import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LogInPage {
    private final JFrame logInFrame;
    private final JPanel contentPanel;
    private final Color COLOR_THEME = new Color(0, 191, 255);    // TODO 微调。
    private final Font FONT_LOGO = new Font("Microsoft Sans Serif", Font.BOLD, 30);
    private final Font FONT_LOG_REQUEST = new Font("Microsoft Sans Serif", Font.PLAIN, 15);
    private final Font FONT_SIGN_UP = new Font("Microsoft Sans Serif", Font.PLAIN, 12);

    public LogInPage(String filePath) {
        MainPage.avlTree = readUsersFromFile(filePath);

        logInFrame = new JFrame("Welcome");
        logInFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPanel = new JPanel();

        logInFrame.add(contentPanel, BorderLayout.CENTER);

        showLogPage();

        logInFrame.setSize(450, 350);
        logInFrame.setResizable(false);
        logInFrame.setVisible(true);
    }

    public AVLTree readUsersFromFile(String filePath) {
        AVLTree avlTree = new AVLTree();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length >= 4) {
                    String userID = fields[0].trim();
                    String name = fields[1].trim();
                    String workplace = fields[2].trim();
                    String hometown = fields[3].trim();

                    User user = new User(userID, name, workplace, hometown);
                    avlTree.insert(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return avlTree;
    }

    private void showLogPage() {
        JLabel logoLabel = new JLabel("Fake Twitter");
        logoLabel.setFont(FONT_LOGO);
        logoLabel.setForeground(COLOR_THEME);

        JLabel idRequestLabel = new JLabel("Enter Your ID:");
        idRequestLabel.setFont(FONT_LOG_REQUEST);

        JTextField idRequestField = new JTextField();
        idRequestField.setFont(FONT_LOG_REQUEST);

        FontMetrics fontMetrics = idRequestField.getFontMetrics(FONT_SIGN_UP);
        int h = fontMetrics.getHeight();
        idRequestField.setMaximumSize(new Dimension(Integer.MAX_VALUE, h + 5));

        JButton logButton = new JButton("Log In");

        logButton.addActionListener(e -> {
            // TODO 相应操作
            String logInUserID = idRequestField.getText();
            AVLNode logAttemptUser = MainPage.avlTree.query(logInUserID);
            if (logAttemptUser != null) {
                User currentUser = logAttemptUser.data;
                logInFrame.setVisible(false);
                new MainPage(currentUser);
            } else {
                JOptionPane.showMessageDialog(null, "No such user found!");
            }
        });

        JLabel signUpLabel = new JLabel("Want to create your own account?");
        signUpLabel.setFont(FONT_SIGN_UP);

        JButton signUpButton = new JButton("Sign Up Now");

        signUpButton.addActionListener(e -> {
            // TODO 相应操作
        });

        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());

        Box logVBox = Box.createVerticalBox();

        Box logoHBox = Box.createHorizontalBox();
        logoHBox.add(Box.createHorizontalGlue());
        logoHBox.add(logoLabel);
        logoHBox.add(Box.createHorizontalGlue());

        logVBox.add(Box.createVerticalStrut(30));
        logVBox.add(logoHBox);
        logVBox.add(Box.createVerticalStrut(50));

        Box labelVBox = Box.createVerticalBox();
        labelVBox.setAlignmentX(Component.RIGHT_ALIGNMENT);
        labelVBox.add(idRequestLabel,Component.RIGHT_ALIGNMENT);

        Box fieldVBox = Box.createVerticalBox();
        fieldVBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        fieldVBox.add(idRequestField);

        Box logHBox = Box.createHorizontalBox();
        logHBox.add(Box.createHorizontalStrut(50));
        logHBox.add(labelVBox);
        logHBox.add(Box.createHorizontalStrut(10));
        logHBox.add(fieldVBox);
        logHBox.add(Box.createHorizontalStrut(50));

        logVBox.add(logHBox);
        logVBox.add(Box.createVerticalStrut(40));

        Box logButtonHBox = Box.createHorizontalBox();
        logButtonHBox.add(Box.createHorizontalGlue());
        logButtonHBox.add(logButton);
        logButtonHBox.add(Box.createHorizontalGlue());

        logVBox.add(logButtonHBox);
        logVBox.add(Box.createVerticalStrut(100));

        Box signHBox = Box.createHorizontalBox();
        signHBox.add(Box.createHorizontalGlue());
        signHBox.add(signUpLabel);
        signHBox.add(Box.createHorizontalStrut(10));
        signHBox.add(signUpButton);
        signHBox.add(Box.createHorizontalGlue());

        Box signVBox = Box.createVerticalBox();
        signVBox.add(signHBox);
        signVBox.add(Box.createVerticalStrut(20));

        contentPanel.add(logVBox, BorderLayout.CENTER);
        contentPanel.add(signHBox, BorderLayout.SOUTH);

        logInFrame.revalidate();
        logInFrame.repaint();
    }
}
