package is.ru.honn.rupin.data;

import is.ru.honn.rupin.domain.User;
import is.ruframework.data.RuDataAccess;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Sir.Thorgeir lap
 * Date: 16.10.2012
 * Time: 12:14
 *
 * @author Þorgeir Auðunn Karlsson.
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

    //TODO Comment
    public Set<User> getFollowersOfUser(int userid);

    //TODO Comment
    public Set<User> getUsersThatFollow(int userid);

    //TODO Comment
    public int addFollower(int useridFollowing, int useridBeingFollowed);

}
