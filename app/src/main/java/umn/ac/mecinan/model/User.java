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

import umn.ac.mecinan.listener.OnGetUserAvatarDataListener;
import umn.ac.mecinan.listener.OnGetUserDataListener;
import umn.ac.mecinan.listener.OnGetUserInProjectListener;
import umn.ac.mecinan.listener.OnGetUserProjectRoleListener;

public class User {

    private String username, email, tagline, phoneNumber;

    final String TAG = "retrieve_profile";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("user");

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser curr_user = auth.getCurrentUser();
    User user;

    public User(){

    }
    public User(String username, String email, String tagline, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.tagline = tagline;
        this.phoneNumber = phoneNumber;
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

        Log.d(TAG, "start method retrieve profile (in User.java)");
        Log.d(TAG, "ref: " + userRef);
        userRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange");

                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    //Log.d(TAG, "curr_user: " + curr_user.getEmail());
                    //Log.d(TAG, "ds: " + ds.getValue(User.class).getEmail());

                    if(curr_user.getEmail().equals(ds.getValue(User.class).getEmail())) {
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
     *      @Context context
     *      @FirebaseUser curr_user
     *      @ImageView avatar
     *
     * return void
     */
    public void retrieveAvatar(FirebaseUser curr_user, final OnGetUserAvatarDataListener userAvatarListener) throws IOException {
        final String TAG = "retrieve_avatar";

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference mAvatarRef;
        mAvatarRef = storage.getReference("user_avatar/" + curr_user.getUid() + ".jpg");

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
     * Method: retrieveUserProjectRole()
     * desc: retrieve user as a project client
     *
     * param:
     *      @String idClient
     *      @OnGetUserDataListener clientListener
     *
     * return void
     */
    public void retrieveUserProjectRole(final Project project, final OnGetUserProjectRoleListener roleListener) {
        final String TAG = "retrieve_project_role";

        Log.d(TAG, "start method retrieve project role (in User.java)");
        Log.d(TAG, "ref: " + userRef);
        userRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange");

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    //Log.d(TAG, "idClient: " + project.getIdClient());
                    //Log.d(TAG, "idWorker: " + project.getIdEmployee());
                    //Log.d(TAG, "key: " + ds.getKey());
                    Log.d(TAG, "curr_user: " + curr_user.getEmail());
                    if(curr_user.getEmail().equals(ds.getValue(User.class).getEmail())) {
                        user = ds.getValue(User.class);

                        if(project.getIdClient().equals(ds.getKey())) {
                            Log.d(TAG, "is Client");
                            roleListener.onSuccess(project, user, false);
                        } else if(project.getIdEmployee().equals(ds.getKey())) {
                            Log.d(TAG, "is Worker");
                            roleListener.onSuccess(project, user, false);
                        }
                    }
                }
                //Log.d(TAG, "user: " + user);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read user value.", error.toException());

                roleListener.onFailed(error);
            }
        });

        Log.d(TAG, "finish method retrieve employer");
    }

    public void retrieveUserInProject(final Project project, final OnGetUserInProjectListener userInProjectListener) {
        final String TAG = "user_in_project";

        Log.d(TAG, "start method retrieve user in project (in User.java)");
        Log.d(TAG, "ref: " + userRef);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int countJoin = 0;
                //Log.d(TAG, "countJoinGlobal: " + countJoin);

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
                            Log.d(TAG, "curr_user: " + curr_user.getEmail());
                            userInProjectListener.onSuccess(project);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                userInProjectListener.onFailed(databaseError);
            }
        });
    }
}