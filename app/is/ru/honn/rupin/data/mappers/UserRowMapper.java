package is.ru.honn.rupin.data.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;


import is.ru.honn.rupin.domain.Board;
import is.ru.honn.rupin.domain.User;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class UserRowMapper implements ParameterizedRowMapper<User> {
    /**
     * Takes in a result set and creates a new user and picks out the data from the result set and fills the
     * user instance with the data.
     * @param rs, the result set where the data is taken from.
     * @param rowNum, just here to be compliment for the interface. Not needed.
     * @return User
     * @throws SQLException
     */
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("ID"));
        user.setUsername(rs.getString("userName"));
        user.setName(rs.getString("Name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("pass"));
        return user;
    }
}