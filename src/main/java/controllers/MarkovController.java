package controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.ComputationController;
import models.realisations.MarkovAlgorithm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static utils.json.Exportable.SAVE_PATH;

public class MarkovController extends ComputationController<MarkovAlgorithm> {
    private static MarkovController controller;

    protected MarkovController() {
    }

    public static MarkovController getInstance() {
        if (controller != null)
            return controller;
        controller = new MarkovController();
        controller.setModels(controller.parseJson().orElseThrow());
        return controller;
    }

    @Override
    protected Optional<List<MarkovAlgorithm>> parseJson() {
        try (var br = Files.newBufferedReader(Path.of(SAVE_PATH.concat("markov.json")))) {
            return Optional.of(new Gson().fromJson(br, new TypeToken<List<MarkovAlgorithm>>() {}.getType()));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<MarkovAlgorithm> getModels() {
        return super.getModels();
    }
}
