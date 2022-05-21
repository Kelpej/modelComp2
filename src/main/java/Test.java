import commands.AddInstructionCommand;
import models.realisations.MarkovAlgorithm;
import controllers.MarkovController;

public class Test {
    public static void main(String[] args) {
        var controller = MarkovController.getInstance();
        var algorithm =
                new MarkovAlgorithm.Builder()
                        .addName("sum")
                        .addDescription("x, y -> x > y ? x : y")
                        .setArity(2)
                        .setIsNumeric(true)
                        .build();

        algorithm.addInstruction(new MarkovAlgorithm.Rule("|a", "a|", false, ""));
        algorithm.addInstruction(new MarkovAlgorithm.Rule("b|", "|b", false, ""));
        algorithm.addInstruction(new MarkovAlgorithm.Rule("|#|", "a#b", false, ""));
        algorithm.addInstruction(new MarkovAlgorithm.Rule("|#", "at", false, ""));
        algorithm.addInstruction(new MarkovAlgorithm.Rule("#|", "fb", false, ""));
        algorithm.addInstruction(new MarkovAlgorithm.Rule("af", "f", false, ""));
        algorithm.addInstruction(new MarkovAlgorithm.Rule("tb", "t", false, ""));
        algorithm.addInstruction(new MarkovAlgorithm.Rule("a", "|", false, ""));
        algorithm.addInstruction(new MarkovAlgorithm.Rule("b", "|", false, ""));
        algorithm.addInstruction(new MarkovAlgorithm.Rule("t", "", true, ""));
        algorithm.addInstruction(new MarkovAlgorithm.Rule("f", "", true, ""));
        algorithm.addInstruction(new MarkovAlgorithm.Rule("", "#", false, ""));
        controller.getModels().add((MarkovAlgorithm) algorithm);
        controller.writeChanges();
    }
}
