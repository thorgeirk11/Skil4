package is.ru.honn.rupin.data;

import is.ru.honn.rupin.domain.Pin;
import is.ruframework.data.RuDataAccess;

import java.util.List;

public interface PinDataGateway extends RuDataAccess
{
  public int add(Pin pin, String boardname, String username);
  public List<Pin> getPinsOnBoard(String boardname, String username);
}
