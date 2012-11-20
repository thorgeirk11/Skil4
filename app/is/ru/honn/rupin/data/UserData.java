package is.ru.honn.rupin.data;

import is.ru.honn.rupin.data.mappers.UserRowMapper;
import is.ru.honn.rupin.domain.User;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: Sir.Thorgeir lap
 * Date: 16.10.2012
 * Time: 16:52
 *
 * @author Þorgeir Auðunn Karlsson.
 */
public class UserData extends ContentDataGateWay implements UserDataMapper {
    public UserData() {
    }

    public UserData(DataSource dataSource) {
        setDataSource(dataSource);
    }

    /**
     * Adds a user to the table ru_user in the db if the user doesn't already exists.
     * Returns the id of the user if he was successfully inserted.
     *
     * @param user, the user to be added to the db
     * @return int, if user already exists then return -1
     */
    public int add(User user) {
        if (con == null)
            OpenConnection(); //Opens a connection to the db if not connected.

        SimpleJdbcInsert insertContent =
                new SimpleJdbcInsert(this.getDataSource())
                        .withTableName("ru_users")
                        .usingGeneratedKeyColumns("ID");

        Map<String, Object> parameters = new HashMap<String, Object>(6);
        parameters.put("userName", user.getUsername());
        parameters.put("Name", user.getName());
        parameters.put("email", user.getEmail());
        parameters.put("pass", user.getPassword());
        try {
            Number n = insertContent.executeAndReturnKey(parameters);
            return n.intValue();
        } catch (DataIntegrityViolationException divex) {
            log.warning("Duplicate entry, User already exist with name '" + user.getUsername() + "'.");
        }
        return -1;
    }

    /**
     * Returns a user with a specific userName from the result set.
     *
     * @param userName, the username of the user to be returned.
     * @return User, if user is not found then returns null.
     */
    public User getUserByUserName(String userName) {
        if (con == null)
            OpenConnection(); //Opens a connection to the db if not connected.+
        String query = "SELECT * FROM ru_users WHERE userName = '" + userName + "'";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            User u = null;
            if (rs.next()) {
                u = new UserRowMapper().mapRow(rs, 1337);//just for the elites ;)
            }
            if (u == null)
                return u;
            u.addFollowers(getUsersThatFollow(u.getId()));
            return u;
        } catch (SQLException sqlex) {
            log.warning("Unable to get user '" + userName + "':\n" + sqlex.getMessage() + "\nSql Query:\n" + query);
        } catch (Exception ex) {
            log.warning("Unable to get user '" + userName + "':\n" + ex.getMessage());
        }
        return null;
    }

    /**
     * Returns a user with a specific ID from the result set.
     *
     * @param ID, id of the user to be returned.
     * @return User, if user is not found the returns null.
     */
    public User getUserByID(int ID) {
        if (con == null)
            OpenConnection(); //Opens a connection to the db if not connected.
        String query = "SELECT * FROM ru_users WHERE ID = " + ID;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            User u = null;
            if (rs.next()) {
                u = new UserRowMapper().mapRow(rs, 42);//Meaning of life ;)
            }
            if (u == null)
                return u;
            u.addFollowers(getUsersThatFollow(ID));
            return u;
        } catch (SQLException sqlex) {
            log.warning("Unable to get user by id '" + ID + "':\n" + sqlex.getMessage() + "\nSql Query:\n" + query);
        }
        return null;
    }

    @Override
    public Set<User> getFollowersOfUser(int userid) {
        if (con == null)
            OpenConnection(); //Opens a connection to the db if not connected.
        String query = "SELECT ru_users.* FROM ru_users " +
                " inner join ru_followers on ru_followers.userID = ru_users.ID" +
                " WHERE ru_followers.UserID = " + userid;
        Set<User> set = new TreeSet<User>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next())
                set.add(new UserRowMapper().mapRow(rs, 42));//Meaning of life :)
        } catch (SQLException sqlex) {
            log.warning("Unable to get user by id '" + userid + "':\n" + sqlex.getMessage() + "\nSql Query:\n" + query);
        }
        return set;
    }

    @Override
    public Set<User> getUsersThatFollow(int userid) {
        if (con == null)
            OpenConnection(); //Opens a connection to the db if not connected.
        String query = "SELECT ru_users.* FROM ru_users " +
                " inner join ru_followers on ru_followers.userID = ru_users.ID" +
                " WHERE ru_followers.UserWhoFollows = " + userid;
        Set<User> set = new TreeSet<User>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next())
                set.add(new UserRowMapper().mapRow(rs, 42));//Meaning of life :)
        } catch (SQLException sqlex) {
            log.warning("Unable to get user by id '" + userid + "':\n" + sqlex.getMessage() + "\nSql Query:\n" + query);
        }
        return set;
    }

    @Override
    public int addFollower(int useridFollowing, int useridBeingFollowed) {
        if (con == null)
            OpenConnection(); //Opens a connection to the db if not connected.

        SimpleJdbcInsert insertContent =
                new SimpleJdbcInsert(this.getDataSource())
                        .withTableName("ru_followers")
                        .usingGeneratedKeyColumns("ID");
        Map<String, Object> parameters = new HashMap<String, Object>(2);
        parameters.put("UserID", useridBeingFollowed);
        parameters.put("UserWhoFollows", useridFollowing);
        try {
            Number n = insertContent.executeAndReturnKey(parameters);
            return n.intValue();
        } catch (DataIntegrityViolationException divex) {
            log.warning("Userid " + useridFollowing + " is already following this userid: " + useridBeingFollowed + "'.");
        }
        return -1;
    }
}
