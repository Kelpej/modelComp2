package ui.factories;

import controllers.MarkovController;
import ui.factories.dialogs.MarkovDialog;
import utils.ModelType;

import javax.swing.*;

public class InstructionDialogFactory {
    public static void createDialog(int row, int column, ModelType modelType) {
        JDialog dialog = switch (modelType) {
            case MARKOV -> new MarkovDialog(row, column, (MarkovController) modelType.getController());
        };
        dialog.pack();
        dialog.setVisible(true);
    }

    public static void createDialog(ModelType modelType) {
        JDialog dialog = switch (modelType) {
            case MARKOV -> new MarkovDialog((MarkovController) modelType.getController());
        };
        dialog.pack();
        dialog.setVisible(true);
    }
}
