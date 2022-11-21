package stage6.server.command.commands;

import com.google.gson.JsonElement;
import lombok.*;
import stage6.server.Database;

import java.io.IOException;

@Data
public class SetCommand implements Command {
    private JsonElement key;
    private JsonElement value;
    Database database = new Database();

    public SetCommand(JsonElement key, JsonElement value) throws IOException {
        this.key = key;
        this.value = value;
    }

    @Override
    public void execute() {
        database.set(key, value);
    }
}
