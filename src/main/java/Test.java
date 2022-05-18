import models.realisations.MarkovAlgorithm;
import controllers.MarkovController;
import utils.json.JsonWriter;

public class Test {
    public static void main(String[] args) {
        var algorithm1 =
                new MarkovAlgorithm.Builder()
                        .addName("sasha")
                        .addDescription("lox")
                        .setAlphabet("|#b")
                        .setArity(1)
                        .setIsNumeric(true)
                        .build();

        algorithm1.addCommand(algorithm1.createCommand()
                .setLeft("abc")
                .setRight("bca")
                .setIsEnd(false));

        algorithm1.addCommand(algorithm1.createCommand()
                .setLeft("lox")
                .setRight("sasha")
                .setIsEnd(true));

        var controller = MarkovController.getInstance();
        System.out.println(controller.getModels());
        JsonWriter.writeToFile("markov.json", controller.getModels());
    }
}
