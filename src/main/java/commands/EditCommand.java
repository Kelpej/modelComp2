package commands;

import models.ComputationController;

public class EditCommand extends AppCommand{
    protected EditCommand(ComputationController<?> controller) {
        super(controller);
    }

    @Override
    public void execute() {

    }

    @Override
    public void backup() {

    }
}
