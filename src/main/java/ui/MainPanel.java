package ui;

import commands.MoveDownCommand;
import commands.MoveUpCommand;
import controllers.MarkovController;
import models.ComputationController;
import ui.factories.InstructionDialogFactory;
import ui.factories.TableFactory;
import utils.ModelType;
import utils.UTFChar;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.EventObject;
import java.util.Locale;

public class MainPanel extends JFrame {
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
                var createModelWindow = new ModelDialog(false, currentModelType);
                createModelWindow.pack();
                createModelWindow.setVisible(true);
            }
        });
        editModel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                var createModelWindow = new ModelDialog(true, currentModelType);
                createModelWindow.pack();
                createModelWindow.setVisible(true);
            }
        });
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (table.isRowSelected(table.getSelectedRow()) && e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
//                    int column = table.getSelectedColumn();
                    InstructionDialogFactory.createDialog(true, row, currentModelType);
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
                if (table.isRowSelected(table.getSelectedRow())) {
                    int row = table.getSelectedRow();
                    InstructionDialogFactory.createDialog(false, row, currentModelType);
                } else {
                    InstructionDialogFactory.createDialog(currentModelType);
                }
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
        moveUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (table.isRowSelected(table.getSelectedRow())) {
                    int row = table.getSelectedRow();
                    currentController.executor().execute(
                            new MoveUpCommand(currentController, row)
                    );
                    currentController.writeChanges();
                    getUI().updateUI();
                    table.changeSelection(row - 1, 0, false, false);
                }
            }
        });
        moveDown.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (table.isRowSelected(table.getSelectedRow())) {
                    int row = table.getSelectedRow();
                    currentController.executor().execute(
                            new MoveDownCommand(currentController, row)
                    );
                    currentController.writeChanges();
                    getUI().updateUI();
                    table.changeSelection(row + 1, 0, false, false);
                }
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

    public void renderModelInfo() {
        var model = currentController.getCurrentModel();
        modelName.setText(model.getName());
        modelDescription.setText(model.getDescription());
        modelArity.setText(String.valueOf(model.getArity()));
        modelIsNumeric.setText(String.valueOf(model.isNumeric() ? UTFChar.TRUE : UTFChar.FALSE));
    }

    public void renderTable() {
        TableFactory.createTable(currentModelType, table);
    }

    public JPanel getRootPanel() {
        return mainPanel;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        Font mainPanelFont = this.$$$getFont$$$("Arial Unicode MS", -1, 15, mainPanel.getFont());
        if (mainPanelFont != null) mainPanel.setFont(mainPanelFont);
        mainPanel.setMinimumSize(new Dimension(800, 193));
        mainPanel.setPreferredSize(new Dimension(800, 600));
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        header = new JPanel();
        header.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(header, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modelType = new JComboBox();
        modelType.setName("Computation Model");
        header.add(modelType, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        input = new JButton();
        input.setText("Insert Models");
        header.add(input, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        output = new JButton();
        output.setText("Export Models");
        header.add(output, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tablePanel = new JPanel();
        tablePanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(tablePanel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        instructionTable = new JScrollPane();
        tablePanel.add(instructionTable, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        instructionTable.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), null, TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, -1, -1, instructionTable.getFont()), null));
        table = new JTable();
        table.setAutoCreateRowSorter(false);
        table.setDragEnabled(false);
        table.setEditingColumn(0);
        table.setEditingRow(0);
        table.setEnabled(true);
        table.setFocusable(false);
        table.setIntercellSpacing(new Dimension(1, 5));
        table.setRowMargin(5);
        instructionTable.setViewportView(table);
        modelInformation = new JPanel();
        modelInformation.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 10, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(modelInformation, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchPanel = new JPanel();
        searchPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        modelInformation.add(searchPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(200, 30), null, null, 0, false));
        textField1 = new JTextField();
        textField1.setMinimumSize(new Dimension(200, 30));
        textField1.setVisible(false);
        searchPanel.add(textField1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nameLabel = new JLabel();
        nameLabel.setText("Name:");
        modelInformation.add(nameLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modelName = new JLabel();
        Font modelNameFont = this.$$$getFont$$$(null, Font.BOLD, -1, modelName.getFont());
        if (modelNameFont != null) modelName.setFont(modelNameFont);
        modelName.setText("");
        modelInformation.add(modelName, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        descriptionLabel = new JLabel();
        descriptionLabel.setText("Description:");
        modelInformation.add(descriptionLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modelDescription = new JLabel();
        Font modelDescriptionFont = this.$$$getFont$$$(null, Font.BOLD, -1, modelDescription.getFont());
        if (modelDescriptionFont != null) modelDescription.setFont(modelDescriptionFont);
        modelDescription.setText("");
        modelInformation.add(modelDescription, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        arityLabel = new JLabel();
        arityLabel.setText("Arity:");
        modelInformation.add(arityLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modelArity = new JLabel();
        Font modelArityFont = this.$$$getFont$$$(null, Font.BOLD, -1, modelArity.getFont());
        if (modelArityFont != null) modelArity.setFont(modelArityFont);
        modelArity.setText("");
        modelInformation.add(modelArity, new com.intellij.uiDesigner.core.GridConstraints(0, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        isNumericLabel = new JLabel();
        isNumericLabel.setText("Is Numeric:");
        modelInformation.add(isNumericLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 7, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modelIsNumeric = new JLabel();
        modelIsNumeric.setText("");
        modelInformation.add(modelIsNumeric, new com.intellij.uiDesigner.core.GridConstraints(0, 8, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        modelInformation.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 9, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 5, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(buttonPanel, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        addModel = new JButton();
        addModel.setText("Add Model");
        buttonPanel.add(addModel, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        runModel = new JButton();
        runModel.setIcon(new ImageIcon(getClass().getResource("/icons/run.png")));
        runModel.setText("");
        buttonPanel.add(runModel, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(-1, 30), 0, false));
        editModel = new JButton();
        editModel.setText("Edit Model");
        buttonPanel.add(editModel, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addInstruction = new JButton();
        addInstruction.setText("Add Command");
        buttonPanel.add(addInstruction, new com.intellij.uiDesigner.core.GridConstraints(1, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        undo = new JButton();
        Font undoFont = this.$$$getFont$$$(null, -1, 18, undo.getFont());
        if (undoFont != null) undo.setFont(undoFont);
        undo.setText("⟲");
        buttonPanel.add(undo, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(-1, 30), 0, false));
        previousModel = new JButton();
        previousModel.setText("⭠");
        buttonPanel.add(previousModel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        redo = new JButton();
        Font redoFont = this.$$$getFont$$$(null, -1, 18, redo.getFont());
        if (redoFont != null) redo.setFont(redoFont);
        redo.setText("⟳");
        buttonPanel.add(redo, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(-1, 30), 0, false));
        nextModel = new JButton();
        nextModel.setText("⭢");
        buttonPanel.add(nextModel, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        moveUp = new JButton();
        moveUp.setText("↑");
        buttonPanel.add(moveUp, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        moveDown = new JButton();
        moveDown.setText("↓");
        buttonPanel.add(moveDown, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}