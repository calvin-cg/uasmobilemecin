package umn.ac.mecinan.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import umn.ac.mecinan.listener.OnGetProjectDataListener;
import umn.ac.mecinan.listener.OnGetUserInProjectListener;
import umn.ac.mecinan.model.ButtonProject;
import umn.ac.mecinan.model.Mail;
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
         * Checking Attached Activity
         */
        Activity activity = getActivity();

        if(isAdded() && activity != null) {
            /**
             * Retrieve My Project
             */
            Project project = new Project();
            project.retrieveProject(new OnGetProjectDataListener() {
                final String TAG = "retrieve_my_project";
                ProjectsViewAdapter myProjectAdapter;

                List<Project> allProject = new ArrayList<>();
                List<Project> listMyProject = new ArrayList<>();

                List<Boolean> listIsEmployee = new ArrayList<>();
                List<ButtonProject> listButton = new ArrayList<>();

                Boolean firstInit = true;

                @Override
                public void onStart() {
                    TextView tvEmpty;
                    ProgressBar pBar;
                    tvEmpty = myFragmentView.findViewById(R.id.tvEmptyMyProject);
                    pBar = myFragmentView.findViewById(R.id.pBar);

                    tvEmpty.setText(getString(R.string.loading_my_project));
                    pBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onDataChange(Project project) {
                    allProject.add(project);
                }

                @Override
                public void onSuccess() {
                    Log.d(TAG, "callback success retrieve my project");

                    final String TAG = "user_in_project";
                    Log.d("user_in_project", "retrieving user in project");
                    User userInProject = new User();

                    /**
                     * Joining Process--
                     * Retrieve User who are on the project
                     * (from idEmployee and idClient)
                     */
                    userInProject.retrieveUserInProject(curr_user, allProject, new OnGetUserInProjectListener() {
                        TextView tvEmpty = myFragmentView.findViewById(R.id.tvEmptyMyProject);
                        RecyclerView recyclerView = myFragmentView.findViewById(R.id.myprojectRecyclerView);
                        ProgressBar pBar = myFragmentView.findViewById(R.id.pBar);

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onDataChange(Project project) {
                            String curr_user_email = curr_user.getEmail();
                            User client = project.getUserClient();

                            if(curr_user_email.equals(client.getEmail())) {
                                tvEmpty.setVisibility(View.GONE);
                                pBar.setVisibility(View.GONE);

                                if(project.getStatus() == 1 || project.getStatus() == 3) {
                                    listMyProject.add(project);
                                    listIsEmployee.add(false);

                                    ButtonProject buttonProject = new ButtonProject();
                                    listButton.add(buttonProject.makeButton(project.getStatus()));
                                } else {
                                    listMyProject.add(project);
                                    listIsEmployee.add(false);

                                    ButtonProject buttonProject = new ButtonProject();
                                    listButton.add(buttonProject.makeButton(4));
                                }
                            }
                        }

                        @Override
                        public void onSuccess() {

                            List<Project> listMyProjectUpdate = new ArrayList<>(listMyProject);
                            List<Boolean> listIsEmployeeUpdate = new ArrayList<>(listIsEmployee);
                            List<ButtonProject> listButtonUpdate = new ArrayList<>(listButton);

                            /**
                             * Set to recycler view from listongoing
                             */
                            if(firstInit) {
                                myProjectAdapter = new ProjectsViewAdapter(getActivity(), listMyProjectUpdate, listIsEmployeeUpdate, listButtonUpdate);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.setAdapter(myProjectAdapter);

                                listMyProject.clear();
                                listIsEmployee.clear();
                                listButton.clear();

                                firstInit = false;
                            } else {
                                myProjectAdapter.updateProjectList(listMyProjectUpdate, listIsEmployeeUpdate, listButtonUpdate);

                                listMyProject.clear();
                                listIsEmployee.clear();
                                listButton.clear();
                            }

                            pBar.setVisibility(View.GONE);

                            if(myProjectAdapter.getItemCount() <= 0) {
                                tvEmpty.setText(getString(R.string.empty_my_project));
                                tvEmpty.setVisibility(View.VISIBLE);
                            }

                            allProject.clear();
                        }

                        @Override
                        public void onFailed(DatabaseError databaseError) {
                            Log.d(TAG, "dbError: " + databaseError);
                        }
                    });
                }

                @Override
                public void onFailed(DatabaseError databaseError) {
                    Log.d(TAG, "dbError: " + databaseError);
                }
            });
        }

        return myFragmentView;
    }
}
