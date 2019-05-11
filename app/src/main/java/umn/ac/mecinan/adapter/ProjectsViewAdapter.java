package umn.ac.mecinan.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import umn.ac.mecinan.R;
import umn.ac.mecinan.model.ButtonProject;
import umn.ac.mecinan.model.Project;

public class ProjectsViewAdapter extends RecyclerView.Adapter<ProjectsViewAdapter.ProjectViewHolder> {

    private Context mCtx;
    private List<Project> projectList;
    private List<Boolean> isEmployeeList;
    private List<ButtonProject> buttonProjectList;

    private String tvStatus;
    private int tvProgressBar;

    private int visibilityBtnLeft, visibilityBtnRight;
    private String stringBtnLeft, stringBtnRight;

    public ProjectsViewAdapter(Context mCtx, List<Project> projectList, List<Boolean> isEmployeeList, List<ButtonProject> buttonProjectList){
        this.isEmployeeList = isEmployeeList;
        this.mCtx = mCtx;
        this.projectList = projectList;
        this.buttonProjectList = buttonProjectList;
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.projects_list, viewGroup, false);
        ProjectViewHolder holder = new ProjectViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder projectViewHolder, int i){

        Project project = projectList.get(i);
        Boolean isEmployee = isEmployeeList.get(i);
        ButtonProject buttonProject = buttonProjectList.get(i);

        /** Set Project Brief Data */
        projectViewHolder.projectTitle.setText(project.getTitle());
        projectViewHolder.projectField.setText(project.getIdField()); // Perlu diganti biar muncul nama dari table lain
        projectViewHolder.projectCategory.setText(project.getIdCategory()); // Perlu diganti biar muncul nama dari table lain

        /** WorkRequest */
        if(isEmployee) {
            projectViewHolder.projectWorkRequest.setText("Requested By ");
            projectViewHolder.projectWRUser.setText(project.getUserClient().getUsername());
        } else {
            projectViewHolder.projectWorkRequest.setText("Worked By ");
            projectViewHolder.projectWRUser.setText(project.getUserEmployee().getUsername());
        }

        /** progressionBar */
        setProgression(project.getStatus());
        projectViewHolder.projectStatus.setText(tvStatus);
        projectViewHolder.projectProgressBar.setProgress(tvProgressBar);

        /** RatingBar */
        if (project.getStatus() == 4) //if project is completed
        {
            projectViewHolder.projectRating.setRating(project.getRating());
            projectViewHolder.projectRating.setVisibility(View.VISIBLE);
        }

        /** Button */
        projectViewHolder.btnLeft.setVisibility(buttonProject.getViewBtnLeft());
        projectViewHolder.btnLeft.setText(buttonProject.getStringBtnLeft());
        projectViewHolder.btnRight.setVisibility(buttonProject.getViewBtnRight());
        projectViewHolder.btnRight.setText(buttonProject.getStringBtnRight());
        buttonProject.makeListener(projectViewHolder.btnLeft, projectViewHolder.btnRight, project.getStatus(), project.getIdProject());
    }

    @Override
    public int getItemCount() {return projectList.size();}

    class ProjectViewHolder extends RecyclerView.ViewHolder{
        TextView projectTitle, projectWorkRequest, projectWRUser, projectField, projectCategory, projectStatus;
        Button btnLeft, btnRight;
        ProgressBar projectProgressBar;
        RatingBar projectRating;

        public ProjectViewHolder(@NonNull View itemView){
            super(itemView);

            projectTitle = itemView.findViewById(R.id.tv_mailList_title);
            projectProgressBar = itemView.findViewById(R.id.projectProgressBar);
            projectWorkRequest = itemView.findViewById(R.id.tvCompletedProject);
            projectWRUser = itemView.findViewById(R.id.employeeCompletedProject);
            projectField = itemView.findViewById(R.id.employeeField);
            projectCategory = itemView.findViewById(R.id.projectCategory);
            projectStatus = itemView.findViewById(R.id.employeeRate);
            projectRating = itemView.findViewById(R.id.employeeRatingBar);
            btnLeft = itemView.findViewById(R.id.btn_left);
            btnRight = itemView.findViewById(R.id.btn_right);
        }
    }

    public void setProgression(int status){
        switch (status){

            case  0:
                tvStatus = "Project Proposed, Waiting for Response";
                tvProgressBar = 10;
                break;
            case  1:
                tvStatus = "Project Accepted, Waiting for Confirmation"; // client need to confirm 'project accepted"
                tvProgressBar = 20;
                break;
            case -1:
                tvStatus = "Project Refused";
                tvProgressBar = 100;
                break;
            case -2:
                tvStatus = "Waiting for Payment";
                tvProgressBar = 30;
                break;
            case  2:
                tvStatus = "Project Paid, Waiting for Confirmation"; // employee need to confirm 'payment"
                tvProgressBar = 40;
                break;
            case -3:
                tvStatus = "Project Not Finished";
                tvProgressBar = 50;
                break;
            case  3:
                tvStatus = "Project Finished, Waiting for Confirmation"; // client need to confirm 'project finished"
                tvProgressBar = 80;
                break;
            case -4:
                tvStatus = "Waiting for Confirmation";
                tvProgressBar = 90;
                break;
            case  4:
                tvStatus = "Project Completed";
                tvProgressBar = 100;
                break;
            default:
                tvStatus = "UNDEFINED";
                tvProgressBar = 0;
        }
    }

    public void updateProjectList(List<Project> projects, List<Boolean> isEmployees, List<ButtonProject> buttonProjects) {
        projectList.clear();
        isEmployeeList.clear();
        buttonProjectList.clear();

        projectList = projects;
        isEmployeeList = isEmployees;
        buttonProjectList = buttonProjects;

        this.notifyDataSetChanged();
    }

}
