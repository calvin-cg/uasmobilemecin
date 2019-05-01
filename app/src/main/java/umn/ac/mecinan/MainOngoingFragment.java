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
    RecyclerView recyclerView;
    ProjectsViewAdapter ongoingAdapter;
    List<Project> listOngoing;
    FirebaseDatabase database;
    DatabaseReference myRef;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("EZRA", "onCreateView MainOngoingFragment");
        View myFragmentView = inflater.inflate(R.layout.fragment_main_ongoing, container, false);

        /**
         * FRAGMENT ONGOING
         */
        listOngoing = new ArrayList<>();
        recyclerView = myFragmentView.findViewById(R.id.ongoingRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Include ini untuk set tanggal
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Log.d("EZRA", "onCreateView MainMyProjectFragment: InputDatabase");
        Project temp1, temp2, temp3;
        temp1 = temp2 = temp3 = null;
        /*try {
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
        }*/


        //Write Data
        /*title = "Website Mecan.an";
        idEmployee = "0001EZRA";
        idClient = "0002SPON";
        idField = "IT";
        idCategory = "Website";
        desc = "Rancang bangun website technopreneurship";
        price = 100000;
        status = 3;
        rating = 4;

        Project project = new Project(
                title,
                idEmployee,
                idClient,
                idField,
                idCategory,
                desc,
                price,
                status,
                rating
        );

        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("project").setValue(project);*/


        //Retrieve data
        //System.out.println(myRef.child("project").child("idEmployee"));
        myRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference temp = myRef.child("project");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("debug", "datachange");
                System.out.println(dataSnapshot.getChildren());

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for(DataSnapshot dataSnapshot1: children){
                    Log.d("debug", "masukfor");
                   // System.out.println(dataSnapshot1.getValue(Project.class));

                    Project listProject = dataSnapshot1.getValue(Project.class);
                    Log.d("debug", "getvalue");
                    Project project = new Project();

                    Log.d("debug", "getset");

                    title = listProject.getTitle();
                    /*dateStart = listProject.getDateStart();
                    dateDue = listProject.getDateDue();
                    dateEnd = listProject.getDateEnd();*/
                    desc = listProject.getDesc();
                    idCategory = listProject.getIdCategory();
                    idEmployee = listProject.getIdEmployee();
                    idClient = listProject.getIdClient();
                    idField = listProject.getIdField();
                    price = listProject.getPrice();
                    rating = listProject.getRating();
                    status = listProject.getStatus();

                    project.setTitle(title);
                    /*project.setDateStart(dateStart);
                    project.setDateDue(dateDue);
                    project.setDateEnd(dateEnd);*/
                    project.setDesc(desc);
                    project.setIdCategory(idCategory);
                    project.setIdEmployee(idEmployee);
                    project.setIdClient(idClient);
                    project.setIdField(idField);
                    project.setPrice(price);
                    project.setRating(rating);
                    project.setStatus(status);

                    listOngoing.add(project);
                }

                ongoingAdapter = new ProjectsViewAdapter(getActivity(), listOngoing, false);
                recyclerView.setAdapter(ongoingAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        ongoingAdapter = new ProjectsViewAdapter(getActivity(), listOngoing, false);
        recyclerView.setAdapter(ongoingAdapter);

        return myFragmentView;
    }

}
