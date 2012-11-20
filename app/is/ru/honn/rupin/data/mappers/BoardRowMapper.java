package is.ru.honn.rupin.data.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;


import is.ru.honn.rupin.data.UserData;
import is.ru.honn.rupin.data.UserDataMapper;
import is.ru.honn.rupin.domain.Board;
import is.ru.honn.rupin.domain.User;
import is.ruframework.data.RuData;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import javax.sql.DataSource;

/**
 * Maps a result set of a board to a class.
 *
 * Created with IntelliJ IDEA.
 * User: Sir.Thorgeir lap
 * Date: 16.10.2012
 * Time: 19:03
 *
 * @author Thorgeir Audunn Karlsson and Gudny Bjork Gunnarsdottir.
 */
public class BoardRowMapper extends RuData implements ParameterizedRowMapper<Board> {
    public BoardRowMapper() {
    }

    /**
     * Sets the dataSource
     * @param dataSource, the dataSource that will be used to initialize data
     */
    public BoardRowMapper(DataSource dataSource) {
        setDataSource(dataSource);
    }

    /**
     * Takes in a result set and creates a new board and picks out the data from the result set and fills the
     * board instance with the data.
     * To be able to set the creator it has to call the userData in order to get the user.
     * @param rs,     the result set where the data is taken from.
     * @param rowNum, Just here to be compliment for the interface. Not needed.
     * @return Board
     * @throws SQLException
     */
    public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
        Board board = new Board();
        board.setCreator(new UserData(getDataSource()).getUserByUserName(rs.getString("userName")));
        board.setId(rs.getInt("ID"));
        board.setName(rs.getString("boardName"));
        board.setCategory(rs.getString("category"));
        board.setCreated(rs.getDate("created"));
        return board;
    }
}