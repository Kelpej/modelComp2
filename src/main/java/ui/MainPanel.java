package ui;

import controllers.MarkovController;
import models.ComputationModel;
import models.realisations.MarkovAlgorithm;
import ui.tables.TableFactory;
import utils.ModelType;

import javax.swing.*;

public class MainPanel extends JFrame{
    private JPanel mainPanel;
    private JComboBox modelType;
    private JButton input;
    private JButton output;
    private JTable table1;
    private JButton addCommandButton;
    private JButton editButton;
    private JButton removeButton;
    private JButton button1;
    private JButton button2;
    private JButton fromTemplateButton;
    private JButton removeModelButton;
    private JButton button3;
    private JSpinner spinner1;

    private ModelType currentModelType = ModelType.MARKOV;

    public MainPanel() {
        createTable();
    }

    public JPanel getRootPanel() {
        return mainPanel;
    }

    private void createTable() {
        TableFactory.createTable(currentModelType, table1);
    }
}
