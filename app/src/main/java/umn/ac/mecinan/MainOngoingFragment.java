package umn.ac.mecinan;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
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

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            ongoingProjectList.add(
                    new Project(
                            "Website Mecan.an",
                            simpleDateFormat.parse("2019-04-30"),
                            simpleDateFormat.parse("2019-07-20"),
                            "Ezra Abednego Hayvito",
                            "Spongebob",
                            "IT",
                            "Aplikasi Mobile",
                            "Rancang bangun website technopreneurship",
                            10000000,
                            3
                    )
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            ongoingProjectList.add(
                    new Project(
                            "Invitation Sweet 17",
                            simpleDateFormat.parse("2019-04-30"),
                            simpleDateFormat.parse("2019-05-01"),
                            "Ezra Abednego Hayvito",
                            "Cute Gurl",
                            "Art & Design",
                            "Design Undangan",
                            "Buat design kartu undangan sweet17 dengan tema laut",
                            120000,
                            4
                    )
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ongoingAdapter = new ProjectsViewAdapter(getActivity(), ongoingProjectList);
        recyclerView.setAdapter(ongoingAdapter);


        return myFragmentView;
    }

}
