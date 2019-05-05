package umn.ac.mecinan.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import umn.ac.mecinan.listener.OnGetProjectDataListener;
import umn.ac.mecinan.listener.OnGetUserInProjectListener;
import umn.ac.mecinan.model.Project;
import umn.ac.mecinan.adapter.ProjectsViewAdapter;
import umn.ac.mecinan.R;
import umn.ac.mecinan.model.User;

public class MainMyProjectFragment extends Fragment {

    /**
     * DECLARATION - FIREBASE
     */
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser curr_user = auth.getCurrentUser();

    /**
     * DECLARATION - DATABASE PROJECT
     */
    private String title;
    private Date dateStart, dateDue;
    private String idEmployee, idClient;
    private String idField, idCategory;
    private String desc;
    private int price;
    private int status;
    private Date dateEnd;
    private float rating;

    public MainMyProjectFragment() {
        // Required empty public constructor
        Log.d("EZRA", "Start = MainMyProjectFragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        Log.d("EZRA", "onCreateView MainMyProjectFragment");
        final View myFragmentView = inflater.inflate(R.layout.fragment_main_myproject, container, false);

        /**
         * FRAGMENT MyProject
         */
        Log.d("EZRA", "onCreateView MainMyProjectFragment: Fragment ");


        /**
         * Retrieve My Project
         */
        Project project = new Project();
        project.retrieveProject(new OnGetProjectDataListener() {
            final String TAG = "retrieve_my_project";
            List<Project> listMyProject = new ArrayList<>();
            List<Boolean> listIsEmployee = new ArrayList<>();

            @Override
            public void onStart() {
                TextView tvEmpty;
                tvEmpty = myFragmentView.findViewById(R.id.tvEmptyMyProject);

                tvEmpty.setText(getString(R.string.loading_my_project));
            }

            @Override
            public void onDataChange(Project project) {
                final String TAG = "user_in_project";
                Log.d("user_in_project", "retrieving user in project");
                User userInProject = new User();

                /**
                 * Joining Process--
                 * Retrieve User who are on the project
                 * (from idEmployee and idClient)
                 */
                userInProject.retrieveUserInProject(curr_user, project, new OnGetUserInProjectListener() {
                    ProjectsViewAdapter myProjectAdapter = null;
                    TextView tvEmpty = myFragmentView.findViewById(R.id.tvEmptyMyProject);
                    RecyclerView recyclerView = myFragmentView.findViewById(R.id.myprojectRecyclerView);

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onDataChange(Project project) {
                        String curr_user_email = curr_user.getEmail();
                        User client = project.getUserClient();

                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        Log.d(TAG, client.getEmail());
                        if(curr_user_email.equals(client.getEmail())) {
                            String TAG = "attaching_ongoing";

                            tvEmpty.setVisibility(View.GONE);
                            if(project.getStatus() == 1 || project.getStatus() == 3) {
                                Log.d("Title Status 1 & 3 :", project.getTitle());
                                listMyProject.add(project);
                            }
                        }

                        /*
                        Log.d("isNullPVA_my", "isNull: " + myProjectAdapter);
                        if(myProjectAdapter == null) {
                            tvEmpty.setVisibility(View.VISIBLE);
                        }
                        */
                    }

                    @Override
                    public void onSuccess() {
                        /**
                         * Set to recycler view from listongoing
                         */
                        listIsEmployee.add(false);
                        myProjectAdapter = new ProjectsViewAdapter(getActivity(), listMyProject, listIsEmployee);

                        recyclerView.setAdapter(myProjectAdapter);

                        if(myProjectAdapter.getItemCount() <= 0) {
                            tvEmpty.setText(getString(R.string.empty_my_project));
                            tvEmpty.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onSuccess() {
                Log.d(TAG, "callback success retrieve my project");
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Log.d(TAG, "dbError: " + databaseError);
            }
        });

        return myFragmentView;
    }
}
