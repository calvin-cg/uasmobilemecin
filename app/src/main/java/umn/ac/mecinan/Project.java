package umn.ac.mecinan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Project {

    private String title;
    //private Date dateStart, dateDue;
    private String idEmployee, idClient;
    private String idField, idCategory;
    private String desc;
    private int price;
    private int status;
    //private Date dateEnd;
    private float rating;


    private final String TAG = "retrieve_project";
    private ProjectsViewAdapter ongoingAdapter;
    private List<Project> listOngoing;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myProjectRef = database.getReference("project");

    /**
     * STATUS
     *  0  = Waiting for Response
     *  1  = Accepted
     * -1  = Refused
     *  2  = Paid
     * -2  = Not Paid
     *  3  = Finished
     * -3  = Ongoing
     *  4  = Confirmed
     * -4  = Unconfirmed
     */
    public Project(){

    }
    /*public Project(String title, Date dateStart, Date dateDue, String idEmployee, String idClient, String idField, String idCategory, String desc, int price, int status) {

        this.title = title;
        this.dateStart = dateStart;
        this.dateDue = dateDue;
        this.idEmployee = idEmployee;
        this.idClient = idClient;
        this.idField = idField;
        this.idCategory = idCategory;
        this.desc = desc;
        this.price = price;
        this.status = status;

        this.dateEnd = null;
        this.rating = 4;

    }
    */
    public Project(String title, String idEmployee, String idClient, String idField, String idCategory, String desc, int price, int status, float rating) {

        this.title = title;
        this.idEmployee = idEmployee;
        this.idClient = idClient;
        this.idField = idField;
        this.idCategory = idCategory;
        this.desc = desc;
        this.price = price;
        this.status = status;
        this.rating = rating;

    }


    /**
     * Method: Setter and Getter
     * desc: Setter and Getter for Project
     *
     * List Method:
     *      Set and Get Title
     *      Set and Get ---
     *      Set and Get ---
     *      Set and Get ---
     *      Etc..
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

   /* public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }


    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }


    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }*/


    public String getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(String idEmployee) {
        this.idEmployee = idEmployee;
    }


    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }


    public String getIdField() {
        return idField;
    }

    public void setIdField(String idField) {
        this.idField = idField;
    }


    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        if (rating>=0 && rating<=5){
            this.rating = rating;
        }
    }


    /**
     * Method: retrieveProject()
     * desc: retrieve project from realtime db and set into MainActivity
     *
     * param:
     *      OnGetProjectDataListener listener
     *      Context context
     *      RecyclerView recyclerview
     *
     * return void
     */
    public void retrieveProject(final Context context, final RecyclerView recyclerView, final OnGetProjectDataListener listener) {
        listOngoing = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        Log.d(TAG, "start retrieve project");
        Log.d(TAG, "ref: " + myProjectRef);
        myProjectRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Project listProject = ds.getValue(Project.class);
                    Project project = new Project();

                    title = listProject.getTitle();
                    desc = listProject.getDesc();
                    idCategory = listProject.getIdCategory();
                    idEmployee = listProject.getIdEmployee();
                    idClient = listProject.getIdClient();
                    idField = listProject.getIdField();
                    price = listProject.getPrice();
                    rating = listProject.getRating();
                    status = listProject.getStatus();

                    project.setTitle(title);
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

                listener.onSuccess(listOngoing);

                /**
                 * Set to recycler view from listongoing
                 */
                ongoingAdapter = new ProjectsViewAdapter(context, listOngoing, false);
                recyclerView.setAdapter(ongoingAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read user value.", databaseError.toException());
            }

        });
    }
}
