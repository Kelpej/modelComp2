import models.markov.MarkovAlgorithm;
import models.markov.MarkovController;

import java.util.List;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        var algorithm = new MarkovAlgorithm("sasha", "lox", "|#b", true);
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
        System.out.println(controller.execute(0, "|||||", 5));
    }
}
