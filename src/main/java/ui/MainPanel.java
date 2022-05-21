package ui;

import controllers.MarkovController;
import models.ComputationController;
import ui.factories.InstructionDialogFactory;
import ui.factories.TableFactory;
import utils.ModelType;
import utils.UTFChar;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class MainPanel extends JFrame{
    private JPanel mainPanel;
    private JComboBox modelType;
    private JButton input;
    private JButton output;
    private JTable table;
    private JButton addInstruction;
    private JButton editModel;
    private JButton runModel;
    private JButton addModel;
    private JButton nextModel;
    private JButton previousModel;
    private JButton undo;
    private JButton redo;
    private JPanel buttonPanel;
    private JPanel modelInformation;
    private JLabel nameLabel;
    private JLabel descriptionLabel;
    private JLabel arityLabel;
    private JLabel isNumericLabel;
    private JPanel header;
    private JPanel tablePanel;
    private JLabel modelName;
    private JLabel modelDescription;
    private JLabel modelArity;
    private JLabel modelIsNumeric;
    private JPanel searchPanel;
    private JTextField textField1;
    private JScrollPane instructionTable;
    private JButton moveUp;
    private JButton moveDown;

    private ModelType currentModelType = ModelType.MARKOV;
    private ComputationController<?> currentController = MarkovController.getInstance();
    private static MainPanel ui;

    public static MainPanel getUI() {
        if (ui == null)
            ui = new MainPanel();
        return ui;
    }

    private MainPanel() {
        init();
        updateUI();
        runModel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                RunDialog dialog = new RunDialog(currentModelType, currentController, mainPanel);
                dialog.pack();
                dialog.setVisible(true);
            }
        });
        modelType.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                currentModelType = (ModelType) modelType.getSelectedItem();
                currentController = currentModelType.getController();
                renderTable();
            }
        });
        addModel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                var createModelWindow = new ModelDialog(false, currentModelType, getUI());
                createModelWindow.pack();
                createModelWindow.setVisible(true);
            }
        });
        editModel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                var createModelWindow = new ModelDialog(true, currentModelType, getUI());
                createModelWindow.pack();
                createModelWindow.setVisible(true);
            }
        });
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (table.isRowSelected(table.getSelectedRow())) {
                    int row = table.getSelectedRow();
                    int column = table.getSelectedColumn();
                    InstructionDialogFactory.createDialog(row, column, currentModelType);
                }
            }
        });
        nextModel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                currentController.nextModel();
                updateUI();
            }
        });
        previousModel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                currentController.previousModel();
                updateUI();
            }
        });
        addInstruction.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                InstructionDialogFactory.createDialog(currentModelType);
            }
        });
        undo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                currentController.executor().undo();
                renderTable();
            }
        });
        redo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentController.executor().redo();
                renderTable();
            }
        });
    }

    public void updateUI() {
        renderModelInfo();
        renderTable();
    }

    private void init() {
        Arrays.stream(ModelType.values())
                .forEach(modelType::addItem);
    }

    private void renderModelInfo() {
        var model = currentController.getCurrentModel();
        modelName.setText(model.getName());
        modelDescription.setText(model.getDescription());
        modelArity.setText(String.valueOf(model.getArity()));
        modelIsNumeric.setText(String.valueOf(model.isNumeric() ? UTFChar.TRUE : UTFChar.FALSE));
    }

    private void renderTable() {
        TableFactory.createTable(currentModelType, table);
    }

    public JPanel getRootPanel() {
        return mainPanel;
    }
}