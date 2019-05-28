package umn.ac.mecinan.listener;

import com.google.firebase.database.DatabaseError;

import umn.ac.mecinan.model.Mail;

public interface OnGetMailListener {
    void onStart();
    void onDataChange(Mail mail);
    void onSuccess();
    void onFailed (DatabaseError databaseError);
}
