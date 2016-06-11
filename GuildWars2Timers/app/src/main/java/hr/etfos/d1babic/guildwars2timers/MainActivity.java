package hr.etfos.d1babic.guildwars2timers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import hr.etfos.d1babic.guildwars2timers.BackgroundService.BackgroundTask;
import hr.etfos.d1babic.guildwars2timers.Database_Handler.DBHelper;
import hr.etfos.d1babic.guildwars2timers.Subs.Subscriptions;
import hr.etfos.d1babic.guildwars2timers.Timers.TimerList;

public class MainActivity extends AppCompatActivity {

    private ActionBar mActionBar;

    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    private TabLayout tabLayout;
    private DBHelper mDBHelper;

    private Intent backgroundIntent;

    private PowerManager powerManager;
    PowerManager.WakeLock wakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initTabLayout();
        setupViewPager();
        initAdapter();
        initDatabase();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(wakeLock != null){
            stopService(backgroundIntent);
            wakeLock.release();
        }

    }

    private void initDatabase() {
        mDBHelper = new DBHelper(this);
        //Function runnable one time only, preload database
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {

            mDBHelper.populateTable();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.apply();
        }
        mDBHelper.close();
    }

    private void initTabLayout() {
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }

    private void initToolbar() {
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
            mActionBar.setDisplayUseLogoEnabled(true);
        }
    }

    private void initAdapter() {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TimerList(), "Timer List");
        adapter.addFragment(new Subscriptions(), "Subscriptions");

        viewPager.setAdapter(adapter);
    }

    private void setupViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            viewPager.setOffscreenPageLimit(2);
        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
        
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "WakeLock");
        wakeLock.acquire();
        backgroundIntent = new Intent(this, BackgroundTask.class);
        backgroundIntent.setData(Uri.parse("Run in background"));
        this.startService(backgroundIntent);
    }
}
