package commands;

import models.ComputationController;
import models.ComputationModel;

public class EditModelCommand extends AppCommand{
    private final ComputationModel old;
    private final ComputationModel edited;

    public EditModelCommand(ComputationController controller, ComputationModel edited) {
        super(controller);
        this.old = controller.getCurrentModel();
        this.edited = edited;
    }

    @Override
    public void execute() {
        int index = controller.getModels().indexOf(old);
        controller.getModels().set(index, edited);
    }

    @Override
    public void backup() {
        int index = controller.getModels().indexOf(edited);
        controller.getModels().set(index, old);
    }
}
