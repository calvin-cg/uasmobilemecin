package umn.ac.mecinan.listener;

import com.google.firebase.database.DatabaseError;

import umn.ac.mecinan.model.User;

public interface OnGetUserDataListener {
    void onStart();
    void onSuccess(User user);
    void onFailed (DatabaseError databaseError);
}
