package controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.ComputationController;
import models.realisations.MarkovAlgorithm;
import utils.Exportable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static utils.Exportable.SAVE_PATH;

public class MarkovController extends ComputationController<MarkovAlgorithm> {
    private static final Pattern numericPattern = Pattern.compile("\\d+");

    transient private static MarkovController controller;

    public static MarkovController getInstance() {
        if (controller != null)
            return controller;

        controller = new MarkovController();
        controller.getModels().addAll(controller.parseJson().orElseThrow());
        if (!controller.getModels().isEmpty())
            controller.currentModel = controller.getModels().get(0);
        return controller;
    }

    @Override
    public void writeChanges() {
        try (var writer = new PrintWriter(new BufferedWriter(new FileWriter(SAVE_PATH.concat("markov.json"))));){
            new Gson().toJson(models, writer);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String[] parseArguments(String input) {
        if (currentModel.isNumeric()) {
            var list = new ArrayList<String>();
            var matcher = numericPattern.matcher(input);

            while (matcher.find())
                list.add(input.substring(matcher.start(), matcher.end()));

            if (list.size() != currentModel.getArity())
                throw new IllegalArgumentException(input + " is invalid argument sequence.");

            return list.toArray(String[]::new);
        }

        return new String[] {input};
    }

    @Override
    public Optional<List<MarkovAlgorithm>> parseJson() {
        try (var br = Files.newBufferedReader(Path.of(SAVE_PATH.concat("markov.json")))) {
            return Optional.of(new Gson().fromJson(br, new TypeToken<List<MarkovAlgorithm>>() {}.getType()));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

}
