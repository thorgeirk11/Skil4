package is.ru.honn.rupin.domain;

import java.util.ArrayList;
import java.util.List;

public class Board extends PinObject {
    protected int id;
    protected String name;
    protected String category;
    List<Pin> pins = new ArrayList<Pin>();

    public Board() {
    }

    public Board(String name, String category) {

        this.name = name;
        this.category = category;
    }

    public Board(int boardID, String name, String category) {
        id = boardID;
        this.name = name;
        this.category = category;
    }

    public Board(int boardID) {
        id = boardID;
    }

    public Pin addPin(Pin pin) {
        pins.add(pin);
        pin.setBoard(this);
        return pin;
    }

    public List<Pin> getPins() {
        return pins;
    }

    public void setPins(List<Pin> pins) {
        this.pins = pins;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
