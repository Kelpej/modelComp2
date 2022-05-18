package ui.tables;

import controllers.MarkovController;
import utils.ModelType;

import javax.swing.*;

public class TableFactory {
    public static void createTable(ModelType model, JTable table) {
        switch (model) {
            case MARKOV -> {
                MarkovTable.createTable(table);
            }
        }
    }
}
