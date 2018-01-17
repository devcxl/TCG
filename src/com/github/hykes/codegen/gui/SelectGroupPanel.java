package com.github.hykes.codegen.gui;

import com.github.hykes.codegen.constants.Defaults;
import com.github.hykes.codegen.model.CodeRoot;
import com.github.hykes.codegen.utils.StringUtils;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hehaiyangwork@gmail.com
 * @date 2018/1/17
 */
public class SelectGroupPanel {
    private JPanel rootPanel;
    private JComboBox groupComboBox;
    private JButton generator;
    private JPanel groupsPanel;

    private Project project;
    private final Map<String, String> groupPathMap = new HashMap<>();

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public JButton getGenerator() {
        return generator;
    }

    public JPanel getGroupsPanel() {
        return groupsPanel;
    }

    public Map<String, String> getGroupPathMap() {
        return groupPathMap;
    }

    public SelectGroupPanel(List<CodeRoot> roots, Project project) {
        super();
        this.project = project;
        for (CodeRoot root: roots) {
            groupComboBox.addItem(root);
        }
        groupComboBox.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox source = (JComboBox) e.getSource();
                CodeRoot root = (CodeRoot) source.getSelectedItem();
                renderGroupsPanel(root);
            }
        });
        renderGroupsPanel(roots.get(0));
    }

    private void renderGroupsPanel(CodeRoot root) {
        groupsPanel.removeAll();
        root.getGroups().forEach( it -> {
            JCheckBox groupBox = new JCheckBox(it.getName());
            groupBox.setName(it.getId());
            SelectPathDialog dialog = new SelectPathDialog(project);
            dialog.setSize(350, 160);
            dialog.setAlwaysOnTop(true);
            dialog.setLocationRelativeTo(this.rootPanel);
            dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            dialog.setResizable(false);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            groupBox.addActionListener(box -> {
                if (groupBox.isSelected()) {
                    dialog.setVisible(true);
                    String outputPath = dialog.getOutPutPath();
                    String basePackage = dialog.getBasePackage();
                    if (StringUtils.isBlank(outputPath) && StringUtils.isBlank(basePackage)) {
                        groupBox.setSelected(false);
                    } else {
                        groupPathMap.put(groupBox.getName(), outputPath + basePackage.replace(".", "/"));
                    }
                }
            });
            groupsPanel.add(groupBox);
        });
        groupsPanel.revalidate();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("SelectGroupPanel");
        frame.setContentPane(new SelectGroupPanel(Defaults.getDefaultTemplates(), null).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        groupComboBox = new ComboBox();
        groupComboBox.setRenderer(new CellRenderer());
        groupsPanel = new JPanel();
        groupsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    public class CellRenderer extends JLabel implements ListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list,
                                                      Object value,
                                                      int index,
                                                      boolean isSelected,
                                                      boolean cellHasFocus) {
            CodeRoot root = (CodeRoot) value;
            setHorizontalAlignment(JLabel.CENTER);
            try {
                this.setText(root.getName());
            } catch (Exception e) {
                this.setText(String.valueOf(index));
            }
            return this;
        }
    }
}
