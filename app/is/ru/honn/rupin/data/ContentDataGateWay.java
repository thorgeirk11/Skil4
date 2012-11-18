package is.ru.honn.rupin.data;

import is.ruframework.data.RuData;
import is.ruframework.data.RuDataAccess;
import is.ruframework.domain.RuObject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Sir.Thorgeir lap
 * Date: 16.10.2012
 * Time: 13:00
 *
 * @author Þorgeir Auðunn Karlsson.
 */
public class ContentDataGateWay extends RuData {
    protected Connection con;

    /**
     * Returns a connection to the database
     * Returns a connection to the db for the gateways.
     * @return Connection
     */
    public Connection OpenConnection() {
        try {
            con = super.getDataSource().getConnection();
        } catch (SQLException ex) {
            log.severe("Could not open connection to database.\n" + ex.getMessage());
            con = null;
        }
        return con;
    }

    /**
     * Closes the connection to the db.
     */
    public void CloseConnection() {
        try {
            con.close();
        } catch (SQLException ex) {
            log.severe("Could not close connection to database.\n" + ex.getMessage());
        }
    }

}
