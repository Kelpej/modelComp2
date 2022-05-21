package models.realisations;

import models.ComputationModel;
import models.Instruction;
import utils.ModelRuntime;
import utils.UTFChar;
import utils.log.MarkovLog;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static models.ComputationController.convertToInt;
import static models.ComputationController.joinArguments;


public final class MarkovAlgorithm extends ComputationModel<MarkovAlgorithm.Rule> {
    private MarkovAlgorithm(Builder b) {
        super(b);
    }

    public static class Builder extends ComputationModel.Builder<MarkovAlgorithm.Rule, Builder> {
        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public ComputationModel<MarkovAlgorithm.Rule> build() {
            return new MarkovAlgorithm(self());
        }
    }

    public static final class Rule extends Instruction {
        private transient Matcher matcher;
        private String left;
        private String right;
        private boolean isEnd;

        public Rule(String left, String right, boolean isEnd, String comment) {
            this.left = left;
            this.right = right;
            this.isEnd = isEnd;
            super.comment = comment;
        }

        @Override
        public void execute(ModelRuntime runtime, boolean debug) {
            if (!debug) {
                if (!left.isEmpty()) {
                    runtime.getTemp().replace(matcher.start(), matcher.end(), right);
                    return;
                }

                runtime.getTemp().insert(0, right);
                return;
            }

            MarkovLog log = new MarkovLog();
            log.setNumber(String.valueOf(runtime.getLogs().size()))
                .setRule(this);

            if (!left.isEmpty()) {
                log.setBefore(runtime.getTemp()
                        .substring(Math.max(matcher.start() - 5, 0),
                                Math.min(matcher.end() + 5, runtime.getTemp().length()))
                );

                runtime.getTemp().replace(matcher.start(), matcher.end(), right);

                log.setAfter(runtime.getTemp()
                        .substring(Math.max(matcher.start() - 5, 0),
                                Math.min(matcher.end() + 5, runtime.getTemp().length()))
                );
                runtime.addLog(log);
                return;
            }
            log.setBefore(runtime.getTemp()
                    .substring(0, Math.min(10, runtime.getTemp().length()))
            );
            runtime.getTemp().insert(0, right);

            log.setAfter(runtime.getTemp()
                    .substring(0, Math.min(right.length() + 5, runtime.getTemp().length()))
            );

            runtime.addLog(log);
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

        public String getRight() {
            return right;
        }

        public boolean isEnd() {
            return isEnd;
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

        public Rule setComment(String comment) {
            super.comment = comment;
            return this;
        }

        @Override
        public String[] toRowFormat() {
            return new String[] {left, right, String.valueOf(isEnd ? UTFChar.TRUE : UTFChar.FALSE), super.comment};
        }

        @Override
        public String toString() {
            var rule = new StringBuilder();
            rule.append(left);
            rule.append(" →");
            if (isEnd)
                rule.append('.');
            rule.append(' ');
            rule.append(right);
            return rule.toString();
        }
    }

    @Override
    public void checkInstructionFields(Rule instruction) throws IllegalArgumentException {
        if (instructions.contains(instruction))
            throw new IllegalArgumentException(instruction + " is already exists, id: " + instructions.indexOf(instruction));
    }

    @Override
    public String execute(boolean debug, ModelRuntime runtime, String... input) {
        runtime.getTemp().append(joinArguments(isNumeric, input));

        instructions.forEach(rule -> rule.matcher =
            Pattern.compile(isNumeric ? rule.left.replace("|", "\\|") : rule.left)
                    .matcher(runtime.getTemp()));

        while (true) {
            var command = instructions.stream()
                    .filter(rule -> rule.matcher.find(0) || rule.getLeft().isEmpty())
                    .findFirst();

            if (command.isPresent()) {
                var rule = command.get();
                rule.execute(runtime, debug);
                if (rule.isEnd)
                    break;
            } else break;
        }

        return isNumeric ? convertToInt(runtime.getTemp()) : runtime.getTemp().toString();
    }

    @Override
    public void addInstruction(Rule instruction) {
        instructions.add(instruction);
    }


    @Override
    public String toString() {
        return  "MarkovAlgorithm{" + "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", commands=" + instructions +
                ", isNumeric=" + isNumeric +
                '}';
    }
}