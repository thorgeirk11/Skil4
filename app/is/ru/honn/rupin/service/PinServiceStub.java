package is.ru.honn.rupin.service;

import is.ru.honn.rupin.domain.Board;
import is.ru.honn.rupin.domain.Pin;
import is.ru.honn.rupin.domain.User;
import is.ru.honn.rupin.domain.UserAuthentication;
import org.codehaus.jackson.map.ser.std.StdArraySerializers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public abstract class PinServiceStub implements PinService {
    protected List<Board> boards = new ArrayList<Board>();
    protected List<User> users = new ArrayList<User>();
    int UserIdMax = 0;
    int BoardIdMax = 0;
    int PinIdMax = 0;

    @Override
    public Board getBoard(int id) {
        return new Board();
    }

    PinServiceStub() {
        try {
            //signUpUser("ari", "asdasd", "asdasd", "asdasd");
            //createBoard("asdasd","Board1","tech");
            //createBoard("asdasd","Board2","Stuff");
            //createPin("asdasd","Board2","Stuff", "tech"); //String username, String boardname, String link, String description
            //createPin("asdasd", "Board1", "Stuff", "tech");
        } catch (Exception ex) {
        }
    }

    @Override
    public User signUpUser(String name, String username, String password, String email)
            throws UsernameExistsException {
        if (getUser(username) != null)
            throw new UsernameExistsException("Notandi er þegar til.");
        User newUser = new User(UserIdMax++, name, username, password, email);
        users.add(newUser);
        return newUser;
    }

    @Override
    public User login(String username, String password) {
        for (User u : users)
            if (u.equals(new UserAuthentication(username, password)))
                return u;
        return null;
    }

    @Override
    public User getUser(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username))
                return u;
        }
        return null;
    }

    @Override
    public Set<User> usersThatFollow(String usernname) {
        Set<User> set = new TreeSet<User>();
        for (User u : users)
            for (User us : u.getFollowers())
                if (us.getUsername().equals(usernname)) {
                    set.add(u);
                    break;
                }
        return set;
    }

    @Override
    public Set<User> followersOfUser(String usernname) {
        for (User u : users)
            if (u.getUsername().equals(usernname))
                return u.getFollowers();
        return new TreeSet<User>();
    }

    @Override
    public User addFollower(String followerName, String userToFollow) {
        for (User u : users)
            if (u.getUsername().equals(userToFollow)) {
                for (User us : users)
                    if (us.getUsername().equals(followerName)) {
                        u.addFollower(us);
                        return us;
                    }
                break;
            }
        return null;
    }

    @Override
    public Board createBoard(String username, String name, String category)
            throws UserNotFoundException {
        User user = getUser(username);
        if (user == null)
            throw new UserNotFoundException();

        Board newBoard = new Board(name, category);

        newBoard.setCreator(user);
        boards.add(newBoard);
        return newBoard;
    }

    @Override
    public Pin createPin(String username, String boardname, String link, String description, String ImageUrl) throws BoardNotFoundException {
        Pin newPin = new Pin();
        Board b = getBoard(username, boardname);
        if (null == b)
            throw new BoardNotFoundException("Board does not exist");
        newPin.setBoard(b);
        newPin.setLink(link);
        newPin.setDescription(description);
        newPin.setCreator(b.getCreator());
        b.addPin(newPin);
        return newPin;
    }

    @Override
    public Board getBoard(String username, String boardname) {
        List<Board> userBoards = getBoards(username);
        for (Board b : userBoards) {
            if (b.getName().equals(boardname)) return b;
        }
        return null;
    }

    @Override
    public List<Board> getBoards(String username) {
        List<Board> userBoards = new ArrayList<Board>();
        for (Board b : boards) {
            if (b.getCreator().getUsername().equals(username))
                userBoards.add(b);
        }
        return userBoards;
    }

    @Override
    public List<Pin> getPinsOnBoard(String username, String boardname) {
        List<Pin> pinsOnBoard = new ArrayList<Pin>();
        Board b = getBoard(username, boardname);
        return b.getPins();
    }

    @Override
    public void likePin(int ID) {

    }

    @Override
    public Pin rePin(int pinID, int boardID) {
        Pin pin = null;
        for (Board b : boards)
            for (Pin p : b.getPins())
                if (p.getId() == pinID)
                    pin = p;
        for (Board b : boards)
            if (b.getId() == boardID)
                b.getPins().add(pin);
        return pin;
    }
}
