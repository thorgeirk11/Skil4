package is.ru.honn.rupin.data;

import is.ru.honn.rupin.domain.Board;
import is.ruframework.data.RuDataAccess;

import java.util.List;

/**
 * An interface that represent a gateway to the ru_board table and provides
 * certain interaction to the table.
 *
 * Created with IntelliJ IDEA.
 * User: Sir.Thorgeir lap
 * Date: 16.10.2012
 * Time: 12:14
 *
 * @author Thorgeir Audunn Karlsson and Gudny Bjork Gunnarsdottir.
 */
public interface BoardDataGateway extends RuDataAccess {

    /**
     * Adds a board to the table ru_board in the db if the board doesn't already exists.
     * Returns the id of the board if it was successfully inserted.
     * @param board, the board to be added to the db
     * @return int, if board already exists then return -1
     */
    public int add(Board board);

    /**
     * Returns a board with a specific boardName and a userName from the result set.
     * @param userName, username of the User owning the board to be returned
     * @param boardName, name of the board to be returned
     * @return Board, if the board is not found then returns null
     */
    public Board getBoard(String userName,String boardName);

    /**
     * Returns a board with a specific boardName and a userName from the result set.
     * @param userName, username of the User owning the boards to be returned
     * @return List of Boards, if the board is not found then returns an empty list.
     */
    public List<Board> getBoards(String userName);

    /**
     * Returns a board with a specific ID from the result set.
     * @param ID, id of the board to be returned
     * @return Board, if the board is not found then returns null
     */
    public Board getBoardByID(int ID);

    /**
     * Returns the board's id with a specific boardName and a userName from the result set.
     * @param userName, username of the user owning the board with the id to be returned
     * @param boardName, name of the board with the id to be returned
     * @return int, if id is not found then returns -1
     */
    public int getBoardID(String userName,String boardName);
}
