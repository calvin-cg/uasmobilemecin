package umn.ac.mecinan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        Log.d("EZRA", "onCreateView MainOngoingFragment");
        View myFragmentView = inflater.inflate(R.layout.fragment_main_ongoing, container, false);

        /**
         * FRAGMENT ONGOING
         */
        ongoingProjectList = new ArrayList<>();
        recyclerView = myFragmentView.findViewById(R.id.ongoingRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Include ini untuk set tanggal
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Log.d("EZRA", "onCreateView MainMyProjectFragment: InputDatabase");
        Project temp1, temp2, temp3;
        temp1 = temp2 = temp3 = null;
        try {
            temp1 = new Project(
                    "Website Mecan.an",
                    simpleDateFormat.parse("2019-04-30"),
                    simpleDateFormat.parse("2019-07-20"),
                    "0001EZRA",
                    "0002SPON",
                    "IT",
                    "Website",
                    "Rancang bangun website technopreneurship",
                    10000000,
                    3
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            temp2 = new Project(
                    "Aplikasi Makan Yuk",
                    simpleDateFormat.parse("2019-04-30"),
                    simpleDateFormat.parse("2019-07-20"),
                    "0001EZRA",
                    "0004ELIN",
                    "IT",
                    "Aplikasi Mobile",
                    "Rancang bangun aplikasi technopreneurship",
                    10000000,
                    4
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            temp3 = new Project(
                    "Invitation Sweet 17",
                    simpleDateFormat.parse("2019-04-30"),
                    simpleDateFormat.parse("2019-05-01"),
                    "0002SPON",
                    "0001EZRA",
                    "Art & Design",
                    "Design Undangan",
                    "Buat design kartu undangan sweet17 dengan tema laut",
                    120000,
                    4
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //MISALKAN: LOGIN as Ezra Abednego Hayvito, id 0001EZRA
        String tempLoggedInUser = "0001EZRA";
        if (temp1.getIdClient() == tempLoggedInUser){
            ongoingProjectList.add(temp1);
        }
        if (temp2.getIdClient() == tempLoggedInUser){
            ongoingProjectList.add(temp2);
        }
        if (temp3.getIdClient() == tempLoggedInUser){
            ongoingProjectList.add(temp3);
        }

        ongoingAdapter = new ProjectsViewAdapter(getActivity(), ongoingProjectList, false);
        recyclerView.setAdapter(ongoingAdapter);

        return myFragmentView;
    }

}
