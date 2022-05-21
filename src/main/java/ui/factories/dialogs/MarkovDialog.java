package ui.factories.dialogs;

import commands.AddInstructionCommand;
import commands.EditInstructionCommand;
import controllers.MarkovController;
import models.realisations.MarkovAlgorithm;
import ui.MainPanel;

import javax.swing.*;
import java.awt.*;
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
    private MarkovAlgorithm.Rule rule;
    private MarkovController controller;

    public MarkovDialog(MarkovController controller) {
        this.controller = controller;
        edit = false;
        init();
    }

    public MarkovDialog(int row, int column, MarkovController controller) {
        this.controller = controller;
        edit = true;
        init();
        rule = controller.getCurrentModel().getInstructions().get(row);
        removeButton.setVisible(true);
        buttonOK.setText("Edit");
        leftField.setText(rule.getLeft());
        rightField.setText(rule.getRight());
        isEndCheckBox.setSelected(rule.isEnd());
        commentField.setText(rule.getComment());

        System.out.println(column);
        switch (column) {
            case 0 -> leftField.requestFocusInWindow();
            case 1 -> rightField.requestFocusInWindow();
            case 2 -> isEndCheckBox.requestFocusInWindow();
            case 3 -> commentField.requestFocusInWindow();
        }
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

    private void onCreate() {
        controller.executor().execute(
                new AddInstructionCommand(controller, grabData())
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
        } else {
            buttonOK.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    onCreate();
                    MainPanel.getUI().updateUI();
                }
            });
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
