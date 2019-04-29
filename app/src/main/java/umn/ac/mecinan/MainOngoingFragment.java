package umn.ac.mecinan;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class MainOngoingFragment extends Fragment {

    /**
     * DECLARATION - DATABASE PROJECT
     */
    RecyclerView recyclerView;
    ProjectsViewAdapter ongoingAdapter;
    List<Project> ongoingProjectList;

    public MainOngoingFragment() {
        // Required empty public constructor
        Log.d("EZRA", "Start = MainOngoingFragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("EZRA", "Start MainOngoingFragment");
        View myFragmentView = inflater.inflate(R.layout.fragment_main_ongoing, container, false);

        /**
         * FRAGMENT ONGOING
         */
        ongoingProjectList = new ArrayList<>();
        recyclerView = myFragmentView.findViewById(R.id.ongoingRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ongoingProjectList.add(
                new Project(
                        001,
                        "Aplikasi Mecan.an",
                        "Ezra Abednego Hayvito",
                        "Spongebob",
                        "IT",
                        "Aplikasi Mobile",
                        "Ongoing",
                        "Membuat rancang bangun berbasis android untuk toko bangunan Mecan.an"
                )
        );

        ongoingProjectList.add(
                new Project(
                        002,
                        "Invitation Sweet 17",
                        "Ezra Abednego Hayvito",
                        "Cute Girl",
                        "Art & Design",
                        "Design Undangan",
                        "Ongoing",
                        "Design kartu undangan Sweet 17 tema langit."
                )
        );

        ongoingAdapter = new ProjectsViewAdapter(getActivity(), ongoingProjectList);
        recyclerView.setAdapter(ongoingAdapter);

        return myFragmentView;
    }

}
