package models;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public abstract class ComputationModel<T extends Command> {
    protected String name;
    protected String description;
    protected int arity;
    protected List<T> commands;
    protected boolean isNumeric;

    protected ComputationModel(String name, String description, int arity, boolean isNumeric) {
        this.name = name;
        this.description = description;
        this.arity = arity;
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

    protected static String convertToNumeric(StringBuilder output) {
        var matcher = Pattern.compile("\\|+").matcher(output);
        while (matcher.find(0))
            output.replace(matcher.start(), matcher.end(),
                    String.valueOf(output.substring(matcher.start(), matcher.end()).length()));
        return output.length() == 0 ? String.valueOf(0) : output.toString();
    }

    protected static String convertFromNumeric(String number) {
        return "|".repeat(Math.max(0, Integer.parseInt(number)));
    }
}
