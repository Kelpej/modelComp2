package commands;

import models.ComputationController;

public class RemoveCommand extends AppCommand{
    protected RemoveCommand(ComputationController<?> controller) {
        super(controller);
    }

    @Override
    public void execute() {

    }

    @Override
    public void backup() {

    }
}
