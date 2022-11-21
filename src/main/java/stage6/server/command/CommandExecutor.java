package stage6.server.command;

import stage6.server.command.commands.Command;

public class CommandExecutor {

    public void executeCommand(Command command) {
        command.execute();
    }
}
