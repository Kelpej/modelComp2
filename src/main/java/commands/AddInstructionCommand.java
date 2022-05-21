package commands;

import models.ComputationController;
import models.Instruction;

public class AddInstructionCommand extends AppCommand{
    private final Instruction instruction;

    public AddInstructionCommand(ComputationController controller, Instruction instruction) {
        super(controller);
        this.instruction = instruction;
    }

    @Override
    public void execute() {
        controller.getCurrentModel().getInstructions().add(instruction);
    }

    @Override
    public void backup() {
        controller.getCurrentModel().getInstructions().remove(instruction);
    }
}
