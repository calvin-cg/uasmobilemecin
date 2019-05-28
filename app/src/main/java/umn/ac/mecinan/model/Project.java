package umn.ac.mecinan.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import umn.ac.mecinan.listener.OnGetProjectDataListener;

public class Project {

    private String idProject;
    private String title;
    //private Date dateStart, dateDue;
    private String idEmployee, idClient;
    private User userEmployee, userClient;
    private String idField, idCategory;
    private String desc;
    private int price;
    private int status;
    //private Date dateEnd;
    private float rating;
    private long date;

    /**
     * STATUS
     *  0  = Waiting for Response
     *  1  = Accepted
     * -1  = Refused
     *  2  = Paid
     * -2  = Not Paid
     *  3  = Finished
     * -3  = Not Finished
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
    public Project(String idProject, String title, String idEmployee, String idClient, String idField, String idCategory, String desc, int price, int status, float rating, long date) {
        this.idProject = idProject;
        this.title = title;
        this.idEmployee = idEmployee;
        this.userEmployee = null;
        this.idClient = idClient;
        this.userClient = null;
        this.idField = idField;
        this.idCategory = idCategory;
        this.desc = desc;
        this.price = price;
        this.status = status;
        this.rating = rating;
        this.date = date;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("idProject", idProject);
        return result;
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
    public String getIdProject() {
        return idProject;
    }
    public void setIdProject(String idProject) {
        this.idProject = idProject;
    }

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

    public long getDate() {
        return date;
    }
    public void setDate(long date) {
        this.date = date;
    }

    public String getIdEmployee() {
        return idEmployee;
    }
    public void setIdEmployee(String idEmployee) {
        this.idEmployee = idEmployee;
    }
    public User getUserEmployee() {
        return userEmployee;
    }
    public void setUserEmployee(User employee) {
        this.userEmployee = employee;
    }


    public String getIdClient() {
        return idClient;
    }
    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }
    public User getUserClient() {
        return userClient;
    }
    public void setUserClient(User client) {
        this.userClient = client;
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
     * desc: retrieve project from realtime db and
     *      call listener when project retrieved
     *
     * param:
     *      OnGetProjectDataListener listener
     *
     * return void
     */
    public void retrieveProject(final OnGetProjectDataListener projectListener) {
        final String TAG = "retrieve_project";

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myProjectRef = database.getReference("project");

        projectListener.onStart();

        Log.d(TAG, "start retrieve project");
        Log.d(TAG, "ref: " + myProjectRef);
        myProjectRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Project project;
                    project = ds.getValue(Project.class);

                    projectListener.onDataChange(project);
                    Log.d(TAG, "looping project");
                }

                projectListener.onSuccess();
                Log.d(TAG, "finish looping");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read user value.", databaseError.toException());

                projectListener.onFailed(databaseError);
            }

        });
    }
}
