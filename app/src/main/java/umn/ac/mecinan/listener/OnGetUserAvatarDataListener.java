package umn.ac.mecinan.listener;

import com.google.firebase.database.DatabaseError;

import java.io.File;

public interface OnGetUserAvatarDataListener {
    void onStart();
    void onSuccess(File file);
    void onFailed (Exception exception);
}
