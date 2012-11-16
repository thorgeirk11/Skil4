package is.ru.honn.rupin.data;

import is.ru.honn.rupin.domain.Board;

import java.util.List;

public interface BoardDataGateway
{
  public int add(Board board, String username);
  public Board getBoard(String username, String boardname);
  public List<Board> getBoards(String username);

}

