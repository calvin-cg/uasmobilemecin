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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import umn.ac.mecinan.listener.OnGetUserInProjectListener;
import umn.ac.mecinan.model.Project;
import umn.ac.mecinan.adapter.ProjectsViewAdapter;
import umn.ac.mecinan.R;
import umn.ac.mecinan.model.User;
import umn.ac.mecinan.listener.OnGetProjectDataListener;
import umn.ac.mecinan.listener.OnGetUserProjectRoleListener;

public class MainOngoingFragment extends Fragment {

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

    public MainOngoingFragment() {
        // Required empty public constructor
        Log.d("EZRA", "Start = MainOngoingFragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("EZRA", "onCreateView MainOngoingFragment");
        final View myFragmentView = inflater.inflate(R.layout.fragment_main_ongoing, container, false);

        /**
         * FRAGMENT ONGOING
         */


        /**
         * Retrieve Ongoing Project
         */
        Project project = new Project();
        project.retrieveProject(new OnGetProjectDataListener() {
            final String TAG = "retrieve_ongoingProject";
            List<Project> listOngoing = new ArrayList<>();
            List<Boolean> listIsEmployee = new ArrayList<>();

            @Override
            public void onStart() {
                TextView tvEmpty;
                tvEmpty = myFragmentView.findViewById(R.id.tvEmptyOngoingProject);

                tvEmpty.setText(getString(R.string.loading_ongoing_project));
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
                userInProject.retrieveUserInProject(project, new OnGetUserInProjectListener() {
                    ProjectsViewAdapter ongoingAdapter = null;
                    TextView tvEmpty = myFragmentView.findViewById(R.id.tvEmptyOngoingProject);
                    RecyclerView recyclerView = myFragmentView.findViewById(R.id.ongoingRecyclerView);

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onDataChange(Project project) {
                        String curr_user_email = curr_user.getEmail();
                        User employee = project.getUserEmployee();

                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                        if(curr_user_email.equals(employee.getEmail())) {
                            String TAG = "attaching_ongoing";

                            tvEmpty.setVisibility(View.GONE);
                            listOngoing.add(project);
                        }
                    }

                    @Override
                    public void onSuccess() {
                        /**
                         * Set to recycler view from listongoing
                         */
                        listIsEmployee.add(true);
                        ongoingAdapter = new ProjectsViewAdapter(getActivity(), listOngoing, listIsEmployee);

                        recyclerView.setAdapter(ongoingAdapter);

                        if(ongoingAdapter.getItemCount() <= 0) {
                            tvEmpty.setText(getString(R.string.empty_ongoing_project));
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
                Log.d(TAG, "callback success retrieve ongoing project");
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Log.d(TAG, "dbError: " + databaseError);
            }
        });

        return myFragmentView;
    }
}
