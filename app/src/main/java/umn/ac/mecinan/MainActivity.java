package umn.ac.mecinan;

import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    TabItem tabOnGoingProjects;
    TabItem tabPastProjects;
    ViewPager viewPager;

    MainProjectsPageAdapter pageAdapter;

    RecyclerView recyclerView;
    ProjectsViewAdapter projectsViewAdapter;
    List<Projects> projectsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("EZRA", "Start Main");

        //Buat Fragment Ongoing - Past
        tabLayout = findViewById(R.id.tabLayout);
        tabOnGoingProjects = findViewById(R.id.tabOnGoingProjects);
        tabPastProjects = findViewById(R.id.tabPastProjects);
        viewPager = findViewById(R.id.view_pager);

        pageAdapter = new MainProjectsPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Buat Project List
        Log.d("EZRA", "log 1");
        projectsList = new ArrayList<>();

        Log.d("EZRA", "log 2");
        recyclerView = findViewById(R.id.ongoingrec);
        Log.d("EZRA", "log 3");

        projectsList.add(
                new Projects(
                        001,
                        "Website Mecan",
                        "Ezra",
                        "Calvin",
                        "IT",
                        "Website",
                        "Ongoing",
                        "Buat website sederhana untuk tugas Technopreneurship, cuma butuh bikin macam tokopedia. Gampang kan?"
                )
        );

        projectsList.add(
                new Projects(
                        002,
                        "Logo Mecan",
                        "Devi",
                        "Calvin",
                        "Design",
                        "Branding",
                        "Ongoing",
                        "Buat logo untuk startup Mecanan, ga ribet kok."
                )
        );

        Log.d("EZRA", "log project list - add to adapter . . . 1");
        projectsViewAdapter = new ProjectsViewAdapter(this, projectsList);
        Log.d("EZRA", "log project list - add to adapter . . . 2");
        //recyclerView.setAdapter(projectsViewAdapter);
        Log.d("EZRA", "log project list - added to adapter");

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1){

                }
                else if (tab.getPosition() == 2){

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Log.d("EZRA", "End Main");
    }
}
