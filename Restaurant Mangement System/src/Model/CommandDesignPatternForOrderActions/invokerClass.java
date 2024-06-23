package Model.CommandDesignPatternForOrderActions;

import java.sql.SQLException;

public class invokerClass { // this is the invoker class of the command design pattern
    private Command OrderCommand;
    public void setCommand(Command command){
        this.OrderCommand=command;
    }

    public void notifyChef() throws SQLException {
        OrderCommand.execute();
    }
}
