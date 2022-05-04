package models;

import java.util.ArrayList;
import java.util.List;

public abstract class ComputationModel<T extends Command> {
    protected String name;
    protected String description;
    protected List<T> commands;
    protected boolean isNumeric;

    protected ComputationModel(String name, String description, boolean isNumeric) {
        this.name = name;
        this.description = description;
        this.isNumeric = isNumeric;
        this.commands = new ArrayList<>();
    }

    public abstract String execute(String input, int steps);

    public void addCommand(T command) {
        commands.add(command);
    }

    public void addCommands(List<T> commands) {
        this.commands.addAll(commands);
    }
}
