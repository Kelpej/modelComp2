package commands;

import models.ComputationController;
import models.ComputationModel;
import models.Instruction;

public class RemoveInstructionCommand extends AppCommand {
    private final Instruction removed;
    private final int removedIndex;

    public RemoveInstructionCommand(ComputationController controller, Instruction removed) {
        super(controller);
        this.removed = removed;
        this.removedIndex = controller.getCurrentModel().getInstructions().indexOf(removed);
    }

    @Override
    public void execute() {
        controller.getCurrentModel().getInstructions().remove(removedIndex);
    }

    @Override
    public void backup() {
        controller.getCurrentModel().getInstructions().add(removedIndex, removed);
    }
}
