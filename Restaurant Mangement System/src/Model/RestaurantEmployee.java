package Model;

import java.sql.SQLException;

public interface RestaurantEmployee {

    public String getGmail();
    public void setGmail(String email);
    public String getPassword();
    public String getName();
    public void setName(String name);
    public void setPassword(String password);
    public String getChefTime();
    public void setChefTime(String workingTime);
    public boolean changeOrderStatus(int id) throws SQLException;
    public boolean deleteOrder(int id) throws SQLException;
}
