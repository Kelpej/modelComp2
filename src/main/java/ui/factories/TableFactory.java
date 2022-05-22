package ui.factories;

import models.ComputationController;
import models.ComputationModel;
import models.Instruction;
import utils.ModelType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TableFactory {
    private static String[][] getTableData(ComputationController<?> controller) {
        return controller.getCurrentModel().getInstructions().stream()
                .map(Instruction::toRowFormat)
                .toArray(String[][]::new);
    }

    public static void createTable(ModelType model, JTable table) {
        var tableModel = new DefaultTableModel(getTableData(model.getController()), model.getLogFormat())
        {
            final boolean[] columnEditables = new boolean[] { false, false, false};

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(tableModel);
    }
}
