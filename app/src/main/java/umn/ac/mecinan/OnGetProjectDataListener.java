package umn.ac.mecinan;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.List;

public interface OnGetProjectDataListener {
    void onStart();
    void onSuccess(List<Project> project);
    void onFailed (DatabaseError databaseError);
}
