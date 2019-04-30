package umn.ac.mecinan;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class ProjectsViewAdapter extends RecyclerView.Adapter<ProjectsViewAdapter.ProjectViewHolder> {

    private Context mCtx;
    private List<Project> projectList;
    private String tvStatus;
    private int tvProgressBar;

    public ProjectsViewAdapter(Context mCtx, List<Project> projectList){
        this.mCtx = mCtx;
        this.projectList = projectList;
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.projects_list, null);
        ProjectViewHolder holder = new ProjectViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder projectViewHolder, int i){
        Project project = projectList.get(i);

        projectViewHolder.projectTitle.setText(project.getTitle());
        projectViewHolder.projectClient.setText(project.getIdClient()); // Perlu diganti biar muncul nama dari table lain
        projectViewHolder.projectField.setText(project.getIdField()); // Perlu diganti biar muncul nama dari table lain
        projectViewHolder.projectCategory.setText(project.getIdCategory());

        switch (project.getStatus()){

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
                tvStatus = "Project On Progress";
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
                projectViewHolder.projectRating.setRating(project.getRating());
                projectViewHolder.projectRating.setVisibility(View.VISIBLE);
                break;
            default:
                tvStatus = "UNDEFINED";
                tvProgressBar = 0;
        }
        projectViewHolder.projectStatus.setText(tvStatus);
        projectViewHolder.projectProgressBar.setProgress(tvProgressBar);

    }

    @Override
    public int getItemCount() {return projectList.size();}

    class ProjectViewHolder extends RecyclerView.ViewHolder{
        TextView projectTitle, projectClient, projectField, projectCategory, projectStatus;
        ProgressBar projectProgressBar;
        RatingBar projectRating;

        public ProjectViewHolder(@NonNull View itemView){
            super(itemView);

            projectTitle = itemView.findViewById(R.id.projectTitle);
            projectProgressBar = itemView.findViewById(R.id.projectProgressBar);
            projectClient = itemView.findViewById(R.id.projectClient);
            projectField = itemView.findViewById(R.id.projectField);
            projectCategory = itemView.findViewById(R.id.projectCategory);
            projectStatus = itemView.findViewById(R.id.projectStatus);
            projectRating = itemView.findViewById(R.id.projectRating);

        }
    }

}
