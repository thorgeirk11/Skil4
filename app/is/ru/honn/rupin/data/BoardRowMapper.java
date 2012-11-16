package is.ru.honn.rupin.data;

import is.ru.honn.rupin.domain.Board;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class BoardRowMapper implements RowMapper
{
  @Override
  public Object mapRow(ResultSet resultSet, int i) throws SQLException
  {
    Board board = new Board(resultSet.getString(1),   // name
                            resultSet.getString(2));  // category
    return board;
  }
}
