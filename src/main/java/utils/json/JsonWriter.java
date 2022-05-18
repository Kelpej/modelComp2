package utils.json;

import com.google.gson.Gson;

import java.io.*;
import java.util.List;

import static utils.json.Exportable.SAVE_PATH;

public abstract class JsonWriter {
    public static void writeToFile(String path, Exportable e) {
        return;
    }

    public static void writeToFile(String fileName, List<? extends Exportable> e) {
        try (var writer = new PrintWriter(new BufferedWriter(new FileWriter(SAVE_PATH.concat(fileName))));){
            new Gson().toJson(e, writer);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
