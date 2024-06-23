package Model.CommandDesignPatternForOrderActions;

import java.sql.SQLException;

public class OrderCommand implements Command{
    private Chef chef;
    private int id;

    public OrderCommand(Chef chef,int id){
        this.chef=chef;this.id = id;
    }

    @Override
    public void execute() throws SQLException {
        chef.changeOrderStatus(id);
    }
}
