package umn.ac.mecinan.listener;

import com.google.firebase.database.DatabaseError;

import umn.ac.mecinan.model.Project;

public interface OnGetUserProjectRoleListener {
    void onStart();
    void onSuccess(Project project);
    void onFailed (DatabaseError databaseError);
}
