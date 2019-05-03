package umn.ac.mecinan.listener;

import com.google.firebase.database.DatabaseError;

import umn.ac.mecinan.model.Project;
import umn.ac.mecinan.model.User;

public interface OnGetUserInProjectListener {
    void onStart();
    void onSuccess(Project project);
    void onFailed (DatabaseError databaseError);
}
