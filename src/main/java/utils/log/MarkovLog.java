package utils.log;

import models.realisations.MarkovAlgorithm;
import utils.TableFormat;

public class MarkovLog implements TableFormat {
    private String number;
    private MarkovAlgorithm.Rule rule;
    private String before;
    private String after;

    public MarkovLog setNumber(String number) {
        this.number = number;
        return this;
    }

    public MarkovLog setRule(MarkovAlgorithm.Rule rule) {
        this.rule = rule;
        return this;
    }

    public MarkovLog setBefore(String before) {
        this.before = before;
        return this;
    }

    public MarkovLog setAfter(String after) {
        this.after = after;
        return this;
    }

    @Override
    public String[] toRowFormat() {
        return new String[] {number, rule.toString(), before.toString(), after.toString()};
    }
}
