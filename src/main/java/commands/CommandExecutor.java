package commands;

import java.util.Stack;

public class CommandExecutor {
    private static final Stack<AppCommand> undo = new Stack<>();
    private static final Stack<AppCommand> redo = new Stack<>();

    public static void execute(AppCommand command) {
        undo.push(command);
        command.execute();
    }

    public static void undo() {
        if (!undo.isEmpty())
            redo.push(undo.pop()).backup();
    }

    public static void redo() {
        if (!redo.isEmpty())
            undo.push(redo.pop()).execute();
    }
}
