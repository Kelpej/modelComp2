package commands;

import models.ComputationController;
import models.Instruction;

public class AddInstructionCommand extends AppCommand{
    private final int index;
    private final Instruction instruction;

    public AddInstructionCommand(ComputationController controller, int index, Instruction instruction) {
        super(controller);
        this.index = index;
        this.instruction = instruction;
    }

    @Override
    public void execute() {
        controller.getCurrentModel().getInstructions().add(index, instruction);
    }

    @Override
    public void backup() {
        controller.getCurrentModel().getInstructions().remove(index);
    }
}
