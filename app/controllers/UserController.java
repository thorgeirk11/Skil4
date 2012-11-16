package controllers;

import com.google.gson.Gson;
import is.ru.honn.rupin.data.UserDataGateway;
import is.ru.honn.rupin.domain.Board;
import is.ru.honn.rupin.domain.User;
import is.ru.honn.rupin.domain.UserAuthentication;
import is.ru.honn.rupin.domain.UserRegistration;
import is.ru.honn.rupin.service.PinService;
import is.ru.honn.rupin.service.UsernameExistsException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import play.data.Form;
import play.mvc.*;
import views.html.*;
import views.html.helper.form;
import views.html.user.*;

import java.util.ArrayList;

public class UserController extends ServiceController {
    final static Form<UserRegistration> signUpForm = form(UserRegistration.class);
    final static Form<UserAuthentication> loginForm = form(UserAuthentication.class);

    public static Result signUp() {
        return ok(signupform.render(signUpForm));
    }

    public static Result signUpSubmit() {
        Form<UserRegistration> filledForm = signUpForm.bindFromRequest();

        if (!"true".equals(filledForm.field("accept").value()))
            filledForm.reject("accept", "You must accept the terms and conditions");

        if (filledForm.field("username").value().length() < 4)
            filledForm.reject("username", "Username must be at least 4 characters");

        if (!filledForm.field("password").value().equals(filledForm.field("repeatPassword").value()))
            filledForm.reject("repeatPassword", "Password does not match");

        if (filledForm.hasErrors())
            return badRequest(signupform.render(filledForm));
        else {
            User created = filledForm.get();

            try {
                loadPinService();
                pinService.signUpUser(created.getName(), created.getUsername(), created.getPassword(), created.getEmail());
            } catch (UsernameExistsException ex) {
                filledForm.reject("UsernameExistsException", "Username already exists Exception");
                return badRequest(signupform.render(filledForm));
            }
            return ok(summary.render(created));
        }

    }

    public static Result login() {
        return ok(loginform.render(loginForm));//form.render(loginForm));
    }

    public static Result loginSubmit() {
        session().clear();
        Form<UserAuthentication> filledForm = loginForm.bindFromRequest();
        loadPinService();
        User logedInUser = pinService.login(filledForm.get().getUsername(), filledForm.get().getPassword());
        if (logedInUser == null) {
            filledForm.reject("wrongCredentials", "The login you entered is incorrect.");
            return badRequest(loginform.render(filledForm));
        }
        session().put(loginStr, "true");
        session().put("User", jsonParser.toJson(logedInUser, User.class));
        return ok(index.render());
    }

    public static Result index() {
        String logStatus = session().get(loginStr);
        if (logStatus != null && logStatus.equals("true") && getSessionUser() != null) {
            loadPinService();
            User user = getSessionUser();
            ArrayList<Board> boards = new ArrayList<Board>();
            for (User u : user.getFollowers()) {
                boards.addAll(pinService.getBoards(u.getUsername()));
            }
            return ok(frontPage.render(boards));
        }
        return ok(index.render());
    }

    private static User getSessionUser() {
        String jsonString = session().get("User");
        if (jsonString != null)
            return jsonParser.fromJson(jsonString, User.class);
        return null;
    }
}