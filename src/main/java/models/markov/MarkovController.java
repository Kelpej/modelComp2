package models.markov;

import java.util.ArrayList;
import java.util.List;

public class MarkovController {
    private final List<MarkovAlgorithm> algorithms;

    public MarkovController() {
        algorithms = new ArrayList<>();
    }

    public MarkovController(List<MarkovAlgorithm> algorithms) {
        this.algorithms = algorithms;
    }

    public String execute(int index, String input, int steps) {
        var algorithm = algorithms.get(index);
        return algorithm.execute(input, steps);
    }

    public void addAlgorithm(MarkovAlgorithm algorithm) {
        algorithms.add(algorithm);
    }

    public List<MarkovAlgorithm> getAlgorithms() {
        return algorithms;
    }
}
