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
        var models = controller.getModels();
        models.set(models.indexOf(old), edited);
        controller.nextModel();
    }

    @Override
    public void backup() {
        var models = controller.getModels();
        models.set(models.indexOf(edited), old);
        controller.nextModel();
    }
}
