import models.markov.MarkovAlgorithm;
import models.markov.MarkovController;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        var algorithm = new MarkovAlgorithm("sasha", "lox", "|#b", 1, true);
        var commands = List.of(
                algorithm.new Rule("#|", "|#", false),
                algorithm.new Rule("#", "||b", false),
                algorithm.new Rule("|||b", "b", false),
                algorithm.new Rule("b", "", true),
                algorithm.new Rule("", "#", false)
        );
        algorithm.addCommands(commands);
        System.out.println(algorithm);
        var controller = new MarkovController(List.of(algorithm));
        System.out.println(controller.execute(0, String.valueOf(200000), Integer.MAX_VALUE));
    }
}
