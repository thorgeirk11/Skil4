package controllers;

import is.ru.honn.rupin.domain.*;
import is.ru.honn.rupin.service.UsernameExistsException;
import play.data.Form;
import play.mvc.*;
import views.html.*;
import views.html.user.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
/**
 *
 * A controller that extends ServiceController and retrieves data from the Forms and uses the pinService
 * to retrieve data from the database.
 *
 * Created with IntelliJ IDEA.
 * User: Sir.Thorgeir lap
 * Date: 16.10.2012
 * Time: 12:14
 *
 * @author Thorgeir Audunn Karlsson and Gudny Bjork Gunnarsdottir.
 */
public class UserController extends ServiceController {
    final static Form<UserRegistration> signUpForm = form(UserRegistration.class);
    final static Form<UserAuthentication> loginForm = form(UserAuthentication.class);

    /**
     * Renders the Form from the View to the UserRegistration
     * @return Result, returns a View
     */
    public static Result signUp() {
        return ok(signupform.render(signUpForm));
    }

    /**
     * Renders the user created from the signUpForm to the pinService and adds him to the database. If the filledForm
     * is accepted and the password is longer than 4 digits and is equal to the repeated password. Else return a
     * rejected statement.
     * @return Result, returns a View.
     */
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
            session().put("User", jsonParser.toJson(created, User.class));
            return ok(summary.render(created));
        }

    }

    /**
     * Renders a Form from the View to the loginform.
     * @return Result, returns a View
     */
    public static Result login() {
        session().clear();
        return ok(loginform.render(loginForm));
    }

    /**
     * Renders the user from the loginForm to the pinService if the user exist else return a badRequest.
     * @return Result, returns a View.
     */
    public static Result loginSubmit() {
        Form<UserAuthentication> filledForm = loginForm.bindFromRequest();
        loadPinService();
        User logedInUser = pinService.login(filledForm.get().getUsername(), filledForm.get().getPassword());
        if (logedInUser == null) {
            filledForm.reject("wrongCredentials", "The login you entered is incorrect.");
            return badRequest(loginform.render(filledForm));
        }
        session().put("User", jsonParser.toJson(logedInUser, User.class));
        return index();
    }

    /**
     * Renders the all the pins that belong to the users that the corresponding user is following to the
     * front page if the user exists.
     * @return Result, returns a View
     */
    public static Result index() {
        User user = getSessionUser();
        if (user != null) {
            loadPinService();
            ArrayList<Pin> pins = new ArrayList<Pin>();
            for (User u : user.getFollowers()) {
                for (Board b : pinService.getBoards(u.getUsername()))
                    pins.addAll(b.getPins());
            }
            Collections.sort(pins, Pin.CompareByCreated);
            return ok(frontPage.render(pins));
        }
        return ok(index.render());
    }
}