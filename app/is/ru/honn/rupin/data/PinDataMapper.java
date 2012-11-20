package is.ru.honn.rupin.data;

import is.ru.honn.rupin.domain.Pin;
import is.ru.honn.rupin.domain.User;
import is.ruframework.data.RuDataAccess;

import java.util.List;
import java.util.Set;

/**
 *
 * An interface that maps to the ru_board table and provides certain interaction to the table.
 *
 * Created with IntelliJ IDEA.
 * User: Sir.Thorgeir lap
 * Date: 16.10.2012
 * Time: 12:14
 *
 * @author Thorgeir Audunn Karlsson and Gudny Bjork Gunnarsdottir.
 */
public interface PinDataMapper extends RuDataAccess{

    /**
     * Adds a pin to the table ru_pins in the db if the pin doesn't already exists.
     * Returns the id of the pin if it was successfully inserted.
     * @param pin, the pin to be added to the db
     * @return int, if pin already exists then return -1
     */
    public int add(Pin pin);

    /**
     * Returns a list of pins which belong to a specific boardName and a userName from the result set.
     * @param userName, username of the User owning the board to be returned
     * @param boardName, name of the pin to be returned
     * @return List<Pin>, if pins not found then returns empty list.
     */
    public List<Pin> getPins(String userName,String boardName);

    /**
     * Returns a list of pins with a specific ID from the result set
     * @param boardID, the id of the board to be returned
     * @return List<Pin>, if pins not found then return empty list.
     */
    public List<Pin> getPins(int boardID);

    /**
     * Returns a certain pin with a a specific ID from the result set
     * @param ID, id of the pin to be returned
     * @return Pin, if pin not found then returns null.
     */
    public Pin getPin(int ID);

    public Pin likePin(User user, int pinID);

    public Set<Integer> getLikersIds(int PinID);
}
