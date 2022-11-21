package stage6.server.command.commands;

import com.google.gson.JsonElement;
import lombok.Data;
import stage6.server.Database;

@Data
public class DeleteCommand implements Command {
    private JsonElement key;
    Database database = new Database();

    public DeleteCommand(JsonElement key) {
        this.key = key;
    }

    @Override
    public void execute() {
        database.delete(key);
    }
}
