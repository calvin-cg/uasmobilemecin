package umn.ac.mecinan.model;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import umn.ac.mecinan.R;
import umn.ac.mecinan.activity.MainActivity;
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

    public void makeListener(final View view, Button btnLeft, Button btnRight, int status, final String idProject, final User userEmployee, final Project project) {
        final String TAG = "button_click";

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
                    mail.setMailReceivedDate(0);
                    mail.setProjectName(project.getTitle());
                    mail.setMailRecipient(project.getUserClient().getId());
                    mail.setMailSender(project.getUserEmployee().getId());
                    mail.setIdProject(project.getIdProject());
                    mail.setProjectName(project.getTitle());
                    mail.setProjectField(project.getIdField());
                    mail.setProjectCategory(project.getIdCategory());
                    int new_status = 1;

                    Log.d(TAG, "Accepted");
                    showCustomDialog(view, mail, project, idProject, userEmployee, new_status);
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
                    mail.setMailReceivedDate(0);
                    mail.setProjectName(project.getTitle());
                    mail.setMailRecipient(project.getUserClient().getId());
                    mail.setMailSender(project.getUserEmployee().getId());
                    mail.setIdProject(project.getIdProject());
                    mail.setProjectName(project.getTitle());
                    mail.setProjectField(project.getIdField());
                    mail.setProjectCategory(project.getIdCategory());
                    int new_status = -1;

                    Log.d(TAG, "Rejected");
                    showCustomDialog(view, mail, project, idProject, userEmployee, new_status);
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
                    mail.setMailReceivedDate(0);
                    mail.setProjectName(project.getTitle());
                    mail.setMailRecipient(project.getUserEmployee().getId());
                    mail.setMailSender(project.getUserClient().getId());
                    mail.setIdProject(project.getIdProject());
                    mail.setProjectName(project.getTitle());
                    mail.setProjectField(project.getIdField());
                    mail.setProjectCategory(project.getIdCategory());
                    int new_status = 2;

                    Log.d(TAG, "Paid");
                    showCustomDialog(view, mail, project, idProject, userEmployee, new_status);
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
                    mail.setMailReceivedDate(0);
                    mail.setProjectName(project.getTitle());
                    mail.setMailRecipient(project.getUserClient().getId());
                    mail.setMailSender(project.getUserEmployee().getId());
                    mail.setIdProject(project.getIdProject());
                    mail.setProjectName(project.getTitle());
                    mail.setProjectField(project.getIdField());
                    mail.setProjectCategory(project.getIdCategory());
                    int new_status = 3;

                    Log.d(TAG, "Finished");
                    showCustomDialog(view, mail, project, idProject, userEmployee, new_status);
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
                    mail.setMailReceivedDate(0);
                    mail.setProjectName(project.getTitle());
                    mail.setMailRecipient(project.getUserEmployee().getId());
                    mail.setMailSender(project.getUserClient().getId());
                    mail.setIdProject(project.getIdProject());
                    mail.setProjectName(project.getTitle());
                    mail.setProjectField(project.getIdField());
                    mail.setProjectCategory(project.getIdCategory());
                    int new_status = 4;

                    Log.d(TAG, "Confirmed");
                    showCustomDialog(view, mail, project, idProject, userEmployee, new_status);
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
                    mail.setMailReceivedDate(0);
                    mail.setProjectName(project.getTitle());
                    mail.setMailRecipient(project.getUserEmployee().getId());
                    mail.setMailSender(project.getUserClient().getId());
                    mail.setIdProject(project.getIdProject());
                    mail.setProjectName(project.getTitle());
                    mail.setProjectField(project.getIdField());
                    mail.setProjectCategory(project.getIdCategory());
                    int new_status = -4;

                    Log.d(TAG, "Revision");
                    showCustomDialog(view, mail, project, idProject, userEmployee, new_status);
                }
            });
        }
    }


    float stars;
    public void showCustomDialog(View view, final Mail mail, final Project project, final String idProject, final User userEmployee, final int new_status) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseUser curr_user = auth.getCurrentUser();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference projectRef = db.getReference("project");
        final DatabaseReference userRef = db.getReference("user");

        final Map<String, Object> newProjectStatus = new HashMap<>();
        final Map<String, Object> newProjectRating = new HashMap<>();
        final Map<String, Object> newUserEmployeeRating = new HashMap<>();
        final Map<String, Object> newUserCompletedProject = new HashMap<>();


        /**
         * Preparing the Inflater with context from passed view (from Project View Adapter)
         */
        LayoutInflater layoutInflater = LayoutInflater.from(view.getContext());


        /**
         * Inflating into new view
         * (showing it to screen according which button is clicked)
         */
        final View new_view;

        if(new_status == 1) {
            new_view = layoutInflater.inflate(R.layout.custom_dialog_accept, null);
        } else if(new_status == -1) {
            new_view = layoutInflater.inflate(R.layout.custom_dialog_reject, null);
        } else if(new_status == 2) {
            new_view = layoutInflater.inflate(R.layout.custom_dialog_pay, null);
        } else if(new_status == 3) {
            new_view = layoutInflater.inflate(R.layout.custom_dialog_finish, null);
        } else if(new_status == 4) {
            new_view = layoutInflater.inflate(R.layout.custom_dialog_confirm, null);
        } else {
            /** For Now the Default Value is -4 */
            new_view = layoutInflater.inflate(R.layout.custom_dialog_revision, null);
        }

        Button btn_positive = (Button) new_view.findViewById(R.id.btn_positive);
        Button btn_negative = (Button) new_view.findViewById(R.id.btn_negative);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(new_view);

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();


        /**
         * Rating Listener if status == 4
         */
        if(new_status == 4) {
            Log.d("rating_bar", "setting");

            stars = 0;
            final RatingBar rb_confirm_project = new_view.findViewById(R.id.rb_confirm_project);

            rb_confirm_project.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        float touchPositionX = event.getX();
                        float width = rb_confirm_project.getWidth();
                        float starsf = (touchPositionX / width) * 5.0f;
                        stars = (int) starsf + 1;
                        rb_confirm_project.setRating(stars);

                        v.setPressed(false);
                    }
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.setPressed(true);
                    }

                    if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                        v.setPressed(false);
                    }

                    return true;
                }
            });
        }


        /**
         * Button Positive Listener
         */
        btn_positive.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mail.sendMail(mail);
                newProjectStatus.put("/" + idProject + "/status", new_status);
                projectRef.updateChildren(newProjectStatus);

                if(new_status == 4) {
                    int completed_project = userEmployee.getTotalProjectCompleted();
                    float rating_employee = userEmployee.getRatingEmployee();

                    completed_project++;
                    rating_employee = rating_employee + stars;

                    newProjectRating.put("/" + idProject + "/rating", stars);
                    newUserCompletedProject.put("/" + userEmployee.getId() + "/totalProjectCompleted", completed_project);
                    newUserEmployeeRating.put("/" + userEmployee.getId() + "/ratingEmployee", rating_employee);

                    projectRef.updateChildren(newProjectRating);
                    userRef.updateChildren(newUserCompletedProject);
                    userRef.updateChildren(newUserEmployeeRating);
                }

                alertDialog.dismiss();

                Intent intent = new Intent(v.getContext(), MainActivity.class);
                v.getContext().startActivity(intent);
            }
        });


        /**
         * Button Negative Listener
         */
        btn_negative.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}
