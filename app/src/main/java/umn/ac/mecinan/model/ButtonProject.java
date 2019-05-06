package umn.ac.mecinan.model;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ButtonProject {
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

    public void makeListener(Button btnLeft, Button btnRight, int status, final String idProject) {
        final String TAG = "button_click";

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference projectRef = db.getReference("project");

        final Map<String, Object> newProjectStatus = new HashMap<>();

        if(status == 0) {
            btnLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newProjectStatus.put("/" + idProject + "/status", 1);

                    projectRef.updateChildren(newProjectStatus);
                    Log.d(TAG, "Accepted");
                }
            });

            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                    newProjectStatus.put("/" + idProject + "/status", 4);

                    projectRef.updateChildren(newProjectStatus);
                    Log.d(TAG, "Confirmed");
                }
            });

            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newProjectStatus.put("/" + idProject + "/status", -4);

                    projectRef.updateChildren(newProjectStatus);
                    Log.d(TAG, "Revision");
                }
            });
        }
    }
}
