package umn.ac.mecinan.listener;

import com.google.firebase.database.DatabaseError;

import umn.ac.mecinan.model.Project;
import umn.ac.mecinan.model.User;

public interface OnGetUserProjectRoleListener {
    void onStart();
    void onSuccess(Project project, User user, boolean isWorker);
    void onFailed (DatabaseError databaseError);
}
