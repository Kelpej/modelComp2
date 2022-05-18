package models;

import utils.json.Exportable;

public interface Command extends Exportable {
    void execute();
    String[] toCellFormat();

    abstract class Builder<T extends Command, B extends Builder<T, B>> {
        public abstract B self();
        public abstract T build();
    }
}
