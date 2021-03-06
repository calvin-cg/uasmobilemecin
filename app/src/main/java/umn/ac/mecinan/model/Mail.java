package umn.ac.mecinan.model;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import umn.ac.mecinan.listener.OnGetMailListener;

public class Mail {

    private String idMail;
    private int mailType;    /** Mail Type is the same as Project Status to determine mail icon */
    private boolean mailIsRead;
    private String mailCategory, mailTitle, mailContent;
    private long mailReceivedDate;
    private String mailRecipient, mailSender;  /** mailRecipient & mailSender will be filled with corresponding user data */
    private User userRecipient, userSender;
    private String idProject, projectName, projectField, projectCategory;

    public Mail() { }
    public Mail(String idMail, int mailType, boolean mailIsRead, String mailCategory,
                String mailTitle, String mailContent, long mailReceivedDate, String mailRecipient,
                String mailSender, String idProject, String projectName, String projectField, String projectCategory) {
        this.idMail = idMail;
        this.mailType = mailType;
        this.mailIsRead = mailIsRead;
        this.mailCategory = mailCategory;
        this.mailTitle = mailTitle;
        this.mailContent = mailContent;
        this.mailReceivedDate = mailReceivedDate;
        this.mailRecipient = mailRecipient;
        this.mailSender = mailSender;
        this.userRecipient = null;
        this.userSender = null;
        this.idProject = idProject;
        this.projectName = projectName;
        this.projectField = projectField;
        this.projectCategory = projectCategory;
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

    public String getMailCategory() { return mailCategory; }
    public void setMailCategory(String mailCategory) { this.mailCategory = mailCategory; }

    public String getMailTitle() { return mailTitle; }
    public void setMailTitle(String mailTitle) { this.mailTitle = mailTitle; }

    public String getMailContent() { return mailContent; }
    public void setMailContent(String mailContent) { this.mailContent = mailContent; }

    public long getMailReceivedDate() { return mailReceivedDate; }
    public void setMailReceivedDate(long mailReceivedDate) { this.mailReceivedDate = mailReceivedDate; }

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

    public String getIdProject() { return idProject; }
    public void setIdProject(String idProject) {
        this.idProject = idProject;
    }

    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }

    public String getProjectField() {
        return projectField;
    }
    public void setProjectField(String projectField) {
        this.projectField = projectField;
    }

    public String getProjectCategory() {
        return projectCategory;
    }
    public void setProjectCategory(String projectCategory) {
        this.projectCategory = projectCategory;
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

        Date date;
        String strDateFormat, formattedDate;
        DateFormat dateFormat;

        date = new Date();
        strDateFormat = "dd-MMMM-yyyy kk:mm:ss";
        dateFormat = new SimpleDateFormat(strDateFormat);
        formattedDate = dateFormat.format(date);
        mail.setMailReceivedDate(date.getTime());

        String key = mail_ref.push().getKey();
        mail.setIdMail(key);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(mail.getIdMail(), mail);
        mail_ref.updateChildren(childUpdates);
    }
}
