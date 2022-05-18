package commands;

import models.ComputationController;

public abstract class AppCommand {
    protected final ComputationController<?> controller;

    protected AppCommand(ComputationController<?> controller) {
        this.controller = controller;
    }

    public abstract void execute();
    public abstract void backup();
}
