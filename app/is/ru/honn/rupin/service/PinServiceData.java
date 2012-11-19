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

public class PinServiceData implements PinService {
    UserDataMapper userDataMapper;
    BoardDataGateway boardDataGateway;
    PinDataMapper pinDataMapper;

    public PinServiceData() {
    }

    public void setUserDataMapper(UserDataMapper userDataMapper) {
        this.userDataMapper = userDataMapper;
    }

    public void setBoardDataGateway(BoardDataGateway boardDataGateway) {
        this.boardDataGateway = boardDataGateway;
    }

    public void setPinDataMapper(PinDataMapper pinDataMapper) {
        this.pinDataMapper = pinDataMapper;
    }

    @Override
    public Board getBoard(String username, String boardname) {
        return boardDataGateway.getBoard(username, boardname);
    }

    @Override
    public Board getBoard(long id) {
        Board b = boardDataGateway.getBoardByID((int) id);
        b.setPins(getPinsOnBoard(b.getCreator().getUsername(), b.getName()));
        return b;
    }

    @Override
    public List<Board> getBoards(String username) {
        List<Board> boards = boardDataGateway.getBoards(username);
        for (Board b : boards) {
            b.setPins(getPinsOnBoard(username, b.getName()));
        }
        return boards;
    }

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

    @Override
    public Pin createPin(String username, String boardname, String link, String description, String ImageUrl) throws BoardNotFoundException {
        Board board = boardDataGateway.getBoard(username, boardname);
        Pin pin = new Pin(link, description, ImageUrl);
        board.addPin(pin);
        pin.setBoard(board);
        pinDataMapper.add(pin);
        return pin;
    }

    @Override
    public List<Pin> getPinsOnBoard(String username, String boardname) {
        return pinDataMapper.getPins(username, boardname);
    }

    @Override
    public void likePin(int ID) {
        return;
    }

    @Override
    public Pin rePin(int pinID, int boardID) {
        return pinDataMapper.getPin(pinID);
    }

    @Override
    public User signUpUser(String name, String username, String password, String email) throws UsernameExistsException {
        User u = new User(name, username, password, email);
        if (userDataMapper.getUserByUserName(username) != null)
            throw new UsernameExistsException();
        userDataMapper.add(u);
        return u;
    }

    @Override
    public User login(String username, String password) {
        User u = userDataMapper.getUserByUserName(username);
        if (u != null && u.getPassword().equals(password))
            return u;
        return null;
    }

    @Override
    public User getUser(String username) {
        return userDataMapper.getUserByUserName(username);
    }

    @Override
    public Set<User> usersThatFollow(String usernname) {
        return userDataMapper.getUsersThatFollow(getUser(usernname).getId());
    }

    @Override
    public Set<User> followersOfUser(String usernname) {
        return userDataMapper.getFollowersOfUser(getUser(usernname).getId());
    }

    @Override
    public User addFollower(String followerName, String userToFollow) {
        int id = userDataMapper.addFollower(getUser(followerName).getId(), getUser(userToFollow).getId());
        if (id == -1)
            return null;
        return userDataMapper.getUserByID(id);
    }
}
