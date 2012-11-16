package is.ru.honn.rupin.service;

import is.ru.honn.rupin.domain.*;

import java.util.List;
import java.util.Set;

public interface PinService {
    public Board getBoard(String username, String boardname);

    public Board getBoard(long id);

    public List<Board> getBoards(String username);

    public Board createBoard(String username, String boardname,
                             String category)
            throws UserNotFoundException;

    public Pin createPin(String username, String boardname,
                         String link, String description)
            throws BoardNotFoundException;

    public List<Pin> getPinsOnBoard(String username, String boardname);

    public void likePin(int ID);

    public Pin rePin(int pinID, int boardID);

    public User signUpUser(String name, String username, String password, String email) throws UsernameExistsException;

    public User login(String username, String password);

    public User getUser(String username);

    //Listi af þeim sem fyljag notanda
    public Set<User> usersThatFollow(String usernname);

    //Listi af þeim sem fyljag ég fylgi
    public Set<User> followersOfUser(String usernname);

    public User addFollower(String followerName, String userToFollow);
}
