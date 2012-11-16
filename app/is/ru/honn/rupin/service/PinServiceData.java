/*package is.ru.honn.rupin.service;

import is.ru.honn.rupin.data.BoardDataGateway;
import is.ru.honn.rupin.data.PinDataGateway;
import is.ru.honn.rupin.data.UserDataGateway;
import is.ru.honn.rupin.domain.Board;
import is.ru.honn.rupin.domain.Pin;
import is.ru.honn.rupin.domain.User;
import org.springframework.dao.DataAccessException;

import java.util.List;

public class PinServiceData implements PinService
{
  UserDataGateway userDataGateway;
  BoardDataGateway boardDataGateway;
  PinDataGateway pinDataGateway;

  public PinServiceData()
  {
  }

  public void setUserDataGateway(UserDataGateway userDataGateway)
  {
    this.userDataGateway = userDataGateway;
  }

  public void setBoardDataGateway(BoardDataGateway boardDataGateway)
  {
    this.boardDataGateway = boardDataGateway;
  }

  public void setPinDataGateway(PinDataGateway pinDataGateway)
  {
    this.pinDataGateway = pinDataGateway;
  }

  @Override
  public Board getBoard(String username, String boardname)
  {
    return boardDataGateway.getBoard(username, boardname);
  }

  @Override
  public List<Board> getBoards(String username)
  {
    return boardDataGateway.getBoards(username);
  }

  @Override
  public Board createBoard(String username, String boardname, String category) throws UserNotFoundException
  {
    // Check the user
    User user = userDataGateway.getUserByUsername(username);
    if (user == null)
      throw new UserNotFoundException();

    // Get the board
    Board board = new Board(boardname, category);
    try
    {
      boardDataGateway.add(board, username);
    } catch (DataAccessException daex)
    {
      return null;
    }
    return board;
  }

  @Override
  public Pin createPin(String username, String boardname, String link, String description) throws BoardNotFoundException
  {
    Board board = boardDataGateway.getBoard(username, boardname);
    Pin pin = new Pin(link, description, null);
    board.addPin(pin);
    pinDataGateway.add(pin, username, boardname);
    return pin;
  }

  @Override
  public List<Pin> getPinsOnBoard(String username, String boardname)
  {
    List<Pin> pins = pinDataGateway.getPinsOnBoard(username, boardname);
    return pins;
  }
}
*/