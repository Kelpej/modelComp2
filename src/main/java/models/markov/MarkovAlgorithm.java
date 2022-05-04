package models.markov;

import models.ComputationModel;
import models.Command;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class MarkovAlgorithm extends ComputationModel<MarkovAlgorithm.Rule> {
    private char[] alphabet;
    private StringBuilder temp;

    public MarkovAlgorithm(String name, String description, String alphabet, boolean isNumeric) {
        super(name, description, isNumeric);
        this.alphabet = alphabet.toCharArray();
    }

    public final class Rule extends Command {
        private Matcher matcher;
        private String left;
        private String right;
        private boolean isEnd;

        public Rule(String left, String right, boolean isEnd) {
            this.left = left;
            this.right = right;
            this.isEnd = isEnd;
        }

        @Override
        public void execute() {
            if (left.isEmpty())
                temp.insert(0, right);
            else
                temp.replace(matcher.start(), matcher.end(), right);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Rule rule)) return false;
            return Objects.equals(left, rule.left) && Objects.equals(right, rule.right);
        }

        public String getLeft() {
            return left;
        }

        public void setLeft(String left) {
            this.left = left;
        }

        public String getRight() {
            return right;
        }

        public void setRight(String right) {
            this.right = right;
        }

        public boolean isEnd() {
            return isEnd;
        }

        public void setEnd(boolean end) {
            isEnd = end;
        }

        @Override
        public String toString() {
            var rule = new StringBuilder();
            rule.append("Rule{");
            rule.append(left);
            rule.append(" ->");
            if (isEnd)
                rule.append(". ");
            else
                rule.append(' ');
            rule.append(right).append('}');
            return rule.toString();
        }
    }

    @Override
    public String execute(String input, int steps) {
        temp = new StringBuilder(input);
        commands.forEach(rule -> rule.matcher =
            Pattern.compile(isNumeric ? rule.left.replace("|", "\\|") : rule.left)
                    .matcher(temp));
        commands.forEach(rule -> System.out.println(rule.matcher.pattern()));

        for (int i = 0; i < steps; i++) {
            var command = commands.stream()
                    //todo не робе
                    .filter(rule -> rule.matcher.find() || rule.getLeft().isEmpty())
                    .findFirst();

            if (command.isPresent()) {
                var rule = command.get();
                rule.execute();
                System.out.println(rule);
                System.out.println(temp);
                if (rule.isEnd)
                    break;
            }
        }
        return temp.toString();
    }

    @Override
    public void addCommand(Rule command) {
        commands.add(command);
    }

    @Override
    public String toString() {
        final StringBuilder markov = new StringBuilder("MarkovAlgorithm{");
        markov.append("name='").append(name).append('\'');
        markov.append(", description='").append(description).append('\'');
        markov.append(", commands=").append(commands);
        markov.append(", alphabet=").append(Arrays.toString(alphabet));
        markov.append('}');
        return markov.toString();
    }
}
