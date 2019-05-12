package umn.ac.mecinan.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import umn.ac.mecinan.listener.OnGetEmployeeListener;
import umn.ac.mecinan.listener.OnGetUserAvatarDataListener;
import umn.ac.mecinan.listener.OnGetUserDataListener;
import umn.ac.mecinan.listener.OnGetUserInProjectListener;
import umn.ac.mecinan.listener.OnGetUserProjectRoleListener;

public class User {

    private String username, email, tagline, phoneNumber, desc, field, category, fee, id;

    /** isEmployee Default Value is false */
    private Boolean isEmployee;

    /** ratingEmployee Default Value is 0.0f */
    private Float ratingEmployee;

    /** totalProject Completed Default Value is 0 */
    private Integer totalProjectCompleted;


    public User(){

    }
    public User(String id, String username, String email, String tagline, String phoneNumber) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.tagline = tagline;
        this.phoneNumber = phoneNumber;
        this.isEmployee = false;
        this.ratingEmployee = 0.0f;
        this.totalProjectCompleted = 0;
    }

    public User(String email, String username, String desc, String phoneNumber, String field, String category, String fee, String id) {
        this.email = email;
        this.username = username;
        this.desc = desc;
        this.phoneNumber = phoneNumber;
        this.field = field;
        this.category = category;
        this.fee = fee;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("username", username);
        result.put("desc", desc);
        result.put("phoneNumber", phoneNumber);
        result.put("field", field);
        result.put("category", category);
        result.put("fee", fee);

        return result;
    }

    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getFee() {
        return fee;
    }
    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getTagline() {
        return tagline;
    }
    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getIsEmployee() {
        return isEmployee;
    }
    public void setIsEmployee(Boolean isEmployee) {
        this.isEmployee = isEmployee;
    }

    public Float getRatingEmployee() {
        return ratingEmployee;
    }
    public void setRatingEmployee(Float ratingEmployee) {
        this.ratingEmployee = ratingEmployee;
    }

    public Integer getTotalProjectCompleted() {
        return totalProjectCompleted;
    }
    public void setTotalProjectCompleted(Integer totalProjectCompleted) {
        this.totalProjectCompleted = totalProjectCompleted;
    }



    /**
     * Method: retrieveProfile()
     * desc: retrieve user from realtime db and set into TextView
     *
     * param:
     *      @FirebaseUser curr_user
     *      @OnGetUserDataListener userListener
     *
     * return void
     */
    public void retrieveProfile(final FirebaseUser curr_user, final OnGetUserDataListener userListener) {
        final String TAG = "retrieve_profile";

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user");

        Log.d(TAG, "start method retrieve profile (in User.java)");
        Log.d(TAG, "ref: " + userRef);
        userRef.addValueEventListener(new ValueEventListener() {
            User user;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange");

                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    //Log.d(TAG, "curr_user: " + curr_user.getEmail());
                    //Log.d(TAG, "ds: " + ds.getValue(User.class).getEmail());

                    if(curr_user.getUid().equals(ds.getKey())) {
                        Log.d(TAG, "ds: " + ds.getValue(User.class).getUsername());
                        Log.d(TAG, "ds: " + ds.getValue(User.class).getTagline());
                        Log.d(TAG, "ds: " + ds.getValue(User.class).getEmail());
                        user = ds.getValue(User.class);

                        userListener.onSuccess(user);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read user value.", error.toException());

                userListener.onFailed(error);
            }
        });

        Log.d(TAG, "finish method retrieve profile");
    }


    /**
     * Method: retrieveAvatar()
     * desc: retrieve user avatar from realtime db and set into ImageView
     *
     * param:
     *      @FirebaseUser curr_user
     *      @OnGetUserAvatarDataListener userAvatarListener
     *
     * return void
     */
    public void retrieveAvatar(FirebaseUser curr_user, final OnGetUserAvatarDataListener userAvatarListener) throws IOException {
        final String TAG = "retrieve_avatar";

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference mAvatarRef;
        mAvatarRef = storage.getReference("user_avatar/" + curr_user.getUid());

        Log.d(TAG, "ref: " + mAvatarRef);
        Log.d(TAG, "snap: " + "start");

        final File localFile = File.createTempFile("images", "jpg");
        mAvatarRef.getFile(localFile)
            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, "snap: " + taskSnapshot);

                    userAvatarListener.onSuccess(localFile);
                }
            })

            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d(TAG, "snap: " + "Failed... Retrieving default avatar...");

                    try{
                        retrieveDefaultAvatar(new OnGetUserAvatarDataListener() {
                            final String TAG = "retrieve_profile";

                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onSuccess(File file) {
                                userAvatarListener.onSuccess(file);
                            }

                            @Override
                            public void onFailed(Exception exception) {
                                userAvatarListener.onFailed(exception);
                            }
                        });
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    public void retrieveDefaultAvatar(final OnGetUserAvatarDataListener userAvatarDataListener) throws IOException {
        final String TAG = "retrieve_avatar";

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference mDefaultAvatarRef;
        mDefaultAvatarRef = storage.getReference("user_avatar/default.jpg");

        Log.d(TAG, "defaultref: " + mDefaultAvatarRef);
        Log.d(TAG, "snap: " + "start");

        final File localFile = File.createTempFile("images", "jpg");
        mDefaultAvatarRef.getFile(localFile)
            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, "snap: " + taskSnapshot);

                    userAvatarDataListener.onSuccess(localFile);
                }
            })

            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d(TAG, "snap: " + "Failed retrieving default avatar...");

                    userAvatarDataListener.onFailed(exception);
                }
            });
    }


    /**
     * Joining Process--
     * Method: retrieveUserInProject()
     * desc: retrieve users who are on the project
     *
     * param:
     *      @Project project
     *      @OnGetUserInProjectListener userInProjectListener
     *
     * return void
     */
    public void retrieveUserInProject(final FirebaseUser curr_user, final List<Project> projects, final OnGetUserInProjectListener userInProjectListener) {
        final String TAG = "user_in_project";

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user");

        Log.d(TAG, "start method retrieve user in project (in User.java)");
        Log.d(TAG, "ref: " + userRef);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Log.d(TAG, "countJoinGlobal: " + countJoin);
                Log.d("adapter_notify", "pr size: " + projects.size());
                for(Project project: projects){
                    int countJoin = 0;

                    for(DataSnapshot ds: dataSnapshot.getChildren()) {
                        if(project.getIdEmployee().equals(ds.getKey())) {
                            Log.d(TAG, "key: " + ds.getKey());
                            project.setUserEmployee(ds.getValue(User.class));
                            countJoin++;
                        }

                        if(project.getIdClient().equals(ds.getKey())) {
                            project.setUserClient(ds.getValue(User.class));
                            countJoin++;
                        }

                        if(project.getIdEmployee().equals(ds.getKey()) || project.getIdClient().equals(ds.getKey())){
                            if(countJoin >= 2) {
                                Log.d(TAG, "onDataChange curr_user: " + curr_user.getEmail());
                                userInProjectListener.onDataChange(project);
                            }
                        }
                    }
                }

                userInProjectListener.onSuccess();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                userInProjectListener.onFailed(databaseError);
            }
        });
    }


    /**
     * Joining Process--
     * Method: retrieveEmployee()
     * desc: retrieve employee to appear in browse
     *
     * param:
     *      @Project project
     *      @OnGetUserInProjectListener userInProjectListener
     *
     * return void
     */
    public void retrieveEmployee(final OnGetEmployeeListener employeeListener) {
        final String TAG = "retrieve_employee";

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user");

        employeeListener.onStart();

        Log.d(TAG, "start method retrieve profile (in User.java)");
        Log.d(TAG, "ref: " + userRef);
        userRef.addValueEventListener(new ValueEventListener() {
            User user;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange");

                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    user = ds.getValue(User.class);
                    employeeListener.onDataChange(user);
                }

                employeeListener.onSuccess();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read user value.", error.toException());

                employeeListener.onFailed(error);
            }
        });

        Log.d(TAG, "finish method retrieve employee");
    }
}