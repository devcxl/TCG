package com.github.devcxl.tcg;

import com.github.devcxl.tcg.gui.SqlEditorPanel;
import com.github.devcxl.tcg.gui.cmt.MyDialogWrapper;
import com.github.devcxl.tcg.model.IdeaContext;
import com.github.devcxl.tcg.constants.CodeGenBundle;
import com.github.devcxl.tcg.utils.PsiUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;

import javax.swing.*;

/**
 * 解析sql生成代码
 *
 * @author hehaiyangwork@gmail.com
 * @date 2017/03/21
 */
public class SQLGeneratorAction extends AnAction implements DumbAware {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {

        Project project = PsiUtil.getProject(anActionEvent);
        DumbService dumbService = DumbService.getInstance(project);
        if (dumbService.isDumb()) {
            dumbService.showDumbModeNotification(CodeGenBundle.message("codegen.plugin.is.not.available.during.indexing"));
            return;
        }

        JFrame frame = new JFrame();
        SqlEditorPanel sqlPane = new SqlEditorPanel(new IdeaContext(project));
        frame.setContentPane(sqlPane.getRootComponent());
        MyDialogWrapper frameWrapper = new MyDialogWrapper(project, frame.getRootPane());
        frameWrapper.setActionOperator(sqlPane);
        frameWrapper.setTitle("CodeGen-SQL");
        frameWrapper.setSize(600, 400);
        frameWrapper.setResizable(false);
        frameWrapper.show();
    }

}
