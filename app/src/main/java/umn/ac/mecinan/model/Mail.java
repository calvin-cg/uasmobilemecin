package umn.ac.mecinan.model;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import umn.ac.mecinan.listener.OnGetMailListener;

public class Mail {

    private String idMail;
    private int mailType;    /** Mail Type is the same as Project Status to determine mail icon */
    private boolean mailIsRead;
    private String mailTitle, mailContent, mailReceivedDate;
    private String projectName, mailRecipient, mailSender;  /** mailRecipient & mailSender will be filled with corresponding user data */
    private User userRecipient, userSender;

    public Mail() { }
    public Mail(String idMail, int mailType, boolean mailIsRead, String mailTitle, String mailContent, String mailReceivedDate, String projectName, String mailRecipient, String mailSender) {
        this.idMail = idMail;
        this.mailType = mailType;
        this.mailIsRead = mailIsRead;
        this.mailTitle = mailTitle;
        this.mailContent = mailContent;
        this.mailReceivedDate = mailReceivedDate;
        this.projectName = projectName;
        this.mailRecipient = mailRecipient;
        this.mailSender = mailSender;
        this.userRecipient = null;
        this.userSender = null;
    }

    public String getIdMail() {
        return idMail;
    }
    public void setIdMail(String idMail) {
        this.idMail = idMail;
    }

    public int getMailType() { return mailType; }
    public void setMailType(int mailType) { this.mailType = mailType; }

    public boolean getMailIsRead() { return mailIsRead; }
    public void setMailIsRead(boolean mailIsRead) { this.mailIsRead = mailIsRead; }

    public String getMailTitle() { return mailTitle; }
    public void setMailTitle(String mailTitle) { this.mailTitle = mailTitle; }

    public String getMailContent() { return mailContent; }
    public void setMailContent(String mailContent) { this.mailContent = mailContent; }

    public String getMailReceivedDate() { return mailReceivedDate; }
    public void setMailReceivedDate(String mailReceivedDate) { this.mailReceivedDate = mailReceivedDate; }

    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }

    public String getMailRecipient() { return mailRecipient; }
    public void setMailRecipient(String mailRecipient) { this.mailRecipient = mailRecipient; }

    public String getMailSender() { return mailSender; }
    public void setMailSender(String mailSender) { this.mailSender = mailSender; }

    public User getUserRecipient() { return userRecipient; }
    public void setUserRecipient(User userRecipient) {
        this.userRecipient = userRecipient;
    }

    public User getUserSender() { return userSender; }
    public void setUserSender(User userSender) {
        this.userSender = userSender;
    }


    /**
     * Method: retrieveMail()
     * desc: retrieve mail from realtime db and
     *      call listener when mail retrieved
     *
     * param:
     *      @OnGetMailListener mailListener
     *
     * return void
     */
    public void retrieveMail(final OnGetMailListener mailListener) {
        final String TAG = "retrieve_mail";

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myMailRef = database.getReference("mail");

        mailListener.onStart();

        Log.d(TAG, "start retrieve mail");
        Log.d(TAG, "ref: " + myMailRef);
        myMailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Mail mail;
                    mail = ds.getValue(Mail.class);

                    Log.d(TAG, "looping mail");
                    mailListener.onDataChange(mail);
                }

                mailListener.onSuccess();
                Log.d(TAG, "finish looping");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read mail value.", databaseError.toException());

                mailListener.onFailed(databaseError);
            }

        });
    }

    public void sendMail(Mail mail) {
        FirebaseDatabase mail_db = FirebaseDatabase.getInstance();
        DatabaseReference mail_ref = mail_db.getReference("mail");

        String key = mail_ref.push().getKey();
        mail.setIdMail(key);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(mail.getIdMail(), mail);
        mail_ref.updateChildren(childUpdates);
    }
}
