package ui.factories.dialogs;

import commands.AddInstructionCommand;
import commands.EditInstructionCommand;
import commands.RemoveInstructionCommand;
import controllers.MarkovController;
import models.realisations.MarkovAlgorithm;
import ui.MainPanel;

import javax.swing.*;
import java.awt.event.*;

public class MarkovDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel buttonPanel;
    private JPanel ruleFields;
    private JButton removeButton;
    private JTextField leftField;
    private JTextField rightField;
    private JLabel left;
    private JLabel right;
    private JCheckBox isEndCheckBox;
    private JTextField commentField;
    private JLabel comment;

    private boolean edit;
    private int row;
    private MarkovAlgorithm.Rule rule;
    private MarkovController controller;

    public MarkovDialog(MarkovController controller) { //simple add
        this.controller = controller;
        edit = false;
        row = controller.getCurrentModel().getInstructions().size();
        init();
    }

    public MarkovDialog(boolean edit, int row, MarkovController controller) { //add by index or edit
        this.controller = controller;
        this.row = row;
        this.edit = edit;
        init();

//        focus code
//        System.out.println(column);
//        switch (column) {
//            case 0 -> leftField.requestFocusInWindow();
//            case 1 -> rightField.requestFocusInWindow();
//            case 2 -> isEndCheckBox.requestFocusInWindow();
//            case 3 -> commentField.requestFocusInWindow();
//        }
    }

    private MarkovAlgorithm.Rule grabData() {
        return new MarkovAlgorithm.Rule(
                leftField.getText(),
                rightField.getText(),
                isEndCheckBox.isSelected(),
                commentField.getText()
        );
    }

    private void onEdit() {
        controller.executor().execute(
                new EditInstructionCommand(controller, rule, grabData())
        );
        controller.writeChanges();
        dispose();
    }

    private void onCreate(int row) {
        controller.executor().execute(
                new AddInstructionCommand(controller, row, grabData())
        );
        controller.writeChanges();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void init() {
        setLocationRelativeTo(MainPanel.getUI());
        setContentPane(contentPane);
        setModal(true);
        setResizable(false);
        getRootPane().setDefaultButton(buttonOK);

        if (edit) {
            buttonOK.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    onEdit();
                    MainPanel.getUI().updateUI();
                }
            });
            rule = controller.getCurrentModel().getInstructions().get(row);
            removeButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    controller.executor().execute(
                            new RemoveInstructionCommand(controller, rule)
                    );
                    MainPanel.getUI().updateUI();
                    dispose();
                }
            });
            buttonOK.setText("Edit");
            leftField.setText(rule.getLeft());
            rightField.setText(rule.getRight());
            isEndCheckBox.setSelected(rule.isEnd());
            commentField.setText(rule.getComment());
        } else {
            buttonOK.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    onCreate(row);
                    MainPanel.getUI().updateUI();
                }
            });
            removeButton.setVisible(false);
        }

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
}
