package umn.ac.mecinan.listener;

import com.google.firebase.database.DatabaseError;

import umn.ac.mecinan.model.Project;
import umn.ac.mecinan.model.User;

public interface OnGetEmployeeListener {
    void onStart();
    void onDataChange(User user);
    void onSuccess();
    void onFailed (DatabaseError databaseError);
}
