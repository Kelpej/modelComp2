package ui.tables;

import controllers.MarkovController;
import models.ComputationController;
import models.realisations.MarkovAlgorithm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Arrays;
import java.util.List;

public class MarkovTable extends JTable {
    public static void createTable(JTable table) {
        table.setModel(new DefaultTableModel(
                MarkovController.getInstance().getModels().stream()
                        .map(MarkovAlgorithm::getCommands)
                        .flatMap(List::stream)
                        .map(MarkovAlgorithm.Rule::toCellFormat)
                        .toArray(String[][]::new),
                new String[] {"Left", "Right", "isEnd"}
        ));
    }
}
