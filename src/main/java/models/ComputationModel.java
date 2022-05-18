package models;

import utils.json.Exportable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public abstract class ComputationModel<T extends Command> implements Exportable {
    protected String name;
    protected String description;
    protected int arity;
    protected boolean isNumeric;
    protected List<T> commands;

    protected ComputationModel(Builder<?> b) {
        this.name = b.name;
        this.description = b.description;
        this.arity = b.arity;
        this.isNumeric = b.isNumeric;
        this.commands = new ArrayList<>();
    }

    public abstract static class Builder<B extends Builder<B>> {
        private String name;
        private String description;
        private int arity = 1;
        private boolean isNumeric = true;

        public B addName(String name) {
            this.name = name;
            return self();
        }

        public B addDescription(String description) {
            this.description = description;
            return self();
        }

        public B setArity(int arity) {
            this.arity = arity;
            return self();
        }

        public B setIsNumeric(boolean isNumeric) {
            this.isNumeric = isNumeric;
            return self();
        }

        protected abstract B self();
        public abstract ComputationModel<?> build();
    }

    public abstract String execute(String input, int steps);

    public abstract T createCommand();

    public void addCommand(T command) {
        commands.add(command);
    }

    public List<T> getCommands() {
        return commands;
    }

    protected static String convertToNumeric(StringBuilder output) {
        var matcher = Pattern.compile("\\|+").matcher(output);
        while (matcher.find(0))
            output.replace(matcher.start(), matcher.end(),
                    String.valueOf(output.substring(matcher.start(), matcher.end()).length()));
        return output.length() == 0 ? String.valueOf(0) : output.toString();
    }

    protected static String convertFromNumeric(String number) {
        return "|".repeat(Integer.parseInt(number));
    }
}
