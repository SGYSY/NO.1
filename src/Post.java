import java.util.Date;
import java.util.TreeSet;

public class Post {
    private String content;
    private Date time;
    private String author;
    private TreeSet<User> likes;

    public Post(String content, Date time, String author) {
        this.content = content;
        this.time = time;
        this.author = author;
        this.likes = new TreeSet<>();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public TreeSet<User> getLikes() {
        return likes;
    }

    public void setLikes(TreeSet<User> likes) {
        this.likes = likes;
    }
}