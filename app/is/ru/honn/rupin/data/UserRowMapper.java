package is.ru.honn.rupin.data;

import is.ru.honn.rupin.domain.User;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements ParameterizedRowMapper<User>
{
    public User mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        return  new User(rs.getInt(1),  // id
                rs.getString(2),        // name
                rs.getString(3),        // username
                rs.getString(4),        // password
                rs.getString(5));       // email
    }
}
