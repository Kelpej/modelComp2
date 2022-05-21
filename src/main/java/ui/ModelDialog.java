package ui;

import commands.AddModelCommand;
import commands.EditModelCommand;
import models.ComputationController;
import models.ComputationModel;
import models.realisations.MarkovAlgorithm;
import utils.ModelType;

import javax.swing.*;
import java.awt.event.*;

public class ModelDialog<M extends ComputationModel<?>> extends JDialog {
    private JPanel contentPane;
    private JButton okButton;
    private JButton cancelButton;
    private JPanel fieldPanels;
    private JTextField nameField;
    private JPanel namePanel;
    private JLabel nameLabel;
    private JLabel nameError;
    private JPanel descriptionPanel;
    private JTextField descriptionField;
    private JLabel descriptionLabel;
    private JLabel descriptionError;
    private JPanel buttonPanel;
    private JPanel arityPanel;
    private JPanel isNumericPanel;
    private JCheckBox isNumeric;
    private JTextField arityField;
    private JLabel arityLabel;
    private JLabel arityError;
    private JLabel isNumericError;
    private JLabel isNumericLabel;
    private JButton removeButton;

    private boolean edit;
    private ComputationController controller;
    private ComputationModel.Builder builder;

    public ModelDialog(boolean edit, ModelType model, JFrame frame) {
        setLocationRelativeTo(frame);
        this.edit = edit;
        this.controller = model.getController();
        this.builder = switch (model) {
            case MARKOV -> new MarkovAlgorithm.Builder();
        };

        if (edit)
            init(controller);

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(okButton);

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOk();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
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

    private void onOk() {
        try {
            if (nameField.getText().isEmpty())
                throw new IllegalArgumentException("Name field can't be empty.");
            int arity = Integer.parseInt(arityField.getText());
            var builder = this.builder
                    .addName(nameField.getText())
                    .addDescription(descriptionField.getText())
                    .setArity(arity)
                    .setIsNumeric(isNumeric.isSelected());

            if (edit) {
                okButton.setText("Edit");
                var model = builder.setInstructions(controller.getCurrentModel().getInstructions())
                        .build();
                controller.executor().execute(
                        new EditModelCommand(controller, model)
                );
            } else {
                var model = builder.build();
                controller.executor().execute(
                        new AddModelCommand(controller, model)
                );
                controller.nextModel();
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Arity must be an integer!");
        } catch (IllegalArgumentException iae) {
            JOptionPane.showMessageDialog(this, iae.getMessage());
        }
        controller.writeChanges();
        MainPanel.getUI().updateUI();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void init(ComputationController controller) {
        var model = controller.getCurrentModel();
        nameField.setText(model.getName());
        descriptionField.setText(model.getDescription());
        arityField.setText(String.valueOf(model.getArity()));
        isNumeric.setSelected(model.isNumeric());
        removeButton.setVisible(true);
    }
}
