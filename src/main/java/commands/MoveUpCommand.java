package commands;

import models.ComputationController;
import models.Instruction;

import java.util.Collections;

public class MoveUpCommand extends AppCommand {
    private final int index;
    public MoveUpCommand(ComputationController controller, int row) {
        super(controller);
        this.index = row;
    }

    @Override
    public void execute() {
        if (index > 0)
            Collections.swap(controller.getCurrentModel().getInstructions(), index, index - 1);
    }

    @Override
    public void backup() {
        Collections.swap(controller.getCurrentModel().getInstructions(), index, index - 1);
    }
}
