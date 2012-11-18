package is.ru.honn.rupin.data.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;


import is.ru.honn.rupin.data.BoardData;
import is.ru.honn.rupin.data.UserData;
import is.ru.honn.rupin.domain.Board;
import is.ru.honn.rupin.domain.Pin;
import is.ru.honn.rupin.domain.User;
import is.ruframework.data.RuData;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import javax.sql.DataSource;

public class PinRowMapper extends RuData implements ParameterizedRowMapper<Pin> {
    public PinRowMapper() {
    }

    public PinRowMapper(DataSource dataSource) {
        setDataSource(dataSource);
    }

    /**
     * Takes in a result set and creates a new pin and picks out the data from the result set and fills the
     * pin instance with the data.
     * In order to get the board's id we need to call boardData.
     * @param rs, the result set where the data is taken from.
     * @param rowNum, just here to be compliment for the interface. Not needed.
     * @return Pin
     * @throws SQLException
     */
    public Pin mapRow(ResultSet rs, int rowNum) throws SQLException {
        Pin pin = new Pin();
        pin.setId(rs.getInt("ID"));
        pin.setLink(rs.getString("link"));
        pin.setImage(rs.getString("image"));
        pin.setDescription(rs.getString("description"));
        pin.setBoard(new BoardData(getDataSource()).getBoardByID(rs.getInt("boardID")));
        pin.setCreator(pin.getBoard().getCreator());
        pin.setCreated(rs.getDate("created"));

        return pin;
    }
}