package umn.ac.mecinan;

import com.google.firebase.database.DatabaseError;

import java.util.List;

public interface OnGetUserDataListener {
    void onStart();
    void onSuccess(User user);
    void onFailed (DatabaseError databaseError);
}
