package commands;

import models.ComputationController;
import models.Instruction;

import java.util.Collections;

public class MoveDownCommand extends AppCommand{
    private final int index;
    public MoveDownCommand(ComputationController controller, int row) {
        super(controller);
        this.index = row;
    }

    @Override
    public void execute() {
        if (index < controller.getCurrentModel().getInstructions().size() - 1)
            Collections.swap(controller.getCurrentModel().getInstructions(), index, index + 1);
    }

    @Override
    public void backup() {
        Collections.swap(controller.getCurrentModel().getInstructions(), index, index + 1);
    }
}
