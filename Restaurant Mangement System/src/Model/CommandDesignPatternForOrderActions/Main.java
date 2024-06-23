package Model.CommandDesignPatternForOrderActions;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Chef chef = new Chef();
        Command orderCommand = new OrderCommand(chef,15);
        Command cancelOrderCommand = new CancelOrderCommand(chef,15);


        invokerClass invokerClass = new invokerClass();




        // Client sets the order command
        invokerClass.setCommand(orderCommand);
        invokerClass.notifyChef(); // Chef is changed the order status.


        // Client sets the cancel order command
        invokerClass.setCommand(cancelOrderCommand);
        invokerClass.notifyChef(); // Chef received a request to cancel the order.
    }
}
