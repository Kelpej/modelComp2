package models;

import utils.ModelRuntime;
import utils.Exportable;

import java.util.ArrayList;
import java.util.List;

public abstract class ComputationModel<T extends Instruction> implements Exportable {
    protected String name;
    protected String description;
    protected int arity;
    protected boolean isNumeric;
    protected List<T> instructions;

    protected ComputationModel(Builder<T, ?> b) {
        this.name = b.name;
        this.description = b.description;
        this.arity = b.arity;
        this.isNumeric = b.isNumeric;
        this.instructions = b.instructions == null ? new ArrayList<>() : b.instructions;
    }

    public static abstract class Builder<T, B extends Builder<T, B>> {
        private String name;
        private String description;
        private int arity = 1;
        private boolean isNumeric = true;
        private List<T> instructions;

        public B addName(String name) {
            this.name = name;
            return self();
        }

        public B addDescription(String description) {
            this.description = description;
            return self();
        }

        public B setArity(int arity) {
            this.arity = arity;
            return self();
        }

        public B setIsNumeric(boolean isNumeric) {
            this.isNumeric = isNumeric;
            return self();
        }

        public B setInstructions(List<T> instructions) {
            this.instructions = instructions;
            return self();
        }

        protected abstract B self();
        public abstract ComputationModel<?> build();
    }

    public abstract void checkInstructionFields(T instruction) throws IllegalArgumentException;

    public abstract String execute(boolean debug, ModelRuntime runtime,  String... input);

    public abstract void addInstruction(T instruction);

    public List<T> getInstructions() {
        return instructions;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getArity() {
        return arity;
    }

    public boolean isNumeric() {
        return isNumeric;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComputationModel)) return false;

        ComputationModel<?> that = (ComputationModel<?>) o;

        if (getArity() != that.getArity()) return false;
        return getName().equals(that.getName());
    }
}