package controllers;

import is.ru.honn.rupin.data.BoardDataGateway;
import is.ru.honn.rupin.domain.Board;
import is.ru.honn.rupin.domain.Pin;
import is.ru.honn.rupin.domain.User;
import is.ru.honn.rupin.domain.UserRegistration;
import play.mvc.*;
import play.data.*;
import views.html.board.addPin;
import views.html.board.board;
import views.html.board.boards;

import java.util.ArrayList;

public class BoardController extends ServiceController {
    final static Form<Pin> addPinForm = form(Pin.class);

    public static Result board(long id) {
        loadPinService();
        return ok(board.render(pinService.getBoard(id)));
    }

    public static Result boards(String userName) {
        loadPinService();
        return ok(boards.render(pinService.getBoards(userName)));
    }

    public static Result boards() {
        loadPinService();
        return ok(boards.render(pinService.getBoards(getSessionUser().getUsername())));
    }

    public static Result addPin(long boardId) {
        loadPinService();
        return ok(addPin.render(pinService.getBoard(boardId),addPinForm));
    }

    public static Result addPinSubmit(long boardId) {
        return ok(board.render(new Board("Stuff", "Cata")));
    }
}

