import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


/**
 * GUI main page.
 * @author Jason Cai
 */
public class MainPage {

    private final JFrame mainPage;
    private JButton homeButton, friendsButton, postButton, selfButton;
    private final JPanel contentPanel;
    private final User currentUser;
    private int friendSortMode = 0;
    public static AVLTree avlTree = new AVLTree();

    // Colors and fonts used.
    private final Color COLOR_THEME = new Color(0, 191, 255);    // TODO 微调。
    private final Color COLOR_DEFAULT = new Color(224, 224, 224);   // 默认颜色。
    private final Font FONT_POST_AUTHOR = new Font("Microsoft Sans Serif", Font.BOLD, 15);
    private final Font FONT_POST_TIME = new Font("Microsoft Sans Serif", Font.PLAIN, 10);
    private final Font FONT_CONTENT = new Font("Microsoft Sans Serif", Font.PLAIN, 18);
    private final Font FONT_TITLE = new Font("Microsoft Sans Serif", Font.BOLD, 20);
    private final Font FONT_SELF_NAME = new Font("Microsoft Sans Serif", Font.BOLD, 25);
    private final Font FONT_SELF_ID = new Font("Microsoft Sans Serif", Font.BOLD, 13);
    /**
     * 构造器。
     */
    public MainPage(User currentUser) {
        mainPage = new JFrame("Fake Twitter");
        mainPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPanel = new JPanel();

        mainPage.add(contentPanel, BorderLayout.CENTER);

        JPanel navBarPanel = createNavBarPanel();
        mainPage.add(navBarPanel, BorderLayout.WEST);

        // 打开时，导航栏默认为Home
        showHomePage();

        mainPage.setSize(800, 600);
        mainPage.setVisible(true);

        this.currentUser = currentUser;
    }

