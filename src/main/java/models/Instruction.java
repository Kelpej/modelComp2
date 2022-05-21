package models;

import utils.Exportable;
import utils.ModelRuntime;
import utils.TableFormat;

public abstract class Instruction implements Exportable, TableFormat {
    protected String comment;

    public String getComment() {
        return comment;
    }

    public Instruction setComment(String comment) {
        this.comment = comment;
        return this;
    }
    public abstract void execute(ModelRuntime runtime, boolean debug);
}