package commands;

import models.ComputationController;

public class AddCommand extends AppCommand{
    protected AddCommand(ComputationController<?> controller) {
        super(controller);
    }

    @Override
    public void execute() {
    }

    @Override
    public void backup() {

    }
}
