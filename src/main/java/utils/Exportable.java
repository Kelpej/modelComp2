package utils;

import com.google.gson.Gson;

public interface Exportable {
    String SAVE_PATH = "src/main/resources/saves/";
    default String exportJson() {
        return new Gson().toJson(this);
    }
}
