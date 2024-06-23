package Model.CommandDesignPatternForOrderActions;

import java.sql.SQLException;

public interface Command {
    public void execute() throws SQLException;
}
