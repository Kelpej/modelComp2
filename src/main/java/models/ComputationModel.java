package models;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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

    protected static String convertNumeric(StringBuilder output) {
        var matcher = Pattern.compile("\\|+").matcher(output);
        while (matcher.find(0))
            output.replace(matcher.start(), matcher.end(),
                    String.valueOf(output.substring(matcher.start(), matcher.end()).length()));
        return output.toString();
    }
}
