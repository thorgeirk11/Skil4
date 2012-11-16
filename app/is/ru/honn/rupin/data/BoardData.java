package is.ru.honn.rupin.data;

import is.ru.honn.rupin.domain.Board;
import is.ruframework.data.RuData;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardData extends RuData implements BoardDataGateway
{
  @Override
  public int add(Board board, String username)
  {
    SimpleJdbcInsert insert =
        new SimpleJdbcInsert(getDataSource())
            .withTableName("ru_boards");

    Map<String, Object> parameters = new HashMap<String, Object>(3);
    parameters.put("boardname", board.getName());
    parameters.put("category", board.getCategory());
    parameters.put("username", username);

    return insert.execute(parameters);
  }

  @Override
  public Board getBoard(String username, String boardname)
  {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
    Board board = (Board)jdbcTemplate.queryForObject(
        "select * from ru_boards where username=? and boardname=?",
        new BoardRowMapper(), username, boardname);
    return board;
  }

  @Override
  public List<Board> getBoards(String username)
  {
    Collection<String> users;
    JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
    List<Board> boards = (List<Board>)jdbcTemplate.query(
        "select * from ru_boards where username=?", new BoardRowMapper(), username);
    return boards;
  }
}
