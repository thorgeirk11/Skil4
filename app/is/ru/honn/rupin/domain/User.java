package is.ru.honn.rupin.domain;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class User implements Comparable<User>  {
    protected int id;
    protected String name;

    protected String username;
    protected String password;
    protected String email;
    protected Set<User> followers = new TreeSet<User>();

    public User() {
    }

    public User(int id, String name, String username, String password, String email) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String name, String username, String password, String email) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User: " + name + " (" + username + ")";
    }


    public Set<User> getFollowers() {
        return followers;
    }

    public void addFollower(User follower) {
        followers.add(follower);
    }
    public void addFollowers(Collection<User> followers) {
        this.followers.addAll(followers);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == UserAuthentication.class)
            return ((UserAuthentication) obj).getUsername().equals(this.getUsername()) &&
                    ((UserAuthentication) obj).getPassword().equals(this.getPassword());
        return obj.getClass() == User.class && ((User) obj).getId() == this.id;
    }

    @Override
    public int compareTo(User o) {
        if (((User) o).getId() > this.id)
            return 1;
        if (((User) o).getId() == this.id)
            return 0;
        return -1;
    }
}
