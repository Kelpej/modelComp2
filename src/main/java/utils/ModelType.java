package utils;

import controllers.MarkovController;
import models.ComputationController;

public enum ModelType {
    MARKOV("Markov algorithm", new String[]{"Left", "Right", "is End", "Comment"},
            new String[] {"№", "Rule", "Before", "After"}, MarkovController.getInstance());
//    TURING("Turing machine",),
//    LAMBDA("Lambda calculus");

    final String modelName;
    final String[] tableColumns;
    final String[] logFormat;
    final ComputationController<?> controller;

    ModelType(String modelName, String[] tableColumns, String[] logFormat, ComputationController<?> controller) {
        this.modelName = modelName;
        this.tableColumns = tableColumns;
        this.logFormat = logFormat;
        this.controller = controller;
    }

    public ComputationController<?> getController() {
        return controller;
    }

    public String[] getLogFormat() {
        return logFormat;
    }

    @Override
    public String toString() {
        return modelName;
    }
}
