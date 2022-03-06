package com.github.devcxl.tcg.model;

import com.intellij.openapi.project.Project;

import java.io.Serializable;

/**
 *
 * @author : hehaiyangwork@gmail.com
 * @date : 17/3/21
 */
public class IdeaContext implements Serializable {

    private static final long serialVersionUID = -3766582517674940760L;

    public IdeaContext() {
    }

    public IdeaContext(Project project) {
        this.project = project;
    }

    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
