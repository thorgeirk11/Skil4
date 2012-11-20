package controllers;

import com.google.gson.Gson;
import is.ru.honn.rupin.domain.User;
import is.ru.honn.rupin.service.PinService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import play.mvc.Controller;

import java.util.logging.Logger;
/**
 *
 * A controller that loads the PinService for extending controllers.
 *
 * Created with IntelliJ IDEA.
 * User: Sir.Thorgeir lap
 * Date: 16.10.2012
 * Time: 12:14
 *
 * @author Thorgeir Audunn Karlsson and Gudny Bjork Gunnarsdottir.
 */
public class ServiceController extends Controller {
    protected static ApplicationContext ctx = new FileSystemXmlApplicationContext("/conf/ApplicationContext.xml");
    protected static PinService pinService;
    protected static Logger log = Logger.getLogger(ServiceController.class.getName());
    protected static Gson jsonParser = new Gson();

    /**
     * Sets the pinService to the corresponding pinService
     * @param pinService,
     */
    public void setPinService(PinService pinService) {
        this.pinService = pinService;
    }

    /**
     * Loads the pinService from the ApplicationContext.xml
     */
    public static void loadPinService() {
        pinService = (PinService) ctx.getBean("PinService");
    }

    /**
     * Returns the user from the jason string that is fetched with session.
     * @return User
     */
    public static User getSessionUser() {
        String jsonString = session().get("User");
        if (jsonString != null)
            return jsonParser.fromJson(jsonString, User.class);
        return null;
    }
}
