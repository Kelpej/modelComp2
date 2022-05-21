package commands;

import java.util.Stack;

public class CommandExecutor {
    private final Stack<AppCommand> undo = new Stack<>();
    private final Stack<AppCommand> redo = new Stack<>();

    public CommandExecutor() {

    }

    public void execute(AppCommand command) {
        undo.push(command);
        command.execute();
    }

    public void undo() {
        if (!undo.isEmpty())
            redo.push(undo.pop()).backup();
    }

    public void redo() {
        if (!redo.isEmpty())
            undo.push(redo.pop()).execute();
    }

    @Override
    public String toString() {
        return "CommandExecutor{" +
                "undo=" + undo +
                ", redo=" + redo +
                '}';
    }
}
