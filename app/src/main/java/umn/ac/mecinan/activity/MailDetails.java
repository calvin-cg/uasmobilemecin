package umn.ac.mecinan.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import umn.ac.mecinan.R;

public class MailDetails extends AppCompatActivity {

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference ref = db.getReference("mail");
    final Map<String, Object> new_mail_read_status = new HashMap<>();

    ImageView iv_mail_icon;
    TextView tvmd_category, tvmd_project_field, tvmd_project_category, tvmd_project_name, tvmd_date, tvmd_title, tvmd_content;
    Button btn_back;

    /**
     * DECLARATION - BOTTOM NAVIGATION
     */
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_details);


        iv_mail_icon = findViewById(R.id.imageView);
        tvmd_category = findViewById(R.id.tv_mail_details_category);
        tvmd_project_field = findViewById(R.id.tv_mail_details_project_field);
        tvmd_project_category = findViewById(R.id.tv_mail_details_project_category);
        tvmd_project_name = findViewById(R.id.tv_mail_details_project_name);
        tvmd_date = findViewById(R.id.tv_mail_details_date);
        tvmd_title = findViewById(R.id.tv_mail_details_title);
        tvmd_content = findViewById(R.id.tv_mail_details_content);
        btn_back = findViewById(R.id.btn_back);


        /** Get User Data from Bundle **/
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras != null) {
            final String md_id_mail = extras.getString("mail_details_id_mail");
            final String md_category = extras.getString("mail_details_category");
            final String md_project_field = extras.getString("mail_details_project_field");
            final String md_project_category = extras.getString("mail_details_project_category");
            final String md_project_name = extras.getString("mail_details_project_name");
            final String md_date = extras.getString("mail_details_date");
            final String md_title = extras.getString("mail_details_title");
            final String md_content = extras.getString("mail_details_content");

            if(md_category.equals("Work")) {
                iv_mail_icon.setImageDrawable(getResources().getDrawable(R.mipmap.work_orange_256));
            } else {
                iv_mail_icon.setImageDrawable(getResources().getDrawable(R.mipmap.mail_closed_orange_256));
            }

            tvmd_category.setText(md_category);
            tvmd_project_field.setText(md_project_field);
            tvmd_project_category.setText(md_project_category);
            tvmd_project_name.setText(md_project_name);
            tvmd_date.setText(md_date);
            tvmd_title.setText(md_title);
            tvmd_content.setText(md_content);

            new_mail_read_status.put("/" + md_id_mail + "/mailIsRead", true);
            ref.updateChildren(new_mail_read_status);
        } else {
            finish();
        }


        /**
         * BUTTON BACK
         */
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        /**
         * BOTTOM NAVIGATION
         */
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        /** Ubah index untuk aktivasi button page sesuai index */
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_home:
                        Intent iHome = new Intent(MailDetails.this, MainActivity.class);
                        startActivity(iHome);
                        break;
                    case R.id.navigation_browse:
                        Intent iBrowse = new Intent(MailDetails.this, SearchActivity.class);
                        startActivity(iBrowse);
                        break;
                    case R.id.navigation_faq:
                        Intent iFAQ = new Intent(MailDetails.this, FAQActivity.class);
                        startActivity(iFAQ);
                        break;
                    case R.id.navigation_inbox:
                        Intent iInbox = new Intent(MailDetails.this, InboxActivity.class);
                        startActivity(iInbox);
                        break;
                    case R.id.navigation_profile:
                        Intent iProfile = new Intent(MailDetails.this, ProfileActivity.class);
                        startActivity(iProfile);
                        break;
                }
                return false;
            }
        });
    }
}
