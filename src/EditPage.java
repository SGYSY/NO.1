import javax.swing.*;
import java.awt.*;

public class EditPage {
    private final User currentUser;
    private final JFrame editFrame;
    private final JPanel contentPanel;
    private final Color COLOR_THEME = new Color(0, 191, 255);    // TODO 微调。
    private final Font FONT_EDIT_TITLE = new Font("Microsoft Sans Serif", Font.BOLD, 20);
    private final Font FONT_EDIT_REQUEST = new Font("Microsoft Sans Serif", Font.PLAIN, 15);

    public EditPage(User currentUser) {
        this.currentUser = currentUser;

        editFrame = new JFrame("Edit Profile");
        editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        contentPanel = new JPanel();

        editFrame.add(contentPanel, BorderLayout.CENTER);

        showEditPage();

        editFrame.setSize(500,400);
        editFrame.setResizable(false);
        editFrame.setVisible(true);
    }

    private void showEditPage() {
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("  Edit Your Profile");
        titleLabel.setFont(FONT_EDIT_TITLE);
        titleLabel.setForeground(COLOR_THEME);

        JLabel avatarLabel = new JLabel("Avatar:");
        avatarLabel.setFont(FONT_EDIT_REQUEST);

        JButton avatarChangeButton = new JButton("Upload");

        avatarChangeButton.addActionListener(e -> {
            // TODO 读取文件更新头像
        });

        String avatarFilePath = currentUser.getAvatarFilePath();
        ImageIcon currentAvatar = new ImageIcon(avatarFilePath);
        Image avatarPic = currentAvatar.getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH);
        JLabel avatarPicLabel = new JLabel();
        avatarPicLabel.setIcon(new ImageIcon(avatarPic));

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(FONT_EDIT_REQUEST);

        JLabel hometownLabel = new JLabel("Hometown:");
        hometownLabel.setFont(FONT_EDIT_REQUEST);

        JLabel workplaceLabel = new JLabel("Workplace:");
        workplaceLabel.setFont(FONT_EDIT_REQUEST);

        JTextField nameField = new JTextField();
        JTextField hometownField = new JTextField();
        JTextField workplaceField = new JTextField();

        nameField.setFont(FONT_EDIT_REQUEST);
        hometownField.setFont(FONT_EDIT_REQUEST);
        workplaceField.setFont(FONT_EDIT_REQUEST);

        FontMetrics fontMetrics = nameField.getFontMetrics(FONT_EDIT_TITLE);
        int h = fontMetrics.getHeight();

        JTextField passwordRequestField = new JTextField();
        passwordRequestField.setFont(FONT_EDIT_TITLE);

        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, h + 5));
        hometownField.setMaximumSize(new Dimension(Integer.MAX_VALUE, h + 5));
        workplaceField.setMaximumSize(new Dimension(Integer.MAX_VALUE, h + 5));

        nameField.setText(currentUser.getName());
        hometownField.setText(currentUser.getHometown());
        workplaceField.setText(currentUser.getWorkplace());

        JButton confirmButton = new JButton("Update");

        confirmButton.addActionListener(e -> {
            // TODO 更新修改操作
        });

        Box titleVBox = Box.createVerticalBox();
        titleVBox.add(Box.createVerticalStrut(10));
        titleVBox.add(titleLabel);
        titleVBox.add(Box.createVerticalStrut(5));

        Box avatarLabelVBox = Box.createVerticalBox();
        avatarLabelVBox.add(avatarLabel);
        avatarLabelVBox.add(Box.createVerticalStrut(10));
        avatarLabelVBox.add(avatarChangeButton);

        Box labelVBox = Box.createVerticalBox();
        labelVBox.add(avatarLabelVBox);
        labelVBox.add(Box.createVerticalStrut(60));
        labelVBox.add(nameLabel);
        labelVBox.add(Box.createVerticalStrut(20));
        labelVBox.add(hometownLabel);
        labelVBox.add(Box.createVerticalStrut(20));
        labelVBox.add(workplaceLabel);
        labelVBox.add(Box.createVerticalStrut(20));

        Box infoVBox = Box.createVerticalBox();
        infoVBox.add(avatarPicLabel);
        infoVBox.add(Box.createVerticalStrut(20));
        infoVBox.add(nameField);
        infoVBox.add(Box.createVerticalStrut(10));
        infoVBox.add(hometownField);
        infoVBox.add(Box.createVerticalStrut(10));
        infoVBox.add(workplaceField);
        infoVBox.add(Box.createVerticalStrut(20));

        Box editHBox = Box.createHorizontalBox();
        editHBox.add(Box.createHorizontalStrut(20));
        editHBox.add(labelVBox);
        editHBox.add(Box.createHorizontalStrut(10));
        editHBox.add(infoVBox);
        editHBox.add(Box.createHorizontalStrut(20));

        Box editVBox = Box.createVerticalBox();
        editVBox.add(Box.createVerticalStrut(15));
        editVBox.add(editHBox);
        editVBox.add(Box.createVerticalGlue());

        Box confirmHBox = Box.createHorizontalBox();
        confirmHBox.add(Box.createHorizontalGlue());
        confirmHBox.add(confirmButton);
        confirmHBox.add(Box.createHorizontalGlue());

        Box confirmVBox = Box.createVerticalBox();
        confirmVBox.add(confirmHBox);
        confirmVBox.add(Box.createVerticalStrut(15));

        contentPanel.add(titleLabel, BorderLayout.NORTH);
        contentPanel.add(editHBox, BorderLayout.CENTER);
        contentPanel.add(confirmVBox, BorderLayout.SOUTH);

        editFrame.revalidate();
        editFrame.repaint();
    }

}
