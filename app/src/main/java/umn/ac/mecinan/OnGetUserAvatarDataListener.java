package umn.ac.mecinan;

import com.google.firebase.database.DatabaseError;

import java.io.File;

public interface OnGetUserAvatarDataListener {
    void onStart();
    void onSuccess(File file);
    void onFailed (DatabaseError databaseError);
}
