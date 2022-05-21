package ui;

import models.ComputationController;
import models.ComputationModel;
import utils.ModelType;
import utils.UTFChar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;

public class RunDialog extends JDialog {
    private JPanel contentPane;
    private JButton run;
    private JButton buttonCancel;
    private JTextArea result;
    private JCheckBox debugCheckBox;
    private JLabel modelName;
    private JLabel modelDescription;
    private JPanel buttonPanel;
    private JPanel workArea;
    private JLabel modelArity;
    private JLabel isModelNumeric;
    private JTable debugTable;
    private JScrollPane debugPanel;
    private JTextField arguments;
    private JComboBox timeBox;

    private ComputationController<?> controller;
    private ComputationModel<?> model;
    private ModelType type;
    private int modelIndex;

    public RunDialog(ModelType type, ComputationController<?> controller, Component c) {
        this.controller = controller;
        this.model = controller.getCurrentModel();
        this.type = type;

        setLocationRelativeTo(c);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(run);

        timeBox.addItem(5);
        timeBox.addItem(10);
        timeBox.addItem(15);

        modelName.setText(modelName.getText() + model.getName());
        modelDescription.setText(modelDescription.getText() + model.getDescription());
        modelArity.setText(modelArity.getText() + model.getArity());
        isModelNumeric.setText(isModelNumeric.getText() + (model.isNumeric() ? UTFChar.TRUE : UTFChar.FALSE));

        run.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

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

    private void onOK() {
        var debug = debugCheckBox.isSelected();
        try {
            var args = controller.parseArguments(arguments.getText());
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Callable<String> callable = () -> controller.execute(debug, args);
            Future<String> result = executor.submit(callable);
            this.result.setText(result.get((int) timeBox.getSelectedItem(), TimeUnit.SECONDS));
            executor.shutdown();

            if (debug) {
                debugPanel.setVisible(true);
                renderDebug(type);
                return;
            }

            debugPanel.setVisible(false);
        } catch (IllegalArgumentException iae) {
            JOptionPane.showMessageDialog(this, iae.getMessage());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            JOptionPane.showMessageDialog(this, "Computation is stopped.","The timer ran out", JOptionPane.ERROR_MESSAGE);
            if (debug)
                renderDebug(type);
            result.setText("undefined");
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void renderDebug(ModelType type) {
        this.setSize(new Dimension(this.getWidth(), this.getHeight() + 150));
        String[] columns = type.getLogFormat();
        debugTable.setModel(new DefaultTableModel(
                controller.getRuntime().createLog(),
                columns
        ));
    }
}