package umn.ac.mecinan.model;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import umn.ac.mecinan.R;
import umn.ac.mecinan.adapter.ProjectsViewAdapter;
import umn.ac.mecinan.fragment.MainOngoingFragment;

public class ButtonProject{
    private int viewBtnLeft, viewBtnRight;
    private String stringBtnLeft, stringBtnRight;

    public ButtonProject() {

    }
    public ButtonProject(int viewBtnLeft, int viewBtnRight, String stringBtnLeft, String stringBtnRight) {
        this.viewBtnLeft = viewBtnLeft;
        this.viewBtnRight = viewBtnRight;
        this.stringBtnLeft = stringBtnLeft;
        this.stringBtnRight = stringBtnRight;
    }

    public int getViewBtnLeft() {
        return viewBtnLeft;
    }
    public void setViewBtnLeft(int viewBtnLeft) {
        this.viewBtnLeft = viewBtnLeft;
    }

    public int getViewBtnRight() {
        return viewBtnRight;
    }
    public void setViewBtnRight(int viewBtnRight) {
        this.viewBtnRight = viewBtnRight;
    }

    public String getStringBtnLeft() {
        return stringBtnLeft;
    }
    public void setStringBtnLeft(String stringBtnLeft) {
        this.stringBtnLeft = stringBtnLeft;
    }

    public String getStringBtnRight() {
        return stringBtnRight;
    }
    public void setStringBtnRight(String stringBtnRight) {
        this.stringBtnRight = stringBtnRight;
    }

    public ButtonProject makeButton(int status) {
        ButtonProject buttonProject = new ButtonProject();

        switch(status){
            case 0:
                buttonProject.setViewBtnLeft(View.VISIBLE);
                buttonProject.setStringBtnLeft("Accept");
                buttonProject.setViewBtnRight(View.VISIBLE);
                buttonProject.setStringBtnRight("Reject");
                break;

            case 1:
                buttonProject.setViewBtnLeft(View.VISIBLE);
                buttonProject.setStringBtnLeft("Pay");
                buttonProject.setViewBtnRight(View.GONE);
                buttonProject.setStringBtnRight("");
                break;

            case 2:
                buttonProject.setViewBtnLeft(View.VISIBLE);
                buttonProject.setStringBtnLeft("Finish");
                buttonProject.setViewBtnRight(View.GONE);
                buttonProject.setStringBtnRight("");
                break;

            case 3:
                buttonProject.setViewBtnLeft(View.VISIBLE);
                buttonProject.setStringBtnLeft("Confirm");
                buttonProject.setViewBtnRight(View.VISIBLE);
                buttonProject.setStringBtnRight("Revision");
                break;

            case 4:
                buttonProject.setViewBtnLeft(View.GONE);
                buttonProject.setStringBtnLeft("");
                buttonProject.setViewBtnRight(View.GONE);
                buttonProject.setStringBtnRight("");
                break;
        }

        return buttonProject;
    }

