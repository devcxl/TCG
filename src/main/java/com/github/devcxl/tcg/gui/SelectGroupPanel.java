package com.github.devcxl.tcg.gui;

import com.github.devcxl.tcg.model.CodeRoot;
import com.github.devcxl.tcg.constants.Defaults;
import com.github.devcxl.tcg.utils.GuiUtil;
import com.github.devcxl.tcg.utils.StringUtils;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.impl.file.PsiDirectoryFactory;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author hehaiyangwork@gmail.com
 * @date 2018/1/17
 */
public class SelectGroupPanel {
    private JPanel rootPanel;
    private JComboBox<CodeRoot> groupComboBox;
    private JPanel groupsPanel;
    private JTextField outputTextField;
    private JButton outputButton;
    private JLabel outputLable;
    private JTextField packageField;
    private Map<String, String> groupPathMap = new HashMap<>();

    public JPanel getRootPanel() {
        return rootPanel;
    }

    /**
     * 获取group的输出路径
     * key: groupId
     * value: output path
     */
    public Map<String, String> getGroupPathMap() {
        List<String> selectGroups = GuiUtil.getAllJCheckBoxValue(groupsPanel);
        selectGroups.forEach(s -> {
            groupPathMap.put(s, outputTextField.getText());
        });
        return groupPathMap;
    }

    /**
     * 是否有选中
     */
    public Boolean hasSelected() {
        return !GuiUtil.getAllJCheckBoxValue(groupsPanel).isEmpty();
    }

    public SelectGroupPanel(List<CodeRoot> roots, Project project) {
        super();
        $$$setupUI$$$();

        outputButton.addActionListener(actionEvent -> {
            FileChooserDescriptor descriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor();
            descriptor.setTitle("选择输出路径");
            descriptor.setShowFileSystemRoots(false);
            descriptor.setDescription("选择输出路径");
            descriptor.setHideIgnored(true);
            descriptor.setRoots(ProjectRootManager.getInstance(project).getContentRoots());
            descriptor.setForcedToUseIdeaFileChooser(true);
            VirtualFile virtualFile = FileChooser.chooseFile(descriptor, project, project.getProjectFile());
            if (Objects.nonNull(virtualFile)) {
                String output = virtualFile.getPath();
                PsiDirectory psiDirectory = PsiDirectoryFactory.getInstance(project).createDirectory(virtualFile);
                PsiPackage psiPackage = JavaDirectoryService.getInstance().getPackage(psiDirectory);
                if (psiPackage != null && psiPackage.getName() != null) {
                    final StringBuilder path = new StringBuilder();
                    path.append(psiPackage.getName());
                    while (psiPackage.getParentPackage() != null && psiPackage.getParentPackage().getName() != null) {
                        psiPackage = psiPackage.getParentPackage();
                        if (path.length() > 0) {
                            path.insert(0, '.');
                        }
                        path.insert(0, psiPackage.getName());
                    }
                    output = output.replace(path.toString().replace(".", "/"), "");
                    packageField.setText(path.toString());
                }
                outputTextField.setText(StringUtils.applyPath(output));
            }
        });

        for (CodeRoot root : roots) {
            groupComboBox.addItem(root);
        }
        groupComboBox.addActionListener(e -> {
            JComboBox source = (JComboBox) e.getSource();
            CodeRoot root = (CodeRoot) source.getSelectedItem();
            renderGroupsPanel(root);
        });
        renderGroupsPanel(roots.get(0));
    }

    private void renderGroupsPanel(CodeRoot root) {
        if (root == null) {
            return;
        }
        groupsPanel.removeAll();
        root.getGroups().forEach(it -> {
            JCheckBox groupBox = new JCheckBox(it.getName());
            groupBox.setName(it.getId());
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
        groupComboBox = new ComboBox<>();
        groupComboBox.setRenderer(new CellRenderer());
        groupsPanel = new JPanel();
        groupsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        rootPanel = new JPanel();
        rootPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 6.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        rootPanel.add(groupsPanel, gbc);
        groupComboBox.setToolTipText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rootPanel.add(groupComboBox, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

    public class CellRenderer extends JLabel implements ListCellRenderer {
        private static final long serialVersionUID = 4769047597333393201L;

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
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
