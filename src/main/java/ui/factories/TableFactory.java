package ui.factories;

import models.ComputationController;
import models.ComputationModel;
import models.Instruction;
import utils.ModelType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TableFactory {
    private static final String[] MARKOV_COLUMNS = new String[]{"Left", "Right", "is End", "Comment"};

    private static String[][] getTableData(ComputationController<?> controller) {
        return controller.getCurrentModel().getInstructions().stream()
                .map(Instruction::toRowFormat)
                .toArray(String[][]::new);
    }

    public static void createTable(ModelType model, JTable table) {
        switch (model) {
            case MARKOV -> {
                table.setModel(new DefaultTableModel(getTableData(model.getController()), MARKOV_COLUMNS));
            }
        }
    }
}
