package is.ru.honn.rupin.domain;

import scala.collection.immutable.List;

import java.util.Set;
import java.util.TreeSet;

public class Pin extends PinObject {
    protected int id;
    protected String link;
    protected String description;
    protected String image;
    protected Board board;
    protected Set<User> likes;

    public Pin() {
        this.likes = new TreeSet<User>();
    }

    public Pin(String link, String description, String image) {
        this.link = link;
        this.description = description;
        this.likes = new TreeSet<User>();
        this.image = image;
    }

    public Pin(String link, String description, String image, Set<User> likes) {
        this.link = link;
        this.description = description;
        this.likes = likes;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Set<User> getLikes() {
        return likes;
    }

    public void addLike(User user) {
        this.likes.add(user);
    }

    public void addLikes(Set<User> users) {
        this.likes.addAll(users);
    }

    public void removeLike(User user) {
        this.likes.remove(user);
    }
}
