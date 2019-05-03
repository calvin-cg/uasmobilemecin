package umn.ac.mecinan.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import umn.ac.mecinan.model.Project;
import umn.ac.mecinan.adapter.ProjectsViewAdapter;
import umn.ac.mecinan.R;
import umn.ac.mecinan.model.User;
import umn.ac.mecinan.listener.OnGetProjectDataListener;
import umn.ac.mecinan.listener.OnGetUserProjectRoleListener;

public class MainOngoingFragment extends Fragment {

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
         * Retrieve Project
         */
        Project project = new Project();
        project.retrieveProject(new OnGetProjectDataListener() {
            final String TAG = "retrieve_project";
            List<Project> listOngoing = new ArrayList<>();

            @Override
            public void onStart() {

            }

            @Override
            public void onDataChange(Project project) {
                User user = new User();

                listOngoing.add(project);
            }

            @Override
            public void onSuccess() {
                Log.d("retrieve_project", "callback retrieve project");

                RecyclerView recyclerView;
                ProjectsViewAdapter ongoingAdapter;

                recyclerView = myFragmentView.findViewById(R.id.ongoingRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                /**
                 * Set to recycler view from listongoing
                 */
                ongoingAdapter = new ProjectsViewAdapter(getActivity(), listOngoing, false);
                recyclerView.setAdapter(ongoingAdapter);
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });

        /*project.retrieveProject(new OnGetProjectDataListener() {
            final String TAG = "retrieve_project";

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List<Project> listOngoing, User user) {
                Log.d("retrieve_project", "callback retrieve project");

                RecyclerView recyclerView;
                ProjectsViewAdapter ongoingAdapter;

                recyclerView = myFragmentView.findViewById(R.id.ongoingRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                *//**
                 * Set to recycler view from listongoing
                 *//*
                ongoingAdapter = new ProjectsViewAdapter(getActivity(), listOngoing, user, false);
                recyclerView.setAdapter(ongoingAdapter);
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Log.d(TAG, "dbError: " + databaseError);
            }
        });*/

        return myFragmentView;
    }
}
