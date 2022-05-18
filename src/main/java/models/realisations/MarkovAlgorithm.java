package models.realisations;

import models.ComputationModel;
import models.Command;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class MarkovAlgorithm extends ComputationModel<MarkovAlgorithm.Rule> {
    private char[] alphabet;
    private StringBuilder temp;

    private MarkovAlgorithm(Builder b) {
        super(b);
        this.alphabet = b.alphabet;
    }

    public static class Builder extends ComputationModel.Builder<Builder> {
        private char[] alphabet = {'|', '#'};
        public Builder setAlphabet(String alphabet) {
            this.alphabet = alphabet.toCharArray();
            return self();
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public ComputationModel<MarkovAlgorithm.Rule> build() {
            return new MarkovAlgorithm(self());
        }

    }

    public final class Rule implements Command {
        private transient Matcher matcher;
        private String left;
        private String right;
        private boolean isEnd;

        public Rule(String left, String right, boolean isEnd) {
            this.left = left;
            this.right = right;
            this.isEnd = isEnd;
        }

        public Rule() {

        }

        @Override
        public void execute() {
            if (!left.isEmpty()) {
                temp.replace(matcher.start(), matcher.end(), right);
                return;
            }
            temp.insert(0, right);
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

        public Rule setLeft(String left) {
            this.left = left;
            return this;
        }

        public Rule setRight(String right) {
            this.right = right;
            return this;
        }

        public Rule setIsEnd(boolean end) {
            isEnd = end;
            return this;
        }

        @Override
        public String[] toCellFormat() {
            return new String[]{left, right, String.valueOf(isEnd)};
        }

        @Override
        public String toString() {
            var rule = new StringBuilder();
            rule.append("Rule{");
            rule.append(left);
            rule.append(" ->");
            if (isEnd)
                rule.append('.');
            rule.append(' ');
            rule.append(right).append('}');
            return rule.toString();
        }

    }
    @Override
    public String execute(String input, int steps) {
        if (isNumeric)
            input = convertFromNumeric(input);

        temp = new StringBuilder(input);
        commands.forEach(rule -> rule.matcher =
            Pattern.compile(isNumeric ? rule.left.replace("|", "\\|") : rule.left)
                    .matcher(temp));

        for (int i = 0; i < steps; i++) {
            var command = commands.stream()
                    .filter(rule -> rule.matcher.find(0) || rule.getLeft().isEmpty())
                    .findFirst();

            if (command.isPresent()) {
                var rule = command.get();
                rule.execute();
                if (rule.isEnd)
                    break;
            }
        }
        return isNumeric ? convertToNumeric(temp) : temp.toString();
    }

    @Override
    public Rule createCommand() {
        return new Rule();
    }

    @Override
    public void addCommand(Rule command) {
        commands.add(command);
    }

    @Override
    public String toString() {
        return  "MarkovAlgorithm{" + "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", commands=" + commands +
                ", alphabet=" + Arrays.toString(alphabet) +
                '}';
    }
}