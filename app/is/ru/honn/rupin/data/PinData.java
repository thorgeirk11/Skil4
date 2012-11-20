package is.ru.honn.rupin.data;


import is.ru.honn.rupin.data.mappers.PinRowMapper;
import is.ru.honn.rupin.domain.Pin;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 *
 * A class that implements the PinDataMapper. Maps data to the ru_pin table.
 *
 * Created with IntelliJ IDEA.
 * User: Sir.Thorgeir lap
 * Date: 16.10.2012
 * Time: 19:03
 *
 * @author Thorgeir Audunn Karlsson and Gudny Bjork Gunnarsdottir.
 */

public class PinData extends ContentDataGateWay implements PinDataMapper {
    public PinData() {
    }

    /**
     * Sets dataSource to the corresponding dataSource
     * @param dataSource
     */
    public PinData(DataSource dataSource) {
        setDataSource(dataSource);
    }

    /**
     * Adds a pin to the table ru_pins in the db if the pin doesn't already exists.
     * Returns the id of the pin if it was successfully inserted.
     *
     * @param pin, the pin to be added to the db
     * @return int, if pin already exists then return -1
     */
    public int add(Pin pin) {
        if (con == null)
            OpenConnection(); //Opens a connection to the db if not connected.
        SimpleJdbcInsert insertContent = new SimpleJdbcInsert(this.getDataSource()).withTableName("ru_pins").usingGeneratedKeyColumns("ID");
        Map<String, Object> parameters = new HashMap<String, Object>(5);
        parameters.put("link", pin.getLink());
        parameters.put("image", pin.getImage());
        parameters.put("description", pin.getDescription());
        parameters.put("created", pin.getCreated());
        parameters.put("boardID", new BoardData(getDataSource()).getBoardID(pin.getBoard().getCreator().getUsername(), pin.getBoard().getName())); // Gets the id of the board.
//
        //Gets the id of the pin
        int returnKey = -1;
        try {
            returnKey = insertContent.executeAndReturnKey(parameters).intValue();
        } catch (DataIntegrityViolationException divex) {
            log.warning("Duplicate entry, Pin on board '" + pin.getBoard().getName()+"' already exists.");
        }
        return returnKey;
    }

    /**
     * Returns a list of pins which belong to a specific boardName and a userName from the result set.
     *
     * @param userName,  username of the User owning the board to be returned
     * @param boardName, name of the pin to be returned
     * @return List<Pin>, if pins not found then returns empty list.
     */
    public List<Pin> getPins(String userName, String boardName) {
        return getPins(new BoardData(getDataSource()).getBoardID(userName, boardName));
    }

    /**
     * Returns a list of pins with a specific ID from the result set
     *
     * @param boardID, the id of the board to be returned
     * @return List<Pin>, if pins not found then return empty list.
     */
    public List<Pin> getPins(int boardID) {
        if (con == null)
            OpenConnection(); //Opens a connection to the db if not connected.
        LinkedList<Pin> pl = new LinkedList<Pin>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ru_pins WHERE boardID =" + boardID);
            while (rs.next())
                pl.add(new PinRowMapper(getDataSource()).mapRow(rs, 1337));
        } catch (SQLException sqlex) {
            log.warning("Unable to get pins for Board: '" + boardID + "':\n" + sqlex.getMessage() + "\nSql Statement:\n" + sqlex.getSQLState());
        } catch (Exception ex) {
            log.warning("Unable to get pins for Board: '" + boardID + "':\n" + ex.getMessage());
        }
        return pl;
    }

    /**
     * Returns a certain pin with a a specific ID from the result set
     *
     * @param ID, id of the pin to be returned
     * @return Pin, if pin not found then returns null.
     */

    public Pin getPin(int ID) {
        if (con == null)
            OpenConnection(); //Opens a connection to the db if not connected.
        Pin p = new Pin();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ru_pins WHERE ID =" + ID);
            if (rs.next())
                return new PinRowMapper(getDataSource()).mapRow(rs, 42);
        } catch (SQLException sqlex) {
            log.warning("Unable to get pin for ID: '" + ID + "':\n" + sqlex.getMessage() + "\nSql Statement:\n" + sqlex.getSQLState());
        } catch (Exception ex) {
            log.warning("Unable to get pin for ID: " + ID + ":\n" + ex.getMessage());
        }
        return null;
    }
}
