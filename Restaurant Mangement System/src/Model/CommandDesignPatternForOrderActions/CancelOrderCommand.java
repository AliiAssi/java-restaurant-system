// Package declaration for the command design pattern related to order actions in the Model package
package Model.CommandDesignPatternForOrderActions;

// Importing SQLException for handling database-related exceptions
import java.sql.SQLException;

// Implementation of the CancelOrderCommand class that implements the Command interface
public class CancelOrderCommand implements Command {
    // Private member variables to store the Chef object and the order ID
    private Chef chef;
    private int id;

    // Constructor to initialize the CancelOrderCommand with a Chef object and an order ID
    public CancelOrderCommand(Chef chef, int id) {
        this.chef = chef;
        this.id = id;
    }

    // Implementation of the execute method from the Command interface
    @Override
    public void execute() throws SQLException {
        // Calling the deleteOrder method on the Chef object to cancel the specified order
        chef.deleteOrder(id);
    }
}
