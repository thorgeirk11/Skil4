package is.ru.honn.rupin.data;

import is.ru.honn.rupin.domain.User;
import is.ruframework.data.RuDataAccess;

import java.util.Set;

/**
 *
 * An interface that maps to the ru_user table and provides certain interaction to the table.
 *
 * Created with IntelliJ IDEA.
 * User: Sir.Thorgeir lap
 * Date: 16.10.2012
 * Time: 12:14
 *
 * @author Thorgeir Audunn Karlsson and Gudny Bjork Gunnarsdottir.
 */
public interface UserDataMapper extends RuDataAccess{

    /**
     * Adds a user to the table ru_user in the db if the user doesn't already exists.
     * Returns the id of the user if he was successfully inserted.
     * @param user, the user to be added to the db
     * @return int, if user already exists then return -1
     */
    public int add(User user);

    /**
     * Returns a user with a specific userName from the result set.
     * @param userName, the username of the user to be returned.
     * @return User, if user is not found then returns null.
     */
    public User getUserByUserName(String userName);

    /**
     * Returns a user with a specific ID from the result set.
     * @param ID, id of the user to be returned.
     * @return User, if user is not found the returns null.
     */
    public User getUserByID(int ID);

    /**
     * Returns a collection of users that the user with a specific userid follows
     * @param userid, id of the user that is following
     * @return Set<User>, a collection of users
     */
    public Set<User> getFollowersOfUser(int userid);

    /**
     * Returns a collection of users that are following a user with a specific userid.
     * @param userid, id of the user that is followed
     * @return Set<Users>, a collection of users
     */
    public Set<User> getUsersThatFollow(int userid);

    /**
     * Adds a user (with the id userBeingFollowed) to the collection of followers that the user (with
     * the id useridFolling) is following. Returns the id of the tuple in ru_followers table where the user
     * was added.
     * @param useridFollowing, user that is adding the follower to his collection of followers
     * @param useridBeingFollowed, user that is to be followed.
     * @return int, returns -1 if the user is already being followed or failed to add.
     */
    public int addFollower(int useridFollowing, int useridBeingFollowed);

}
