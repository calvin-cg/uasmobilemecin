package umn.ac.mecinan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ProjectsViewAdapter extends RecyclerView.Adapter<ProjectsViewAdapter.ProjectViewHolder> {

    private Context mCtx;
    private List<Project> projectList;

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
        projectViewHolder.projectField.setText(project.getNameField());
        projectViewHolder.projectCat.setText(project.getNameCategory());
        projectViewHolder.projectDesc.setText(project.getDesc());
        projectViewHolder.projectClient.setText(project.getNameClient());

    }

    @Override
    public int getItemCount() {return projectList.size();}

    class ProjectViewHolder extends RecyclerView.ViewHolder{
        TextView projectTitle, projectField, projectCat, projectDesc, projectClient;

        public ProjectViewHolder(@NonNull View itemView){
            super(itemView);

            projectTitle = itemView.findViewById(R.id.projectTitle);
            projectField = itemView.findViewById(R.id.projectField);
            projectCat = itemView.findViewById(R.id.projectCat);
            projectDesc = itemView.findViewById(R.id.projectDesc);
            projectClient = itemView.findViewById(R.id.projectClient);

        }
    }

}
