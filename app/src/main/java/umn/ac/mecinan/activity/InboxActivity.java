package umn.ac.mecinan.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import umn.ac.mecinan.R;
import umn.ac.mecinan.adapter.MailAdapter;
import umn.ac.mecinan.adapter.ProjectsViewAdapter;
import umn.ac.mecinan.listener.OnGetMailListener;
import umn.ac.mecinan.model.ButtonProject;
import umn.ac.mecinan.model.Mail;
import umn.ac.mecinan.model.Project;

public class InboxActivity extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser curr_user = auth.getCurrentUser();

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference ref = db.getReference("mail");

    /**
     * DECLARATION - BOTTOM NAVIGATION
     */
    BottomNavigationView bottomNavigationView;

    /**
     * BELOM BIKIN MAIL-MAIL AN
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        /**
         * Logout Broadcast Receiver
         */
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("umn.ac.mecinan.ACTION_LOGOUT");
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("onReceive","Logout in progress");
                finish();
            }
        }, intentFilter);


        /**
         * Retrieve Inbox
         */
        final Mail mail = new Mail();
        mail.retrieveMail(new OnGetMailListener() {
            final String TAG = "retrieve_mail";
            TextView tvEmpty = findViewById(R.id.tvEmptyMail);
            ProgressBar pBar = findViewById(R.id.pBar);

            MailAdapter mail_adapter;
            RecyclerView recyclerView = findViewById(R.id.rv_inbox_mail);

            List<Mail> all_mail = new ArrayList<>();
            List<Mail> mail_list = new ArrayList<>();
            Boolean first_init = true;

            @Override
            public void onStart() {
                tvEmpty.setText(getString(R.string.loading_ongoing_project));
                pBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onDataChange(Mail mail) {
                String mail_recipient = mail.getMailRecipient();
                String curr_user_uid = curr_user.getUid();

                if(mail_recipient.equals(curr_user_uid) || mail_recipient.equals("ALL")) {
                    Log.d(TAG, "adding new mail list");
                    tvEmpty.setVisibility(View.GONE);
                    pBar.setVisibility(View.GONE);

                    mail_list.add(mail);
                }
            }

            @Override
            public void onSuccess() {
                Log.d(TAG, "callback success retrieve mail");

                List<Mail> mail_list_update = new ArrayList<>(mail_list);

                /**
                 * Set to recycler view from mail_list_update
                 */
                if(first_init) {
                    mail_adapter = new MailAdapter(InboxActivity.this, mail_list_update);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(mail_adapter);

                    mail_list.clear();

                    first_init = false;
                } else {
                    mail_adapter.updateMailList(mail_list_update);

                    mail_list.clear();
                }

                if(mail_adapter.getItemCount() <= 0) {
                    tvEmpty.setText(getString(R.string.empty_mail));
                    tvEmpty.setVisibility(View.VISIBLE);

                    pBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Log.d(TAG, "dbError: " + databaseError.getMessage());
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
                        Intent iHome = new Intent(InboxActivity.this, MainActivity.class);
                        startActivity(iHome);
                        break;
                    case R.id.navigation_browse:
                        Intent iBrowse = new Intent(InboxActivity.this, SearchActivity.class);
                        startActivity(iBrowse);
                        break;
                    case R.id.navigation_faq:
                        Intent iFAQ = new Intent(InboxActivity.this, FAQActivity.class);
                        startActivity(iFAQ);
                        break;
                    case R.id.navigation_inbox:
                        /*Intent iInbox = new Intent(FAQActivity.this, InboxActivity.class);
                        startActivity(iInbox);*/
                        Toast.makeText(getApplicationContext(), "Inbox Selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_profile:
                        Intent iProfile = new Intent(InboxActivity.this, ProfileActivity.class);
                        startActivity(iProfile);
                        break;
                }
                return false;
            }
        });
    }
}
