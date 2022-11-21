package stage5.server;

import com.google.gson.Gson;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Database {

    public Map<String, String> database = new LinkedHashMap<>();
    String fileName;
    ReadWriteLock lock;
    Lock readLock, writeLock;

    public Database(String fileName) {
        this.fileName = fileName;
        lock = new ReentrantReadWriteLock();
        readLock = lock.readLock();
        writeLock = lock.writeLock();
        try {
            writeLock.lock();
            FileWriter writer = new FileWriter(fileName);
            writer.write("{}");
            writer.close();
            writeLock.unlock();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String get(String key) {
        String result;
        readLock.lock();
        result = database.get(key);
        readLock.unlock();
        return result;
    }

    public void set(String key, String value) {
        readLock.lock();
        database.put(key, value);
        writeJsonToFile();
        readLock.unlock();
    }

    public void delete(String key) {

        readLock.lock();
        writeJsonToFile();
        database.remove(key);
        readLock.unlock();
    }

    private void writeJsonToFile() {
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(new Gson().toJson(database));
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
