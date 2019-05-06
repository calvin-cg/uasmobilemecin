package umn.ac.mecinan.listener;

import com.google.firebase.database.DatabaseError;

import umn.ac.mecinan.model.Project;
import umn.ac.mecinan.model.User;

public interface OnGetUserInProjectListener {
    void onStart();
    void onDataChange(Project project);
    void onSuccess();
    void onFailed (DatabaseError databaseError);
}
