package is.ru.honn.rupin.domain;

import java.util.Comparator;
import java.util.Date;

public class PinObject {
    public static Comparator<PinObject> CompareByCreated = new Comparator<PinObject>() {
        public int compare(PinObject p1, PinObject p2) {
            return p1.getCreated().compareTo(p2.getCreated()) == 1 ? -1 : p1.getCreated().compareTo(p2.getCreated()) == 0 ? 0 : 1 ;
        }
    };
    public static Comparator<PinObject> CompareByCreator = new Comparator<PinObject>() {
        public int compare(PinObject p1, PinObject p2) {
            return p1.getCreator().compareTo(p2.getCreator());
        }
    };

    protected User creator;
    protected Date created = new Date();

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
