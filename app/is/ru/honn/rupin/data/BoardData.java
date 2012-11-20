package is.ru.honn.rupin.data;

import is.ru.honn.rupin.data.mappers.BoardRowMapper;
import is.ru.honn.rupin.domain.Board;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * A class that implements the BoardDataGateway. Stores or receives data from the ru_boards table.
 *
 * Created with IntelliJ IDEA.
 * User: Sir.Thorgeir lap
 * Date: 16.10.2012
 * Time: 19:03
 *
 * @author Thorgeir Audunn Karlsson and Gudny Bjork Gunnarsdottir.
 */
public class BoardData extends ContentDataGateWay implements BoardDataGateway {
    public BoardData() {
    }

    public BoardData(DataSource dataSource) {
        setDataSource(dataSource);
    }

    /**
     * Adds a board to the table ru_board in the db if the board doesn't already exists.
     * Returns the id of the board if it was successfully inserted.
     *
     * @param board, the board to be added to the db
     * @return int, if board already exists then return -1
     */
    public int add(Board board) {
        if (con == null)
            OpenConnection(); //Opens a connection to the db if not connected.
        SimpleJdbcInsert insertContent =
                new SimpleJdbcInsert(this.getDataSource())
                        .withTableName("ru_boards")
                        .usingGeneratedKeyColumns("ID");

        Map<String, Object> parameters = new HashMap<String, Object>(3);
        parameters.put("userName", board.getCreator().getUsername());
        parameters.put("boardName", board.getName());
        parameters.put("category", board.getCategory());
        try {
            Number n = insertContent.executeAndReturnKey(parameters);
            return n.intValue();
        } catch (DataIntegrityViolationException divex) {
            log.warning("Duplicate entry for board with name '" + board.getName() + "' for user: '" + board.getCreator().getUsername() + "'.");
        }
        return -1;
    }

    /**
     * Returns a board with a specific boardName and a userName from the result set.
     *
     * @param userName,  username of the User owning the board to be returned
     * @param boardName, name of the board to be returned
     * @return Board, if the board is not found then returns null
     */
    public Board getBoard(String userName, String boardName) {
        if (con == null)
            OpenConnection(); //Opens a connection to the db if not connected.
        String query = "SELECT * FROM ru_boards WHERE userName = '" + userName + "' and boardName = '" + boardName + "'";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next())
                    return new BoardRowMapper(getDataSource()).mapRow(rs, 1337); //just for the elites
        } catch (SQLException sqlex) {
            log.warning("Unable to get board '" + boardName + "' for user '" + userName + "'::\n" + sqlex.getMessage() + "\nSql Query:\n" + query);
        } catch (Exception ex) {
            log.warning("Unable to get board '" + boardName + "' for user '" + userName + "':\n" + ex.getMessage() + "\nSql Query:\n" + query);
        }
        return null;
    }

    /**
     * Returns a board with a specific ID from the result set.
     *
     * @param ID, id of the board to be returned
     * @return Board, if the board is not found then returns null
     */
    public Board getBoardByID(int ID) {
        if (con == null)
            OpenConnection(); //Opens a connection to the db if not connected.
        String query = "SELECT * FROM ru_boards WHERE ID = " + ID;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next())
                return new BoardRowMapper(getDataSource()).mapRow(rs, 42);//just for the elites ;)
        } catch (SQLException sqlex) {
            log.warning("Unable to get board id '" + ID + "':\n" + sqlex.getMessage() + "\nSql Query:\n" + query);
        } catch (Exception ex) {
            log.warning("Unable to get board id '" + ID + "':\n" + ex.getMessage() + "\nSql Query:\n" + query);
        }
        return null;
    }


    /**
     * Returns a list of boards with the provided username
     * @param userName, username of the User owning the boards to be returned
     * @return a list of boards
     */
    public List<Board> getBoards(String userName) {
        if (con == null)
            OpenConnection(); //Opens a connection to the db if not connected.
        LinkedList<Board> bl = new LinkedList<Board>();
        String query = "SELECT * FROM ru_boards WHERE userName = '" + userName + "'";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
                bl.add(new BoardRowMapper(getDataSource()).mapRow(rs, 1337));
        } catch (SQLException sqlex) {
            log.warning("Unable to get all Boards for the user: '" + userName + "':\n" + sqlex.getMessage() + "\nSql Query:\n" + query);
        } catch (Exception ex) {
            log.warning("Unable to get all Boards for the user: '" + userName + "':\n" + ex.getMessage() + "\nSql Query:\n" + query);
        }
        return bl;
    }

    /**
     * Returns the board's id with a specific boardName and a userName from the result set.
     *
     * @param userName,  username of the user owning the board with the id to be returned
     * @param boardName, name of the board with the id to be returned
     * @return int, if id is not found then returns -1
     */
    public int getBoardID(String userName, String boardName) {
        if (con == null)
            OpenConnection(); //Opens a connection to the db if not connected.
        String query = "SELECT * FROM ru_boards WHERE userName = '" + userName + "' and boardName = '" + boardName + "'";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next())
                return rs.getInt("ID");
        } catch (SQLException sqlex) {
            log.warning("Unable to get board '" + boardName + "' for user '" + userName + "'::\n" + sqlex.getMessage() + "\nSql Query:\n" + query);
        } catch (Exception ex) {
            log.warning("Unable to get board '" + boardName + "' for user '" + userName + "':\n" + ex.getMessage() + "\nSql Query:\n" + query);
        }
        return -1;
    }
}
