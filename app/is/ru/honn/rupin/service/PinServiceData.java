package is.ru.honn.rupin.service;

import is.ru.honn.rupin.data.BoardDataGateway;
import is.ru.honn.rupin.data.PinDataMapper;
import is.ru.honn.rupin.data.UserDataMapper;
import is.ru.honn.rupin.domain.Board;
import is.ru.honn.rupin.domain.Pin;
import is.ru.honn.rupin.domain.User;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Set;

/**
 *
 * Class that implements PinService and uses a dataMappers and Gateways to store the data.
 *
 * Created with IntelliJ IDEA.
 * User: Sir.Thorgeir lap
 * Date: 16.10.2012
 * Time: 12:48
 *
 * @author Þorgeir Auðunn Karlsson and Guðný Björk Gunnarsdóttir.
 */
public class PinServiceData implements PinService {
    UserDataMapper userDataMapper;
    BoardDataGateway boardDataGateway;
    PinDataMapper pinDataMapper;

    public PinServiceData() {
    }

    /**
     * Sets the UserDataMapper
     * @param userDataMapper
     */
    public void setUserDataMapper(UserDataMapper userDataMapper) {
        this.userDataMapper = userDataMapper;
    }

    /**
     * Set sthe BoardDataGateway
     * @param boardDataGateway
     */
    public void setBoardDataGateway(BoardDataGateway boardDataGateway) {
        this.boardDataGateway = boardDataGateway;
    }

    /**
     * Sets the pinDataMapper
     * @param pinDataMapper
     */
    public void setPinDataMapper(PinDataMapper pinDataMapper) {
        this.pinDataMapper = pinDataMapper;
    }

    /**
     * Returns the board with the corresponding boardname which belongs to the user with the supplied username.
     * @param username, the username of the board owner
     * @param boardname, name of the board
     * @return Board
     */
    @Override
    public Board getBoard(String username, String boardname) {
        return boardDataGateway.getBoard(username, boardname);
    }

    /**
     * Returns the board with the corresponding id
     * @param id, id of the board
     * @return Board
     */
    @Override
    public Board getBoard(int id) {
        Board b = boardDataGateway.getBoardByID(id);
        if (b != null)
            b.setPins(getPinsOnBoard(b.getCreator().getUsername(), b.getName()));
        return b;
    }

    /**
     * Returns a list of boards that belongs to a user with the supplied username.
     * and sets the pin belonging to the board on it.
     * @param username, the username of the board owner
     * @return a list of Boards
     */
    @Override
    public List<Board> getBoards(String username) {
        List<Board> boards = boardDataGateway.getBoards(username);
        for (Board b : boards) {
            b.setPins(getPinsOnBoard(username, b.getName()));
        }
        return boards;
    }

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
    @Override
    public Board createBoard(String username, String boardname, String category) throws UserNotFoundException {
        // Check the user
        User user = userDataMapper.getUserByUserName(username);
        if (user == null)
            throw new UserNotFoundException();

        // Get the board
        Board board = new Board(boardname, category);
        try {
            boardDataGateway.add(board);
        } catch (DataAccessException daex) {
            return null;
        }
        return board;
    }

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
    @Override
    public Pin createPin(String username, String boardname, String link, String description, String ImageUrl) throws BoardNotFoundException {
        Board board = boardDataGateway.getBoard(username, boardname);
        Pin pin = new Pin(link, description, ImageUrl);
        board.addPin(pin);
        pin.setBoard(board);
        pinDataMapper.add(pin);
        return pin;
    }

    /**
     * Calls boardDataMapper to fetch the board with the corresponding username and boardname.
     * If the board does not exist an empty list is returned.
     * Finally calls pinGate to get all the pins for the corresponding board.
     * @param username, username of the owner of the board
     * @param boardname, name of the board
     * @return list of pins
     */
    @Override
    public List<Pin> getPinsOnBoard(String username, String boardname) {
        return pinDataMapper.getPins(username, boardname);
    }

    /**
     *
     * @param ID, id of the pin to be liked
     */
    @Override
    public void likePin(int ID) {
        return;
    }

    /**
     * Copies the pin with the corresponding pinID and adds it to the board
     * with the corresponding boardID
     * @param pinID, id of the pin that is to be copied
     * @param boardID, id of the board that is to be pinned.
     * @return Pin
     */
    @Override
    public Pin rePin(int pinID, int boardID) {
        return pinDataMapper.getPin(pinID);
    }

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
    @Override
    public User signUpUser(String name, String username, String password, String email) throws UsernameExistsException {
        User u = new User(name, username, password, email);
        if (userDataMapper.getUserByUserName(username) != null)
            throw new UsernameExistsException();
        userDataMapper.add(u);
        return u;
    }

    /**
     * Checks if the user with the corresponding username and password exist and
     * returns the user.
     * @param username, username of the user that is logging in
     * @param password, password of the user that is logging in
     * @return User
     */
    @Override
    public User login(String username, String password) {
        User u = userDataMapper.getUserByUserName(username);
        if (u != null && u.getPassword().equals(password))
            return u;
        return null;
    }

    /**
     * Returns the user with corresponding username
     *
     * @param username, username of the user
     * @return User
     */
    @Override
    public User getUser(String username) {
        return userDataMapper.getUserByUserName(username);
    }

    /**
     * Returns a collection of users that all are following the user with
     * the corresponding username
     * @param usernname, username of the user that is followed.
     * @return Set<User>, a collection of users.
     */
    @Override
    public Set<User> usersThatFollow(String usernname) {
        return userDataMapper.getUsersThatFollow(getUser(usernname).getId());
    }

    /**
     * Returns a collection of users that the user (with the corresponding
     * username) is following.
     * @param usernname, usernam of the user that is following other users
     * @return Set<User>, a collection of users.
     */
    @Override
    public Set<User> followersOfUser(String usernname) {
        return userDataMapper.getFollowersOfUser(getUser(usernname).getId());
    }

    /**
     * Adds a user (with the corresponding usertoFollow username) to the
     * collection of followers that the user (with the corresponding followerName) is following.
     * @param followerName, username of the user that is following a new user
     * @param userToFollow, username of the user that is to be followed
     * @return User
     */
    @Override
    public User addFollower(String followerName, String userToFollow) {
        int id = userDataMapper.addFollower(getUser(followerName).getId(), getUser(userToFollow).getId());
        if (id == -1)
            return null;
        return userDataMapper.getUserByID(id);
    }
}
