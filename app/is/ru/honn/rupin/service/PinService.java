package is.ru.honn.rupin.service;

import is.ru.honn.rupin.domain.*;

import java.util.List;
import java.util.Set;

/**
 * An interface that proveds interaction with the database.
 * Created with IntelliJ IDEA.
 * User: Sir.Thorgeir lap
 * Date: 16.10.2012
 * Time: 12:48
 *
 * @author Þorgeir Auðunn Karlsson and Guðný Björk Gunnarsdóttir.
 */
public interface PinService {
    /**
     * Returns the board with the corresponding boardname which belongs to the user with the supplied username.
     * @param username, the username of the board owner
     * @param boardname, name of the board
     * @return Board
     */
    public Board getBoard(String username, String boardname);


    /**
     * Returns the board with the corresponding id
     * @param id, id of the board
     * @return Board
     */
    public Board getBoard(int id);

    /**
     * Returns a list of boards that belongs to a user with the supplied username.
     * and sets the pin belonging to the board on it.
     * @param username, the username of the board owner
     * @return a list of Boards
     */
    public List<Board> getBoards(String username);

    /**
     * Gets the user with the corresponding username from the userDataMapper and checks if he exists.
     * If he doesn't an exeption is thrown else the board is created.
     * The boardDataGateway is called to add the board if it doesn't already exists.
     * If it already exist we return null else the board that was added.
     * Username and boardname must be provided but the category is optional.
     * @param username, the username of the board owner
     * @param boardname, name of the board to be created
     * @param category, the category of the board
     * @return Board
     * @throws UserNotFoundException
     */
    public Board createBoard(String username, String boardname,
                             String category)
            throws UserNotFoundException;

    /**
     * Gets the board from the boardDataGateway, if it doesn't exists an exception is thrown.
     * The pin is created with supplied parameters and is added with the pinDataMapper.
     * If the pin already exists null is return else the pin.
     * @param username, the username of the pin owner
     * @param boardname, the name of the board the pin is to be added to
     * @param link, the link to be pinned
     * @param description, description of the pin
     * @param ImageUrl, image on the pin
     * @return Pin
     * @throws BoardNotFoundException
     */
    public Pin createPin(String username, String boardname,
                         String link, String description, String ImageUrl)
            throws BoardNotFoundException;

    /**
     * Calls boardDataMapper to fetch the board with the corresponding username and boardname.
     * If the board does not exist an empty list is returned.
     * Finally calls pinGate to get all the pins for the corresponding board.
     * @param username, username of the owner of the board
     * @param boardname, name of the board
     * @return list of pins
     */
    public List<Pin> getPinsOnBoard(String username, String boardname);

    /**
     *
     * @param ID, id of the pin to be liked
     */
    public void likePin(int ID);

    /**
     * Copies the pin with the corresponding pinID and adds it to the board
     * with the corresponding boardID
     * @param pinID, id of the pin that is to be copied
     * @param boardID, id of the board that is to be pinned.
     * @return Pin
     */
    public Pin rePin(int pinID, int boardID);

    /**
     * Creates a new user and calls the userDataMapper to check if the user already exists.
     *
     * @param name, name of the user
     * @param username, username of the user
     * @param password, password of the user
     * @param  email, email of the user
     * @throws UsernameExistsException
     * @return User
     */
    public User signUpUser(String name, String username, String password, String email) throws UsernameExistsException;

    /**
     * Checks if the user with the corresponding username and password exist and
     * returns the user.
     * @param username, username of the user that is logging in
     * @param password, password of the user that is logging in
     * @return User
     */
    public User login(String username, String password);

    /**
     * Returns the user with corresponding username
     *
     * @param username, username of the user
     * @return User
     */
    public User getUser(String username);

    /**
     * Returns a collection of users that all are following the user with
     * the corresponding username
     * @param usernname, username of the user that is followed.
     * @return Set<User>, a collection of users.
     */
    public Set<User> usersThatFollow(String usernname);

    /**
     * Returns a collection of users that the user (with the corresponding
     * username) is following.
     * @param usernname, usernam of the user that is following other users
     * @return Set<User>, a collection of users.
     */
    public Set<User> followersOfUser(String usernname);

    /**
     * Adds a user (with the corresponding usertoFollow username) to the
     * collection of followers that the user (with the corresponding followerName) is following.
     * @param followerName, username of the user that is following a new user
     * @param userToFollow, username of the user that is to be followed
     * @return User
     */
    public User addFollower(String followerName, String userToFollow);
}
