package controllers;

import com.google.gson.Gson;
import is.ru.honn.rupin.service.PinService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import play.mvc.Controller;

import java.util.logging.Logger;

public class ServiceController extends Controller {
    protected static ApplicationContext ctx = new FileSystemXmlApplicationContext("/conf/ApplicationContext.xml");
    protected static PinService pinService;
    protected static Logger log = Logger.getLogger(ServiceController.class.getName());
    protected static Gson jsonParser = new Gson();
    protected static final String loginStr = "logIn";

    public void setPinService(PinService pinService) {
        this.pinService = pinService;
    }

    public static void loadPinService() {
        pinService = (PinService) ctx.getBean("PinService");
    }

}
