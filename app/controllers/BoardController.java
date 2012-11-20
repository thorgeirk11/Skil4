package controllers;

import is.ru.honn.rupin.data.BoardDataGateway;
import is.ru.honn.rupin.domain.Board;
import is.ru.honn.rupin.domain.Pin;
import is.ru.honn.rupin.domain.User;
import is.ru.honn.rupin.domain.UserRegistration;
import is.ru.honn.rupin.service.BoardNotFoundException;
import play.mvc.*;
import play.data.*;
import views.html.board.addPin;
import views.html.board.board;
import views.html.board.boards;

import java.util.ArrayList;

/**
 *
 * A controller that extends ServiceController and retrieves data from the Form and uses the pinService
 * to retrieve data from the database.
 *
 * Created with IntelliJ IDEA.
 * User: Sir.Thorgeir lap
 * Date: 16.10.2012
 * Time: 12:14
 *
 * @author Thorgeir Audunn Karlsson and Gudny Bjork Gunnarsdottir.
 */
public class BoardController extends ServiceController {
    final static Form<Pin> addPinForm = form(Pin.class);

    /**
     * Renders the board with the corresponding id to a view if it exists else return a badRequest
     * @param id, the id of the board to be rendered
     * @return Result, returns a View
     */
    public static Result board(Integer id) {
        if (getSessionUser() != null) {
            loadPinService();
            Board b = pinService.getBoard(id);
            if (b != null)
                return ok(board.render(b));
        }
        return badRequest();
    }

    /**
     * Renders the board belonging to a user with corresponding userName if the user exists, else
     * return a badRequest
     * @param userName, username of the user owning the board
     * @return Result, returns a View
     */
    public static Result boards(String userName) {
        if (getSessionUser() != null) {
            loadPinService();
            return ok(boards.render(pinService.getBoards(userName)));
        }
        return badRequest();
    }

    /**
     * Renders all boards belonging to a user
     * @return Result, returns a View
     */
    public static Result myBoards() {
        if (getSessionUser() != null) {
            loadPinService();
            return ok(boards.render(pinService.getBoards(getSessionUser().getUsername())));
        }
        return badRequest();
    }

    /**
     * Renders a pin created from the view to a board with the corresponding boardId if the
     * user exist else return a badRequest.
     * @param boardId
     * @return Result, returns a View
     */
    public static Result addPin(Integer boardId) {
        if (getSessionUser() != null) {
            loadPinService();
            return ok(addPin.render(pinService.getBoard(boardId), addPinForm));
        }
        return badRequest();
    }

    /**
     * Adds the pin from the Form to the PinService, if the pin's link, descriptin are declared or any other
     * errors did not occur, else return a badRequest
     * @param boardId, id of the board to be added on
     * @return Result, returns a View
     */
    public static Result addPinSubmit(Integer boardId) {
        User u = getSessionUser();
        if (u != null) {
            Form<Pin> filledForm = addPinForm.bindFromRequest();
            Pin p = filledForm.get();
            if (p.getLink() == null || p.getLink() == "")
                filledForm.reject("link", "There must be a link");
            if (p.getDescription() == null || p.getDescription() == "")
                filledForm.reject("description", "There must be a description");
            if (filledForm.hasErrors())
                return badRequest(addPin.render(pinService.getBoard(boardId), filledForm));
            loadPinService();
            Board ownedByBoard = pinService.getBoard(boardId);
            p.setBoard(ownedByBoard);
            p.setCreator(u);
            try {
                pinService.createPin(ownedByBoard.getCreator().getUsername(), ownedByBoard.getName(), p.getLink(), p.getDescription(), p.getImage());
            } catch (BoardNotFoundException ex) {
                filledForm.reject("BoardID", "Board not found.");
                return badRequest(addPin.render(pinService.getBoard(boardId), filledForm));
            }
            return board(boardId);
        }
        return badRequest();
    }
}

