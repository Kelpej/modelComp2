package models;

import commands.CommandExecutor;
import ui.MainPanel;
import utils.Exportable;
import utils.ModelRuntime;

import java.util.*;
import java.util.regex.Pattern;

public abstract class ComputationController<M extends ComputationModel<?>> implements Exportable {
    protected List<M> models = new ArrayList<>();
    transient protected M currentModel;
    transient protected CommandExecutor executor = new CommandExecutor();
    transient protected ModelRuntime runtime;

    public abstract void writeChanges();

    protected ModelRuntime createRuntime() {
        return runtime = new ModelRuntime();
    }

    public void nextModel() {
        int index = models.indexOf(currentModel);
        if (index < models.size() - 1) {
            currentModel = models.get(++index);
        } else {
            currentModel = models.get(0);
        }
        MainPanel.getUI().updateUI();
    }

    public void previousModel() {
        int index = models.indexOf(currentModel);
        if (index > 0) {
            currentModel = models.get(--index);
        } else {
            currentModel = models.get(models.size() - 1);
        }
        MainPanel.getUI().updateUI();
    }

    public String execute(boolean debug, String... input) {
        return currentModel.execute(debug, createRuntime(), input);
    }

    public abstract String[] parseArguments(String input);

    public M getCurrentModel() {
        return currentModel;
    }

    public List<M> getModels() {
        return models;
    }

    public CommandExecutor executor() {
        return executor;
    }

    public ModelRuntime getRuntime() {
        return runtime;
    }

    protected abstract Optional<List<M>> parseJson();

    public void checkModelFields(M model) throws IllegalArgumentException {
        if (models.contains(model))
            throw new IllegalArgumentException(model.getClass().getSimpleName()+ " " + model.getName() + " is already exist! \n");
    }

    public static String convertToInt(StringBuilder output) {
        var matcher = Pattern.compile("\\|+").matcher(output);
        while (matcher.find(0))
            output.replace(matcher.start(), matcher.end(),
                    String.valueOf(output.substring(matcher.start(), matcher.end()).length()));
        return output.length() == 0 ? String.valueOf(0) : output.toString();
    }

    public static String[] convertToNumeric(String... numbers) {
        return Arrays.stream(numbers)
                .map(num -> "|".repeat(Integer.parseInt(num)))
                .toArray(String[]::new);
    }

    public static String joinArguments(boolean isNumeric, String... args) {
        var sb = new StringBuilder();
        if (isNumeric)
            args = convertToNumeric(args);

        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]);
            if (i < args.length - 1)
                sb.append('#');
        }

        return sb.toString();
    }
}