package ui.factories;

import controllers.MarkovController;
import ui.factories.dialogs.MarkovDialog;
import utils.ModelType;

import javax.swing.*;

public class InstructionDialogFactory {
    public static void createDialog(boolean edit, int row, ModelType modelType) {
        JDialog dialog = switch (modelType) {
            case MARKOV -> new MarkovDialog(edit, row, (MarkovController) modelType.getController());
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
