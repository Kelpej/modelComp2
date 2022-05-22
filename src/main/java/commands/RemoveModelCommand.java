package commands;

import models.ComputationController;
import models.ComputationModel;

public class RemoveModelCommand extends AppCommand{
    private final ComputationModel removed;
    private final int removedIndex;

    public RemoveModelCommand(ComputationController controller) {
        super(controller);
        this.removed = controller.getCurrentModel();
        this.removedIndex = controller.getModels().indexOf(controller.getCurrentModel());
    }

    @Override
    public void execute() {
        controller.getModels().remove(removedIndex);
        controller.previousModel();
    }

    @Override
    public void backup() {
        controller.getModels().add(removedIndex, removed);
        controller.nextModel();
    }
}
