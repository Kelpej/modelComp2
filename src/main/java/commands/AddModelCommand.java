package commands;

import models.ComputationController;
import models.ComputationModel;
import utils.ModelType;

public class AddModelCommand extends AppCommand{
    private final ComputationModel<?> model;

    public AddModelCommand(ComputationController controller, ComputationModel model) {
        super(controller);
        this.model = model;
    }

    @Override
    public void execute() {
        controller.getModels().add(model);
    }

    @Override
    public void backup() {
        controller.getModels().remove(model);
    }
}
