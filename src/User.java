import java.util.HashSet;
import java.util.LinkedList;

public class User implements Comparable<User> {
    private String userID;
    private String name;
    private String workplace;
    private String hometown;
    private HashSet<User> friends;
    private LinkedList<Post> posts;
    private String avatarFilePath;
    private String friendsData;

    public User(String userID, String name, String workplace, String hometown, String avatarFilePath, String friendsData) {
        this.userID = userID;
        this.name = name;
        this.workplace = workplace;
        this.hometown = hometown;
        this.avatarFilePath = avatarFilePath;
        this.friends = new HashSet<>();
        this.posts = new LinkedList<>();
        this.friendsData = friendsData;
    }

    // Getter and setter methods
    public void setID(String userID) {
        this.userID = userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public void setFriends(HashSet<User> friends) {
        this.friends = friends;
    }

    public void setPosts(LinkedList<Post> posts) {
        this.posts = posts;
    }

    public void setAvatarFilePath(String avatarFilePath) {
        this.avatarFilePath = avatarFilePath;
    }

    public void setFriendsData(String friendsData) {
        this.friendsData = friendsData;
    }

    public String getID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getWorkplace() {
        return workplace;
    }

    public String getHometown() {
        return hometown;
    }

    public HashSet<User> getFriends() {
        return friends;
    }

    public LinkedList<Post> getPosts() {
        return posts;
    }

    public String getAvatarFilePath() {
        return avatarFilePath;
    }

    @Override
    public int compareTo(User otherUser) {
        // 根据ID进行比较
        return this.userID.compareTo(otherUser.userID);
    }

    public void addFriend(User user) {
        friends.add(user);

    }

    public boolean isFriend(User user) {
        return friends.contains(user);
    }

    public HashSet<User> getCommonFriends(User myFriend) {
        HashSet<User> commonFriends = new HashSet<>();

        // 遍历当前用户的好友列表
        for (User friend : friends) {
            // 如果对方用户的好友列表中也包含当前用户，说明是共同好友
            if (myFriend.getFriends().contains(friend)) {
                commonFriends.add(friend);
            }
        }

        return commonFriends;
    }

    public String getFriendsData() {
        // 返回friendsData字段的值
        return friendsData;
    }

    public HashSet<User> filterFriendsByHometown(String hometown) {
        HashSet<User> hometownFriends = new HashSet<>();

        // 遍历好友列表
        for (User friend : friends) {
            // 如果好友的家乡与指定的家乡相同，则将其添加到筛选后的好友列表中
            if (friend.getHometown().equals(hometown)) {
                hometownFriends.add(friend);
            }
        }

        return hometownFriends;
    }

    public HashSet<User> filterFriendsByWorkplace(String workplace) {
        HashSet<User> workplaceFriends = new HashSet<>();

        // 遍历好友列表
        for (User friend : friends) {
            // 如果好友的工作地点与指定的工作地点相同，则将其添加到筛选后的好友列表中
            if (friend.getWorkplace().equals(workplace)) {
                workplaceFriends.add(friend);
            }
        }

        return workplaceFriends;
    }
}