    /**
     * 初始化导航栏。
     * @return the panel after construction.
     */
    private JPanel createNavBarPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(224, 224, 224));

        Box vbox = Box.createVerticalBox();

        homeButton = createNavButton("Home");
        friendsButton = createNavButton("Friends");
        postButton = createNavButton("Post");
        selfButton = createNavButton("Me");

        vbox.add(homeButton);
        vbox.add(Box.createVerticalStrut(5));
        vbox.add(friendsButton);
        vbox.add(Box.createVerticalStrut(5));
        vbox.add(postButton);
        vbox.add(Box.createVerticalStrut(5));
        vbox.add(selfButton);
        vbox.add(Box.createVerticalBox());

        panel.add(vbox);

        return panel;
    }

    /**
     * 创建导航栏按钮。
     * @param title the words shown inside the button.
     * @return the button created.
     */
    private JButton createNavButton(String title) {
        JButton button = new JButton(title);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBackground(COLOR_DEFAULT);

        button.addActionListener(e -> {
            if (e.getSource() == homeButton) {
                showHomePage();
            } else if (e.getSource() == friendsButton) {
                showFriendsPage();
            } else if (e.getSource() == postButton) {
                showPostPage();
            } else if (e.getSource() == selfButton) {
                showMePage();
            }
        });

        button.setSize(new Dimension(100,30));

        return button;
    }

    /**
     * 栏目1：主页模式（启动时默认，发完帖子后默认）
     */
    public void showHomePage() {
        homeButton.setBackground(COLOR_THEME);
        friendsButton.setBackground(COLOR_DEFAULT);
        postButton.setBackground(COLOR_DEFAULT);
        selfButton.setBackground(COLOR_DEFAULT);

        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("  Home");
        titleLabel.setFont(FONT_TITLE);
        titleLabel.setForeground(COLOR_THEME);
        Box titleVBox = Box.createVerticalBox();

        titleVBox.add(Box.createVerticalStrut(5));
        titleVBox.add(titleLabel);
        titleVBox.add(Box.createVerticalStrut(10));

        JPanel postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
        postPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // TODO 链接帖子
        ImageIcon elonPic = new ImageIcon("musk.jpg");
        ImageIcon cookPic = new ImageIcon("cook.jpg");
        ImageIcon bidenPic = new ImageIcon("biden.jpg");
        ImageIcon jasonPic = new ImageIcon("Pic.jpg");

        postPanel.add(createPost(jasonPic,"Jason Cai", "2h", "Hello!"));
        postPanel.add(createPost(elonPic,"Elon Musk", "5h", "Hold on to your horse as the carousel of destiny spins ever faster"));
        postPanel.add(createPost(bidenPic,"President Biden", "1d", "Republican sucks! <br> We will win the 2024 election!"));
        postPanel.add(createPost(cookPic,"Tim Cook", "2d", "Introducing the all new Vision Pro, starting at just $3499. <br> It will be available early next year!"));

        JLabel bottomLabel = new JLabel("No more posts!");
        postPanel.add(bottomLabel);

        JScrollPane scrollPane = new JScrollPane(postPanel);

        contentPanel.add(titleVBox, BorderLayout.NORTH);
        contentPanel.add(scrollPane,BorderLayout.CENTER);

        mainPage.revalidate();
        mainPage.repaint();
    }

    /**
     * 创建帖子。
     * TODO 临时创建帖子方法，未使用帖子相关类。
     * @param avatar the avatar of the author.
     * @param friendName the author's name.
     * @param postTime time the post is released.
     * @param postContent the content of the post.
     * @return the post in JPanel, to be displayed in GUI.
     */
    private JPanel createPost(ImageIcon avatar, String friendName, String postTime, String postContent) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(COLOR_DEFAULT,4));

        JPanel authorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Image avatarPic = avatar.getImage().getScaledInstance(15,15,Image.SCALE_SMOOTH);
        JLabel avatarLabel = new JLabel();
        avatarLabel.setIcon(new ImageIcon(avatarPic));

        JLabel nameLabel = new JLabel(friendName);
        nameLabel.setFont(FONT_POST_AUTHOR);

        authorPanel.add(avatarLabel);
        authorPanel.add(nameLabel);

        authorPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        authorPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        JLabel timeLabel = new JLabel(postTime);
        timeLabel.setFont(FONT_POST_TIME);

        JLabel contentLabel = new JLabel("<html>" + postContent + "</html>");
        contentLabel.setFont(FONT_CONTENT);

        final int[] likes = {0};
        JButton likeButton = new JButton("Like " + likes[0]);

        likeButton.addActionListener(e -> {
            likes[0]++;
            likeButton.setText("Liked " + likes[0]);
        });

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));

        textPanel.add(timeLabel);
        textPanel.add(contentLabel);
        textPanel.add(Box.createVerticalStrut(10));
        textPanel.add(likeButton);
        textPanel.add(Box.createVerticalStrut(10));

        panel.add(authorPanel);
        panel.add(textPanel, BorderLayout.CENTER);

        return panel;
    }

    /**
     * 栏目2：好友列表模式。
     */
    public void showFriendsPage() {
        homeButton.setBackground(COLOR_DEFAULT);
        friendsButton.setBackground(COLOR_THEME);
        postButton.setBackground(COLOR_DEFAULT);
        selfButton.setBackground(COLOR_DEFAULT);

        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());

        // Title
        Box titleVBox = Box.createVerticalBox();
        Box titleHBox = Box.createHorizontalBox();
        JLabel titleLabel = new JLabel("  My Friends");
        titleLabel.setFont(FONT_TITLE);
        titleLabel.setForeground(COLOR_THEME);

        JComboBox<String> sortBox = new JComboBox<>();
        JLabel sortLabel = new JLabel("View: ");
        sortBox.addItem("All My Friends");
        sortBox.addItem("Same Hometown");
        sortBox.addItem("Same Workplace");

        sortBox.setSelectedIndex(friendSortMode);

        JButton addButton = new JButton("Add New Friend");

        sortBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedMode = (String) sortBox.getSelectedItem();
                assert selectedMode != null;
                switch (selectedMode) {
                    case "All My Friends" ->{
                        friendSortMode = 0;
                        showFriendsPage();
                    }
                    case "Same Hometown" -> {
                        friendSortMode = 1;
                        showFriendsPage();
                    }
                    case "Same Workplace" -> {
                        friendSortMode = 2;
                        showFriendsPage();
                    }
                }
            }
        });

        addButton.addActionListener(e -> {
            new AddPage(this);
        });

        titleHBox.add(titleLabel);
        titleHBox.add(Box.createHorizontalGlue());
        titleHBox.add(sortLabel);
        titleHBox.add(sortBox);
        titleHBox.add(Box.createHorizontalStrut(10));
        titleHBox.add(addButton);
        titleHBox.add(Box.createHorizontalStrut(10));

        titleVBox.add(Box.createVerticalStrut(5));
        titleVBox.add(titleHBox);
        titleVBox.add(Box.createVerticalStrut(10));

        JScrollPane scrollPane = new JScrollPane(friendsList(friendSortMode)); // TODO 此处链接相关数据获取朋友

        contentPanel.add(titleVBox,BorderLayout.NORTH);
        contentPanel.add(scrollPane,BorderLayout.CENTER);

        mainPage.revalidate();
        mainPage.repaint();
    }

    /**
     * 获取朋友列表。
     * TODO 从相关数据结构获取。此处在此方法内设定。
     * @param sortMode for sorting. 用于展示自己的朋友列表时筛选。0：代表获取所有朋友。 1：代表获取同家乡。 2：代表获取同工作地点。
     * @return the friends list, in JList.
     */
    private JList<String> friendsList(int sortMode) {
        List<String> sortedList = new ArrayList<>(currentUser.getFriends().size());

        switch (sortMode) {
            case 0 -> {
                for (User user : currentUser.getFriends()) {
                    sortedList.add(user.getName());
                }
            }
            case 1 -> {
                for (User user : currentUser.filterFriendsByHometown(currentUser.getHometown())) {
                    sortedList.add(user.getName());
                }
            }
            case 2 -> {
                for (User user : currentUser.filterFriendsByWorkplace(currentUser.getWorkplace())) {
                    sortedList.add(user.getName());
                }
            }
        }
        Collections.sort(sortedList);

        DefaultListModel<String> defaultListModel = new DefaultListModel<>();
        for (String friendName : sortedList) {
            defaultListModel.addElement(friendName);
        }

        JList<String> friendsList = new JList<>(defaultListModel);
        friendsList.setFont(FONT_POST_AUTHOR);
        friendsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        friendsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String selectedFriend = friendsList.getSelectedValue();
                    showSelectedFriendPage(selectedFriend); // TODO 相应操作
                }
            }
        });

        return friendsList;
    }



    /**
     * 展示好友的主页.
     * @param friend the friend's name. TODO 此处可能需要调整参数类型，比如换成ID，以便查找。
     */
    private void showSelectedFriendPage(String friend) {
        JOptionPane.showMessageDialog(mainPage, "History posts for " + friend);
    }

    /**
     * 栏目3：发帖子模式。
     */
    private void showPostPage() {
        // 更新按钮颜色状态
        homeButton.setBackground(COLOR_DEFAULT);
        friendsButton.setBackground(COLOR_DEFAULT);
        postButton.setBackground(COLOR_THEME);
        selfButton.setBackground(COLOR_DEFAULT);

        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());

        JLabel postTitle = new JLabel("  What's New?");
        postTitle.setFont(FONT_TITLE);
        postTitle.setForeground(COLOR_THEME);

        Box titleVBox = Box.createVerticalBox();
        titleVBox.add(Box.createVerticalStrut(5));
        titleVBox.add(postTitle);
        titleVBox.add(Box.createVerticalStrut(10));

        JTextArea postTextArea = new JTextArea();
        postTextArea.setLineWrap(true);
        postTextArea.setWrapStyleWord(true);
        postTextArea.setFont(FONT_CONTENT);
        postTextArea.setRows(10);

        JScrollPane scrollPane = new JScrollPane(postTextArea);

        JButton saveButton = new JButton("Save Draft");
        JButton sendButton = new JButton("Post");

        Box postHBox = Box.createHorizontalBox();
        postHBox.add(Box.createHorizontalGlue());
        postHBox.add(saveButton);
        postHBox.add(Box.createHorizontalStrut(15));
        postHBox.add(sendButton);
        postHBox.add(Box.createHorizontalGlue());

        Box postVBox = Box.createVerticalBox();
        postVBox.add(Box.createVerticalStrut(15));
        postVBox.add(postHBox);
        postVBox.add(Box.createVerticalStrut(10));

        contentPanel.add(titleVBox,BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(postVBox,BorderLayout.SOUTH);

        mainPage.revalidate();
        mainPage.repaint();
    }

    private void showMePage() {
        // 更新按钮颜色状态
        homeButton.setBackground(COLOR_DEFAULT);
        friendsButton.setBackground(COLOR_DEFAULT);
        postButton.setBackground(COLOR_DEFAULT);
        selfButton.setBackground(COLOR_THEME);

        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());

        JLabel selfTitle = new JLabel("  Me");
        selfTitle.setFont(FONT_TITLE);
        selfTitle.setForeground(COLOR_THEME);

        Box titleHBox = Box.createHorizontalBox();
        titleHBox.add(selfTitle);
        titleHBox.add(Box.createHorizontalGlue());

        JButton editButton = new JButton("Edit Profile");
        JButton logOutButton = new JButton("Log Out");
        titleHBox.add(editButton);
        titleHBox.add(Box.createHorizontalStrut(10));
        titleHBox.add(logOutButton);
        titleHBox.add(Box.createHorizontalStrut(20));

        Box titleVBox = Box.createVerticalBox();
        titleVBox.add(Box.createVerticalStrut(5));
        titleVBox.add(titleHBox);
        titleVBox.add(Box.createVerticalStrut(10));

        Box selfInfoHBox = Box.createHorizontalBox();
        Box selfInfoVBox = Box.createVerticalBox();

        Box mainInfoHBox = Box.createHorizontalBox();
        Box nameInfoVBox = Box.createVerticalBox();

        JLabel nameLabel = new JLabel(currentUser.getName());
        nameLabel.setFont(FONT_SELF_NAME);

        JLabel idLabel = new JLabel("Unique ID: " + currentUser.getID());
        idLabel.setFont(FONT_SELF_ID);

        nameInfoVBox.add(nameLabel);
        nameInfoVBox.add(Box.createVerticalStrut(10));
        nameInfoVBox.add(idLabel);

        JLabel hometownLabel = new JLabel("Hometown: " + currentUser.getHometown());
        JLabel workplaceLabel = new JLabel("Workplace: " + currentUser.getWorkplace());

        hometownLabel.setFont(FONT_CONTENT);
        workplaceLabel.setFont(FONT_CONTENT);

        nameInfoVBox.add(Box.createVerticalStrut(10));
        nameInfoVBox.add(hometownLabel);
        nameInfoVBox.add(Box.createVerticalStrut(10));
        nameInfoVBox.add(workplaceLabel);

        String currentWorkingDirectory = System.getProperty("user.dir");
        String avatarFilePath = currentUser.getAvatarFilePath();
        String completeFilePath = currentWorkingDirectory + File.separator + avatarFilePath; // TODO
        ImageIcon image = new ImageIcon(completeFilePath);
        Image avatarPic = image.getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH);
        JLabel avatarLabel = new JLabel();
        avatarLabel.setIcon(new ImageIcon(avatarPic));

        mainInfoHBox.add(avatarLabel);
        mainInfoHBox.add(Box.createHorizontalStrut(20));
        mainInfoHBox.add(nameInfoVBox);
        mainInfoHBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

        selfInfoVBox.add(Box.createVerticalStrut(20));
        selfInfoVBox.add(mainInfoHBox);
        selfInfoVBox.add(Box.createVerticalStrut(40));

        JPanel myPostPanel = new JPanel();
        myPostPanel.setLayout(new BoxLayout(myPostPanel, BoxLayout.Y_AXIS));
        myPostPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // TODO 链接我的历史帖子

        myPostPanel.add(createPost(image,"Jason Cai", "2h", "Hello!"));

        JLabel bottomLabel = new JLabel("No more posts!");
        myPostPanel.add(bottomLabel);

        JLabel myPostTitle = new JLabel("My History Posts");
        myPostTitle.setFont(FONT_TITLE);

        Box myPostVBox = Box.createVerticalBox();
        myPostVBox.add(myPostTitle, Component.LEFT_ALIGNMENT);
        myPostVBox.add(Box.createVerticalStrut(10));

        JScrollPane scrollPane = new JScrollPane(myPostPanel);
        myPostVBox.add(scrollPane);

        selfInfoVBox.add(myPostVBox);

        selfInfoHBox.add(Box.createHorizontalStrut(20));
        selfInfoHBox.add(selfInfoVBox);
        selfInfoHBox.add(Box.createHorizontalStrut(20));

        contentPanel.add(titleVBox, BorderLayout.NORTH);
        contentPanel.add(selfInfoHBox, BorderLayout.CENTER);

        editButton.addActionListener(e -> {
            new EditPage(currentUser);
        });

        mainPage.revalidate();
        mainPage.repaint();
        saveUsersToFile(avlTree,"\"C:\\Users\\YANSIYU\\IdeaProjects\\Social Meida\\social_network.txt\"");
    }

    public User getCurrentUser() {
        return currentUser;
    }
    public void saveUsersToFile(AVLTree avlTree, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (AVLNode node : avlTree.inOrderTraversal()) {
                User user = node.getData();
                String line = user.getID() + "," + user.getName() + "," + user.getWorkplace() + ","
                        + user.getHometown() + "," + user.getAvatarFilePath() + "," + user.getFriendsData();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

