package utils;

import javax.swing.table.AbstractTableModel;
import java.util.LinkedList;

public class ModelRuntime  {
    public static int time = 15;

    private StringBuilder temp = new StringBuilder(50);
    private final LinkedList<String[]> logs = new LinkedList<>();

    public StringBuilder getTemp() {
        return temp;
    }

    public LinkedList<String[]> getLogs() {
        return logs;
    }

    public void addLog(TableFormat log) {
        logs.add(log.toRowFormat());
    }

    //todo write values to table by logs.stream()
    public String[][] createLog() {
        return logs.toArray(String[][]::new);
    }
}
