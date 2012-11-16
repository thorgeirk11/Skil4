package is.ru.honn.rupin.data;

import is.ru.honn.rupin.domain.User;
import is.ruframework.data.RuDataAccess;

public interface UserDataGateway extends RuDataAccess
{
  public int add(User user);
  public User getUserByUsername(String username);
  public User getUserByID(int id);
}
