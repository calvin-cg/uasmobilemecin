package umn.ac.mecinan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        View myFragmentView = inflater.inflate(R.layout.fragment_main_ongoing, container, false);

        /**
         * FRAGMENT ONGOING
         */


        /**
         * Retrieve Project
         */
        RecyclerView recyclerView;

        recyclerView = myFragmentView.findViewById(R.id.ongoingRecyclerView);

        Project project = new Project();
        project.retrieveProject(getActivity(), recyclerView);

        return myFragmentView;
    }
}
