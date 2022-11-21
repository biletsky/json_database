package stage6.server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import stage6.server.exceptions.NoSuchKeyException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Database {

    private static final Path DB_PATH = Path.of("src/main/java/stage6/server/data/db.json");
    JsonObject database;
    ReadWriteLock lock;
    Lock readLock, writeLock;

    public Database() {
        lock = new ReentrantReadWriteLock();
        readLock = lock.readLock();
        writeLock = lock.writeLock();
        try {
            writeLock.lock();
            if (Files.exists(DB_PATH)) {
                String content = new String(Files.readAllBytes(DB_PATH));
                database = new Gson().fromJson(content, JsonObject.class);
            } else {
                Files.createFile(DB_PATH);
                database = new JsonObject();
            }
            writeLock.unlock();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeJsonToFile() {
        try {
            FileWriter writer = new FileWriter(DB_PATH.toFile());
            writer.write(new Gson().toJson(database));
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JsonElement get(JsonElement key) {
        try {
            readLock.lock();
            if (key.isJsonPrimitive() && database.has(key.getAsString())) {
                return database.get(key.getAsString());
            } else if (key.isJsonArray()) {
                return findElement(key.getAsJsonArray(), false);
            } else {
                throw new NoSuchKeyException();
            }
        } finally {
            readLock.unlock();
        }
    }

    private JsonElement findElement(JsonArray keys, boolean isPresent) {
        JsonElement element = database;
        if (isPresent) {
            for (JsonElement key : keys) {
                if (!(element.getAsJsonObject().has(key.getAsString()))) {
                    element.getAsJsonObject().add(key.getAsString(), new JsonObject());
                }
                element = element.getAsJsonObject().get(key.getAsString());
            }
        } else {
            for (JsonElement key : keys) {
                element = element.getAsJsonObject().get(key.getAsString());
            }
        }
        return element;
    }

    public void set(JsonElement key, JsonElement value) {
        try {
            readLock.lock();
            if (key.isJsonPrimitive()) {
                database.add(key.getAsString(), value);
            } else {
                JsonArray keys = key.getAsJsonArray();
                String toAdd = keys.remove(keys.size() - 1).getAsString();
                findElement(keys, true).getAsJsonObject().add(toAdd, value);
            }
            writeJsonToFile();
        } finally {
            readLock.unlock();
        }

    }

    public void delete(JsonElement key) {
        try {
            readLock.lock();
            if (key.isJsonPrimitive() && database.has(key.getAsString())) {
                database.remove(key.getAsString());
            } else if (key.isJsonArray()) {
                JsonArray keys = key.getAsJsonArray();
                String removeKey = keys.remove(keys.size() - 1).getAsString();
                findElement(keys, false).getAsJsonObject().remove(removeKey);
            } else {
                throw new NoSuchKeyException();
            }
            writeJsonToFile();
        } finally {
            readLock.unlock();
        }
    }
}
