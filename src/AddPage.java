import javax.swing.*;
import java.awt.*;

public class AddPage {
    private final JFrame addFrame;
    private final JPanel contentPanel;
    private final Color COLOR_THEME = new Color(0, 191, 255);    // TODO 微调。
    private final Font FONT_ADD_TITLE = new Font("Microsoft Sans Serif", Font.BOLD, 20);
    private final Font FONT_ADD_REQUEST = new Font("Microsoft Sans Serif", Font.PLAIN, 12);
    private final MainPage mainPage;

    public AddPage(MainPage mainPage) {
        this.mainPage = mainPage;

        addFrame = new JFrame("Add New Friend");
        contentPanel = new JPanel();

        addFrame.setSize(600, 200);
        addFrame.setResizable(false);

        showAddPage();

        addFrame.add(contentPanel,BorderLayout.CENTER);

        addFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addFrame.setVisible(true);
    }

    private void showAddPage() {
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("  Add a new friend");
        titleLabel.setFont(FONT_ADD_TITLE);
        titleLabel.setForeground(COLOR_THEME);

        JLabel idRequestLabel = new JLabel("Enter the ID of your new friend:");
        idRequestLabel.setFont(FONT_ADD_REQUEST);

        JTextField idRequestField = new JTextField();
        idRequestField.setFont(FONT_ADD_REQUEST);

        FontMetrics fontMetrics = idRequestField.getFontMetrics(FONT_ADD_REQUEST);
        int h = fontMetrics.getHeight();
        idRequestField.setMaximumSize(new Dimension(Integer.MAX_VALUE, h + 5));

        JButton confirmButton = new JButton("Add");

        confirmButton.addActionListener(e -> {
            String expectedID = idRequestField.getText();
            AVLNode addAttemptUser = MainPage.avlTree.query(expectedID);
            if (addAttemptUser != null) {
                User newFriend = addAttemptUser.data;
                addFrame.setVisible(false);
                mainPage.getCurrentUser().addFriend(newFriend);
                mainPage.showFriendsPage();
            } else {
                JOptionPane.showMessageDialog(null, "No such user found!");
            }
        });

        Box titleVBox = Box.createVerticalBox();
        titleVBox.add(Box.createVerticalStrut(5));
        titleVBox.add(titleLabel);
        titleVBox.add(Box.createVerticalStrut(20));

        Box idRequestHBox = Box.createHorizontalBox();
        idRequestHBox.add(Box.createHorizontalStrut(30));
        idRequestHBox.add(idRequestLabel);
        idRequestHBox.add(Box.createHorizontalStrut(10));
        idRequestHBox.add(idRequestField);
        idRequestHBox.add(Box.createHorizontalStrut(30));

        Box idRequestVBox = Box.createVerticalBox();
        idRequestVBox.add(idRequestHBox);
        idRequestVBox.add(Box.createVerticalStrut(30));

        Box confirmHBox = Box.createHorizontalBox();
        confirmHBox.add(Box.createHorizontalGlue());
        confirmHBox.add(confirmButton);
        confirmHBox.add(Box.createHorizontalGlue());

        Box confirmVBox = Box.createVerticalBox();
        confirmVBox.add(Box.createVerticalStrut(20));
        confirmVBox.add(confirmHBox);
        confirmVBox.add(Box.createVerticalStrut(20));

        contentPanel.add(titleVBox, BorderLayout.NORTH);
        contentPanel.add(idRequestVBox, BorderLayout.CENTER);
        contentPanel.add(confirmVBox, BorderLayout.SOUTH);

        addFrame.revalidate();
        addFrame.repaint();
    }
}