package umn.ac.mecinan.model;

import java.util.List;

public class ProjectAttributes {
    private Project project_list;
    private Boolean isEmployee_list;
    private ButtonProject buttonProject_list;

    public ProjectAttributes() {}
    public ProjectAttributes(Project project_list, Boolean isEmployee_list, ButtonProject buttonProject_list) {
        this.project_list = project_list;
        this.isEmployee_list = isEmployee_list;
        this.buttonProject_list = buttonProject_list;
    }



    public Project getProject() {
        return project_list;
    }
    public Boolean getIsEmployee() {
        return isEmployee_list;
    }
    public ButtonProject getButtonProject() {
        return buttonProject_list;
    }


    public void setProject(Project project_list) {
        this.project_list = project_list;
    }
    public void setIsEmployee(Boolean isEmployee_list) {
        this.isEmployee_list = isEmployee_list;
    }
    public void setButtonProject(ButtonProject buttonProject_list) {
        this.buttonProject_list = buttonProject_list;
    }
}