    public void makeListener(Button btnLeft, Button btnRight, int status, final String idProject, final Project project) {
        final String TAG = "button_click";

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference projectRef = db.getReference("project");

        final Map<String, Object> newProjectStatus = new HashMap<>();

        if(status == 0) {
            btnLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /**
                     * Set variable for mail
                     */
                    Mail mail = new Mail();
                    mail.setMailType(1);
                    mail.setMailIsRead(false);
                    mail.setMailCategory("Work");
                    mail.setMailTitle("Project Accepted");
                    mail.setMailContent(project.getUserEmployee().getUsername() + " Has accepted your project proposal. Please continue with your payment to begin this project.");
                    mail.setMailReceivedDate(null);
                    mail.setProjectName(project.getTitle());
                    mail.setMailRecipient(project.getUserClient().getId());
                    mail.setMailSender(project.getUserEmployee().getId());
                    mail.setIdProject(project.getIdProject());
                    mail.setProjectName(project.getTitle());
                    mail.setProjectField(project.getIdField());
                    mail.setProjectCategory(project.getIdCategory());
                    mail.sendMail(mail);

                    newProjectStatus.put("/" + idProject + "/status", 1);

                    projectRef.updateChildren(newProjectStatus);
                    Log.d(TAG, "Accepted");

                    //showCustomDialog();
                }
            });

            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /**
                     * Set variable for mail
                     */
                    Mail mail = new Mail();
                    mail.setMailType(-1);
                    mail.setMailIsRead(false);
                    mail.setMailCategory("Work");
                    mail.setMailTitle("Project Rejected");
                    mail.setMailContent(project.getUserEmployee().getUsername() + " Has reject your project proposal.");
                    mail.setMailReceivedDate(null);
                    mail.setProjectName(project.getTitle());
                    mail.setMailRecipient(project.getUserClient().getId());
                    mail.setMailSender(project.getUserEmployee().getId());
                    mail.setIdProject(project.getIdProject());
                    mail.setProjectName(project.getTitle());
                    mail.setProjectField(project.getIdField());
                    mail.setProjectCategory(project.getIdCategory());
                    mail.sendMail(mail);

                    newProjectStatus.put("/" + idProject + "/status", -1);

                    projectRef.updateChildren(newProjectStatus);
                    Log.d(TAG, "Rejected");
                }
            });
        }

        else if(status == 1) {
            btnLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /**
                     * Set variable for mail
                     */
                    Mail mail = new Mail();
                    mail.setMailType(2);
                    mail.setMailIsRead(false);
                    mail.setMailCategory("Work");
                    mail.setMailTitle("Project Paid");
                    mail.setMailContent(project.getUserClient().getUsername() + " Has paid this project. You may start to work on this project.");
                    mail.setMailReceivedDate(null);
                    mail.setProjectName(project.getTitle());
                    mail.setMailRecipient(project.getUserEmployee().getId());
                    mail.setMailSender(project.getUserClient().getId());
                    mail.setIdProject(project.getIdProject());
                    mail.setProjectName(project.getTitle());
                    mail.setProjectField(project.getIdField());
                    mail.setProjectCategory(project.getIdCategory());
                    mail.sendMail(mail);

                    newProjectStatus.put("/" + idProject + "/status", 2);

                    projectRef.updateChildren(newProjectStatus);
                    Log.d(TAG, "Paid");
                }
            });
        }

        else if(status == 2) {
            btnLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /**
                     * Set variable for mail
                     */
                    Mail mail = new Mail();
                    mail.setMailType(3);
                    mail.setMailIsRead(false);
                    mail.setMailCategory("Work");
                    mail.setMailTitle("Project Finished");
                    mail.setMailContent(project.getUserEmployee().getUsername() + " Has finished this project. You may give a review within n-days.");
                    mail.setMailReceivedDate(null);
                    mail.setProjectName(project.getTitle());
                    mail.setMailRecipient(project.getUserClient().getId());
                    mail.setMailSender(project.getUserEmployee().getId());
                    mail.setIdProject(project.getIdProject());
                    mail.setProjectName(project.getTitle());
                    mail.setProjectField(project.getIdField());
                    mail.setProjectCategory(project.getIdCategory());
                    mail.sendMail(mail);

                    newProjectStatus.put("/" + idProject + "/status", 3);

                    projectRef.updateChildren(newProjectStatus);
                    Log.d(TAG, "Finished");
                }
            });
        }

        else if(status == 3) {
            btnLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /**
                     * Set variable for mail
                     */
                    Mail mail = new Mail();
                    mail.setMailType(4);
                    mail.setMailIsRead(false);
                    mail.setMailCategory("Work");
                    mail.setMailTitle("Congratulations! All has been done");
                    mail.setMailContent("Congratulations! " + project.getUserClient().getUsername() + " happy with your work. See you in the next project ;)");
                    mail.setMailReceivedDate(null);
                    mail.setProjectName(project.getTitle());
                    mail.setMailRecipient(project.getUserEmployee().getId());
                    mail.setMailSender(project.getUserClient().getId());
                    mail.setIdProject(project.getIdProject());
                    mail.setProjectName(project.getTitle());
                    mail.setProjectField(project.getIdField());
                    mail.setProjectCategory(project.getIdCategory());
                    mail.sendMail(mail);

                    newProjectStatus.put("/" + idProject + "/status", 4);

                    projectRef.updateChildren(newProjectStatus);
                    Log.d(TAG, "Confirmed");

                }
            });

            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /**
                     * Set variable for mail
                     */
                    Mail mail = new Mail();
                    mail.setMailType(4);
                    mail.setMailIsRead(false);
                    mail.setMailCategory("Work");
                    mail.setMailTitle("Complain");
                    mail.setMailContent("I'm really sorry to say this, but " + project.getUserClient().getUsername() + " has some review with your work.");
                    mail.setMailReceivedDate(null);
                    mail.setProjectName(project.getTitle());
                    mail.setMailRecipient(project.getUserEmployee().getId());
                    mail.setMailSender(project.getUserClient().getId());
                    mail.setIdProject(project.getIdProject());
                    mail.setProjectName(project.getTitle());
                    mail.setProjectField(project.getIdField());
                    mail.setProjectCategory(project.getIdCategory());
                    mail.sendMail(mail);

                    newProjectStatus.put("/" + idProject + "/status", -4);

                    projectRef.updateChildren(newProjectStatus);
                    Log.d(TAG, "Revision");
                }
            });
        }
    }

    /*private void showCustomDialog() {
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, viewGroup, false);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }*/

}
