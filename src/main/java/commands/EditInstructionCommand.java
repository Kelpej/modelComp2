package commands;

import models.ComputationController;
import models.Instruction;

public class EditInstructionCommand extends AppCommand{
    private final Instruction old;
    private final Instruction edited;

    public EditInstructionCommand(ComputationController controller, Instruction old, Instruction edited) {
        super(controller);
        this.old = old;
        this.edited = edited;
    }

    @Override
    public void execute() {
        var instructions = controller.getCurrentModel().getInstructions();
        instructions.set(instructions.indexOf(old), edited);
    }

    @Override
    public void backup() {
        var instructions = controller.getCurrentModel().getInstructions();
        instructions.set(instructions.indexOf(edited), old);
    }
}
