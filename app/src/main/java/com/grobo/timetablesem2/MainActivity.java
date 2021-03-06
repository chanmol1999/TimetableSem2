package com.grobo.timetablesem2;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity{

    String branchPreference;
    private int dayOfWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setElevation(0);
    //    actionBar.setIcon(R.drawable.icon);
        actionBar.setTitle("TIMETABLE");

        Calendar calendar = Calendar.getInstance();
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        branchPreference = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("branchPreference", "");

        if (branchPreference == "") {
            startActivity(new Intent(MainActivity.this, FirstActivity.class));
        } else {
            showTimetable();
        }
    }

    private void showTimetable(){
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        DaysFragmentAdapter pagerAdapter = new DaysFragmentAdapter(MainActivity.this, getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(dayOfWeek - 2);

    }

    @Override
    protected void onStart() {
        showTimetable();
        super.onStart();
    }

    @Override
    protected void onResume() {
        showTimetable();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.update){
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("branchPreference", "").apply();
            startActivity(new Intent(MainActivity.this, FirstActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();

        super.onBackPressed();
    }
}
